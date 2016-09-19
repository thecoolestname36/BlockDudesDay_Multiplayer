/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockDudesDay;


import BlockClient.BlockClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Brad
 */
public class BlockRunner {

    public BlockRunner(BlockCanvas c) {
        this.canvas = c;
        settings = new BlockSettings();
        clientIsConnected = false;

        final int DELAY = 3;
        t = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (canvas.blockLogic.getPlayerHealth() <= 0.0) {

                    //gameStop();
                    canvas.blockLogic.resetPlayer();
                    canvas.controls.resetContols();
                    //canvas.blockLogic.reset();
                    //t.start();

                } else {
                    canvas.render();
                }
            }
        });
    }

    public void gameStart() {
        canvas.controls.setFocusable(true);
        canvas.controls.requestFocus(true);
        if (canvas.getGameLevel() == 0) {
            canvas.setGameLevel(canvas.getGameLevel() + 1);
        } else {
            canvas.setGameLevel(canvas.getGameLevel());
        }
        t.start();

    }

    public void gameStop() {
        canvas.setFocusable(false);
        canvas.controls.setFocusable(false);
        canvas.controls.requestFocus(false);
        t.stop();
    }

    public boolean isRunning() {
        return t.isRunning();
    }

    public BlockCanvas getCanvas() {
        return canvas;
    }

    public BlockSettings getGameSettings() {
        return settings;
    }
    
//    public void setClientConnection(BlockClient b) {
//        clientIsConnected = true;
//        client = b;
//       
//    }
//    
//    public void closeClientConnection() {
//        clientIsConnected = false;
//        client = null;
//    }
    
    public BlockClient getClientConnection() {
        return client;
    }
    
    public boolean isClientConnected() {
        return clientIsConnected;
    }
    
    

    private BlockCanvas canvas;
    private Timer t;
    private BlockSettings settings;
    private boolean clientIsConnected;
    private BlockClient client;
}
