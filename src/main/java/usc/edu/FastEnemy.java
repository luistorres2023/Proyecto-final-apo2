package usc.edu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FastEnemy extends Enemy {

    private long dodgeTextUntil = 0;

    public FastEnemy(double x,double y,BufferedImage sprite) {
        super(x,y,40,2.5,10,sprite);
        points = 10;
    }

    @Override
    public void takeDamage(int damage) {

        if(Math.random() < 0.20) {
            dodgeTextUntil = System.currentTimeMillis() + 300;
            return;
        }

        super.takeDamage(damage);
    }

    @Override
    public void draw(Graphics2D g2) {

        super.draw(g2);

        if(System.currentTimeMillis() < dodgeTextUntil) {
            g2.setColor(Color.WHITE);
            g2.drawString("MISS",(int)x + 5,(int)y - 10);
        }
    }
}