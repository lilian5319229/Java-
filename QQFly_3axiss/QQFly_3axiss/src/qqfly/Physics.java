package qqfly;

import QQfly2.ObjA;
import QQfly2.ObjB;
import QQfly2.ObjC;
import java.util.LinkedList;
import qqfly.Bullets.BULLETWAY;
import qqfly.Player.PLAYER;

public class Physics {

    public static boolean Collision(ObjB entb, ObjC entc) {

        if (entb.getBounds().intersects(entc.getBounds())) {
            return true;
        }
        return false;
    }

    public static boolean Collision(ObjB entb, ObjA enta) {

        if (entb.getBounds().intersects(enta.getBounds())) {
            return true;
        }
        return false;
    }

    public static boolean Collision(ObjA entb, ObjA enta) {

        if (entb.getBounds().intersects(enta.getBounds())) {
            return true;
        }
        return false;
    }

    public static boolean Collision(ObjC entc, ObjA enta) {

        if (entc.getBounds().intersects(enta.getBounds())) {
            if((enta.getBulletWay() == BULLETWAY.DOWN && entc.getP() == PLAYER.P1) || (enta.getBulletWay() == BULLETWAY.UP && entc.getP() == PLAYER.P2))
            return true;
        }
        return false;
    }
}
