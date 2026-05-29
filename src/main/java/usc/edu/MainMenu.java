package usc.edu;
import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("Tower Defense");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(25,25,35));
        panel.setLayout(new GridLayout(3,1,20,20));

        JButton play = new JButton("PLAY");
        JButton config = new JButton("CONFIG");
        JButton exit = new JButton("EXIT");

        Font f = new Font("Arial",Font.BOLD,28);

        play.setFont(f);
        config.setFont(f);
        exit.setFont(f);

        play.addActionListener(e -> {
            dispose();
            new WaveMenu();
        });

        exit.addActionListener(e -> System.exit(0));

        panel.add(play);
        panel.add(config);
        panel.add(exit);

        add(panel);
        setVisible(true);
    }
}