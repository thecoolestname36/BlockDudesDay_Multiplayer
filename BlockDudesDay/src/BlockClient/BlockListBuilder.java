package BlockClient;

import BlockDudesDay.Character.Player;
import BlockDudesDay.Foe.Zombie;
import BlockDudesDay.Projectile.Bullet;
import java.util.LinkedList;

/**
 * Block list builder is designed to disassemble the data sent from the server.
 * @author Bradley Cutshall
 */
public class BlockListBuilder {
    
    private LinkedList zombieList;
    private LinkedList bulletList;
    private LinkedList playerList;
    private String inputString;
    private int listType;
    
    /**
     * Default constructor.
     */
    public BlockListBuilder() {
        inputString = new String();
        zombieList = new LinkedList();
        bulletList = new LinkedList();
        playerList = new LinkedList();
    }
    
   // 
    /**
     * The list builder will disassemble the pre-assembled string sent from
     * the server it's connected to. Format must be...
     * |ZERO|(zombie data)|ONE|(bullet data)|TWO|(other players data)|THREE|
     * @param s 
     */
    public BlockListBuilder(String str) {
        inputString = new String(str);
        zombieList = new LinkedList();
        bulletList = new LinkedList();
        playerList = new LinkedList();
        
        parseZombies(inputString.substring(7, inputString.indexOf("|ONE|")-1));
        parseBullets(inputString.substring(inputString.indexOf("|ONE|")+6, inputString.indexOf("|TWO|")-1));
        
        parsePlayers(inputString.substring(inputString.indexOf("|TWO|")+6, inputString.indexOf("|THREE|")-1));
        
    }
    
    /**
     * BlockDudesDay.Foe.Zombie[x=0,y=0,w=10,h=10,v=1,hp=4.0,shp=4.0,d=1.0,s=Point2D.Double[379.51385435161967, 0.0],e=Point2D.Double[394.0, 494.0]], BlockDudesDay.Foe.Zombie[x=0,y=0,w=10,h=10,v=1,hp=4.0,shp=4.0,d=1.0,s=Point2D.Double[409.82005977113823, 0.0],e=Point2D.Double[394.0, 494.0]]
     * List with leading 0.
     */
    private void parseZombies(String s) {
        LinkedList alist = new LinkedList();
        while(s.indexOf("BlockDudesDay.Foe.Zombie") > -1) {
            s = s.substring(s.indexOf("BlockDudesDay.Foe.Zombie"));
            int substrEnd = s.indexOf("]]") + 1;
            alist.add(new Zombie(s.substring(s.indexOf("BlockDudesDay.Foe.Zombie"), substrEnd)));
            s = s.substring(s.indexOf("]]") + 2);
        }
        zombieList = new LinkedList(alist);
    }
    
    /**
     * List with leading 1.
     * 1[BlockDudesDay.Projectile.Bullet[x=400.0,y=500.0,w=2.0,h=2.0,v=2.0,s=Point2D.Double[394.0, 494.0],e=Point2D.Double[413.23, 81.02]]]
     */
    private void parseBullets(String s) {
        LinkedList alist = new LinkedList();
        while(s.indexOf("BlockDudesDay.Projectile.Bullet") > -1) {
            s = s.substring(s.indexOf("BlockDudesDay.Projectile.Bullet"));
            int substrEnd = s.indexOf("]]") + 1;
            alist.add(new Bullet(s.substring(s.indexOf("BlockDudesDay.Projectile.Bullet"), substrEnd)));
            s = s.substring(s.indexOf("]]") + 2);
        }
        bulletList = new LinkedList(alist);
    }
    
    /**
     * @param s   * List with leading 2.
     */
    public void parsePlayers(String s) {
        LinkedList aList = new LinkedList();
        while(s.indexOf("BlockDudesDay.Character.Player") > -1) {
            s.substring(s.indexOf("BlockDudesDay.Character.Player"));
            int substrEnd = s.indexOf("]") + 1;
            aList.add(new Player(s.substring(s.indexOf("BlockDudesDay.Character.Player"), substrEnd)));
            s = s.substring(s.indexOf("]")+1);
        }
        playerList = new LinkedList(aList);
    }
    
    /**
     * Returns 0 for zombieList, 1 for bulletList, 2 for playerList.
     * @return int listType; 
     */
    public int getListType() {
        return listType;
    }
    
    public LinkedList getZombieList() {
        return zombieList;
    }
    
    public LinkedList getBulletList() {
        return bulletList;
    }
    
    public LinkedList getPlayerList() {
        return playerList;
    }
    
}
