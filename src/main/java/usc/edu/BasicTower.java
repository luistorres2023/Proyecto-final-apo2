package usc.edu;
import java.awt.image.BufferedImage;

public class BasicTower extends Tower {
    public BasicTower(int gridX, int gridY, BufferedImage sprite) {
        super(gridX, gridY,180,15,500, sprite);
        bulletSprite="/assets/basicbullet.png";
        bulletSize=30;
        this.hp = 100;
        this.maxHp = 100;
    }
}