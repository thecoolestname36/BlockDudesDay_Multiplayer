/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Projectile;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author brad
 */
public abstract class Projectile extends Rectangle2D.Double {
    
    public Projectile() {
        super();
    }
    
    /**
     * /**
     * String s = new String(
                "BlockDudesDay.Projectile.Bullet["
                +"x=" + x + ","
                +"y=" + y + ","
                +"w=" + width + ","
                +"h=" + height +","
                +"s=" + startPoint.toString() + ","
                +"e=" + endPoint.toString() + ","
                +"v" + velocity + ","
                +"f=" + fired + "]"
        );
     * @param s
     */
    public Projectile(String s){
        super();
        s = s.substring(32);
        if(s.indexOf(',') >= 0) {
            this.x = new Integer(s.substring(2, s.charAt(s.indexOf(',')-1)));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            this.y = new Integer(s.substring(2, s.charAt(s.indexOf(',')-1)));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            this.width = new Integer(s.substring(2, s.charAt(s.indexOf(',')-1)));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            this.height = new Integer(s.substring(2, s.charAt(s.indexOf(',')-1)));
            s = s.substring(s.indexOf(',')+1);
        }
        this.fired = true;
    }

    public Projectile(Projectile lead) {
        super(lead.x, lead.y, lead.width, lead.height);
        this.x = lead.x;
        this.y = lead.y;
        this.width = lead.width;
        this.height = lead.height;
        this.fired = lead.fired;
    }

    public Projectile(double x, double y, double w, double h) {
        super(x, y, w, h);
        rect = new Rectangle2D.Double(x, y, w, h);
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean b) {
        this.fired = b;
    }

    public abstract void setDamage(int damage);

    public abstract int getDamage();
    
    public Rectangle2D.Double rect;
    public boolean fired;
}
