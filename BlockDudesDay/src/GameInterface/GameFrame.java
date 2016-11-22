package GameInterface;


import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

///////////////////////* Comment/Uncomment to run as windowed JFrame *///////////////////////////
public class GameFrame extends JFrame {

    public static Frame gameFrame;
    public GameGUI gameGUI;
    public static int REFRESH_RATE = 60;
    

    /**
     * @return
     */
    public GameFrame(String[] args) {
        add(new GameGUI());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
            
    /**
     * @param args the command line arguments
     */
    public static String[] argies;
    public static synchronized void main(String[] args) {
        argies = args;
        new Thread() {
            @Override
            public void run() {
                new GameFrame(new String[]{"Blank", argies.toString()});
            }
        }.start();
        
    }
}

/////////////////////////* Comment/Uncomment to run as appleet *///////////////////////////
///**
// * @author Bradley Scott Cutshall
// */
//public class GameFrame extends JApplet {
//
//
//    @Override
//    public void init() {
//        try {
//            SwingUtilities.invokeAndWait(new Runnable() {
//                @Override
//                public void run() {
//                    add(new GameGUI());
//                }
//            });
//        } catch (InvocationTargetException ex) {
//            JOptionPane.showMessageDialog(null, ex.toString());
//        } catch(RuntimeException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        } catch (Exception e) {
//            System.err.println("createGUI didn't complete successfully");
//        }
//
//    }
//}


///////////////////////////* Comment/Uncomment to run as fullscreen JFrame *///////////////////////////
//public class GameFrame extends JFrame {
//
//    public static Frame gameFrame;
//    public GameGUI gameGUI;
//    public static int REFRESH_RATE = 30;
//    public static String argies[];
//    /**
//     * @return
//     */
//    public GameFrame(String[] args) {
//        add(new GameGUI());
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        pack();
//        setVisible(true);
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        argies = args;
//        new Thread() {
//            @Override
//            public void run() {
//                int option = JOptionPane.showOptionDialog(null, "Play in windowed mode, fullscreen\n runs slower for some reason. \n Block Dudes Day", "Play", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Windowed Mode", "Full Screen", "Exit"}, null);
//                System.out.println(option);
//                if(option == 0) {
//                try {
//                    String[] sl = new String[]{};
//                    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                    GraphicsDevice device = env.getDefaultScreenDevice();
//                    GraphicsConfiguration gc = device.getDefaultConfiguration();
//                    gameFrame = new Frame(gc);
//                    gameFrame.setUndecorated(true);
//                    gameFrame.setIgnoreRepaint(true);
//                    gameFrame.add(new GameGUI());
//                    device.setFullScreenWindow(gameFrame);
//                    if (device.isDisplayChangeSupported()) {
//                        device.setDisplayMode(new DisplayMode(1024, 768, 32, 120));
//                    }
//
//                } catch (Exception e) {
//                    gameFrame.dispose();
//                    JOptionPane.showMessageDialog(gameFrame, "Error: Unable To Set Resolution!\n\n Entering Windowed Mode");
//                    gameFrame = new GameFrame(new String[]{e.toString(), argies.toString()});
//                }
//                }
//                if(option == 1) {
//                    new GameFrame(new String[]{"Blank", argies.toString()});
//                }
//            }
//        }.start();
//    }
//}