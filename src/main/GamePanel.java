package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    //screen setting
    final int oeiginalTileSize=16; //16x16 tile
    final int scale=3;

    final int tileSize = oeiginalTileSize * scale ;
    final int maxScreenCol = 16 ;
    final int maxScreenRow= 12 ;

    final int screenWidth = tileSize *maxScreenCol;
    final int screenHeight= tileSize * maxScreenRow ;

    Thread gamethread;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread()
    {
        gamethread = new Thread(this);
        gamethread.start();
    }

    @Override
    public void run() {

    }
}
