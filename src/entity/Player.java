package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp,KeyHandler keyH)
    {
        screenX=gp.screenWidth/2-(gp.tileSize)/2;
        screenY=gp.screenHeight/2-(gp.tileSize)/2;

        this.gp=gp;
        this.keyH=keyH;
        setDefaultValue();
        getPlayerImage();
    }
    public void setDefaultValue()
    {
        worldX=gp.tileSize *23;
        worldY=gp.tileSize * 21;
        speed=4;
        direction="down" ;
    }

    public void getPlayerImage()
    {
        try
        {
            up1= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_up_1.png"));
            up2= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_up_2.png"));
            down1= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_down_1.png"));
            down2= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_down_2.png"));
            left1= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_left_1.png"));
            left2= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_left_2.png"));
            right1= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_right_1.png"));
            right2= ImageIO.read(getClass ().getResourceAsStream("/Player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed==true || keyH.downPressed==true ||
                keyH.leftPressed==true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
                worldY -= speed;
            } else if (keyH.downPressed == true) {
                direction = "down";
                worldY += speed;
            } else if (keyH.leftPressed == true) {
                direction = "left";
                worldX -= speed;
            } else if (keyH.rightPressed == true) {
                direction = "right";
                worldX += speed;
            }
            spiteCounter++;
            if (spiteCounter > 12) {
                if (spiteNum == 1)
                    spiteNum = 2;
                else if (spiteNum == 2)
                    spiteNum = 1;
                spiteCounter = 0;
            }
        }


        }
    public void draw(Graphics2D g2)
    {
       // g2.setColor(Color.white);
       // g2.fillRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage image =null;

        switch (direction)
        {
            case "up" :
                if(spiteNum==1)
                  image=up1;
                else if(spiteNum==2)
                    image=up2;
                break;

            case "down":
                if(spiteNum==1)
                    image=down1;
                else if(spiteNum==2)
                    image=down2;
                break;

            case "left":
                if(spiteNum==1)
                    image=left1;
                else if(spiteNum==2)
                    image=left2;
                break;

            case "right":
                if(spiteNum==1)
                     image=right1;
                else if(spiteNum==2)
                    image=right2;
                break;
        }
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

    }

}
