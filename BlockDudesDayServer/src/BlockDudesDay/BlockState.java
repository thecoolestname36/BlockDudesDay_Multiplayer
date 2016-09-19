/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;


/**
 *
 * @author brad
 */
public class BlockState {

    public BlockState() {
        this.level = 0;
    }

    public void setLevel(int l) {
        this.level = l;
        this.foesRemaining = l + (l * 2);
    }

    public int getLevel() {
        return level;
    }

    public int getFoesRemaining() {
        return foesRemaining;
    }

    public void setFoesRemaining(int foes) {
        foesRemaining = foes;
    }

    private int foesRemaining;
    private int level;
}
