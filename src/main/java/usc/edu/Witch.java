package usc.edu;
import java.awt.image.BufferedImage;

public class Witch extends Enemy {

    private int healAmount = 35;     
    private long healCooldown = 3000; 
    private long lastHealTime;
    public Witch(double x, double y, BufferedImage sprite) {
        super(x, y, 180, 1.2, 50, sprite);
        points = 50;
        lastHealTime = System.currentTimeMillis();
    }

    @Override
    public void update() {

        super.update();

        if (!alive)
            return;

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastHealTime >= healCooldown) {
            hp += healAmount;

            if (hp > maxHp)
                hp = maxHp;

            lastHealTime = currentTime;
        }
    }
}