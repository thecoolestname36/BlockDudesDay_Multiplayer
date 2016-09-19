/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Map;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 *
 * @author Brad
 */
public class BlockHouse {
    
    public BlockHouse(BlockHouse other) {
        this.x = other.getX();
        this.y = other.getY();
        this.size = other.getSize();
        this.houseSize = other.getHouseSize();
        this.buildingBlocks = new LinkedList();
        this.rectangle = new Rectangle2D.Double(this.x, this.y, this.size + 10, this.size + 10);
        buildBlockHouse();
        this.outline = buildOutline();

    }

    public BlockHouse(int xp, int yp, int s) {
        this.x = xp;
        this.y = yp;
        this.size = s;
        this.buildingBlocks = new LinkedList();
        this.rectangle = new Rectangle2D.Double(x, y, size + 10, size + 10);
        buildBlockHouse();
        this.outline = buildOutline();

    }

    public BlockHouse buildBlockHouse() {

        for (int i = 0; i < size; i = i + 10) {
            WallObject block = new WallObject(x + i, y);
            for (Shape r : block) {
                buildingBlocks.add(r);
            }
        }
        for (int i = 0; i < size; i = i + 10) {
            WallObject block = new WallObject(x, y + i);
            for (Shape r : block) {
                buildingBlocks.add(r);
            }
        }
        for (int i = 0; i < size; i = i + 10) {
            WallObject block = new WallObject(x + i, y + size);
            for (Shape r : block) {
                buildingBlocks.add(r);
            }
        }
        for (int i = 0; i < (size / 3); i = i + 10) {
            WallObject block = new WallObject(x + size, y + i);
            for (Shape r : block) {
                buildingBlocks.add(r);
            }
        }
        for (int i = 0; i < (size / 3); i = i + 10) {
            WallObject block = new WallObject(x + size, (y + size) - i);
            for (Shape r : block) {
                buildingBlocks.add(r);
            }
        }
        return this;
    }

    public Rectangle2D getOutline() {
        return rectangle;
        //return outline;
    }

    public Polygon getPreciseOutline() {
        return outline;
    }

    public LinkedList<Rectangle2D> getBuildingBlocks() {
        return buildingBlocks;
    }

    public void setBuildingBlocks(LinkedList<Rectangle2D> b) {
        buildingBlocks = b;
    }

    private Polygon buildOutline() {
        Polygon poly = new Polygon();
        poly.addPoint(x, y);
        poly.addPoint(x + (size + 10), y);
        poly.addPoint(x + (size + 10), (y + ((size + 10) / 3)));
        poly.addPoint(x + size, (y + ((size + 10) / 3)));
        poly.addPoint(x + size, y + 10);
        poly.addPoint(x + 10, y + 10);
        poly.addPoint(x + 10, y + 10);
        poly.addPoint(x + 10, y + size);
        poly.addPoint(x + size, y + size);
        poly.addPoint(x + size, y + (((size + 10) / 3) * 2));
        poly.addPoint(x + (size + 10), y + (((size + 10) / 3) * 2));
        poly.addPoint(x + (size + 10), y + size + 10);
        poly.addPoint(x, y + size + 10);

        return poly;
    }
    
    public int getX() {
        return new java.lang.Integer(x);
    }
    
    public int getY() {
        return new java.lang.Integer(y);
    }
    
    public int getHouseSize() {
        return new java.lang.Integer(houseSize);
    }
    
    public int getSize() {
        return new java.lang.Integer(size);
    }

    private int x;
    private int y;
    private int houseSize;
    private int size;
    private Polygon outline;
    private Rectangle2D.Double rectangle;
    private LinkedList buildingBlocks;

}
