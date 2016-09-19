/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;


import BlockDudesDay.Map.BlockHouse;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Brad
 */
public class BlockControls extends javax.swing.JComponent {

    public BlockControls(Dimension d) {
        setBounds(0, 0, d.width, d.height);
        setPreferredSize(d);
        oneArmed = false;
        twoArmed = false;

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (1 == e.getButton()) {
                    cannonRecharge = 0;
                    cannonFiring = true;
                }
                if (3 == e.getButton()) {
                    missleRecharge = 0;
                    missleFiring = true;
                }
//                if (missleArmed) {
//                    missleRecharge = 0;
//                    missleFiring = true;
//                }
//                if (cannonArmed) {
//                    cannonRecharge = 0;
//                    cannonFiring = true;
//                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (1 == e.getButton()) {
                    cannonFiring = false;
                }
                if (3 == e.getButton()) {
                    missleFiring = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseLocation.setLocation(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseLocation.setLocation(e.getX(), e.getY());
            }
        });

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                int code = ke.getKeyCode();
                if ((code == 87) || (code == 38)) {
                    wPressed = true;
                }
                if ((code == 83) || (code == 40)) {
                    sPressed = true;
                }
                if ((code == 65) || (code == 37)) {
                    aPressed = true;
                }
                if ((code == 68) || (code == 39)) {
                    dPressed = true;
                }
                if (code == 16) {
                    shiftPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int code = ke.getKeyCode();

                if ((code == 65) || (code == 37)) {
                    aPressed = false;
                }
                if ((code == 68) || (code == 39)) {
                    dPressed = false;
                }
                if ((code == 87) || (code == 38)) {
                    wPressed = false;
                }
                if ((code == 83) || (code == 40)) {
                    sPressed = false;
                }
                if (code == 16) {
                    shiftPressed = false;
                }
                if (code == 49) {
                    oneArmed = true;
                    twoArmed = false;
                }
                if (code == 50) {
                    oneArmed = false;
                    twoArmed = true;
                }
            }
        });
    }
    
    public void setControlsEnabled(boolean b) {
        controlsEnabled = b;
    }

     public void processInput(java.util.LinkedList<BlockHouse> walls) {
        //Point2D point = new Point2D.Double(keyedPoint.getX(), keyedPoint.getY());
        Point2D point = new Point2D.Double();
        // point.setLocation(keyedPoint.getX(), keyedPoint.getY());
        if (shiftPressed) {
            acceleration = 1;
        } else {
            acceleration = 0;
        }

        if (!isFocusOwner()) {
            aPressed = false;
            dPressed = false;
            wPressed = false;
            sPressed = false;
            shiftPressed = false;
        }

        if(controlsEnabled) {
        if ((aPressed == true) && !(keyedPoint.getX() <= 0)) {
            point.setLocation(keyedPoint.getX(), keyedPoint.getY());
            keyedPoint.setLocation((keyedPoint.getX() - (1 + acceleration)), (keyedPoint.getY()));
            for (BlockHouse b : walls) {
                if (((java.awt.Shape) b.getOutline()).contains(keyedPoint)) {
                    for (Rectangle2D block : b.getBuildingBlocks()) {
                        if (block.contains(keyedPoint)) {
                            keyedPoint.setLocation((point.getX()), (point.getY()));
                            break;
                        }
                    }
                }
            }
        }
        //else
        //keyedPoint.setLocation((keyedPoint.getX()+(1)), (keyedPoint.getY()));
        if ((dPressed == true) && !(keyedPoint.getX() >= (getPreferredSize().getWidth()))) {
            point.setLocation(keyedPoint.getX(), keyedPoint.getY());
            keyedPoint.setLocation((keyedPoint.getX() + (1 + acceleration)), (keyedPoint.getY()));
            for (BlockHouse b : walls) {
                if (((java.awt.Shape) b.getOutline()).contains(keyedPoint)) {
                    for (Rectangle2D block : b.getBuildingBlocks()) {
                        if (block.contains(keyedPoint)) {
                            keyedPoint.setLocation((point.getX()), (point.getY()));
                            break;
                        }
                    }
                }
            }
        }
        //else
        //keyedPoint.setLocation((keyedPoint.getX()-(1)), (keyedPoint.getY()));
        if ((wPressed == true) && !(keyedPoint.getY() <= 0)) {
            point.setLocation(keyedPoint.getX(), keyedPoint.getY());
            keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY() - (1 + acceleration)));
            for (BlockHouse b : walls) {
                if (((java.awt.Shape) b.getOutline()).contains(keyedPoint)) {
                    for (Rectangle2D block : b.getBuildingBlocks()) {
                        if (block.contains(keyedPoint)) {
                            keyedPoint.setLocation((point.getX()), (point.getY()));
                            break;
                        }
                    }
                }
            }
        }
        //else
        //keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY()+(1)));
        if ((sPressed == true) && !(keyedPoint.getY() >= (getPreferredSize().getHeight()))) {
            point.setLocation(keyedPoint.getX(), keyedPoint.getY());
            keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY() + (1 + acceleration)));
            for (BlockHouse b : walls) {
                if (((java.awt.Shape) b.getOutline()).contains(keyedPoint)) {
                    for (Rectangle2D block : b.getBuildingBlocks()) {
                        if (block.contains(keyedPoint)) {
                            keyedPoint.setLocation((point.getX()), (point.getY()));
                            break;
                        }
                    }
                }
            }
        }
     }
        //else
        //keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY()-(1)));

//        if ((aPressed == true) && !(keyedPoint.getX() <= 0))
//            keyedPoint.setLocation((keyedPoint.getX()-(1+acceleration)), (keyedPoint.getY()));
//        else
//            keyedPoint.setLocation((keyedPoint.getX()+(1)), (keyedPoint.getY()));
//        if ((dPressed == true) && !(keyedPoint.getX() >= (getPreferredSize().getWidth()-20)))
//            keyedPoint.setLocation((keyedPoint.getX()+(1+acceleration)), (keyedPoint.getY()));
//        else
//            keyedPoint.setLocation((keyedPoint.getX()-(1)), (keyedPoint.getY()));
//        if ((wPressed == true) && !(keyedPoint.getY() <= 0))
//            keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY()-(1+acceleration)));
//        else
//            keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY()+(1)));
//        if ((sPressed == true) && !(keyedPoint.getY() >= (getPreferredSize().getHeight()-20)))
//            keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY()+(1+acceleration)));
//        else
//            keyedPoint.setLocation((keyedPoint.getX()), (keyedPoint.getY()-(1)));
//        
//        for (Object s : map) {
//                if (((java.awt.Shape)s).contains(keyedPoint))
//                    keyedPoint.setLocation((point.getX()), (point.getY()));
//        }
    }

    public void resetContols() {
        cannonFiring = false;
        missleArmed = false;
        missleFiring = false;
        missleReady = false;
        aPressed = false;
        dPressed = false;
        wPressed = false;
        sPressed = false;
        shiftPressed = false;
    }

    public void setKeyedPoint(Point p) {
        keyedPoint = p;
    }

    public Point keyedPoint = new Point(400, 500);
    public Point2D.Double mouseLocation = new Point2D.Double(0, 601);
    public int acceleration, cannonRecharge, missleRecharge;
    
    public boolean cannonArmed, cannonFiring, missleArmed, missleFiring, missleReady, aPressed, dPressed, wPressed, sPressed, shiftPressed, oneArmed, twoArmed;
    private boolean controlsEnabled = true;
}
