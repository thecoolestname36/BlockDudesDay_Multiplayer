package BlockClient;

import BlockDudesDay.BlockLogic;
import GameInterface.MultiplayerPanel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Client that communicates with a single server.
 *
 * @author Bradley Cutshall
 */
public class BlockClient {

    /**
     * Default constructor builds an empty socket.
     *
     * @throws IOException
     */
    public BlockClient() throws IOException {
        socket = new Socket();
    }

    /**
     * Builds a socket with a server given the IP address and port.
     * Throws an IOException on a bad connection.
     * @param ipAddr Inet4Address
     * @param port int 
     * @throws IOException
     */
    public BlockClient(Inet4Address ipAddr, int port) throws IOException {
        socket = new Socket(ipAddr, port);
    }

    /**
     * Builds a socket with a server given the IP address and port.
     * Throws an IOException on a bad connection.
     * @param ip Inet4Address
     * @param port int
     * @param l BlockLogic
     * @throws IOException 
     */
    public BlockClient(BlockLogic l, JPanel p) throws IOException {
        multiplayerPanel = (MultiplayerPanel)p;
        String ip = multiplayerPanel.getIPTextFeild().getText();
        int port = multiplayerPanel.getPort();
        playerID = new String();
        logic = l;
        timeoutCounter = 0;
        
        
        try {
            Inet4Address inet = (Inet4Address) Inet4Address.getByName(ip);
            boolean b;
            socket = new Socket(inet, port);

            timeoutCheckThread = new Thread() {
                @Override
                public void run() {
                    while(true) {
                        if (timeoutCounter >= 2500) {
                            stopTimeoutClient();
                        }
                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println("CLIENT:sendThread:SLEEP:" + ex.getMessage());
                        }
                    }
                }
            };
            
            // While connected the server will constantly attempt to send information to the server.
            // Sends the new bullets fired by the client and the client's player.
            sendThread = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            // This is where you send data to the server.
                            out = new PrintWriter(socket.getOutputStream(), true);
                            out.println("|ZERO|" + logic.getNewBulletList().toString()
                                    + "|ONE|" + playerID + "[" + logic.getPlayer().toString() + "]|TWO|"
                            );
                        } catch (IOException ex) {
                            System.out.println("CLIENT:sendThread:SEND:" + ex.getMessage());
                            this.stop();
                        } catch (NullPointerException exception) {
                            System.out.println("CLIEN:sendThread:NUL_POINTER:" + exception.getMessage());
                        }
                        try {
                            sleep(SPEEDMILS);
                        } catch (InterruptedException ex) {
                            System.out.println("CLIENT:sendThread:SLEEP:" + ex.getMessage());
                        }
                    }
                }
            };

            // While connected the client will contantly attempt to receive information from the server.
            // Receives the zombies' locations, bullets from other users and the other players in the server.
            receiveThread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        BlockListBuilder b = new BlockListBuilder();
                        String inputString = new String();
                        // This is where you receive the data from the server and do something with it.


                        // Receive game logic from the server.
                        try {
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            inputString = new String(in.readLine());
                            if((!receiveID) && (inputString.length() >0)){
                            b = new BlockListBuilder(inputString);

                            // Uses blockListBuilder to decode the string representation of the objects.
                            logic.setFoeList(b.getZombieList());
                            logic.setInputBulletList(b.getBulletList());
                            logic.setPlayerList(b.getPlayerList());
                            timeoutCounter = 0;
                            } else {
                                timeoutCounter++;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("BLOCKCLIENT:receiveThread:BAD_STRING_INPUT:" + e.getMessage());
                        } catch (StringIndexOutOfBoundsException exc) {
                            System.out.println("BLOCKCLIENT:receiveThread:BAD_STRING_INDEX:"+inputString+":" + timeoutCounter + ":"  + exc.getMessage());
                            timeoutCounter ++;
                        }  catch (IOException e) {
                            System.out.println("CLIENT:receiveThread:READ:" +inputString+":" + timeoutCounter + ":"  + e.getMessage());
                            timeoutCounter ++;
                        }
                        
                        // Receive the player ID fromt the server on initial connection.
                        try {
                            if (receiveID) {
                                if (inputString.length() == 3) {
                                    playerID = inputString.substring(2);
                                    System.out.println("PlayerID: " + playerID);
                                    receiveID = false;
                                }
                            }
                        } catch (NullPointerException exception) {
                            System.out.println("CLIEN:receiveThread:NUL_POINTER:" + exception.getMessage());
                        } catch (StringIndexOutOfBoundsException exc) {
                            System.out.println("BLOCKCLIENT:receiveThread:BAD_STRING_INDEX:" + exc.getMessage());
                        }
                        
                        // Waits for a couple miliseconds
                        try {
                            sleep(SPEEDMILS);
                        } catch (InterruptedException ex) {
                            System.out.println("CLIENT:receiveThread:SLEEP: " + ex.getMessage());
                        }
                    }
                }
            };

        // If the connection times out the user will receive a message
        // stating that the server could not be contaced. Most likely
        // cause of this is an incorrectly typed ip or port.
            
        } catch (UnknownHostException ex) {
                    multiplayerPanel.addLineToMessageArea("Failed To Connect. Check IP address and port.", Color.RED);
        }
    }

    /**
     * Closes the connection between the server and client.
     *
     * @return
     */
    public boolean closeConnection() {
        boolean success = true;
        try {
            socket.close();
            multiplayerPanel.addLineToMessageArea("Disconnected From The Server", Color.BLACK);
        } catch (IOException ex) {
            System.out.println("Failed to Close Connection");
            success = false;
        }
        return success;
    }

    /**
     * Starts the threads that run the connection.
     */
    public void start() {
        receiveThread.start();
        sendThread.start();
        timeoutCheckThread.start();
    }

        /**
     * Stops the client and closes the connection.
     *
     * @return boolean success
     */
    public boolean stopTimeoutClient() {
        boolean closeSuccess = false;
        
        receiveThread.stop();
        sendThread.stop();
        receiveThread = null;
        sendThread = null;
        this.closeConnection();
        try {
            in.close();
            out.close();
            socket.close();
            closeSuccess = true;
        } catch (IOException e) {
            System.out.println("CLIENT:stop:CLOSEERROR:" + e.getMessage());
        }

        return closeSuccess;
    }
    
    /**
     * Stops the client and closes the connection.
     *
     * @return boolean success
     */
    public boolean stopClient() {
        boolean closeSuccess = false;
        
        receiveThread.stop();
        sendThread.stop();
        timeoutCheckThread.stop();
        receiveThread = null;
        sendThread = null;
        timeoutCheckThread = null;
        this.closeConnection();
        try {
            in.close();
            out.close();
            socket.close();
            closeSuccess = true;
        } catch (IOException e) {
            System.out.println("CLIENT:stop:CLOSEERROR:" + e.getMessage());
        }

        return closeSuccess;
    }

    private final int SPEEDMILS = 15;
    private int timeoutCounter;
    private MultiplayerPanel multiplayerPanel;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Thread sendThread;
    private Thread receiveThread;
    private Thread timeoutCheckThread;
    private BlockLogic logic;
    private String playerID;
    private boolean receiveID = true;

}
