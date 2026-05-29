package usc.edu;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class InfoWindow extends JFrame {

    public InfoWindow() {

        setTitle("Game Encyclopedia");
        setSize(900,650);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Towers", createTowerPanel());
        tabs.add("Enemies", createEnemyPanel());

        add(tabs);
        setVisible(true);
    }

    private JScrollPane createTowerPanel() {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        addCard(p,"Basic Tower","/assets/tower.png","Price: 50\nDamage: Medium\nRange: Medium\nAttack Speed: Fast");
        addCard(p,"Slow Tower","/assets/tower4.png","Price: 60\nDamage: Low\nAbility: Slow enemies");
        addCard(p,"Magic Tower","/assets/tower3.png","Price: 75\nDamage: High");
        addCard(p,"Sniper Tower","/assets/tower2.png","Price: 100\nDamage: Very High\nRange: Long");

        return new JScrollPane(p);
    }

    private JScrollPane createEnemyPanel() {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        addCard(p,"Normal Enemy","/assets/normalenemy.png","HP: Medium\nDamage: 1");
        addCard(p,"Fast Enemy","/assets/enemy_fast.png","HP: Low\nSpeed: Fast");
        addCard(p,"Tank Enemy","/assets/enemy_tank.png","HP: High\nDamage: 2.5");
        addCard(p,"Witch","/assets/witch.png","HP: High\nAbility: Magic attack");
        addCard(p,"Nigromante","/assets/nigromante.png","HP: Boss\nAbility: Necromancy");

        return new JScrollPane(p);
    }

    private void addCard(JPanel panel,String title,String imgPath,String text) {

        JPanel card = new JPanel(new BorderLayout());

        card.setBorder(BorderFactory.createTitledBorder(title));

        try {

            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(imgPath));

            JLabel pic =new JLabel(new ImageIcon(img.getScaledInstance(90,90,Image.SCALE_SMOOTH)));

            card.add(pic,BorderLayout.WEST);

        } catch(Exception e){}

        JTextArea area = new JTextArea(text);
        area.setEditable(false);

        card.add(area,BorderLayout.CENTER);

        panel.add(card);
    }
}