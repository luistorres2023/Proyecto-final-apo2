package usc.edu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class StartMenu extends JPanel implements MouseListener{    
Rectangle playBtn = new Rectangle(360, 250, 240, 70);
Rectangle infoBtn = new Rectangle(360, 350, 240, 70);
Rectangle configBtn = new Rectangle(360, 450, 240, 70);

public StartMenu(){

 setPreferredSize(new Dimension(960, 720)); 
 addMouseListener(this);  
}
protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    g2d.fillRect(0, 0, 960, 720);
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 55));
    g2d.drawString("TOWER DEFENSE", 200, 140);
    draw(g2d,playBtn,"JUGAR");
    draw(g2d,infoBtn,"INFO");
    draw(g2d,configBtn,"CONFIG");
}
void draw(Graphics2D g2, Rectangle r, String t){
    g2.setColor(Color.DARK_GRAY);
    g2.fillRoundRect(r.x, r.y, r.width, r.height, 20, 20);
    g2.setColor(Color.WHITE);
    g2.drawRoundRect(r.x, r.y, r.width, r.height, 20, 20);
    g2.setFont(new Font("Arial", Font.BOLD, 30));
    g2.drawString(t, r.x+65,r.y+45);
}
public void mousePressed(MouseEvent e) {
    Point p = e.getPoint();
    if(playBtn.contains(p)){
        Main.frame.setContentPane(new WaveMenu());
        Main.frame.revalidate();
    } if(infoBtn.contains(p)){
        new InfoWindow();
    } if(configBtn.contains(p)){
        JOptionPane.showMessageDialog(this, "Configuraciones pronto");
    
    }
}

public void mouseReleased(MouseEvent e) {}
public void mouseClicked(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
}
