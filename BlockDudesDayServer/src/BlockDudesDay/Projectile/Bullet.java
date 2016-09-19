/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Projectile;

import java.awt.geom.Point2D;
import java.lang.Double;

/**
 *
 * @author brad
 */
public class Bullet extends Projectile {
    
    
    public Bullet() {
        this.x = 400;
        this.y= 500;
        this.width = 2;
        this.height = 2;
        this.velocity = 2;
        this.fired = true;
        this.startPoint = new Point2D.Double(394.0, 494.0);
        this.endPoint = new Point2D.Double(413.23, 81.02);
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
     * [BlockDudesDay.Projectile.Bullet[x=400.0,y=500.0,w=2.0,h=2.0,s=Point2D.Double[394.0, 494.0],e=Point2D.Double[413.2363228676032, 81.02658904582667],v2.0,f=true]]
     */
    public Bullet(String s){
        super();
        s = s.substring(32, s.length());
        if(s.indexOf(',') >= 0) {
            this.x = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        
        if(s.indexOf(',') >= 0) {
            this.y = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            this.width = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            this.height = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        
        if(s.indexOf(',') >= 0) {
            this.velocity = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //s=Point2D.Double[394.0, 494.0],
            double xVal = java.lang.Double.parseDouble(s.substring(17, s.indexOf(',')-1));
            s = s.substring(s.indexOf(',')+2);
            double yVal = java.lang.Double.parseDouble(s.substring(0, s.indexOf(']')-1));
            this.startPoint = new Point2D.Double(xVal, yVal);
            s = s.substring(s.indexOf(',')+1);
        }
        //e=Point2D.Double[413.2363228676032, 81.02658904582667],
        if(s.indexOf(',') >= 0) {
            //s=Point2D.Double[394.0, 494.0],
            double xVal = java.lang.Double.parseDouble(s.substring(17, s.indexOf(',')-1));
            s = s.substring(s.indexOf(',')+2);
            double yVal = java.lang.Double.parseDouble(s.substring(0, s.indexOf(']')-1));
            this.endPoint = new Point2D.Double(xVal, yVal);
        }
        fired = true;
    }


    public Bullet(Bullet lead) {
        super(new Bullet(lead.getTranslateXY().getX(), lead.getTranslateXY().getY(), lead.isFired(), lead.getStartPoint(), lead.getEndPoint(), lead.getSpeed()));
        this.x = lead.getTranslateXY().getX();
        this.y = lead.getTranslateXY().getY();
        this.fired = lead.isFired();
        this.startPoint = lead.getStartPoint();
        this.endPoint = lead.getEndPoint();
        this.velocity = lead.getSpeed();

    }

    public Bullet(double x, double y) {
        super(x, y, 2.0, 2.0);
    }

    public Bullet(double x, double y, boolean b) {
        super(x, y, 2.0, 2.0);
        this.fired = b;
    }

    public Bullet(double x, double y, boolean b, Point2D.Double start, Point2D.Double end, double speed) {
        super(x, y, 2.0, 2.0);
        this.fired = b;
        this.startPoint = start;
        this.endPoint = end;
        this.velocity = speed;
    }

    public Point2D.Double getEndPoint() {
        return endPoint;
    }

    public Point2D.Double getStartPoint() {
        return startPoint;
    }

    public void setSpeed(double speed) {
        this.velocity = speed;
    }

    public double getSpeed() {
        return velocity;
    }

    @Override
    public void setDamage(int damage) {
        bulletDamage = new Integer(damage).intValue();
    }

    @Override
    public int getDamage() {
        return new Integer(bulletDamage).intValue();
    }

//Add in innaccuracies 
    public Point2D.Double getTranslateXY() {
        return new Point2D.Double((this.x - (this.startPoint.x - this.endPoint.x) * (this.velocity / (Math.sqrt(Math.pow((this.startPoint.x - this.endPoint.x), 2) + Math.pow((this.startPoint.y - this.endPoint.y), 2))))),
                (this.y - (this.startPoint.y - this.endPoint.y) * (this.velocity / (Math.sqrt(Math.pow((this.startPoint.x - this.endPoint.x), 2) + Math.pow((this.startPoint.y - this.endPoint.y), 2))))));
    }

    public void setTranslateXY() {
        Point2D.Double point = getTranslateXY();
        x = point.getX();
        y = point.getY();
    }

    @Override
    public boolean isFired() {
        return fired;
    }

    @Override
    public void setFired(boolean b) {
        this.fired = b;
    }
    
    /**
     * BlockDudesDay.Projectile.Bullet[x=400.0,y=500.0,w=2.0,h=2.0]
     * @return 
     */
    @Override
    public String toString() {
        
        String xS = java.lang.Double.toString(x);
        if(xS.length() > 7){
            xS = xS.substring(0, xS.indexOf(".")+ 3);
        }
        String yS = java.lang.Double.toString(y);
        if(yS.length() > 7){
            yS = yS.substring(0, yS.indexOf(".")+ 3);
        }
        String wS = java.lang.Double.toString(width);
        if(wS.length() > 7){
            wS = wS.substring(0, wS.indexOf(".")+ 3);
        }
        String hS = java.lang.Double.toString(height);
        if(hS.length() > 7){
            hS = hS.substring(0, hS.indexOf(".")+ 3);
        }
        String vS = java.lang.Double.toString(velocity);
        if(vS.length() > 7){
            vS = vS.substring(0, vS.indexOf(".")+ 3);
        }
        
        String startPointSX = java.lang.Double.toString(startPoint.getX());
        if(startPointSX.length() > 7){ 
            startPointSX = startPointSX.substring(0, startPointSX.indexOf(".") + 3);
        }
        String startPointSY = java.lang.Double.toString(startPoint.getY());
        if(startPointSY.length() > 7){
            startPointSY = startPointSY.substring(0, startPointSY.indexOf(".") + 3);
        }
        String endPointSX = java.lang.Double.toString(endPoint.getX());
        if(endPointSX.length() > 7){
            endPointSX = endPointSX.substring(0, endPointSX.indexOf(".") + 3);
        }
        String endPointSY = java.lang.Double.toString(endPoint.getY());
        if(endPointSY.length() > 7){
            endPointSY = endPointSY.substring(0, endPointSY.indexOf(".") + 3);
        }
        
        String s = new String(
                "BlockDudesDay.Projectile.Bullet["
                +"x=" + xS + ","
                +"y=" + yS + ","
                +"w=" + wS + ","
                +"h=" + hS +","
                +"v=" + vS + ","
                //+"s=" + startPoint.toString() + ","
                //+"e=" + endPoint.toString() + "]"
                +"s=Point2D.Double[" + startPointSX + ", " + startPointSY + "],"
                +"e=Point2D.Double[" + endPointSX + ", " + endPointSY + "]]"
        );
        return s;
    }

    private Point2D.Double startPoint;
    private Point2D.Double endPoint;
    private double velocity;
    private int bulletDamage = 1;
    private boolean fired;
}
