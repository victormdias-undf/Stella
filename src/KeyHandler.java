import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean leftPressed, rightPressed, upPressed, downPressed;
    public int codeteste;
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        codeteste=code;
        if(code == KeyEvent.VK_A){
            this.leftPressed=true;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed=true;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed=true;
        }
        if(code== KeyEvent.VK_W){
            this.upPressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A){
            this.leftPressed=false;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed=false;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed=false;
        }
        if(code== KeyEvent.VK_W){
            this.upPressed=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
       
    }

    
}
