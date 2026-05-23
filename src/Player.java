import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler key;

    public int screenX;
    public int screenY;
    public Player(GamePanel gp, KeyHandler key){
        this.gp = gp;
        screenX = gp.screenWidth/2-(gp.tileSz/2);
        screenY = gp.screenHeight/2-(gp.tileSz/2);
        worldX=screenX;
        worldY=screenY;
        this.key = key;
    }
    public void update(){
        this.Collide();
    }
    public void andar(){
        if(key.leftPressed){
            worldX-=3;
        }
        if(key.rightPressed){
            worldX+=3;
        }
        if(key.upPressed){
            worldY-=3;
        }
        if(key.downPressed){
            worldY+=3;
        }
    }
    public void Collide(){
        float distancia = gp.En.worldX-this.worldX-gp.tileSz;
        if(distancia<200 && distancia>=100){
            System.out.println("Barra de medo em 30%");
        }else if(distancia<100 && distancia>=50){
            System.out.println("Barra de medo em 50%");
        }else if(distancia<50 && distancia>0){
            System.out.println("Barra de medo em 80%");
        }else if(distancia<=0){
            System.out.println("Game Over");
        }
    }
    public void Draw(Graphics2D g2){
        g2.setColor(Color.PINK);
        g2.fillRect(screenX, screenY, gp.tileSz, gp.tileSz);
           
    }
}
