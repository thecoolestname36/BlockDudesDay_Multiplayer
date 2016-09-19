/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Projectile;

import java.awt.geom.Point2D;

/**
 *
 * @author brad
 */
public class Missile extends Projectile {

    public Missile(Missile m, Point2D.Double mousePoint) {
        super(new Missile(m.getTranslateXY().getX(), m.getTranslateXY().getY(), m.isFired(), m.getStartPoint(), m.getEndPoint(), m.getSpeed()));
        this.x = m.getTranslateXY().getX();
        this.y = m.getTranslateXY().getY();
        this.fired = m.isFired();
        this.startPoint = m.getStartPoint();
        this.endPoint = m.getEndPoint();
        this.velocity = m.getSpeed();
        this.targetPoint = mousePoint;
    }

    public Missile(double x, double y) {
        super(x, y, 6.0, 6.0);
    }

    public Missile(double x, double y, boolean b) {
        super(x, y, 6.0, 6.0);
        this.fired = b;
    }

    public Missile(double x, double y, boolean b, Point2D.Double start, Point2D.Double end, double speed) {
        super(x, y, 6.0, 6.0);
        this.fired = b;
        this.startPoint = start;
        this.endPoint = new Point2D.Double(end.getX(), end.getY());
        this.velocity = speed;
        targetPoint = new Point2D.Double(end.getX(), end.getY());
    }

    /* Use trig function to calculate arc movement */
    public Point2D.Double getTranslateXY() {
        double xValue, yValue;

        double xValue1 = getEndPoint().getX();
        double yValue1 = getEndPoint().getY();

        double xValue2 = getStartPoint().getX();
        double yValue2 = getStartPoint().getY();

        xValue = this.x;
        yValue = this.y;
        //this.y -= 1;
//FIGURE OUT WHY THE OBJECT TRACKS OPPOSITE OF CHANGE!!
        double dx1 = (getEndPoint().getX()) - (getStartPoint().getX());
        double dy1 = (getEndPoint().getY()) - (getStartPoint().getY());
        double distance1 = Math.sqrt((dx1 * dx1) + (dy1 * dy1));
        xValue1 += (dx1 * ((this.velocity + (this.velocity * 0)) / distance1));
        yValue1 += (dy1 * ((this.velocity + (this.velocity * 0)) / distance1));
        setEndPoint(new Point2D.Double(xValue1, yValue1));

        double dx2 = getTargetPoint().getX() - getStartPoint().getX();
        double dy2 = getTargetPoint().getY() - getStartPoint().getY();
        double distance2 = Math.sqrt((dx2 * dx2) + (dy2 * dy2));
        xValue2 += (dx2 * ((this.velocity * 2) / distance2));
        yValue2 += (dy2 * ((this.velocity * 2) / distance2));
        setStartPoint(new Point2D.Double(xValue2, yValue2));
        //setStartPoint(new Point2D.Double(x, y));

        double dx = this.endPoint.getX() - this.getX();
        double dy = this.endPoint.getY() - this.getY();
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        xValue += (dx * (this.velocity / distance));
        yValue += (dy * (this.velocity / distance));

        return new Point2D.Double(xValue, yValue);
    }

    @Override
    public boolean isFired() {
        return fired;
    }

    @Override
    public void setFired(boolean b) {
        this.fired = b;
    }

    public void setTranslateXY(Point2D.Double targetLocation) {
        setTargetPoint(new Point2D.Double(targetLocation.getX(), targetLocation.getY()));
        Point2D.Double newLocation = getTranslateXY();
        x = newLocation.getX();
        y = newLocation.getY();
    }

    public void setTargetPoint(Point2D.Double target) {
        this.targetPoint = target;
    }

    public Point2D.Double getTargetPoint() {
        return targetPoint;
    }

    public void setStartPoint(Point2D.Double start) {
        this.startPoint.x = start.getX();
        this.startPoint.y = start.getY();
    }

    public Point2D.Double getStartPoint() {
        return startPoint;
    }

    public void setEndPoint(Point2D.Double end) {
        this.endPoint.x = end.getX();
        this.endPoint.y = end.getY();
    }

    public Point2D.Double getEndPoint() {
        return endPoint;
    }

    public void setSpeed(double v) {
        this.velocity = v;
    }

    public double getSpeed() {
        return velocity;
    }

    @Override
    public void setDamage(int damage) {
        damage = new Integer(damage).intValue();
    }

    @Override
    public int getDamage() {
        return new Integer(damage).intValue();
    }

    private double width = 6, height = 6;
    private Point2D.Double startPoint;
    private Point2D.Double endPoint;
    private Point2D.Double targetPoint;
    private double velocity;
    private int damage = 10;
    private boolean fired;
}
