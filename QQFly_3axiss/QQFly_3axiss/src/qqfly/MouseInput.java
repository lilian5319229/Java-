package qqfly;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //play button
        if (mx >= 260 && mx <= 460) {
            if (my >= 300 && my <= 350) {
                //pressed play button
                QQFly.p1Start = true;

            }
        }

        // quit button
        if (mx >= 260 && mx <= 460) {
            if (my >= 370 && my <= 400) {
                //pressed quit button
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
