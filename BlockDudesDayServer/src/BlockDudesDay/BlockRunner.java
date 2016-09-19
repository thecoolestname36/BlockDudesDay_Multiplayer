/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Brad
 */
public class BlockRunner {

    //public BlockRunner(BlockProblem p, BlockGameLogic l, BlockCanvas c) {
    public BlockRunner(BlockServerLogic l) {
        //this.problem = p;
        this.blockLogic = l;
        settings = new BlockSettings();
        

        final int DELAY = 3;
        t = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                blockLogic.processLogic();
            }
        });
    }

    public void gameStart() {
//        canvas.controls.setFocusable(true);
//        canvas.controls.requestFocus(true);
//        if (canvas.getGameLevel() == 0) {
//            canvas.setGameLevel(canvas.getGameLevel() + 1);
//        } else {
//            canvas.setGameLevel(canvas.getGameLevel());
//        }
        t.start();

    }

    public void gameStop() {
        t.stop();
    }

    public boolean isRunning() {
        return t.isRunning();
    }

    public BlockServerLogic getBlockGameLogic() {
        return blockLogic;
        
    }

    public BlockSettings getGameSettings() {
        return settings;
    }

    public BlockServerLogic blockLogic; 
    private Timer t;
    private BlockSettings settings;
}
