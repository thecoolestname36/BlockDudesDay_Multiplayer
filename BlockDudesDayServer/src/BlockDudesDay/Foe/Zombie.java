/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Foe;


import BlockDudesDay.Map.BlockHouse;
import BlockDudesDay.Map.WallObjectSegment;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Double;
import java.util.LinkedList;

/**
 *
 * @author cutsh020
 */
public class Zombie extends Foe {
    
    
    /**
     * @Override
    public String toString() {
        String s = new String("BlockDudesDay.Foe.Zombie["
                +"x=" + xCoord + ","
                +"y=" + yCoord + ","
                +"w=" + width + ","
                +"h=" + height + ","
                +"v=" + velocity + ","
                +"hp=" + health + ","
                +"shp=" + startHP + ","
                +"d=" + damage + ","
                +"s=" + startPoint.toString() + ","
                +"e=" + targetPoint.toString() + "]"
        );
        return s;
    }
     * @param s 
     */
    public Zombie(String s) {
        super();
        s = s.substring(25, s.length());
        
        if(s.indexOf(',') >= 0) {
            //x
            this.x = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //y
            this.y = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //w
            this.width = new Integer(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //h
            this.height = new Integer(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //v
            this.velocity = new Integer(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //hp
            this.health = java.lang.Double.parseDouble(s.substring(3, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //shp
            this.startHP = java.lang.Double.parseDouble(s.substring(4, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            //d
            this.damage = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(',') >= 0) {
            double xVal = java.lang.Double.parseDouble(s.substring(17, s.indexOf(',')-1));
            s = s.substring(s.indexOf(',')+2);
            double yVal = java.lang.Double.parseDouble(s.substring(0, s.indexOf(']')-1));
            this.startPoint = new Point2D.Double(xVal, yVal);
            s = s.substring(s.indexOf(',')+1);
        }
        if(s.indexOf(']') >= 0) {
            double xVal = java.lang.Double.parseDouble(s.substring(17, s.indexOf(',')-1));
            s = s.substring(s.indexOf(',')+2);
            double yVal = java.lang.Double.parseDouble(s.substring(0, s.indexOf(']')-1));
           this.targetPoint = new Point2D.Double(xVal, yVal);
        }
        this.zombieColor = Color.RED;
    }

    public Zombie() {
        super(400, 400, 10, 10);
        this.x = 400;
        this.y = 400;
        this.width = 10;
        this.height = 10;
    }

    public Zombie(int x, int y) {
        super(x, y, 10, 10);
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;
    }

    public Zombie(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public Zombie(int x, int y, Point2D target) {
        super(x, y, 10, 10);
        this.x = x;
        this.y = y;
        this.targetPoint = target;
    }

    public Zombie(Point2D.Double target, Point2D.Double start, double spd) {
        super((int) start.getX(), (int) start.getY(), 10 - (int) spd, 10 - (int) spd);
        this.setSize(10 - (int) spd, 10 - (int) spd);
        this.damage = (4 - (int) spd) * 0.25;
        this.health = 10- ((int) spd)*2;
        this.startHP = health;
        this.velocity = (int) spd+1;
        this.targetPoint = target;
        this.startPoint = start;
        this.zombieColor = Color.RED;
    }
    
    public Zombie(double x1, double y1, int w, int h, int v, double hp, double shp, double d, Point2D.Double startPoint1, Point2D.Double endPoint1) {
        super();
        this.x = x1;
        this.y = y1;
        this.width = w;
        this.height = h;
        this.velocity = v;
        this.health = hp;
        this.startHP = shp;
        this.damage = d;
        this.startPoint = startPoint1;
        this.targetPoint = endPoint1;
    }
    
    public void setBossStats(int w, int h, int d, int he, int sp) {
        this.width = w;
        this.height = h;
        this.damage = d;
        this.health = he;
        this.velocity = sp;
    }

    @Override
    public void setDamage(double dmg) {
        damage = dmg;
    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHealth(double hp) {
        health = hp;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setTargetLocation(double x, double y) {
        targetPoint.setLocation(x, y);
    }

    @Override
    public Point2D getTargetLocation() {
        return targetPoint;
    }

    @Override
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }
        return false;
    }



    @Override
    public java.awt.Color getHealthColor() {
        if (health == 0) {
            zombieColor = new Color(255, 0, 0);
        } else if ((health / startHP) <= 0.2) {
            zombieColor = new Color(88, 0, 0);
        } else if ((health / startHP) <= 0.4) {
            zombieColor = new Color(136, 0, 0);
        } else if ((health / startHP) <= 0.6) {
            zombieColor = new Color(192, 0, 0);
        } else if ((health / startHP) <= 0.8) {
            zombieColor = new Color(255, 0, 0);
        }
        return zombieColor;
    }

    @Override
    public void setTranslateXY(Point2D target, LinkedList<BlockHouse> walls) {

        boolean notDetected = false;
        Point2D point = new Point2D.Double(getX(), getY());
        Line2D viewpoint = new Line2D.Double(point, target);
        if ((!walls.isEmpty()) && (!playerDetected)) {
            for (BlockHouse w : walls) {
                if (viewpoint.intersects(w.getOutline())) {
                    for (Rectangle2D block : w.getBuildingBlocks()) {
                        if (viewpoint.intersects(block)) {
                            notDetected = true;
                        }
                    }
                }
            }

            if (!notDetected) {
                playerDetected = true;
            } else {
                x -= ((Math.random() * Math.random()) + (-(Math.random() * Math.random())));
                y -= ((Math.random() * Math.random()) + (-(Math.random() * Math.random())));
                for (BlockHouse w : walls) {
                    if (w.getOutline().intersects(this)) {
                        x = point.getX();
                        y = point.getY();
                    }
                }
            }
        } else {
            x -= ((Math.random() * Math.random()) + (-(Math.random() * Math.random()))) + ((x - target.getX()) * ((this.velocity*.3) / Math.sqrt(((x - target.getX()) * (x - target.getX())) + ((y - target.getY()) * (y - target.getY())))));
            y -= ((Math.random() * Math.random()) + (-(Math.random() * Math.random()))) + ((y - target.getY()) * ((this.velocity*.3) / Math.sqrt(((x - target.getX()) * (x - target.getX())) + ((y - target.getY()) * (y - target.getY())))));
            for (BlockHouse w : walls) {
                if (w.getOutline().intersects(this)) {
                    for (Rectangle2D block : w.getBuildingBlocks()) {
                        if (block.intersects(this)) {
//                            w.getBuildingBlocks().remove(block);
//                            ((WallObjectSegment) block).takeDamage(this.damage);
//                            System.out.println(((WallObjectSegment) block).getHealth());
//                            w.getBuildingBlocks().add(block);
                            x = point.getX();
                            y = point.getY();
                        }
                    }
                }
            }
        }
    }
    
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
        String hS = java.lang.Double.toString(health);
        if(hS.length() > 7){
            hS = hS.substring(0, hS.indexOf(".")+ 3);
        }
        String shpS = java.lang.Double.toString(startHP);
        if(shpS.length() > 7){
            shpS = shpS.substring(0, shpS.indexOf(".")+ 3);
        }
        String dS = java.lang.Double.toString(damage);
        if(dS.length() > 7){
            dS = dS.substring(0, dS.indexOf(".")+ 3);
        }
        String startPointSX = java.lang.Double.toString(startPoint.getX());
        if(startPointSX.length() > 7){ 
            startPointSX = startPointSX.substring(0, startPointSX.indexOf(".") + 3);
        }
        String startPointSY = java.lang.Double.toString(startPoint.getY());
        if(startPointSY.length() > 7){
            startPointSY = startPointSY.substring(0, startPointSY.indexOf(".") + 3);
        }
        String targetPointSX = java.lang.Double.toString(targetPoint.getX());
        if(targetPointSX.length() > 7){
            targetPointSX = targetPointSX.substring(0, targetPointSX.indexOf(".") + 3);
        }
        String targetPointSY = java.lang.Double.toString(targetPoint.getY());
        if(targetPointSY.length() > 7){
            targetPointSY = targetPointSY.substring(0, targetPointSY.indexOf(".") + 3);
        }
        
        String s = new String("BlockDudesDay.Foe.Zombie["
                +"x=" + xS + ","
                +"y=" + yS + ","
                +"w=" + width + ","
                +"h=" + height + ","
                +"v=" + velocity + ","
                +"hp=" + hS + ","
                +"shp=" + shpS + ","
                +"d=" + dS + ","
                //+"s=" + startPoint.toString() + ","
                +"s=Point2D.Double[" + startPointSX + ", " + startPointSY + "],"
                //+"e=" + targetPoint.toString() + "]"
                +"e=Point2D.Double[" + targetPointSX + ", " + targetPointSY + "]]"
        );
        return s;
    }
    
    @Override
    public void setPlayerDetected(boolean b) {
        playerDetected = b;
    }

    private boolean playerDetected = false;

    private int width;
    private int height;
    private int velocity;
    private double health = 2;
    private double startHP;
    private double damage = 1;
    private Color zombieColor;
    private Point2D startPoint;
    private Point2D targetPoint;

}
