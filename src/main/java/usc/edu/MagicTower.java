package usc.edu;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
    public class MagicTower extends Tower{
        public MagicTower(int gridX,int gridY,BufferedImage sprite){
        super(gridX,gridY,250,35,750,sprite,75);
        bulletSprite="/assets/bullet.png";
        bulletSize=35;
        this.hp = 175;
        this.maxHp = 175;
        scoreMultiplier = 1.5;
        skillCost = 150;
    }
    @Override
public void useSkill(ArrayList<Enemy> enemies){

    ArrayList<Enemy> targets = new ArrayList<>();

    for(Enemy enemy : enemies){

        if(enemy.alive && enemy instanceof NIGROMANTE){

            targets.add(enemy);
        }
    }

    for(Enemy enemy : enemies){

        if(enemy.alive &&
           enemy instanceof Witch &&
           !targets.contains(enemy)){

            targets.add(enemy);
        }
    }

    for(Enemy enemy : enemies){

        if(enemy.alive &&
           !targets.contains(enemy)){

            targets.add(enemy);
        }
    }

    int maxQuemados = 7 + (int)(Math.random() * 4);
    int quemados = 0;

    for(Enemy enemy : targets){

        enemy.burning = true;
        enemy.burnSpreadUsed = false;
        enemy.burnUntil = System.currentTimeMillis() + 10000;
        enemy.nextBurnTick = System.currentTimeMillis();

        quemados++;

        if(quemados >= maxQuemados)
            break;
    }
}
    }