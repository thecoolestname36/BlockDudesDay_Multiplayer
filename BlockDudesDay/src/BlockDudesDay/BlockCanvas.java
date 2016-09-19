/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;

import BlockDudesDay.Character.Player;
import BlockDudesDay.Foe.Foe;
import BlockDudesDay.Foe.Zombie;
import BlockDudesDay.Map.BlockHouse;
import BlockDudesDay.Projectile.Missile;
import BlockDudesDay.Projectile.Projectile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 *
 * @author brad
 */
public class BlockCanvas extends JComponent{

    public BlockCanvas(BlockLogic logic) {
        super();
        setPreferredSize(new Dimension(800, 600));
        controls = new BlockControls(new Dimension(800, 600));
        this.add(controls);
        blockLogic = logic;
        blockLogic.addControls(controls);
        playingField = new Rectangle2D.Double(0, 0, getPreferredSize().getWidth() + 100, getPreferredSize().getHeight() + 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(new Color(120, (120 - blockLogic.getLevel()), 0));
        if (blockLogic.getLevel() >= 30) {
            g2D.setColor(new Color((120 - (blockLogic.getLevel() - 30)), (120 - blockLogic.getLevel()), 0));
        }
        g2D.fill(playingField);

        for(Object p: blockLogic.getPlayerList()) {
            g2D.setColor(((Player)p).getHealthColor());
            g2D.fill((Player)p);
        }

        //Draw Missles
        g2D.setColor(orange);
        for (Projectile p : blockLogic.missileList) {
            if (!playingField.contains(p)) {
                p.setFired(false);
            }
            if (((Missile) p).getSpeed() == blockLogic.missle_speed_1) {
                g2D.fill(p);
            }
        }
        g2D.setColor(white);
        for (Projectile p : blockLogic.bulletList) {
            g2D.draw(p);
        }

        for (Foe f : blockLogic.foeList) {
            g2D.setColor(f.getHealthColor());
            g2D.fill((Zombie) f);
        }

        if (blockLogic.player.intersects(blockLogic.mousePointRectangle)) {
            g2D.setColor(blue);
        } else {
            g2D.setColor(yellow);
        }
        g2D.draw(blockLogic.mousePointRectangle);

        g2D.setColor(black);
        g2D.fill(new Rectangle2D.Double(250, 0, 300, 22));

        g2D.setColor(red);
        //g2D.drawString("Health: " + Integer.toString((int) (blockLogic.player.getHealth() * 10)) + "   Zombies Left: " + Integer.toString(blockLogic.getFoesRemaining()) + "   Level: " + Integer.toString(blockLogic.getLevel()) + "   Kills: " + Integer.toString(blockLogic.amountOfKills), 260, 15);
        g2D.drawString("Health: " + Integer.toString((int) (blockLogic.player.getHealth())), 260, 15);
        g2D.setColor(black);
        for (BlockHouse b : blockLogic.buildingList) {
            for (Rectangle2D block : b.getBuildingBlocks()) {
                g2D.fill(block);
            }
        }
// TROUBLESHOOTING WALL COLLISION DETECTION
//            g2D.setColor(red);
//            g2D.draw(b.getPreciseOutline());
//            g2D.setColor(black);
////
        

        if (blockLogic.getLevel() == 0) {
            //Intro Screen
            displayIntroScren(g2D);   
        }
//        else if ((blockLogic.getLevel() > 0)&& (blockLogic.getPlayerHealth() <= 0.0)) {
//            displayDeathScreen(g2D);
//        }
    }

    public void displayDeathScreen(java.awt.Graphics2D g2D) {
        int deathX = 250;
        int deathY = 320;
        g2D.setColor(darkerRed);
        g2D.fill(new Rectangle2D.Double(deathX, deathY-20, 300, 180));
        g2D.setColor(white);
        g2D.drawString("YOU HAVE DIED!", deathX+30, deathY+5);
        g2D.drawString("Controls:", deathX + 30, deathY + 30);
        g2D.drawString("Aim With Your Mouse", deathX + 50, deathY + 45);
        g2D.drawString("Left Click Fires Cannon", deathX + 50, deathY + 56);
        g2D.drawString("Right Click Launches an Explosive", deathX + 50, deathY + 67);
        g2D.drawString("W = Move Up", deathX + 50, deathY + 78);
        g2D.drawString("A = Move Left", deathX + 50, deathY + 89);
        g2D.drawString("S = Move Down", deathX + 50, deathY + 100);
        g2D.drawString("D = Move Right", deathX + 50, deathY + 111);
        g2D.drawString("Press Shift to Move Faster", deathX + 50, deathY + 122);
    }

    public void displayIntroScren(java.awt.Graphics2D g2D) {
        g2D.setColor(black);
        g2D.fill(new Rectangle2D.Double(0, 0, 800, 600));
        g2D.setColor(darkRed);

        Font jugLabelFont = new Font(Font.MONOSPACED, Font.TRUETYPE_FONT, 75);
        FontRenderContext fontRenderContext = getFontMetrics(jugLabelFont).getFontRenderContext();
        String title = "Block Dude's Day";
        for (int i = 0; i < title.length(); i++) {
            GlyphVector titleGlyph = jugLabelFont.createGlyphVector(fontRenderContext, Character.toString(title.charAt(i)));
            titleGlyph.setGlyphPosition(0, new Point2D.Double((75 + (i * 40)), 100));
            g2D.fill(titleGlyph.getGlyphOutline(0));
        }

        //Intro
        g2D.fill(new Rectangle2D.Double(80, 114, 640, 486));
        g2D.setColor(white);
        int introX = 100;
        int introY = 125;
        g2D.drawString("Block Dude was once but an ordinary block. His days were spent blocking traffic, blocking toilets, and blocking", introX, introY);
        g2D.drawString("pop-ups in browsers.  Then there came the zombies! Swarms of nasty, gory, barrier-eliminating zombies", introX, introY + 13);
        //g2D.drawString("", introX, introY+34);
        g2D.drawString("started to spread and multiply at rates that would frighten even rabbits. Who will stop them? Who will stand ", introX, introY + 26);
        g2D.drawString("in the way of this hungry hoard?!", introX, introY + 39);
        g2D.drawString("This is Block Dude. And this is his day.", introX + 185, introY + 150);

        //Controls Info
        int controlsX = 250;
        int controlsY = 320;
        g2D.setColor(darkerRed);
        g2D.fill(new Rectangle2D.Double(controlsX, controlsY, 300, 150));
        g2D.setColor(white);
        g2D.drawString("Controls:", controlsX + 30, controlsY + 30);
        g2D.drawString("Aim With Your Mouse", controlsX + 50, controlsY + 45);
        g2D.drawString("Left Click Fires Cannon", controlsX + 50, controlsY + 56);
        g2D.drawString("Right Click Launches an Explosive", controlsX + 50, controlsY + 67);
        g2D.drawString("W = Move Up", controlsX + 50, controlsY + 78);
        g2D.drawString("A = Move Left", controlsX + 50, controlsY + 89);
        g2D.drawString("S = Move Down", controlsX + 50, controlsY + 100);
        g2D.drawString("D = Move Right", controlsX + 50, controlsY + 111);
        g2D.drawString("Press Shift to Move Faster", controlsX + 50, controlsY + 122);
    }

    public void render() {
        //Timer
        if (time >= 10000) {
            time = 0;
        }
        //IO & Error Checking
        Point previousPoint = new Point((int) controls.keyedPoint.getX(), (int) controls.keyedPoint.getY());
        if ((time % 1) == 0) {
            controls.processInput(blockLogic.buildingList);
        }
        blockLogic.processLogic();

        //Ship & Background
        blockLogic.mousePointRectangle.setRect((controls.mouseLocation.getX()), (controls.mouseLocation.getY()), 6, 6);
        repaint();
        time++;
    }

    public void setPanelSize(Dimension d) {
        setPreferredSize(d);
        playingField = new Rectangle2D.Double(0, 0, getPreferredSize().getWidth(), getPreferredSize().getHeight());
        //controls = new BlockControls(d);
        controls.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight());
        controls.setPreferredSize(d);
    }

    public void reset() {

        blockLogic.reset();

        render();
    }

    public int getGameLevel() {
        return blockLogic.getLevel();
    }

    public void setGameLevel(int l) {
        blockLogic.setLevel(l);
    }
    
    public BlockLogic getBlockLogic() {
        return blockLogic;
    }
    
    public void setLogic(BlockLogic l) {
        blockLogic = l;
    }
    
    public BlockLogic getblockLogic() {
        return blockLogic;
    }

    public BlockControls controls;
    public BlockLogic blockLogic;
    private int time = 0;
    private Rectangle2D playingField;
    private final Color black = new Color(0, 0, 0);
    private final Color blue = new Color(0, 0, 255);
    private final Color red = new Color(255, 0, 0);
    private final Color darkRed = new Color(102, 0, 0);
    private final Color darkerRed = new Color(60, 0, 0);
    private final Color orange = new Color(255, 128, 0);
    private final Color yellow = new Color(255, 255, 0);
    private final Color white = new Color(255, 255, 255);

}
