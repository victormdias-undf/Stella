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

public class GamePanel extends JPanel implements Runnable{
    final int ogTileSz = 24; //tamanho original dos sprites do jogo
    final int scale = 3;
    final int tileSz = ogTileSz*scale; //tamanho a ser mostrado no painel
    final int maxScreenCol = 20;
    final int maxScreenRow = 12;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldCol * tileSz;
    public final int worldHeight = maxWorldRow * tileSz;


    final int screenWidth = tileSz * maxScreenCol; //tamanho largura da screen 1440px
    final int screenHeight = tileSz * maxScreenRow; // tamanho altura da screen 864px

    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public int gameState = TITLE_STATE;

    Thread GameThread;
    JButton startButton;
    
    KeyHandler key = new KeyHandler();
    BufferedImage backgroundImage;
    Player player = new Player(this, key);
    TileManager tileManager = new TileManager(this);
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //evitar piscar imagens atualizadas em tempo real
        this.setLayout(null);
        this.addKeyListener(key);
        this.setFocusable(true);
        
        // Carregar imagem de background
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("./res/menu.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar background: " + e.getMessage());
        }
        
        startButton = new JButton("Começar o jogo");
        startButton.setBounds(screenWidth/2 - 100, screenHeight/2 + 50, 200, 40); //posicionar botão no meio
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        this.add(startButton);
    }

    public void startGameThread(){
        GameThread = new Thread(this); //fazer o jogo ser capaz de correr em tempo real
        GameThread.start();
    }

    public void startGame() {
        //Remover o titulo para entrar na cena de jogar
        this.remove(startButton);
        this.revalidate();
        this.repaint();

        //Atualiza o estado do jogo
        gameState = PLAY_STATE;

        // Inicia a Thread
        if (GameThread == null) {
            startGameThread();
        }
    }

    @Override
    public void run() {
        while (GameThread != null) {
            // GameLoop pra acontecer os gráficos
            update();
            repaint();

            try {
                Thread.sleep(16); // adicionar um jogo 60FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();
            player.andar(); // Atualizar movimento do player

    
            // Adicionar os funcionamentos da gameplay
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;

        if (gameState == TITLE_STATE) {
            // Desenhar o titulo
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            // Desenhar o subtitulo
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String subtitle = "Aperte o botão para começar";
            int subtitleWidth = g.getFontMetrics().stringWidth(subtitle);
            g.drawString(subtitle, screenWidth/2 - subtitleWidth/2, screenHeight/2);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
            }

        } else if (gameState == PLAY_STATE) {
            tileManager.Draw(g2);
            player.Draw(g2);
            
            
        }
    }
    

    
    
}
