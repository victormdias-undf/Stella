package com.stella.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.stella.player.Player;
import com.stella.player.KeyHandler;
import com.stella.world.TileManager;
import com.stella.entities.superObject;
import com.stella.physics.CollisionChecker;
import com.stella.assets.AssetSetter;

/**
 * Painel principal do jogo.
 * Gerencia toda a lógica de jogo, renderização e loop do jogo.
 */
public class GamePanel extends JPanel implements Runnable{
    // Configurações de câmera e mundo
    final int ogTileSz = 24;           // Tamanho original dos sprites
    final int scale = 3;               // Escala para ampliação
    public final int tileSz = ogTileSz*scale; // Tamanho final dos tiles (72px)
    final int maxScreenCol = 20;       // Quantas colunas de tiles cabem na tela
    final int maxScreenRow = 12;       // Quantas linhas de tiles cabem na tela

    // Tamanho do mundo
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 12;
    public final int worldWidth = maxWorldCol * tileSz;
    public final int worldHeight = maxWorldRow * tileSz;

    // Tamanho da tela em pixels
    public final int screenWidth = tileSz * maxScreenCol;  // 1440px
    public final int screenHeight = tileSz * maxScreenRow; // 864px

    // Estados do jogo
    public static final int TITLE_STATE = 0;  // Tela de título
    public static final int PLAY_STATE = 1;   // Jogo em andamento
    public int gameState = TITLE_STATE;

    // Thread do jogo (para rodar o loop em paralelo)
    Thread GameThread;
    JButton startButton;
    
    // Componentes principais do jogo
    public KeyHandler key = new KeyHandler();
    BufferedImage backgroundImage;
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, key);
    AssetSetter aSetter = new AssetSetter(this);
    
    // Array de objetos do mundo (inimigos, itens, etc)
    public superObject obj[] = new superObject[5];

    // Posição da câmera (coordenada do mundo que aparece no canto superior-esquerdo)
    public int cameraX = 0;
    public int cameraY = 0;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Evita piscar com buffer duplo
        this.setLayout(null);
        this.addKeyListener(key);     // Adiciona o detector de teclado
        this.setFocusable(true);      // Permite receber eventos de teclado
        
        // Tenta carregar a imagem de fundo do menu
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/menu.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar background: " + e.getMessage());
        }
        
        // Cria e posiciona o botão de iniciar
        startButton = new JButton("Começar o jogo");
        startButton.setBounds(screenWidth/2 - 100, screenHeight/2 + 50, 200, 40);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        this.add(startButton);
    }

    /**
     * Cria e inicia a thread do jogo.
     */
    public void startGameThread(){
        GameThread = new Thread(this);
        GameThread.start();
    }

    /**
     * Inicia o jogo removendo a tela de título.
     */
    public void startGame() {
        // Remove o botão de iniciar
        this.remove(startButton);
        this.revalidate();
        this.repaint();

        // Muda para o estado de jogo
        gameState = PLAY_STATE;

        // Inicia a thread se não estiver rodando
        if (GameThread == null) {
            startGameThread();
        }
    }

    /**
     * Loop principal do jogo (roda 60 vezes por segundo).
     */
    @Override
    public void run() {
        while (GameThread != null) {
            // Atualiza a lógica do jogo
            update();
            
            // Redesenha a tela
            repaint();

            try {
                // Aguarda ~16ms para manter 60 FPS
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Atualiza a lógica do jogo a cada frame.
     */
    public void update() {
        if (gameState == PLAY_STATE) {
            // Atualiza o estado do jogador (colisão, medo)
            player.update();
            
            // Move o jogador baseado nas teclas pressionadas
            player.andar();
            
            // Ajusta a câmera para não sair dos limites do mapa.
            // cameraX/Y = coordenada do mundo que estará no canto superior-esquerdo da tela
            int maxCameraX = worldWidth - screenWidth;
            int maxCameraY = worldHeight - screenHeight;
            if (maxCameraX < 0) maxCameraX = 0;
            if (maxCameraY < 0) maxCameraY = 0;

            int cameraX = player.worldX - player.screenX;
            int cameraY = player.worldY - player.screenY;

            // Limita a câmera entre 0 e o máximo do mundo
            cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
            cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

            // Calcula onde o jogador ficará na tela com a câmera limitada
            player.screenX = player.worldX - cameraX;
            player.screenY = player.worldY - cameraY;

            // Guarda a posição da câmera no GamePanel para que o mundo use-a ao desenhar
            this.cameraX = cameraX;
            this.cameraY = cameraY;
        }
    }

    /**
     * Desenha na tela.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;

        if (gameState == TITLE_STATE) {
            // Desenha a tela de título
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            
            // Desenha o subtítulo
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String subtitle = "Aperte o botão para começar";
            int subtitleWidth = g.getFontMetrics().stringWidth(subtitle);
            g.drawString(subtitle, screenWidth/2 - subtitleWidth/2, screenHeight/2);
            
            // Desenha o background da tela de título
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
            }

        } else if (gameState == PLAY_STATE) {
            // Desenha o mapa
            tileManager.Draw(g2);
            
            // Desenha os objetos do mundo (inimigos, itens)
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }
            
            // Desenha o jogador
            player.Draw(g2);
        }
    }

    /**
     * Inicializa todos os objetos do jogo.
     */
    public void setupGame(){
        aSetter.setObject();
    }
}
