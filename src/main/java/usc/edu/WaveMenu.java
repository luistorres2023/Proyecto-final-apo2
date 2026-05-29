package usc.edu;
import java.awt.*;
import javax.swing.*;

public class WaveMenu extends JFrame {

    public WaveMenu() {

        setTitle("Select Waves");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1,15,15));
        panel.setBackground(new Color(35,35,45));

        addButton(panel,"10 WAVES",10);
        addButton(panel,"20 WAVES",20);
        addButton(panel,"30 WAVES",30);
        addButton(panel,"40 WAVES",40);
        addButton(panel,"INFINITE",-1);

        add(panel);
        setVisible(true);
    }

    private void addButton(JPanel panel,String text,int waves){

        JButton b = new JButton(text);
        b.setFont(new Font("Arial",Font.BOLD,24));

        b.addActionListener(e -> {

            JFrame gameFrame = new JFrame("Tower Defense");

            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.add(new GamePanel(waves));
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            dispose();
        });

        panel.add(b);
    }
}