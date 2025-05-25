
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    GamePanel gp;

    public boolean upPressed,downPressed,leftPressed,rightPressed;

    public KeyHandler(GamePanel gp)
    {
        this.gp=gp;
    }
    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_SPACE) {
                gp.setupGame();
                gp.gameState = gp.playState;
            }
        }

        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_UP) upPressed = true;
            if (code == KeyEvent.VK_DOWN) downPressed = true;
            if (code == KeyEvent.VK_LEFT) leftPressed = true;
            if (code == KeyEvent.VK_RIGHT) rightPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code=e.getKeyCode();

        if(code == KeyEvent.VK_UP)
        {
            upPressed=false;
        }
        if(code == KeyEvent.VK_DOWN)
        {
            downPressed=false;
        }
        if(code == KeyEvent.VK_LEFT)
        {
            leftPressed=false;
        }
        if(code == KeyEvent.VK_RIGHT)
        {
            rightPressed=false ;
        }
    }
}
