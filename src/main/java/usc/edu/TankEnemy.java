package usc.edu;
import java.awt.image.BufferedImage;

public class TankEnemy extends Enemy {
    public TankEnemy(double x, double y, BufferedImage sprite) {
        super(x, y, 140, 1.1,25, sprite);
        points = 25;
    }
}