package usc.edu;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class InfoWindow extends JFrame{

    public InfoWindow(){

        setTitle("Game Encyclopedia");
        setSize(850,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs=new JTabbedPane();

        tabs.setUI(new BasicTabbedPaneUI(){

@Override
protected void paintTabBackground(
        Graphics g,
        int tabPlacement,
        int tabIndex,
        int x,
        int y,
        int w,
        int h,
        boolean isSelected) {

    if (isSelected) {
        g.setColor(new Color(120, 120, 120)); 
    } else {
        g.setColor(new Color(60, 60, 60)); 
    }

    g.fillRect(x, y, w, h);
}

            @Override protected void paintContentBorder(Graphics g,int tabPlacement,int selectedIndex){}
            @Override protected void paintTabBorder(Graphics g,int tabPlacement,int tabIndex,int x,int y,int w,int h,boolean isSelected){}
            @Override protected void paintFocusIndicator(Graphics g,int tabPlacement,Rectangle[] rects,int tabIndex,Rectangle iconRect,Rectangle textRect,boolean isSelected){}
        });

        tabs.setOpaque(false);
        tabs.setBorder(null);
        tabs.setFont(MedievalFont.getFont(32f));
        tabs.setForeground(new Color(235,190,90));
        JScrollPane towerScroll=new JScrollPane(createTowerPanel());
        JScrollPane enemyScroll=new JScrollPane(createEnemyPanel());
        towerScroll.setBorder(null);
        towerScroll.setOpaque(false);
        towerScroll.getViewport().setOpaque(false);
        enemyScroll.setBorder(null);
        enemyScroll.setOpaque(false);
        enemyScroll.getViewport().setOpaque(false);
        towerScroll.getVerticalScrollBar().setUnitIncrement(16);
        enemyScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabs.add("Towers",towerScroll);
        tabs.add("Enemies",enemyScroll);
        tabs.setBackgroundAt(0, new Color(60, 60, 60));
        tabs.setBackgroundAt(1, new Color(60, 60, 60));
        ((JScrollPane)tabs.getComponentAt(0)).setBorder(null);
        ((JScrollPane)tabs.getComponentAt(0)).getViewport().setOpaque(false);
        ((JScrollPane)tabs.getComponentAt(0)).setOpaque(false);
        ((JScrollPane)tabs.getComponentAt(1)).setBorder(null);
        ((JScrollPane)tabs.getComponentAt(1)).getViewport().setOpaque(false);
        ((JScrollPane)tabs.getComponentAt(1)).setOpaque(false);
     setContentPane(new JPanel(){

    Image bg=new ImageIcon(getClass().getResource("/assets/ui/medieval_bg.jpg")).getImage();

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        g.drawImage(bg,0,0,getWidth(),getHeight(),this);
    }
});

getContentPane().setLayout(new BorderLayout());

add(tabs,BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTowerPanel(){

    JPanel panel=new JPanel(null);

    panel.setOpaque(false);
    panel.setPreferredSize(new Dimension(1400,2200));

    addCard(panel,"Basic Tower","/assets/tower.png","Price: 50\nDamage: 15\nRange: 180\nAttack Speed: 0.5s\nHP: 100",80);
    addCard(panel,"Slow Tower","/assets/tower4.png","Price: 60\nDamage: 0\nRange: 245\nAttack Speed: 1.2s\nHP: 150\nSlow enemies for five seconds",420);
    addCard(panel,"Magic Tower","/assets/tower3.png","Price: 75\nDamage: 35\nRange: 250\nAttack Speed: 0.75s\nHP: 175",760);
    addCard(panel,"Sniper Tower","/assets/tower2.png","Price: 100\nDamage: 60\nRange: 350\nAttack Speed: 2.5s\nHP: 250",1100);

    return panel;
}
    private JPanel createEnemyPanel(){

    JPanel panel=new JPanel(null);

    panel.setOpaque(false);
    panel.setPreferredSize(new Dimension(1400,2200));

    addCard(panel,"Normal Enemy","/assets/normalenemy.png","Hp: 130\nSpeed: 1.5\nDamage: 15\n Lives: -1\nReward: +5 coins",80);
    addCard(panel,"Fast Enemy","/assets/enemy_fast.png","Hp: 40\nSpeed: 2.5\nDamage: 10\n Lives: -1\nReward: +5 coins",420);
    addCard(panel,"Tank Enemy","/assets/enemy_tank.png","Hp: 140\nSpeed: 1.1\nDamage: 25\n Lives: -2.5\nReward: +15 coins",760);
    addCard(panel,"Witch","/assets/witch.png","Hp: 180\nSpeed: 1.2\nDamage: 50\n Lives: -3\nReward: +30 coins\n Recupera 35HP cada 3s",1100);
    addCard(panel,"Nigromante","/assets/nigromante.png","Hp: 6000\nSpeed: 0.9\nDamage: 100\n Lives: -10\nReward: +250 coins",1440);

    return panel;
}

    private void addCard(JPanel panel,String title,String imagePath,String stats,int y){

        JLabel titleLabel=new JLabel(title);

        titleLabel.setFont(MedievalFont.getFont(42f));
        titleLabel.setForeground(new Color(235,190,90));
        titleLabel.setBounds(100,y,500,50);

        panel.add(titleLabel);

        try{

            BufferedImage img=ImageIO.read(getClass().getResourceAsStream(imagePath));

            JLabel image=new JLabel(new ImageIcon(img.getScaledInstance(170,170,Image.SCALE_SMOOTH)));

            image.setBounds(120,y+40,170,170);

            panel.add(image);

        }catch(Exception e){
            e.printStackTrace();
        }

        JTextArea text=new JTextArea(stats);

        text.setOpaque(false);
        text.setEditable(false);
        text.setForeground(new Color(245,220,160));
        text.setFont(MedievalFont.getFont(30f));
        text.setBounds(380,y+20,9000,2220);
        JSeparator line=new JSeparator();

        line.setForeground(new Color(180,140,70));
        line.setBounds(80,y+300,950,3);
        panel.add(line);
        panel.add(text);
    }
}