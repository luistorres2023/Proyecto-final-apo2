package usc.edu;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NormalEnemy extends Enemy {
    public NormalEnemy(double x, double y, BufferedImage sprite) {
        super(x, y, 130, 1.5,15, sprite);
    points = 15;
    }
}