/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;


/**
 *
 * @author brad
 */

public class BlockProblem {

    public BlockProblem() {
        currentState = new BlockState();
    }

    public BlockState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BlockState state) {
        this.currentState = state;
    }

    public BlockState currentState;
}
