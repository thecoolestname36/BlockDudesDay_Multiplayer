/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;


/**
 *
 * @author Brad
 */
public class BlockSettings {

    private Integer difficulty;

    public BlockSettings() {
        difficulty = new Integer(0);
    }

    public void setDifficulty(int i) {
        difficulty = new Integer(i);
    }

    public int getDifficulty() {
        return new Integer(difficulty.intValue());
    }

    public int getGameSpeed() {
        int speed = 25;
        if (difficulty == 1) {
            speed = 15;
        } else if (difficulty >= 2) {
            speed = 8;
        }
        return speed;
    }
}
