package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class TankEnemyTest {

    @Test
    void tanqueExiste(){

        BufferedImage img =
        new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        TankEnemy enemy =
        new TankEnemy(0,0,img);

        assertNotNull(enemy);
    }

    @Test
    void tanqueTieneVidaAlta(){

        BufferedImage img =
        new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        TankEnemy enemy =
        new TankEnemy(0,0,img);

        assertTrue(enemy.hp > 0);
    }
}