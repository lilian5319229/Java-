
package QQfly2;

import java.awt.Graphics;
import java.awt.Rectangle;
import qqfly.Player.PLAYER;


public interface ObjC {
    
    public void tick();
    public void render(Graphics g);
    public Rectangle getBounds();
    public PLAYER getP();
    
    public double getX();
    public double getY();
    
}
