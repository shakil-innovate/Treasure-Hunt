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
    public int hasKey=0;


    public Player(GamePanel gp,KeyHandler keyH)
    {
        screenX=gp.screenWidth/2-(gp.tileSize)/2;
        screenY=gp.screenHeight/2-(gp.tileSize)/2;

        this.gp=gp;
        this.keyH=keyH;

        solidArea=new Rectangle();
        solidArea.x=11;
        solidArea.y=16;
        solidArea.width=26;
        solidArea.height=20;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;


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
            up1= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_up_1.png"));
            up2= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_up_2.png"));
            down1= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_down_1.png"));
            down2= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_down_2.png"));
            left1= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_left_1.png"));
            left2= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_left_2.png"));
            right1= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_right_1.png"));
            right2= ImageIO.read(getClass ().getResourceAsStream("/Resource/Player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed==true || keyH.downPressed==true ||
                keyH.leftPressed==true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";

            } else if (keyH.downPressed == true) {
                direction = "down";

            } else if (keyH.leftPressed == true) {
                direction = "left";

            } else if (keyH.rightPressed == true) {
                direction = "right";

            }

            //check collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object collision
            int objIndex=gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            if(collisionOn==false)
            {
                switch (direction)
                {
                    case "up": worldY -= speed;break;
                    case "down":worldY += speed; break;
                    case "left": worldX -= speed;break;
                    case "right":worldX += speed;break;
                }
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

    public void pickUpObject(int i)
    {
      if(i!=999)
      {
          String objectName=gp.obj[i].name;

          switch(objectName)
          {
              case "Key":
                  hasKey++;
                  gp.playSE(1);
                  gp.obj[i]=null;
                  gp.ui.showMessage("You got a key!");
                  break;

              case "Door":
                  if(hasKey > 0)
                  {
                      gp.ui.showMessage("You opened the Door!");
                      gp.playSE(3);
                      gp.obj[i]=null;
                      hasKey--;
                  }
                  else gp.ui.showMessage("You need a Key!");
                  break;

              case "Boots":
                  gp.ui.showMessage("Speed Up!");
                  gp.playSE(2);
                  speed+=1;
                  gp.obj[i]=null;
                  break;

              case "Chest":
                  gp.ui.gameFinished=true;
                  gp.stopMusic();
                  gp.playSE(4);
                break;


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
