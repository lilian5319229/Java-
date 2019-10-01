
package qqfly;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
    
    public Rectangle playButton = new Rectangle(260,300,200,50);
     public Rectangle quitButton = new Rectangle(260,370,200,50);
    
    public void render(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            
            Font fnt0 = new Font("arial",Font.BOLD,50);
            g.setFont(fnt0);
            g.setColor(Color.red);
            g.drawString("QQFly",280,250);
            
            Font fnt1 = new Font("arial",Font.BOLD,40);
            g.setFont(fnt1);
            
            
            g.drawString("Play", playButton.x+55,playButton.y+35 );
            g2d.draw(playButton); //畫框框
            
            g.drawString("Quit", quitButton.x+55,quitButton.y+35 );
            g2d.draw(quitButton);//畫框框
            
            

            
    }
}
