package BlockDudesDay;

import BlockClient.BlockClient;
import BlockDudesDay.Character.Player;
import BlockDudesDay.Foe.Foe;
import BlockDudesDay.Map.BlockHouse;
import BlockDudesDay.Map.Buildings;
import BlockDudesDay.Map.WallObjectSegment;
import BlockDudesDay.Projectile.Bullet;
import BlockDudesDay.Projectile.Missile;
import BlockDudesDay.Projectile.Projectile;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 *
 * @author Tech Terminal
 */
public class BlockLogic {

    public BlockLogic(BlockProblem problem) {
        //controls = new BlockControls(new Dimension(800,600));
        currentProblem = problem;

        
        bullet_speed = 3;
        respawnCounter = 90000;
        missle_speed_1 = 2;
        missle_speed_2 = 6;
        amountOfMissles = 1;

        FOETYPES = 12;
        MAXFOES = 25;
        buildingList = new Buildings(0);
        bulletList = new LinkedList<Projectile>();
        newBulletList = new LinkedList<Projectile>();
        previousFrameBulletList = new LinkedList<Projectile>();
        missileList = new LinkedList<Projectile>();
        foeList = new LinkedList<Foe>();
        mousePointRectangle = new Rectangle2D.Double(1, 1, 1, 1);
        player = new Player(400, 500, 12, 12, 100.0);
        lastNetworkPlayer = new Player(400, 500, 12, 12, 100.0);
        playerList = new LinkedList<Player>();
        //playingField = new Rectangle2D.Double(0,0,getPreferredSize().getWidth()+100,getPreferredSize().getHeight()+100);
        playingField = new Rectangle2D.Double(0, 0, 800, 600);
        processBuildings(buildingList);
    }

    public void addControls(BlockControls cont) {
        controls = cont;
        controls.oneArmed = true;
    }

    public boolean processLogic() {
        processPlayer();
        processProjectiles();
        //processFoes();
        
        return true;
    }
    
    private void processBuildings(Buildings buildings) {

        Buildings newBuildingList = new Buildings();
        for (BlockHouse b : buildings) {
            LinkedList blockList = new LinkedList<Rectangle2D>();
            for (Rectangle2D block : b.getBuildingBlocks()) {
                if (((WallObjectSegment) block).isPresent()) {
                    blockList.add(block);
                }
            }
            b.setBuildingBlocks(blockList);
            newBuildingList.add(b);
        }
        buildingList.setMapBuildings(newBuildingList);
    }

    private LinkedList<Projectile> addMissles(LinkedList aList) {
        LinkedList<Projectile> list = new LinkedList<Projectile>();
        double speed = 0;
        for (Object p : aList) {
            list.add((Projectile) p);
        }
        if (controls.oneArmed) {
            speed = missle_speed_1;
        }
//        if (controls.twoArmed)
//            speed = missle_speed_2;
        list.addFirst(new Missile(new Double(player.getX()),
                new Double(player.getY()),
                false,
                new Point2D.Double(new Double(player.getX()), new Double(player.getY())),
                controls.mouseLocation,
                speed));
        return list;
    }

    private LinkedList<Projectile> setMissles(LinkedList aList) {
        LinkedList<Projectile> list = new LinkedList<Projectile>();
        double speed = 0;
        int counter = aList.size();
        if (controls.oneArmed) {
            speed = missle_speed_1;
        }
        if (controls.twoArmed) {
            speed = missle_speed_2;
        }
        for (Object p : aList) {
            if (!playingField.intersects(((Projectile) p))) {
                ((Projectile) p).setFired(false);
            }
            if (((Projectile) p).isFired()) {
                ((Missile) p).setTranslateXY(new Point2D.Double(controls.mouseLocation.getX(), controls.mouseLocation.getY()));
                list.add(((Missile) p));
            } else if ((controls.oneArmed) || (controls.twoArmed)) {
                int t = this.time;
                list.addFirst(new Missile(new Double(player.getX()),
                        new Double(player.getY()),
                        false,
                        new Point2D.Double(new Double(player.getX()), new Double(player.getY())),
                        new Point2D.Double(controls.mouseLocation.getX(), controls.mouseLocation.getY()),
                        speed));
                counter--;
            }
        }
        return list;
    }


    /*
     *list.add((Projectile)new Bullet(
     *X                               new Double(shuttleRectangle.getX()+9), 
     *Y                               new Double(shuttleRectangle.getY()), 
     *fired                           true, 
     *StartPoint                      new Point2D.Double(new Double(shuttleRectangle.getX()+9), new Double(shuttleRectangle.getY())), 
     *EndPoint                        new Point2D.Double(new Double(mouseLocation.getX()), new Double(mouseLocation.getY())),
     *Speed                           3));
     */
    private LinkedList<Projectile> addBullets(LinkedList aList) {
        aList.add((Projectile) new Bullet(new Double(controls.keyedPoint.getX()), new Double(controls.keyedPoint.getY()), true, new Point2D.Double(new Double(player.getX()), new Double(player.getY())), new Point2D.Double(new Double(controls.mouseLocation.getX() + ((20 * Math.random()) + (0 - (20 * Math.random())))), new Double(controls.mouseLocation.getY() + ((20 * Math.random()) + (0 - (20 * Math.random()))))), bullet_speed));
        return aList;
    }

//    private LinkedList<Projectile> setBullets(LinkedList aList) {
//        LinkedList<Projectile> list = new LinkedList<Projectile>();
//        for (Object p : aList) {
//            if (!playingField.contains(((Projectile) p))) {
//                ((Projectile) p).setFired(false);
//            }
//            if (((Bullet) p).isFired()) {
//                ((Bullet) p).setTranslateXY();
//                list.add(((Bullet) p));
//            }
//        }
//        return list;
//    }
        private LinkedList<Projectile> setBullets(LinkedList aList) {
        LinkedList<Projectile> list = new LinkedList<Projectile>();
        LinkedList<Projectile> tempBulletList = new LinkedList(aList);
        Buildings tempBuildingsList = new Buildings(buildingList);
        for (Object p : tempBulletList) {
            if (!playingField.contains(((Projectile) p))) {
                ((Projectile) p).setFired(false);
            } else {
                for (BlockHouse b : tempBuildingsList) {

                    if ((b.getOutline().contains((Projectile) p)) || (b.getOutline().intersects((Projectile) p))) {
                        for (Rectangle2D block : b.getBuildingBlocks()) {
                            if ((block.contains((Projectile) p)) || (block.intersects((Projectile) p))) {

                                ((Projectile) p).setFired(false);
                                ((WallObjectSegment) block).takeDamage(((Projectile) p).getDamage());
                                if (!((WallObjectSegment)block).isPresent()) {
                                    //b.getBuildingBlocks().remove((WallObjectSegment)block);
                                    processBuildings(tempBuildingsList);
                                }

                            }
                        }
                    }
                }

            }
            if (((Bullet) p).isFired()) {
                ((Bullet) p).setTranslateXY();
                list.add(((Bullet) p));
            }
        }
        buildingList = new Buildings(tempBuildingsList);
        return list;
    }
    
    public void setInputBulletList(LinkedList aList) {
        if(bulletListUnlocked) {
            bulletList = new LinkedList(aList);
        }
    }

    private void processProjectiles() {
        //Cannon
        
        if(newBulletListUnlocked){
        newBulletListUnlocked = false;
        if (controls.cannonFiring) {
            controls.cannonRecharge++;
            if (controls.cannonRecharge > 20) {
                controls.cannonRecharge = 0;
            }
            if (controls.cannonRecharge == 1) {
                newBulletList = addBullets(newBulletList);
            }
        }
        }
        newBulletListUnlocked = true;
        LinkedList<Projectile> tempBulletList = new LinkedList();
        if(bulletListUnlocked) {
            bulletListUnlocked = false;
            tempBulletList = new LinkedList(bulletList);
            previousFrameBulletList = new LinkedList(tempBulletList);
            bulletListUnlocked = true;
        } else {
            tempBulletList = new LinkedList(previousFrameBulletList);
        }
        if (bulletList.size() != 0) {
            tempBulletList = setBullets(tempBulletList);
        }
        
        if(bulletListUnlocked) {
            bulletListUnlocked = false;
            bulletList = new LinkedList(tempBulletList);
            previousFrameBulletList = new LinkedList(tempBulletList);
            bulletListUnlocked = true;
        } else {
            tempBulletList = new LinkedList(previousFrameBulletList);
        }
        
        
        
        //Missle
        if ((missileList.size() < amountOfMissles)) {
            missileList = addMissles(missileList);
        }

        if (controls.missleFiring) {
            missileList.getFirst().setFired(true);
            controls.missleFiring = false;
        }
        missileList = setMissles(missileList);
    }


    public LinkedList<Foe> addZombies(LinkedList aList) {
        LinkedList<Foe> list = new LinkedList<Foe>();
        for (Object f : aList) {
            list.add((Foe) f);
        }
        return list;

    }

    private void processPlayer() {
        Player tempPlayer = new Player();
        LinkedList tempList = new LinkedList();
        
        playerUnlocked = false;
        player.setLocationPoint(controls.keyedPoint.getLocation());
        tempPlayer = new Player(player);
        playerUnlocked = true;
        
        
        foeListUnlocked = false;
        tempList = new LinkedList(foeList);
        foeListUnlocked = true;
        
        
        
        for (Object f : tempList) {
            //if ((f.intersects(player)) && ((time % 50) == 0))
            if ((((Foe)f).intersects(tempPlayer)) && ((time % 10) == 0)) {
                tempPlayer.setHealth(tempPlayer.getHealth() - ((Foe)f).getDamage());
            }
        }
        playerUnlocked = false;
        player.setHealth(tempPlayer.getHealth());
        playerUnlocked = true;
    }

    public int getLevel() {
        return currentProblem.currentState.getLevel();
    }

    public void resetPlayer() {
        int counter = 0;
        controls.setControlsEnabled(false);
        while (counter < respawnCounter) {
            
            counter++;
        }
        while(!playerUnlocked) {
        }
        if(playerUnlocked) {
            playerUnlocked = false;
        
            if (counter >= respawnCounter - 1) {
                if (player.getHealth() <= 0) {
                    player = new Player(400, 500, 12, 12, player.getStartHealth());
                    //foeList.clear();
                    System.out.println("respawned");
                    controls.keyedPoint = new Point(410, 510);
                    controls.setControlsEnabled(true);
                }
            }
            playerUnlocked = true;
        }
        
    }

    public double getPlayerHealth() {
        Player p = new Player();
        playerUnlocked = false;
        p = new Player(player);
        playerUnlocked = true;
        return p.getHealth();
    }

    public void setLevel(int l) {
        currentProblem.currentState.setFoesRemaining(l);
        currentProblem.currentState.setLevel(l);
    }

    public int getFoesRemaining() {
        return currentProblem.currentState.getFoesRemaining();
    }

    public void reset() {

        // This is where you can change the level to jump to a certain lvl for debugging.
        currentProblem.currentState.setLevel(0);
        amountOfKills = 0;
        //currentProblem.currentState.setFoesRemaining(1);
        controls.resetContols();
        player = new Player(400, 500, 12, 12, player.getStartHealth());
        foeList.clear();
        bulletList.clear();
        missileList.clear();
        controls.keyedPoint = new Point(410, 510);
    }
    
    public LinkedList<Projectile> getBulletList() {
        LinkedList list = new LinkedList();
        if(bulletListUnlocked) {
            list = new LinkedList(bulletList);
        }
        return list;
    }
    
    public LinkedList<Projectile> getNewBulletList() {
        LinkedList list = new LinkedList();
        if(newBulletListUnlocked) {
            list = new LinkedList(newBulletList);
            newBulletList.clear();
        }
        return list;
    }
    
    public void setBulletList(LinkedList<Projectile> l) {
        if(bulletListUnlocked) {
            bulletList = new LinkedList<Projectile>(l);
        }
    }
    
    public void setNewBulletList(LinkedList<Projectile> l) {
        if(newBulletListUnlocked) {
            newBulletList = new LinkedList<Projectile>(l);
        }
    }
    
    public LinkedList<Projectile> getMissileList() {
        return missileList;
    }
    
    public void setMissileList(LinkedList<Projectile> l) {
        missileList = new LinkedList<Projectile>(l);
    }
    
    public LinkedList<Foe> getFoeList() {
        LinkedList list = new LinkedList();
        if(foeListUnlocked) {
            list = new LinkedList(foeList);
        }
        return list;
    }
    
    public void setFoeList(LinkedList<Foe> l) {
        if(foeListUnlocked) {
            foeList = new LinkedList<Foe>(l);
        }
    }
    
    public void setBlockClient(BlockClient c) {
        client = c;
    }
    
    public void clearBlockClient() {
        client.stopClient();
        client = null;
    }
    
    public Player getPlayer() {
        Player p = new Player();
        if(playerUnlocked) {
            lastNetworkPlayer = new Player(player);
            p = player;
        } else {
            p = lastNetworkPlayer;
        }
        return p;
    }
    
    public void setPlayerList(LinkedList l) {
        if(playerListUnlocked)
        playerList = new LinkedList(l);
    }
    
    public LinkedList getPlayerList() {
        LinkedList list = new LinkedList();
        if(playerListUnlocked) {
            list = new LinkedList(playerList);
        }
        return list;
    }
    
    public BlockControls controls;
    private BlockProblem currentProblem;
    private int time = 0;
    private int amountOfMissles;
    public int amountOfKills;
    private final int FOETYPES;
    private final int MAXFOES;
    public int respawnCounter;
    public double missle_speed_2, missle_speed_1, bullet_speed;
    public BlockClient client;
    public LinkedList<Projectile> missileList;
    public LinkedList<Projectile> bulletList;
    public LinkedList<Projectile> newBulletList;
    public LinkedList<Projectile> previousFrameBulletList;
    public LinkedList<Player> playerList;
    public LinkedList<Foe> foeList;
    public Buildings buildingList;
    public boolean bulletListUnlocked = true;
    public boolean newBulletListUnlocked = true;
    public boolean playerListUnlocked = true;
    public boolean playerUnlocked = true;
    public boolean foeListUnlocked = true;
    private Rectangle2D playingField;
    public Rectangle2D mousePointRectangle;
    public Player player;
    public Player lastNetworkPlayer;
    

}
