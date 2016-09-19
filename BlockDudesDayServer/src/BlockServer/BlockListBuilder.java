/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockServer;

import BlockDudesDay.Character.Player;
import BlockDudesDay.Foe.Zombie;
import BlockDudesDay.Projectile.Bullet;
import java.util.LinkedList;

/**
 *
 * @author Brad
 */
public class BlockListBuilder {

    private LinkedList zombieList;
    private LinkedList bulletList;
    private Player player;
    private String inputString;
    private int listType;

   // |ZERO|[BlockDudesDay.Foe.Zombie[x=0,y=0,w=10,h=10,v=1,hp=4.0,shp=4.0,d=1.0,s=Point2D.Double[422.8737313042174, 0.0],e=Point2D.Double[400.0, 500.0]]]|ONE|[BlockDudesDay.Projectile.Bullet[x=400.0,y=500.0,w=2.0,h=2.0,v=2.0,s=Point2D.Double[394.0, 494.0],e=Point2D.Double[413.23, 81.02]], BlockDudesDay.Projectile.Bullet[x=400.0,y=500.0,w=2.0,h=2.0,v=2.0,s=Point2D.Double[394.0, 494.0],e=Point2D.Double[413.23, 81.02]]]|TWO|
    /**
     * A string with a leading number indicating what type of list it is.
     *
     * @param s
     */
    public BlockListBuilder(String str) {
        try {
        inputString = new String(str);
        zombieList = new LinkedList();
        bulletList = new LinkedList();
        player = new Player();
        parseBullets(inputString.substring(7, inputString.indexOf("|ONE|") - 1));
        parsePlayer(inputString.substring(inputString.indexOf("|ONE|") + 5, inputString.indexOf("|TWO|") - 1));
        } catch(NullPointerException ex) {
            System.out.println("LISTBUILDER:BlockListBuilder:" + ex.getMessage());
            
        }
    }

    /**
     * BlockDudesDay.Foe.Zombie[x=0,y=0,w=10,h=10,v=1,hp=4.0,shp=4.0,d=1.0,s=Point2D.Double[379.51385435161967,
     * 0.0],e=Point2D.Double[394.0, 494.0]],
     * BlockDudesDay.Foe.Zombie[x=0,y=0,w=10,h=10,v=1,hp=4.0,shp=4.0,d=1.0,s=Point2D.Double[409.82005977113823,
     * 0.0],e=Point2D.Double[394.0, 494.0]] List with leading 0.
     */
    private void parseZombies(String s) {
        LinkedList alist = new LinkedList();
        while (s.indexOf("BlockDudesDay.Foe.Zombie") > -1) {
            s = s.substring(s.indexOf("BlockDudesDay.Foe.Zombie"));
            int substrEnd = s.indexOf("]]") + 1;
            alist.add(new Zombie(s.substring(s.indexOf("BlockDudesDay.Foe.Zombie"), substrEnd)));
            s = s.substring(s.indexOf("]]") + 2);
        }
        zombieList = new LinkedList(alist);
    }

    /**
     * List with leading 1.
     * 1[BlockDudesDay.Projectile.Bullet[x=400.0,y=500.0,w=2.0,h=2.0,v=2.0,s=Point2D.Double[394.0,
     * 494.0],e=Point2D.Double[413.23, 81.02]]]
     */
    private void parseBullets(String s) {
        LinkedList alist = new LinkedList();
        while (s.indexOf("BlockDudesDay.Projectile.Bullet") > -1) {
            s = s.substring(s.indexOf("BlockDudesDay.Projectile.Bullet"));
            int substrEnd = s.indexOf("]]") + 1;
            alist.add(new Bullet(s.substring(s.indexOf("BlockDudesDay.Projectile.Bullet"), substrEnd)));
            s = s.substring(s.indexOf("]]") + 2);
        }
        bulletList = new LinkedList(alist);
    }

    /**
     * @param s * List with leading 2.
     */
    public void parsePlayer(String s) {
        Player p = new Player();
        String playerID = s.substring(0, s.indexOf("["));
        while (s.indexOf("BlockDudesDay.Character.Player") > -1) {
            s.substring(s.indexOf("BlockDudesDay.Character.Player"));
            int substrEnd = s.indexOf("]") + 1;
            p = new Player(s.substring(s.indexOf("BlockDudesDay.Character.Player"), substrEnd));
            s = s.substring(s.indexOf("]") + 1);
        }
        player = new Player(p);
        if (!playerID.equals("[")) {
            player.setPlayerID(playerID);
        }
    }

    /**
     * Returns 0 for zombieList, 1 for bulletList, 2 for playerList.
     *
     * @return int listType;
     */
    public int getListType() {
        return listType;
    }

    public LinkedList getZombieList() {
        return zombieList;
    }

    public LinkedList getNewBulletList() {
        return bulletList;
    }

    public Player getPlayer() {
        return player;
    }

}
