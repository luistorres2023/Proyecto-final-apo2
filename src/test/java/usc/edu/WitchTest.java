package usc.edu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

public class WitchTest {

    @Test
    void brujaExiste(){

        BufferedImage img=new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);

        Witch w=new Witch(0,0,img);

        assertNotNull(w);
    }
}