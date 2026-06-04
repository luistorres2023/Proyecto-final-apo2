package usc.edu;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class InfoWindow extends JFrame{

    public InfoWindow(){

        setTitle("Game Encyclopedia");
        setSize(1000,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs=new JTabbedPane();

        tabs.setUI(new BasicTabbedPaneUI(){

@Override
protected void paintTabBackground(Graphics g,int tabPlacement,int tabIndex,int x,int y,int w,int h,boolean isSelected) {

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
        JScrollPane controlsScroll = createControlsPanel();
        controlsScroll.setBorder(null);
        controlsScroll.setOpaque(false);
        controlsScroll.getViewport().setOpaque(false);
        tabs.add("Controls", controlsScroll);
        tabs.setBackgroundAt(2, new Color(60, 60, 60));
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
    panel.setPreferredSize(new Dimension(1600,2600));

    addCard(panel,"Basic Tower","/assets/tower.png","STATS\nPrice: 50 Coins\nDamage: 15\nRange: 180\nAttack Speed: 0.5s\nHealth: 100\nABILITY\nDispara 3 veces mas rapido durante 5 segundos\nPrice Ability:\n 75 Coins\nCOOLDOWN\n20s",80);
    addCard(panel,"Slow Tower","/assets/tower4.png","STATS\nPrice: 60 Coins\nDamage: 0\nRange: 245\nAttack Speed: 1.2s\nHealth: 150\nABILITY\nTodos los enemigos en pantalla son congelados\npor 2 segundos\nPrice Ability:\n 100 Coins\nCOOLDOWN\n20s",700);
    addCard(panel,"Magic Tower","/assets/tower3.png","STATS\nPrice: 75 Coins\nDamage: 35\nRange: 250\nAttack Speed: 0.75s\nHealth: 175\nABILITY\nTodos los enemigos en pantalla son quemados durante\n8 segundos y si un enemigo sale al lado de uno\nquemado tiene un 10% de probabilidad de quemarse\nPrice Ability:\n 150 Coins\nCOOLDOWN\n20s",1320);
    addCard(panel,"Sniper Tower","/assets/tower2.png","STATS\nPrice: 100 Coins\nDamage: 60\nRange: 350\nAttack Speed: 2.5s\nHealth: 250\nABILITY\nLas siguientes 4 balas que salen hacen daño en area\ny tiene un *4 de daño\nPrice:\n 175 Coins\nCOOLDOWN\n20s",1940);
    return panel;
}
    private JPanel createEnemyPanel(){

    JPanel panel=new JPanel(null);

    panel.setOpaque(false);
    panel.setPreferredSize(new Dimension(1600,3300));

    addCard(panel,"Normal Enemy","/assets/normalenemy.png","STATS\nHealth: 130\nSpeed: 1.5\nTower Damage: 15\nLives Lost: 1\nReward: 5 Coins\nABILITY\nSpeed buff:\n +50% velocidad si tiene - de 30% de vida\nCOOLDOWN\nun solo uso",80);
    addCard(panel,"Fast Enemy","/assets/enemy_fast.png","STATS\nHealth: 40\nSpeed: 2.5\nTower Damage: 10\nLives Lost: 1\nReward: 5 Coins\nABILITY\nSpeed force:\n 20% de probabilidadde esquivar una bala\nCOOLDOWN\n0.3 Segundos",700);
    addCard(panel,"Tank Enemy","/assets/enemy_tank.png","STATS\nHealth: 140\nSpeed: 1.1\nTower Damage: 25\nLives Lost: 2.5\nReward: 15 Coins\nABILITY\nStrong armor:\n 30% de probabilidad de tener una armadura que reduce\n el daño un 60%\nCOOLDOWN\nUn solo uso",1320);
    addCard(panel,"Witch","/assets/witch.png","STATS\nHealth: 180\nSpeed: 1.2\nTower Damage: 50\nLives Lost: 3\nReward: 30 Coins\nABILITY\nAura verde:\n +35 de vida a enemigos y a si misma\nAura morada:\n 30% de probabilidad de reelentizar por 3 segundos\nCOOLDOWN\n3 Segundos",1940);
    addCard(panel,"Nigromante","/assets/NIGROMANTE.png","STATS\nHealth: 4000\nSpeed: 0.5\nTower Damage: 100\nLives Lost: 10\nReward: 1500 Coins\nABILITY\nAl aparecer la pantalla se llena de sombra\nTodos los enemigos quedan reelentizados por 5 seg\nAura morada:\n +velocidad a enemigos\n -40% de velocidad de disparo a torres\nSpawn:\n invoca tank enemy y una witch\nCOOLDOWN\nSpawn: 10 seg",2560);
    return panel;
}
private JScrollPane createControlsPanel() {

    JPanel panel = new JPanel(null);
    panel.setOpaque(false);
    panel.setPreferredSize(new Dimension(1600, 900));
    JTextArea text = new JTextArea("CONTROLES DEL JUEGO\n\n" +"COLOCACIÓN DE TORRES:\n" +"Tecla 1 - Basic Tower\n" +"Tecla 2 - Slow Tower\n" +"Tecla 3 - Magic Tower\n" +"Tecla 4 - Sniper Tower\n\n" +"HABILIDADES:\n" +"- Presiona 5 para activar el modo habilidad\n" +"- Click en una torre para seleccionarla\n" +"- Presiona 5 para salir de el modo habilidad\n");
    text.setFont(MedievalFont.getFont(26f));
    text.setForeground(new Color(245, 220, 160));
    text.setOpaque(false);
    text.setEditable(false);
    text.setLineWrap(true);
    text.setWrapStyleWord(true);
    text.setBounds(100, 80, 900, 600);

    panel.add(text);

    JScrollPane scroll = new JScrollPane(panel);
    scroll.setBorder(null);
    scroll.setOpaque(false);
    scroll.getViewport().setOpaque(false);

    scroll.getVerticalScrollBar().setUnitIncrement(16);

    return scroll;
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

            image.setBounds(120,y+80,170,170);

            panel.add(image);

        }catch(Exception e){
            e.printStackTrace();
        }

        JTextArea text=new JTextArea(stats);

        text.setOpaque(false);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setForeground(new Color(245,220,160));
        text.setFont(MedievalFont.getFont(24f));
        text.setBounds(390,y+60,750,720);
        JSeparator line=new JSeparator();
        line.setForeground(new Color(180,140,70));
        int lineOffset = 500;
        if(title.equals("Nigromante")){
            lineOffset = 600;
        }
        line.setBounds(80,y+lineOffset,1200,3);
        panel.add(line);
        panel.add(text);
    }
}