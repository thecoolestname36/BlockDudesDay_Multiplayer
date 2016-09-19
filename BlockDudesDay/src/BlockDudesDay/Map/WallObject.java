/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay.Map;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Brad
 */
public class WallObject extends ArrayList<Shape> {

    public WallObject(double x, double y) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.add((Shape) new WallObjectSegment(x + (i * 5), y + (j * 5)));
            }
        }
    }

    public ArrayList getWallSegments() {
        return this;
    }
}
