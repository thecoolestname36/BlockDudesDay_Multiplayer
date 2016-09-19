/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Character;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Brad
 */
public class Player extends Rectangle2D.Double {

    /**
     * Default constructor.
     */
    public Player() {
        super();
        this.x = 0;
        this.y = 0;
        this.width = 10;
        this.height = 10;
        this.startHP = 100;
        this.hp = 100;
        this.playerID = "-1";
        this.playerColor = new Color(0, 255, 0);
        this.damageArea = new Rectangle2D.Double(x + 2, y + 2, this.width - 4, this.height - 4);
    }

    /**
     * Copy Constructor.
     *
     * @param p
     */
    public Player(Player p) {
        super(p.x, p.y, p.height, p.width);
        this.startHP = p.startHP;
        this.hp = p.hp;
        this.playerColor = p.getHealthColor();
        this.playerID = p.getPlayerID();
        this.damageArea = new Rectangle2D.Double(x + 2, y + 2, p.width - 4, p.height - 4);
    }

    /**
     * String s = new String("BlockDudesDay.Character.Player[" +"x=" + x + ","
     * +"y=" + y + "," +"w=" + width + "," +"h=" + height + "," +"hp=" + hp +
     * "," +"shp=" + startHP + "]" );
     *
     * return s;
     */
    public Player(String s) {
        super();
        s = s.substring(31, s.length());
        if (s.indexOf(',') >= 0) {
            this.x = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',') + 1);
        }
        if (s.indexOf(',') >= 0) {
            this.y = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',') + 1);
        }
        if (s.indexOf(',') >= 0) {
            this.width = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',') + 1);
        }
        if (s.indexOf(',') >= 0) {
            this.height = java.lang.Double.parseDouble(s.substring(2, s.indexOf(',')));
            s = s.substring(s.indexOf(',') + 1);
        }
        if (s.indexOf(',') >= 0) {
            this.hp = java.lang.Double.parseDouble(s.substring(3, s.indexOf(',')));
            s = s.substring(s.indexOf(',') + 1);
        }
        if (s.indexOf(']') > 0) {
            this.startHP = java.lang.Double.parseDouble(s.substring(4, s.indexOf(']') - 1));
            //s = s.substring(s.indexOf(']')+1);
        }

    }

    public Player(double x, double y, double w, double h, double startHealth) {
        super(x, y, w, h);
        this.startHP = startHealth;
        this.hp = startHealth;
        this.playerColor = new Color(0, 255, 0);
        this.damageArea = new Rectangle2D.Double(x + 2, y + 2, w - 4, h - 4);
    }

    public void setHealth(double health) {
        hp = health;
    }

    public double getHealth() {
        if (hp <= 0) {
            return 0;
        } else {
            return hp;
        }
    }

    public double maxHealth() {
        return startHP;
    }

    public java.awt.Color getHealthColor() {
        if (hp == 0) {
            playerColor = new Color(0, 0, 0);
        } else if ((hp / startHP) <= 0.2) {
            playerColor = new Color(255, 0, 0);
        } else if ((hp / startHP) <= 0.4) {
            playerColor = new Color(255, 128, 0);
        } else if ((hp / startHP) <= 0.6) {
            playerColor = new Color(255, 255, 0);
        } else if ((hp / startHP) <= 0.8) {
            playerColor = new Color(128, 255, 0);
        }
        return playerColor;
    }

    public void setLocationPoint(java.awt.Point point) {
        x = (point.getX() - (getWidth() / 2));
        y = (point.getY() - (getHeight() / 2));
    }

    public java.awt.Point getCurrentPoint() {
        return new java.awt.Point((int) getX(), (int) getY());
    }

    public Rectangle2D getRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public void setStartHealth(double start) {
        this.startHP = start;
    }

    public double getStartHealth() {
        return startHP;
    }

    public void setPlayerID(String s) {
        playerID = new String(s);
    }

    public String getPlayerID() {
        return playerID;
    }

    @Override
    public String toString() {
        
        String xS = java.lang.Double.toString(x);
        if(xS.length() > 7) {
            xS = xS.substring(0, xS.indexOf(".")+ 3);
        }
        String yS = java.lang.Double.toString(y);
        if(yS.length() > 7) {
            yS = yS.substring(0, yS.indexOf(".")+ 3);
        }
        String wS = java.lang.Double.toString(width);
        if(wS.length() > 7) {
            wS = wS.substring(0, wS.indexOf(".")+ 3);
        }
        String hS = java.lang.Double.toString(height);
        if(hS.length() > 7) {
            hS = hS.substring(0, hS.indexOf(".")+ 3);
        }
        String hpS = java.lang.Double.toString(hp);
        if(hpS.length() > 7) {
            hpS = hpS.substring(0, hpS.indexOf(".")+ 3);
        }
        String shS = java.lang.Double.toString(startHP);
        if(shS.length() > 7) {
            shS = shS.substring(0, shS.indexOf(".")+ 3);
        }
        String s = new String("BlockDudesDay.Character.Player["
                +"x=" + xS + ","
                +"y=" + yS + ","
                +"w=" + wS + ","
                +"h=" + hS + ","
                +"hp=" + hpS +  ","
                +"shp=" + shS + "]"
        );
        
        return s;
    }

    @Override
    public boolean equals(Object other) {
        String sOther = ((Player) other).getPlayerID();
        return sOther.equals(playerID);
    }

    private String playerID = new String();
    private double startHP;
    private double hp;
    private Color playerColor;
    private Rectangle2D damageArea;

}
