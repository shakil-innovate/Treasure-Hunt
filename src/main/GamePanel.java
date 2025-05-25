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
    int previousGameState = -1;

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;

    //World setting
    public final int maxWorldCol=50;
    public int maxWorldRow=50;


    //FPS
    int FPS=60;                            //per seocond 60 time update
    TileManager tileM=new TileManager(this);
    KeyHandler keyH= new KeyHandler(this);


    //sound
    Sound music=new Sound();
    Sound se=new Sound();
    public CollisionChecker cChecker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);
    public UI ui=new UI(this);
    Thread gamethread;          //will run the game loop

    //Entity and object
    public Player player=new Player(this , keyH);
    public SuperObject obj[] = new SuperObject[10];



    public GamePanel()
    {
        gameState=titleState;
        playMusic(5);
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);         //help reduce flicering
        this.addKeyListener(keyH);             //capture input
        this.setFocusable(true);
    }

    public void setupGame()
    {
        aSetter.setObject();         //place object
       // playMusic(0);              //background music
    }

    public void startGameThread()       //implement runnable
    {
        gamethread = new Thread(this);
        gamethread.start();
    }

    @Override
    public void run() {               //core game loop
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

    public void update() {
        // Check if the game state has changed
        if (gameState != previousGameState) {
            if (gameState == titleState) {
                stopMusic();
                playMusic(5); // Title screen music
            } else if (gameState == playState) {
                stopMusic();
                playMusic(0); // Gameplay music
            }

            previousGameState = gameState; // Update the tracker
        }

        // Regular update
        if (gameState == playState) {
            player.update();
        }
    }

    public void paintComponent(Graphics g)         //automatically call by GUI
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g ;

        if(gameState==titleState){
            ui.drawTitleScreen(g2);

        }

        else {
            //tile
            tileM.draw(g2);

            //object
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            //player
            player.draw(g2);              //player character
            ui.draw(g2);                   //draw ui
            g2.dispose();
        }
    }

    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic()
    {
        music.stop();
    }
    public void playSE(int i)
    {
        se.setFile(i);
        se.play();
    }
}