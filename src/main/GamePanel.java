package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;





public class GamePanel extends JPanel implements Runnable
{
    //screen setting
    final int orginalTileSize=16; //16x16 tile
    final int scale=3;

   public final int tileSize = orginalTileSize * scale ;
   public final int maxScreenCol = 16 ;
   public final int maxScreenRow= 12 ;
   public final int screenWidth = tileSize *maxScreenCol;
   public final int screenHeight= tileSize * maxScreenRow ;

   //World setting
    public final int maxWorldCol=50;
    public int maxWorldRow=50;
    public int maxWorldWidth=tileSize * maxWorldCol;
    public int maxWorldHeight=tileSize * maxWorldRow;

    //FPS
    int FPS=60;
    TileManager tileM=new TileManager(this);
    KeyHandler keyH= new KeyHandler();
    Thread gamethread;
    public CollisionChecker cChecker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);
    public Player player=new Player(this , keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        aSetter.setObject();
    }

    public void startGameThread()
    {
        gamethread = new Thread(this);
        gamethread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime=System.nanoTime()+ drawInterval;
        while(gamethread != null)
        {
            long currentTime=System.nanoTime();

            //update the information like char position
            update();
            //draw the screen with updated information
            repaint();

            try {
                double remainingTime=nextDrawTime - System.nanoTime();
                remainingTime=remainingTime/1000000;

                if(remainingTime < 0) remainingTime=0;

                Thread.sleep((long) remainingTime) ;

                nextDrawTime = nextDrawTime + drawInterval ;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update()
    {
       player.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g ;
        //tile
        tileM.draw(g2);

        //object
        for(int i=0;i<obj.length;i++)
        {
            if(obj[i] != null)
            {
                obj[i].draw(g2,this);
            }
        }

        //player
        player.draw(g2);
        g2.dispose();
    }
}
