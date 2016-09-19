package BlockDudesDay.Map;

import java.util.LinkedList;

/**
 *
 * @author Brad
 */
public class Buildings extends LinkedList<BlockHouse> {

    public Buildings() {
        super();
    }
    
    public Buildings(Buildings other) {
        super();
        for(Object o : other) {
            this.add((BlockHouse)o);
        }
        this.level = other.getLevel();
    }

    public Buildings(int l) {
        super();
        this.level = l;
        buildBuildings(level);
    }

    public Buildings getBuildings() {
        return this;
    }

    public void setMapBuildings(Buildings newB) {
        this.clear();
        for (BlockHouse b : newB) {
            this.add(b);
        }
    }

    public void setLevel(int l) {
        this.level = l;
    }
    
    public int getLevel() {
        return new java.lang.Integer(level);
    }

    private void buildBuildings(int l) {
        
        
        
        this.add(new BlockHouse(360, 450, 80));
        this.add(new BlockHouse(200, 150, 120));
        this.add(new BlockHouse(200, 150, 50));
        this.add(new BlockHouse(20, 400, 150));
        this.add(new BlockHouse(20, 500, 50));
        this.add(new BlockHouse(25, 600, 50));
        this.add(new BlockHouse(725, 200, 50));
        this.add(new BlockHouse(625, 200, 50));
        this.add(new BlockHouse(525, 200, 50));
        this.add(new BlockHouse(80, 62, 10));

    }

    private int level;

}
