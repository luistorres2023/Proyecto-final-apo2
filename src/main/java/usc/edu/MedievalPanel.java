package usc.edu;
import java.awt.*;
import javax.swing.*;

public class MedievalPanel extends JPanel{

    private Image bg;

    public MedievalPanel(){
        java.net.URL url=getClass().getResource("/assets/ui/medieval_bg.jpg");

        if(url!=null){
            bg=new ImageIcon(url).getImage();
        }

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2=(Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        if(bg!=null){
            g2.drawImage(bg,0,0,getWidth(),getHeight(),this);
        }else{
            GradientPaint gp=new GradientPaint(
                0,0,new Color(34,17,8),
                0,getHeight(),new Color(72,38,15)
            );

            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());
        }

        g2.setColor(new Color(15,7,2,220));
        g2.fillRoundRect(18,18,getWidth()-36,getHeight()-36,30,30);

        g2.setStroke(new BasicStroke(6));
        g2.setColor(new Color(196,145,52));
        g2.drawRoundRect(18,18,getWidth()-36,getHeight()-36,30,30);

        g2.setStroke(new BasicStroke(2));
        g2.setColor(new Color(255,220,120));
        g2.drawRoundRect(26,26,getWidth()-52,getHeight()-52,24,24);

        drawCorner(g2,22,22);
        drawCorner(g2,getWidth()-62,22);
        drawCorner(g2,22,getHeight()-62);
        drawCorner(g2,getWidth()-62,getHeight()-62);
    }

    private void drawCorner(Graphics2D g2,int x,int y){
        g2.setColor(new Color(255,215,120));

        g2.drawArc(x,y,40,40,0,90);
        g2.drawArc(x+5,y+5,30,30,0,90);

        g2.fillOval(x+28,y+28,6,6);
    }
}