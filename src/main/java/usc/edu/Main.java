package usc.edu;

import javax.swing.*;

public class Main {

    public static JFrame frame;
    public static SoundManager soundManager;

    public static void main(String[] args) {

        soundManager = new SoundManager();
        frame = new JFrame("Tower Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(new StartMenu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}