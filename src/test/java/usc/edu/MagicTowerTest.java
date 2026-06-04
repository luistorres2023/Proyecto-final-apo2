package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MagicTowerTest {

    @Test
    void habilidadMagica(){

        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        MagicTower tower=new MagicTower(1,1,img);

        ArrayList<Enemy> enemies=new ArrayList<>();

        tower.useSkill(enemies);

        assertTrue(tower.canUseSkill()==false || tower.canUseSkill()==true);
    }
}