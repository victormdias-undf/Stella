import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

    final int screenWidth = tileSz * maxScreenCol; //tamanho largura da screen 1440px
    final int screenHeight = tileSz * maxScreenRow; // tamanho altura da screen 864px

    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public int gameState = TITLE_STATE;

    Thread GameThread;
    JButton startButton;
    //BufferedImage backgroundImage;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //evitar piscar imagens atualizadas em tempo real
        this.setLayout(null);
        
        // Carregar imagem de background
        /*try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/background.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar background: " + e.getMessage());
        }*/
        
        startButton = new JButton("Começar o jogo");
        startButton.setBounds(screenWidth/2 - 75, screenHeight/2 + 50, 150, 40); //posicionar botão no meio
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
            // Adicionar os funcionamentos da gameplay
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        /* 
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        }*/

        if (gameState == TITLE_STATE) {
            // Desenhar o titulo
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String title = "STELLA";
            int titleWidth = g.getFontMetrics().stringWidth(title);
            g.drawString(title, screenWidth/2 - titleWidth/2, screenHeight/2 - 50);
            // Desenhar o subtitulo
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String subtitle = "Aperte o botão para começar";
            int subtitleWidth = g.getFontMetrics().stringWidth(subtitle);
            g.drawString(subtitle, screenWidth/2 - subtitleWidth/2, screenHeight/2);

        } else if (gameState == PLAY_STATE) {
            // adicionar depois os jogadores, sprites e etc
            
        }
    }
}
