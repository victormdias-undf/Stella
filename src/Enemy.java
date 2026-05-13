import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Entity{
    GamePanel gp;
    Player p;
    public Enemy(GamePanel gp, Player p){
        this.p=p;
        worldX=gp.tileSz*3;
        this.gp = gp;
        worldY=gp.tileSz*4;
        
    }
    public void Draw(Graphics2D g2){
        g2.setColor(Color.red);
        worldX=400;
        worldY=100;
            g2.fillRect(worldX, worldY, gp.tileSz*2, gp.tileSz*2);
    }
}
