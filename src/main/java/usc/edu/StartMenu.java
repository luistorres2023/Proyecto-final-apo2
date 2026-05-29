package usc.edu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JPanel implements MouseListener{

Rectangle playBtn = new Rectangle(360,250,240,70);
Rectangle infoBtn = new Rectangle(360,350,240,70);
Rectangle historiaBtn = new Rectangle(360,450,240,70);

public StartMenu(){

    setPreferredSize(new Dimension(960,720));

    addMouseListener(this);
}

protected void paintComponent(Graphics g){

    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);
    g2d.fillRect(0,0,960,720);
    g2d.setColor(Color.WHITE);
    g2d.setFont(MedievalFont.getFont(55f));
    g2d.drawString("TOWER DEFENSE",180,140);
    draw(g2d,playBtn,"JUGAR");
    draw(g2d,infoBtn,"INFO");
    draw(g2d,historiaBtn,"HISTORIA");

}

void draw(Graphics2D g2,Rectangle r,String t){

    g2.setColor(new Color(40,40,40));

    g2.fillRoundRect(r.x,r.y,r.width,r.height,20,20);

    g2.setColor(new Color(235,190,90));

    g2.drawRoundRect(r.x,r.y,r.width,r.height,20,20);

    g2.setFont(MedievalFont.getFont(30f));

    FontMetrics fm = g2.getFontMetrics();

    int textX = r.x + (r.width - fm.stringWidth(t)) / 2;

    int textY = r.y + 45;

    g2.drawString(t,textX,textY);
}

public void mousePressed(MouseEvent e){

    Point p = e.getPoint();

    if(playBtn.contains(p)){

        new WaveMenu();

        Main.frame.dispose();
    }

    if(infoBtn.contains(p)){

        new InfoWindow();
    }

    if(historiaBtn.contains(p)){

        new Historia();
    }

    
}

public void mouseReleased(MouseEvent e){}
public void mouseClicked(MouseEvent e){}
public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}
}