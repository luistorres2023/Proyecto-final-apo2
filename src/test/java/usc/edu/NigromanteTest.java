package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class NigromanteTest {

    @Test
    void nigromanteTieneAura(){

        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        NIGROMANTE n=new NIGROMANTE(0,0,img);

        assertTrue(n.auraRange>0);
    }
}