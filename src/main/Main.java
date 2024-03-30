package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //create a window
        JFrame window = new JFrame();
        //let it can properly close
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //prohibit thezh adjustment of the window size
        window.setResizable(false);
        //set the title
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        //display window at the center
        window.setLocationRelativeTo(null);
        //make it visible
        window.setVisible(true);

        gamePanel.setupGame();
        //call startGameThread()
        gamePanel.startGameThread();
    }
}