package BlockServer;

import BlockDudesDay.BlockServerLogic;
import BlockDudesDay.BlockProblem;
import BlockDudesDay.BlockRunner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import javax.swing.JTextArea;

/**
 * Server for multiplayer BlockDudesDay.
 * @author Bradley Cutshall
 */
public class BlockServer {

    /**
     * Constructor that takes the GUI's text area so it can write to it.
     * @param c 
     */
    public BlockServer(JTextArea c) {
        console = c;
        socketList = new LinkedList();
        runner = new BlockRunner(new BlockServerLogic(new BlockProblem()));
        disconnectedClient = new String();
        iNetSocket = 4000;
        socketListUnlocked = true;
        addrSuccess = false;

        runner.gameStart();

        // The server first tries to obtain its ip address and if successful
        // it will setup a socket for the server to make connections to clients
        // with.  Only one server can run on one given ip address.  
        // This thread will wait on a connection from a client and once one is
        // established the server will send the client it's userID.  The userID
        // is for updating player positions within the game's logic. If the
        // list that holds the clients is being used the client will sadly be
        // dropped.
        try {
            addr = (Inet4Address) Inet4Address.getLocalHost();
            addrSuccess = true;
            try {
                serverSocket = new ServerSocket(iNetSocket, 1, addr);

                connectionThread = new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                // Thread pauses here and waits for a connection
                                BlockSocket s = new BlockSocket(serverSocket.accept());
                                addLineToConsole("User " + s.getSocketIP() + " Connected");
                                out = new PrintWriter(s.getSocket().getOutputStream(), true);

                                while (!socketListUnlocked) {
                                    // Waits for the socket list to be unlocked 
                                    // to add the client to the list.
                                }
                                if (socketListUnlocked) {
                                    socketListUnlocked = false;
                                    if(socketList.size() < 9) {
                                        int tempID = 0;
                                        for (BlockSocket b : socketList) {
                                            if (tempID == b.getPlayerID()) {
                                                tempID++;
                                            }
                                        }
                                        String str = new String("ID" + tempID);
                                        out.println(str);
                                        socketList.add(s);
                                    }
                                    socketListUnlocked = true;
                                }
                            } catch (IOException exec) {
                                System.out.println("SERVER:connectionThread:" + exec.getMessage());
                            } catch (ConcurrentModificationException exec) {
                                System.out.println("SERVER:connectionThread:" + exec.getMessage());
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException ex) {
                                System.out.println("SERVER:SEND:SLEEP::" + ex.getMessage());
                            }
                        }
                    }
                };

                // This is where the server sends its information to all of its clients.
                // A client who is disconnected is dropped from the server's client list.
                sendThread = new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            LinkedList tempSockets = new LinkedList<BlockSocket>();
                            if (socketListUnlocked) {
                                socketListUnlocked = false;
                                try {
                                    for (BlockSocket s : socketList) {
                                        if (s.getSocket().isConnected()) {
                                            try {
                                                out = new PrintWriter(s.getSocket().getOutputStream(), true);

                                                // This is where you send the data to the clients.
                                                LinkedList testingList = new LinkedList();
                                                testingList.add(new BlockDudesDay.Projectile.Bullet());
                                                testingList.add(new BlockDudesDay.Projectile.Bullet());

                                                // This is that the server actually sends to its clients.
                                                out.println("|ZERO|" + runner.getBlockGameLogic().getFoeList().toString()
                                                        + "|ONE|" + runner.getBlockGameLogic().getBulletList()
                                                        + "|TWO|" + runner.getBlockGameLogic().getPlayerList()
                                                        + "|THREE|"
                                                );
                                                
                                                tempSockets.add(s);
                                            } catch (IOException e) {
                                                System.out.println("SendError: " + e.getMessage());
                                            }
                                        }
                                    }
                                    if (socketList.size() != tempSockets.size()) {
                                        addLineToConsole("A Client Has Disconnected");
                                    }
                                } catch (ConcurrentModificationException ex) {
                                    System.out.println("SERVER:SendError:" + ex.getMessage());
                                }
                                socketList = new LinkedList<BlockSocket>(tempSockets);
                                socketListUnlocked = true;
                            }
                            try {
                                sleep(SPEEDMILS);
                            } catch (InterruptedException ex) {
                                System.out.println("SERVER:SendError:SLEEP:" + ex.getMessage());
                            }

                        }
                    }//End Of Run
                };

                // This is where the server receives data from all of the clients.
                // A client times out by not sending any messages for 5 seconds.
                // When the client times out the server will drop the client's socket.
                receiveThread = new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            LinkedList tempSockets = new LinkedList<BlockSocket>();
                            if (socketListUnlocked) {
                                socketListUnlocked = false;
                                try {
                                    for (BlockSocket s : socketList) {
                                        if (s.getSocket().isConnected()) {
                                            // Receive
                                            try {
                                                try {
                                                    if (s.getTimeout() < 1000) {
                                                        in = new BufferedReader(new InputStreamReader(s.getSocket().getInputStream()));
                                                        String inputString = new String();
                                                        try {
                                                            inputString = in.readLine();
                                                        } catch(NullPointerException excep){
                                                            inputString = "";
                                                            System.out.println("SERVER:RECEIVE:inputString:" + excep.getMessage());
                                                        }

                                                        // This is where you receive the data and do something with it.
                                                        if ((inputString == null) || (inputString.length() == 0)) {
                                                            s.timeoutCount();
                                                        } else {
                                                            BlockListBuilder b = new BlockListBuilder(inputString);
                                                            runner.getBlockGameLogic().addBullets(b.getNewBulletList());
                                                            runner.getBlockGameLogic().setPlayerList(b.getPlayer());
                                                            s.resetTimeout();
                                                        }

                                                        tempSockets.add(s);
                                                    } else {
                                                        addLineToConsole("User " + s.getSocketIP() + " has disconnected.");
                                                        runner.getBlockGameLogic().removePlayerFromList(s.getPlayerID());
                                                    }
                                                } catch (NumberFormatException exc) {
                                                    System.out.println("SERVER:RECEIVE0:" + exc.getMessage());
                                                } catch (StringIndexOutOfBoundsException exc) {
                                                    System.out.println("SERVER:RECEIVE1:" + exc.getMessage());
                                                } catch (ConcurrentModificationException exc) {
                                                    System.out.println("SERVER:RECEIVE2:" + exc.getMessage());
                                                }
                                            } catch (IOException e) {
                                                try {
                                                    s.getSocket().close();
                                                } catch (IOException exce) {
                                                    System.out.println("SERVER:RECEIVE:SOCKET_CLOSE_ERROR:" + exce.getMessage());
                                                }
                                                System.out.println("SERVER:RECEIVE:READERROR:" + e.getMessage());
                                                
                                            }
                                        } else {
                                            addLineToConsole("User " + s.getSocketIP() + " has disconnected.");
                                            
                                        }
                                    }
                                    if (socketList.size() != tempSockets.size()) {
                                        addLineToConsole("A Client Has Disconnected");
                                    }
                                    socketList = new LinkedList<BlockSocket>(tempSockets);
                                    socketListUnlocked = true;

                                } catch (ConcurrentModificationException ex) {
                                    System.out.println("SERVER:ReceiveError:" + ex.getMessage());
                                }
                            }
                            try {
                                sleep(SPEEDMILS);
                            } catch (InterruptedException ex) {
                                System.out.println("SERVER:RECEIVE:SLEEP::" + ex.getMessage());
                            }
                        }

                    }
                };
            } catch (IOException exc) {
                addLineToConsole("Serve:Server_Socket_Init_Error::" + exc.getMessage());
            }
        } catch (UnknownHostException ex) {
            addLineToConsole("Server:IP_Init_Error::" + ex.getMessage());
        }
    }

    /**
     * Gets the server's ip address.
     * @return
     * @throws NullPointerException 
     */
    public Inet4Address getInetAddress() throws NullPointerException {
        Inet4Address inet = null;
        if (addrSuccess) {
            inet = addr;
        }
        return inet;
    }

    /**
     * Starts the server's connections;
     */
    public void start() {
        try {
        connectionThread.start();
        receiveThread.start();
        sendThread.start();
        } catch (NullPointerException e) {
            System.out.println("SERVER:start:serverAlreadyExistsOnIP:" + e.getMessage());
            addLineToConsole("Another server is already using this ip.");
        }
        System.out.println("started");
    }

    /**
     * Adds the given line of text to the console gui.
     * @param str 
     */
    private void addLineToConsole(String str) {
        console.setText(console.getText() + "\n" + str);
    }

    private final int SPEEDMILS = 15;
    private int iNetSocket;
    private Inet4Address addr;
    private boolean addrSuccess;
    private ServerSocket serverSocket;
    private LinkedList<BlockSocket> socketList;
    private JTextArea console;
    private PrintWriter out;
    private BufferedReader in;
    private BlockRunner runner;
    private Thread sendThread;
    private Thread receiveThread;
    private Thread connectionThread;
    private String disconnectedClient;
    private Boolean socketListUnlocked;

}
