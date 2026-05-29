package usc.edu;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class InfoWindow extends JFrame{

    public InfoWindow(){

        setTitle("Game Encyclopedia");
        setSize(1400,900);
        setLocationRelativeTo(null);

        JTabbedPane tabs=new JTabbedPane();

        tabs.setFont(MedievalFont.getFont(30f));
        tabs.setBackground(new Color(25,10,0));
        tabs.setForeground(new Color(230,180,60));

        tabs.add("Towers",createTowerPanel());
        tabs.add("Enemies",createEnemyPanel());

        add(tabs);

        setVisible(true);
    }

    private JScrollPane createTowerPanel(){

        MedievalPanel p=new MedievalPanel();

        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        addCard(p,"Basic Tower","/assets/tower.png","Price: 50\nDamage: 15\nRange: 180\nAttack Speed: 0.5s\nHP: 100");

        addCard(p,"Slow Tower","/assets/tower4.png","Price: 60\nRange: 235\nSlow enemies for five seconds");

        addCard(p,"Magic Tower","/assets/tower3.png","Price: 75\nDamage: 35\nRange: 250");

        addCard(p,"Sniper Tower","/assets/tower2.png","Price: 100\nDamage: 60\nRange: 350");

        JScrollPane sp=new JScrollPane(p);

        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    private JScrollPane createEnemyPanel(){

        MedievalPanel p=new MedievalPanel();

        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        addCard(p,"Normal Enemy","/assets/normalenemy.png","Hp: 130\nSpeed: 1.5\nDamage: 15\n Lives: -1\nReward: +5 coins");

        addCard(p,"Fast Enemy","/assets/enemy_fast.png","Hp: 40\nSpeed: 2.5\nDamage: 10\n Lives: -1\nReward: +5 coins");

        addCard(p,"Tank Enemy","/assets/enemy_tank.png","Hp: 140\nSpeed: 1.1\nDamage: 25\n Lives: -2.5\nReward: +15 coins");

        addCard(p,"Witch","/assets/witch.png","Hp: 180\nSpeed: 1.2\nDamage: 50\n Lives: -3\nReward: +30 coins");

        addCard(p,"Nigromante","/assets/nigromante.png","Hp: 6000\nSpeed: 0.9\nDamage: 100\n Lives: -10\nReward: +250 coins");

        JScrollPane sp=new JScrollPane(p);

        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    private void addCard(JPanel panel,String title,String imgPath,String text){

        JPanel card=new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(1200,260));

        card.setBackground(new Color(60,25,5));

        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170,120,40),4),
                BorderFactory.createEmptyBorder(15,15,15,15)
            )
        );

        JLabel titleLabel=new JLabel(title);

        titleLabel.setFont(MedievalFont.getFont(38f));

        titleLabel.setForeground(new Color(235,190,90));

        card.add(titleLabel,BorderLayout.NORTH);

        try{

            BufferedImage img=ImageIO.read(getClass().getResourceAsStream(imgPath));

            JLabel pic=new JLabel(
                new ImageIcon(
                    img.getScaledInstance(220,220,Image.SCALE_SMOOTH)
                )
            );

            pic.setBorder(BorderFactory.createEmptyBorder(10,10,10,40));

            card.add(pic,BorderLayout.WEST);

        }catch(Exception e){}

        JTextArea area=new JTextArea(text);

        area.setEditable(false);

        area.setOpaque(false);

        area.setForeground(new Color(245,220,160));

        area.setFont(MedievalFont.getFont(28f));

        area.setLineWrap(true);

        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(-10,0,0,0));
        card.add(area,BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(25));

        panel.add(card);
    }
}