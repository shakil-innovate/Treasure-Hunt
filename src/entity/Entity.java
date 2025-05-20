package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {           //base class     ---abstract class

    public int worldX,worldY;
    public int speed;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;
    public int spiteCounter=0;
    private int spiteNum=1;                       //encapculation
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn= false;

    public static int player_no=0;

    public void spiteChange(int num)       //setter
    {
        spiteNum=num;
    }
    public int  getSpite()            //getter
    {
         return spiteNum;
    }

}
