package BlockDudesDay;

import BlockDudesDay.Character.Player;
import BlockDudesDay.Foe.Foe;
import BlockDudesDay.Foe.Zombie;
import BlockDudesDay.Map.BlockHouse;
import BlockDudesDay.Map.Buildings;
import BlockDudesDay.Map.WallObjectSegment;
import BlockDudesDay.Projectile.Bullet;
import BlockDudesDay.Projectile.Projectile;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 *
 * @author Tech Terminal
 */
public class BlockServerLogic {

    public BlockServerLogic(BlockProblem problem) {
        currentProblem = problem;
        bullet_speed = 3;
        missle_speed_1 = 2;
        missle_speed_2 = 6;
        amountOfMissles = 1;
        FOETYPES = 12;
        MAXFOES = 25;
        respawnCounter = 0;
        bulletList = new LinkedList<Projectile>();
        newBulletList = new LinkedList<Projectile>();
        previousFrameBulletList = new LinkedList<Projectile>();
        missileList = new LinkedList<Projectile>();
        foeList = new LinkedList<Foe>();
        playerList = new LinkedList<Player>();
        previousFramePlayerList = new LinkedList<Player>();
        buildingList = new Buildings(0);
        previousFrameBuildingList = new Buildings(0);
        mousePointRectangle = new Rectangle2D.Double(1, 1, 1, 1);
        player = new Player(400, 500, 12, 12, 100.0);
        playingField = new Rectangle2D.Double(0, 0, 800, 600);
        processBuildings(buildingList);
    }

    public boolean processLogic() {
        processProjectiles();
        processFoes();

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

    public void addBullets(LinkedList aList) {
        // Get information from clients and add bullets here
        if (bulletListUnlocked) {
            bulletListUnlocked = false;
            for (Object p : aList) {
                bulletList.add((Projectile) p);
            }
            bulletListUnlocked = true;
        }
    }

    private LinkedList<Projectile> setBullets(LinkedList aList) {
        LinkedList<Projectile> list = new LinkedList<Projectile>();
        Buildings tempBuildingList = new Buildings(buildingList);
        LinkedList<Projectile> tempBulletList = new LinkedList(aList);
        for (Object p : tempBulletList) {
            if (!playingField.contains(((Projectile) p))) {
                ((Projectile) p).setFired(false);
            } else {
                for (BlockHouse b : tempBuildingList) {
                    if ((b.getOutline().contains((Projectile) p)) || (b.getOutline().intersects((Projectile) p))) {
                        for (Rectangle2D block : b.getBuildingBlocks()) {
                            if ((block.contains((Projectile) p)) || (block.intersects((Projectile) p))) {
                                ((Projectile) p).setFired(false);
                                ((WallObjectSegment) block).takeDamage(((Projectile) p).getDamage());
                                if (!((WallObjectSegment)block).isPresent()) {
                                    //b.getBuildingBlocks().remove((WallObjectSegment)block);
                                    processBuildings(tempBuildingList);
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
        buildingList = new Buildings(tempBuildingList);
        return list;
    }
    
    private void processProjectiles() {
        //Cannon
        LinkedList<Projectile> tempBulletList = new LinkedList();
        if (bulletListUnlocked) {
            bulletListUnlocked = false;
            tempBulletList = new LinkedList(bulletList);
            previousFrameBulletList = new LinkedList(tempBulletList);
            bulletListUnlocked = true;
        } else {
            tempBulletList = new LinkedList(previousFrameBulletList);
        }
        
        if (bulletList.size() != 0) {
                tempBulletList = setBullets(new LinkedList(tempBulletList));
        }
        if (bulletListUnlocked) {
            bulletListUnlocked = false;
            bulletList = new LinkedList(tempBulletList);
            previousFrameBulletList = new LinkedList(tempBulletList);
            bulletListUnlocked = true;
        } else {
            tempBulletList = new LinkedList(previousFrameBulletList);
        }
        
    }

    private LinkedList<Foe> addZombies(LinkedList aList) {
        LinkedList<Foe> list = new LinkedList<Foe>();
        for (Object f : aList) {
            list.add((Foe) f);
        }
        

        if (currentProblem.currentState.getLevel() <= 3) {
            list.add((Foe) new Zombie(new Point2D.Double((Math.random() * 50) + 375, 10), new Point2D.Double((Math.random() * 50) + 375, 10), 0));
        } else if (currentProblem.currentState.getLevel() <= 6) {
            list.add((Foe) new Zombie(new Point2D.Double(790, (Math.random() * 100)), new Point2D.Double(790, (Math.random() * 100)), (1 - (Math.random() * 1))));
        } else if (currentProblem.currentState.getLevel() <= 8) {
            list.add((Foe) new Zombie(new Point2D.Double((Math.random() * 100), 10), new Point2D.Double((Math.random() * 100), 10), (2 - (Math.random() * 2))));
        } else if (currentProblem.currentState.getLevel() <= 10) {
            list.add((Foe) new Zombie(new Point2D.Double(400, 590), new Point2D.Double(400, 590), (3 - (Math.random() * 3))));
        } else if (currentProblem.currentState.getLevel() <= 12) {
            list.add((Foe) new Zombie(new Point2D.Double(10, (Math.random() * 580)+10), new Point2D.Double(10, (Math.random() * 580)+10), (FOETYPES - (Math.random() * FOETYPES))));
        } else if (currentProblem.currentState.getLevel() <= 15) {
            //list.add((Foe)new Zombie(new Point2D.Double(player.getX(), player.getY()), new Point2D.Double((Math.random()*800), (Math.random()*600)), ((FOETYPES+(currentProblem.currentState.getLevel() -30)) - (Math.random() * (FOETYPES+(currentProblem.currentState.getLevel() -30))))));
            list.add((Foe) new Zombie(new Point2D.Double(10, (Math.random() * 580)+10), new Point2D.Double(10, (Math.random() * 580)+10), (FOETYPES - (Math.random() * FOETYPES))));
            list.add((Foe) new Zombie(new Point2D.Double((Math.random() * 780)+10, 590), new Point2D.Double((Math.random() * 780)+10, 590), (FOETYPES - (Math.random() * FOETYPES))));
        } else {
            //list.add((Foe)new Zombie(new Point2D.Double(player.getX(), player.getY()), new Point2D.Double((Math.random()*800), (Math.random()*600)), ((FOETYPES+(currentProblem.currentState.getLevel() -30)) - (Math.random() * (FOETYPES+(currentProblem.currentState.getLevel() -30))))));
            list.add((Foe) new Zombie(new Point2D.Double(10, (Math.random() * 580)+10), new Point2D.Double(10, (Math.random() * 580)+10), (FOETYPES - (Math.random() * FOETYPES))));
            list.add((Foe) new Zombie(new Point2D.Double(790, (Math.random() * 580)+10), new Point2D.Double(790, (Math.random() * 580)+10), (FOETYPES - (Math.random() * FOETYPES))));
            list.add((Foe) new Zombie(new Point2D.Double((Math.random() * 780)+10, 590), new Point2D.Double((Math.random() * 780)+10, 590), (FOETYPES - (Math.random() * FOETYPES))));
            list.add((Foe) new Zombie(new Point2D.Double((Math.random() * 780)+10, 10), new Point2D.Double((Math.random() * 780)+10, 10), (FOETYPES - (Math.random() * FOETYPES))));
        }
        return list;
//        if (currentProblem.currentState.getLevel() <= 30) {
//            if (Math.random() <= 0.25)
//                list.add((Foe)new Zombie(new Point2D.Double(player.getX(), player.getY()), new Point2D.Double(760, (Math.random()*600)), (FOETYPES - (Math.random() * FOETYPES))));
//            else if (Math.random() <= 0.5)
//                list.add((Foe)new Zombie(new Point2D.Double(player.getX(), player.getY()), new Point2D.Double((Math.random()*800), 560), (FOETYPES - (Math.random() * FOETYPES))));
//            else if (Math.random() <= 0.75)
//                list.add((Foe)new Zombie(new Point2D.Double(player.getX(), player.getY()), new Point2D.Double(60, (Math.random()*600)), (FOETYPES - (Math.random() * FOETYPES))));
//            else
//                list.add((Foe)new Zombie(new Point2D.Double(player.getX(), player.getY()), new Point2D.Double((Math.random()*800), 60), (FOETYPES - (Math.random() * FOETYPES))));
//        }
    }

    private LinkedList<Foe> setFoes(LinkedList aList) {
        LinkedList<Foe> list = new LinkedList<Foe>();
        LinkedList tempPlayerList = new LinkedList();
        if (playerListUnlocked) {
            playerListUnlocked = false;
            try {
                tempPlayerList = new LinkedList(playerList);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("BLOCKSERVERLOGIC:setFoes:" + ex.getMessage());
            }
            playerListUnlocked = true;
        }
        if ((tempPlayerList == null) || (tempPlayerList.size() == 0)) {
            tempPlayerList = new LinkedList(previousFramePlayerList);
        }

        Player tempPlayer = new Player();
        try {
            for (Object f : aList) {
                if (((Foe) f).isAlive()) {
                    tempPlayer = closestPlayerToFoe(tempPlayerList, (Foe) f);
                    ((Foe) f).setTargetLocation(tempPlayer.x, tempPlayer.y);
                    if (!((Foe) f).intersects((Rectangle2D) tempPlayer)) {
                        ((Foe) f).setTranslateXY(new Point2D.Double(tempPlayer.x, tempPlayer.y), buildingList);
                    }
                    list.add(((Foe) f));
                }
            }
            //}
            previousFramePlayerList = new LinkedList();
            for (Object p : tempPlayerList) {
                previousFramePlayerList.add((Player) p);
            }
        } catch (NullPointerException ex) {
            //list = new LinkedList(previousFrameFoeList);
            System.out.println("BLOCKLOGIC:setFoes:" + ex.getMessage() + "\n"
                    + "tempplayerList: " + tempPlayerList.toString() + "\n"
                    + "aList: " + aList.toString() + "\n"
                    + "list: " + list.toString() + "\n"
                    + "tempPlayer: " + tempPlayer.toString()
            );
        }

        return list;
    }

    private Player closestPlayerToFoe(LinkedList l, Foe f) {
        Player closeP = new Player();
        double shortestD = 1000;
        double distance = 0;
        for (Object p : l) {
            double deltaX = Math.abs(((Player) p).getX()) - Math.abs(f.getX());
            double deltaY = Math.abs(((Player) p).getY()) - Math.abs(f.getY());
            distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
            if (distance < shortestD) {
                shortestD = distance;
                closeP = (Player) p;
            }
        }
        return closeP;
    }

    private void processFoes() {
        
        if (bulletListUnlocked && foeListUnlocked) {
            bulletListUnlocked = false;
            foeListUnlocked = false;
            if ((foeList.size() < currentProblem.currentState.getLevel()) && (foeList.size() <= MAXFOES) && (!(foeList.size() >= currentProblem.currentState.getFoesRemaining()))) {
                foeList = addZombies(foeList);
            }
            foeList = setFoes(foeList);

            for (Projectile p : bulletList) {
                for (Foe f : foeList) {
                    if (p.intersects(f)) {
                        f.setHealth(f.getHealth() - p.getDamage());
                        p.setFired(false);
                        if (f.getHealth() <= 0) {
                            currentProblem.currentState.setFoesRemaining(currentProblem.currentState.getFoesRemaining() - 1);
                            amountOfKills++;
                        }
                    }
                }
            }
            bulletListUnlocked = true;
            if (currentProblem.currentState.getFoesRemaining() == 0) {
                currentProblem.currentState.setLevel(currentProblem.currentState.getLevel() + 1);
                if (player.getHealth() < player.maxHealth()) {
                    player.setHealth(player.getHealth() + (currentProblem.currentState.getLevel() * 0.05));
                }
            }
            foeListUnlocked = true;
        }
    }

    public int getLevel() {
        return currentProblem.currentState.getLevel();
    }

    public void resetPlayer() {
        if (player.getHealth() <= 0) {
            player = new Player(400, 500, 20, 20, player.getStartHealth());
            //foeList.clear();
        }
    }

    public double getPlayerHealth() {
        return player.getHealth();
    }

    public void setLevel(int l) {
        currentProblem.currentState.setFoesRemaining(l);
        currentProblem.currentState.setLevel(l);
    }

    public int getFoesRemaining() {
        return currentProblem.currentState.getFoesRemaining();
    }

    public void setFoeList(LinkedList l) {
        if (foeListUnlocked) {
            foeListUnlocked = false;
            foeList = l;
            foeListUnlocked = true;
        }
    }

    public LinkedList getFoeList() {
        LinkedList list = new LinkedList();
        if (foeListUnlocked) {
            foeListUnlocked = false;
            list = new LinkedList(foeList);
            foeListUnlocked = true;
        }
        return list;
    }

    public void setPlayerList(Player userP) {

        if (playerListUnlocked) {
            playerListUnlocked = false;
            if (userP.getPlayerID().length() > 0) {
                if (java.lang.Integer.parseInt(userP.getPlayerID()) >= 0) {
                    playerList.remove(userP);
                    playerList.add(userP);
                }
            }
            playerListUnlocked = true;
        }
    }

    /**
     * This function will already be locked when called. This function should
     * not be called anywhere else besides in the server receive thread.
     *
     * @param id
     */
    public void removePlayerFromList(int id) {
        Player player = new Player();
        for (Player p : playerList) {
            if (java.lang.Integer.parseInt(p.getPlayerID()) == id) {
                playerList.remove(p);
                break;
            }
        }
    }

    public LinkedList getPlayerList() {
        LinkedList list = new LinkedList();
        if (playerListUnlocked) {
            playerListUnlocked = false;
            try {
                list = new LinkedList(playerList);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("BLOCKLOGIC:getPlayerList:" + ex.getMessage());
            }
            playerListUnlocked = true;
        }
        return list;
    }

    public LinkedList<Projectile> getBulletList() {
        LinkedList list = new LinkedList();
        try {
            if (bulletListUnlocked) {
                bulletListUnlocked = false;
                list = new LinkedList(bulletList);
                bulletListUnlocked = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("SERVERLOGIC:getBulletList:" + ex.getMessage());
        }
        return list;
    }

    public LinkedList<Projectile> getNewBulletList() {
        LinkedList list = new LinkedList();
        if (newBulletListUnlocked) {
            newBulletListUnlocked = false;
            list = new LinkedList(newBulletList);
            newBulletListUnlocked = true;
        }
        return list;
    }

    public void setBulletList(LinkedList<Projectile> l) {
        bulletList = new LinkedList<Projectile>(l);
    }

    public void setNewBulletList(LinkedList<Projectile> l) {
        if (newBulletListUnlocked) {
            newBulletListUnlocked = false;
            newBulletList = new LinkedList<Projectile>(l);
            newBulletListUnlocked = true;
        }
    }

    public void reset() {

        // This is where you can change the level to jump to a certain lvl for debugging.
        currentProblem.currentState.setLevel(0);
        amountOfKills = 0;
        //currentProblem.currentState.setFoesRemaining(1);
        player = new Player(400, 500, 12, 12, player.getStartHealth());
        foeList.clear();
        bulletList.clear();
        missileList.clear();
    }

    private BlockProblem currentProblem;
    private int time = 0;
    private int respawnCounter;
    private int amountOfMissles;
    public int amountOfKills;
    private final int FOETYPES;
    private final int MAXFOES;
    public double missle_speed_2, missle_speed_1, bullet_speed;
    public LinkedList<Projectile> missileList;
    public LinkedList<Projectile> bulletList;
    public LinkedList<Projectile> newBulletList;
    public LinkedList<Projectile> previousFrameBulletList;
    public LinkedList<Player> playerList;
    public LinkedList<Foe> foeList;
    public LinkedList<Player> previousFramePlayerList;
    public Buildings buildingList;
    public Buildings previousFrameBuildingList;
    private Rectangle2D playingField;
    public Rectangle2D mousePointRectangle;
    public Player player;
    public boolean bulletListUnlocked = true;
    public boolean foeListUnlocked = true;
    public boolean playerListUnlocked = true;
    public boolean newBulletListUnlocked = true;
    public boolean buildingListUnlocked = true;
}
