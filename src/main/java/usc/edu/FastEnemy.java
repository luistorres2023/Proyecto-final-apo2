package usc.edu;
import java.awt.image.BufferedImage;

public class FastEnemy extends Enemy {
    public FastEnemy(double x,double y,BufferedImage sprite) {
        super(x,y,40,2.5,10,sprite);
    }
}