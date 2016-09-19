/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Foe;


import BlockDudesDay.Map.BlockHouse;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.LinkedList;

/**
 *
 * @author Brad
 */
public abstract class Foe extends java.awt.geom.Rectangle2D.Double {

    public Foe() {
        super(400, 400, 10, 10);
    }

    public Foe(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public abstract void setSize(int x, int y);

    @Override
    public abstract double getWidth();

    @Override
    public abstract double getHeight();

    public abstract void setTargetLocation(double x, double y);

    public abstract Point2D getTargetLocation();

    public abstract void setHealth(double hp);

    public abstract double getHealth();

    public abstract void setDamage(double dmg);

    public abstract double getDamage();

    public abstract boolean isAlive();

    //public abstract void setTranslateXY(Point2D.Double target);
    public abstract void setTranslateXY(Point2D target, LinkedList<BlockHouse> map);

    public abstract java.awt.Color getHealthColor();
    
    public abstract void setPlayerDetected(boolean b);

}
