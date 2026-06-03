package usc.edu;
import java.awt.image.BufferedImage;

public class NIGROMANTE extends Enemy {
    long lastSummon = 0;
    long summonCooldown = 8000;
    boolean firstSpawnEffect = false;
    public int auraRange = 300;

    public NIGROMANTE(double x, double y, BufferedImage sprite) {
        super(x, y, 4000, 0.6,100,sprite);
        points = 1500;
        auraRange =300;
    }

    @Override
public void update() {

    super.update();

    if (!alive)
        return;
    if(!firstSpawnEffect){

    firstSpawnEffect = true;

    for(Tower tower : GamePanel.instance.towers){

        tower.slowUntil =
            System.currentTimeMillis() + 5000;
    }
}

    if (System.currentTimeMillis() - lastSummon > summonCooldown) {

        lastSummon = System.currentTimeMillis();

        TankEnemy tank =
            new TankEnemy(x + 40, y, GamePanel.instance.tankEnemyImg);

        Witch witch =
            new Witch(x - 40, y, GamePanel.instance.witchImg);

        tank.pathIndex = this.pathIndex;
        witch.pathIndex = this.pathIndex;

        GamePanel.instance.enemiesToAdd.add(tank);
        GamePanel.instance.enemiesToAdd.add(witch);
    }
}
}