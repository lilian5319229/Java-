
package qqfly;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class LoadPictures { 
     private BufferedImage image;
    
    public BufferedImage loadImage(String index) {
        
         try {
             image = ImageIO.read(getClass().getResource(index));
         } catch (IOException ex) {
             Logger.getLogger(LoadPictures.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        return image;
    }
}
