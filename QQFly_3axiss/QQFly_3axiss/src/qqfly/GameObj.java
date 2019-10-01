
package qqfly;

import java.awt.Rectangle;


public class GameObj { // GameObject Class
    
    public double x;
    public double y;
    
    public GameObj(double x , double y){
        this.x = x;
        this.y = y;
    }
    
    public Rectangle getBounds(int width,int height){
        
        Rectangle obj = new Rectangle((int)x,(int)y,width,height);     
        
        return obj;
    }
    
}
