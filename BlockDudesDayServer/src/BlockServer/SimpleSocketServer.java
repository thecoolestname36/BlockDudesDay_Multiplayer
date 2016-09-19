/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockServer;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import BlockServer.serverpanel.ServerPanel;

public class SimpleSocketServer extends JFrame {

    private BlockServer server;
    private ServerPanel panel;

    public SimpleSocketServer() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 300));

        panel = new ServerPanel();

        server = new BlockServer(panel.getTextArea());
        String ip = new String();
        try {
            ip = server.getInetAddress().getHostAddress();
        } catch (NullPointerException ex) {
            panel.addLineToConsole("IPError: Could Not Open Socket");
        }

        panel.addLineToConsole(ip + ":" + 4000);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        server.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SimpleSocketServer();

    }

}
