
package main;

import javax.swing.*;       //Gui library(swing) need for JFrame and Jpanel

public class Main
{
    public static void main(String[] argc)
    {
        JFrame window=new JFrame();             //main window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Treasure Hunt");

        GamePanel gamePanel= new GamePanel();         //where game is drown
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);         //center
        window.setVisible(true);



        gamePanel.setupGame();             //map,player etc.
        gamePanel.startGameThread();      //game is running
    }
}
