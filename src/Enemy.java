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
        int ScreenX = gp.player.worldX + gp.player.screenX;
        int ScreenY =gp.player.worldY + gp.player.screenY;
            g2.fillRect(ScreenX, ScreenY, gp.tileSz*2, gp.tileSz*2);
    }
}
