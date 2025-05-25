
package main;

import object.OBJ_Key;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import java.io.IOException;

public class UI {
    GamePanel gp;
    Font arial_40,arial_80B;
    Font arial_60;
    BufferedImage keyImage;
    public boolean messageOn=false;
    public String message="";
    int messageCounter=0;
    public boolean gameFinished=false;
    double playTime;
    DecimalFormat dFormat=new DecimalFormat("#0.00");
    BufferedImage titleImage;
    double bestTime = Double.MAX_VALUE;
    final String bestTimeFile = "best_time.txt";

    Rectangle playButton = new Rectangle();
    Rectangle bestTimeButton = new Rectangle();
    Rectangle exitButton = new Rectangle();

    public UI(GamePanel gp)
    {
        this.gp=gp;
        arial_40=new Font("Arial",Font.PLAIN,40);
        arial_60=new Font("Arial",Font.PLAIN,60);
        arial_80B=new Font("Arial",Font.BOLD,80);
        OBJ_Key key=new OBJ_Key();
        keyImage=key.image;


        try {
            titleImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/Resource/uiImage/title.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(bestTimeFile))) {
            String line = br.readLine();
            if (line != null) {
                bestTime = Double.parseDouble(line);
            }
        } catch (IOException e) {
            System.out.println("No previous best time found.");
        }
    }
    public void showMessage(String text)
    {
        message=text;
        messageOn=true;
    }

    public void drawTitleScreen(Graphics2D g2) {

        g2.drawImage(titleImage, 0, 0, gp.screenWidth, gp.screenHeight, null);

         g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.white);
        String text = "TREASURE HUNT";
        int x = getXforCenteredText(text, g2);
        int y = gp.tileSize * 3;
       // g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Press ENTER to Start";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);

        g2.setFont(new Font("Arial", Font.BOLD, 32));
        int buttonWidth = 240;
        int buttonHeight = 50;
        int centerX = gp.screenWidth / 2 - buttonWidth / 2;
        int startY = gp.tileSize * 5;

        // BEST TIME Button
       // bestTimeButton.setBounds(centerX, startY + 70, buttonWidth, buttonHeight);
       // g2.draw(bestTimeButton);
        g2.drawString("Best Time "+dFormat.format(bestTime), centerX, startY + 70 );

    }

    // Helper function
    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public void draw(Graphics2D g2)
    {
        if(gameFinished==true)
        {

            if (playTime < bestTime) {
                bestTime = playTime;
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(bestTimeFile))) {
                    bw.write(String.valueOf(bestTime));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String text;
            int textLength;

            int x;
            int y;

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            text="You find the TREASURE!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenHeight/2-(gp.tileSize*3);
            g2.drawString(text,x,y);

            text="Your Time is:"+ dFormat.format(playTime)+"!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenHeight/2+(gp.tileSize*4);
            g2.drawString(text,x,y);

            g2.drawString("Best Time is: " + dFormat.format(bestTime), x,y+48);

            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW) ;

            text="Congratulations!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenHeight/2+(gp.tileSize*2);
            g2.drawString(text,x,y);

            gp.gamethread=null;

        }
        else {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("x "+ gp.player.hasKey,74,65);

            //Time

            playTime+=(double)1/60;
            g2.drawString("Time:"+dFormat.format(playTime),gp.tileSize*11,65);

            //Message
            if(messageOn==true)
            {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize*5);

                messageCounter++;
                if(messageCounter >120 )
                {
                    messageCounter=0;
                    messageOn=false;
                }
            }
        }


    }

}
