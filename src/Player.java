import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
        solidArea = new Rectangle(6,12, 12, 12);
        worldY=screenY;
        this.key = key;
    }
    public void update(){
        
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
    
    public void Draw(Graphics2D g2){
        g2.setColor(Color.PINK);
        g2.fillRect(screenX, screenY, gp.tileSz, gp.tileSz);
           
    }
}
