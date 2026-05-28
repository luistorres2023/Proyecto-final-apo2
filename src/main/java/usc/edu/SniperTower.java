package usc.edu;
import java.awt.image.BufferedImage;

public class SniperTower extends Tower {
    public SniperTower(int gridX, int gridY, BufferedImage sprite) {
        super(gridX,gridY,350,60,2500, sprite);
        bulletSprite="/assets/sniperbullet.png";
        bulletSize=25;
        this.hp = 250;
        this.maxHp = 250;
    }
}