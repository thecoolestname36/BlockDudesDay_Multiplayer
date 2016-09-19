package BlockServer;

/**
 * BlockSocket has a timeout variable so it 
 * will know when the client has timed out.
 * @author Bradley Cutshall
 */
public class BlockSocket {
    
    /**
     * Default constructor, creates a default socket.  
     */
    public BlockSocket() {
        socket = new java.net.Socket();
        socketIP = new String();
        timeout = 1;
        playerID = -1;
    }
    
    /**
     * Creates a new BlockSocket with the given socket and
     * its timeout at 0;
     * @param s 
     */
    public BlockSocket(java.net.Socket s) {
        socket = s;
        socketIP = socket.getInetAddress().getHostAddress();
        timeout = 0;
    }
    
    /**
     * Assigns the given socket to the BlockSocket's socket.
     * @param s 
     */
    public void setSocket(java.net.Socket s) {
        socket = s;
    }
    
    /**
     * Returns the BlockSocket's socket.
     * @return socket
     */
    public java.net.Socket getSocket() {
        return socket;
    }
    
    /**
     * Increments this socket's timeout by one.
     */
    public void timeoutCount() {
        timeout ++;
    }
    
    /**
     * Returns the timeout count.
     * @return timeout
     */
    public int getTimeout() {
        return timeout;
    }
    
    /**
     * Resets the timeout of this BlockSocket
     */
    public void resetTimeout() {
        timeout = 1;
    }
    
    /**
     * Gets this socket's IP address.
     * @return 
     */
    public String getSocketIP() {
        return new String(socketIP);
    }
    
    /**
     * Sets the connection id for the user.
     * @param i int
     */
    public void setPlayerID(int i) {
        playerID = i;
    }
    
    
    public int getPlayerID() {
        return playerID;
    }
            
    private java.net.Socket socket;
    private String socketIP;
    private int timeout;
    private int playerID;
}
