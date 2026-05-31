package usc.edu;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JPanel implements MouseListener{

Rectangle playBtn = new Rectangle(225, 570, 130, 55);
Rectangle infoBtn = new Rectangle(400, 570, 130, 55);
Rectangle historiaBtn = new Rectangle(570, 570, 130, 55);

private BufferedImage backgroundImage;

public StartMenu(){

    setPreferredSize(new Dimension(960,720));

    addMouseListener(this);

    try {

    backgroundImage = ImageIO.read(
        getClass().getResourceAsStream(
            "/assets/menu_background.png"
        )
    );

} catch(Exception e) {

    e.printStackTrace();
}
}

protected void paintComponent(Graphics g){

    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    if(backgroundImage != null){

    g2d.drawImage(
        backgroundImage,
        0,
        0,
        960,
        720,
        null
    );

}

}

void draw(Graphics2D g2,Rectangle r,String t){

    g2.setColor(new Color(20,20,20,220));

    g2.fillRoundRect(r.x,r.y,r.width,r.height,20,20);

    g2.setColor(new Color(255,215,120));

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