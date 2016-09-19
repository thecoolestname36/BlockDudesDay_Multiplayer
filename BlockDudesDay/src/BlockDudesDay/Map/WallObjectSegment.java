/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Map;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

/**
 *
 * @author Brad
 */
public class WallObjectSegment extends Rectangle2D.Double {

    public WallObjectSegment(double x, double y) {
        super(x, y, 5, 5);
        this.startHealth = new java.lang.Double(health.doubleValue());
        this.present = true;
    }

    /**
     * Takes damage in double form, max health 4.0
     */
    public void takeDamage(double d) {
        health = health - d;

        java.lang.Double alpha = (health / startHealth);

        //healthColor = new Color(0,0,0, alpha.floatValue());
        if (health <= 0.0) {
            health = 0.0;
            present = false;
        }
    }

    public void setPoint(Point2D.Double point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Color getColor() {
        return healthColor;
    }

    public boolean isPresent() {
        boolean alive;
        if (present == true) {
            alive = true;
        } else {
            alive = false;
        }
        return alive;
    }

    private Color healthColor = new Color(0, 0, 0, 1);
    private boolean present;
    private java.lang.Double health = 3.0;
    private double startHealth;
}
