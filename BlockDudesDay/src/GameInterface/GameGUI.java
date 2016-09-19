package GameInterface;

import BlockClient.BlockClient;
import BlockDudesDay.BlockCanvas;
import BlockDudesDay.BlockLogic;
import BlockDudesDay.BlockProblem;
import BlockDudesDay.BlockRunner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author brad
 */
public class GameGUI extends JPanel {

    public GameGUI() {
        // Create new game logic and runner
        runner = new BlockRunner(new BlockCanvas(new BlockLogic(new BlockProblem())));
        
        // Set the GUI's layout
        setLayout(new BorderLayout(5, 5));
        gamePanel = new JPanel();
        setResolution(800, 600);
        setDifficulty(0);
        gameStarted = false;

        controllerPanel = new JPanel(new GridLayout(1, 5));
        multiplayerPanel = new MultiplayerPanel();

        pause = new JButton("Start Game!");
        pause.setFont(menuFont);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!runner.isRunning()) {
                    gameStarted = true;
                    pause.setText("Pause Game");
                    runner.gameStart();
//                    controllerPanel.setVisible(false);
                } else {
                    pause.setText("Resume Game");
                    runner.gameStop();
                }
            }
        });
        pause.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                if (!runner.isRunning() && gameStarted) {

                    pause.setText("Resume Game");
                }
            }
        });

        restart = new JButton("Restart Game");
        restart.setFont(menuFont);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                runner.getCanvas().reset();
                pause.setText("Start Game!");
                runner.gameStop();
                gameStarted = false;
            }
        });

        exit = new JButton("Exit Game");
        exit.setFont(menuFont);
        exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                runner.gameStop();
                gameStarted = false;
                System.exit(0);
            }
            @Override
            public void mousePressed(MouseEvent me) {
            }
            @Override
            public void mouseReleased(MouseEvent me) {
            }
            @Override
            public void mouseEntered(MouseEvent me) {
            }
            @Override
            public void mouseExited(MouseEvent me) {
            }
        });

        resolutionSelector = new JComboBox(new String[]{"800,600", "1024,768", "1280,1024"});
        resolutionSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (0 == ((JComboBox) ae.getSource()).getSelectedIndex()) {
                    setResolution(800, 600);
                }
                if (1 == ((JComboBox) ae.getSource()).getSelectedIndex()) {
                    setResolution(1024, 768);
                }
                if (2 == ((JComboBox) ae.getSource()).getSelectedIndex()) {
                    setResolution(1280, 1024);
                }
            }
        });

        difficultySelector = new JComboBox(new String[]{"Easy", "Medium", "Hard"});
        difficultySelector.setSize(30, 30);
        difficultySelector.setFont(menuFont);
        difficultySelector.addFocusListener((new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                runner.gameStop();
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        }));
        difficultySelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setDifficulty(((JComboBox) ae.getSource()).getSelectedIndex());
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    if (!controllerPanel.isVisible()) {
                        controllerPanel.setVisible(true);
                    }
                    runner.gameStop();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    if (controllerPanel.isVisible()) {
                        controllerPanel.setVisible(false);
                    }
                    runner.gameStart();
                }
            }
        });
        multiplayerPanel.setIPLabelText("IP Address:");
        multiplayerPanel.setIPTextFieldText("Enter IP Address");
        multiplayerPanel.getIPTextFeild().addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if (multiplayerPanel.getIPTextFeild().getText().equals("Enter IP Address")) {
                    multiplayerPanel.getIPTextFeild().setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (multiplayerPanel.getIPTextFeild().getText().equals("")) {
                    multiplayerPanel.getIPTextFeild().setText("Enter IP Address");
                }
            }
        });
        multiplayerPanel.setPortLabelText("Port:(Default 4000)");
        
        multiplayerPanel.setPortTextFieldText("Enter Port");
        multiplayerPanel.getPortTextField().addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if (multiplayerPanel.getPortTextField().getText().equals("Enter Port")) {
                    multiplayerPanel.getPortTextField().setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (multiplayerPanel.getPortTextField().getText().equals("")) {
                    multiplayerPanel.getPortTextField().setText("Enter Port");
                }
            }
        });
       
        multiplayerPanel.getMultiplayerButton().setText("Play Multiplayer");
        multiplayerPanel.getMultiplayerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStarted = true;
                if (multiplayerPanel.getMultiplayerButton().getText().equals("Play Multiplayer")) {
                    if ((!multiplayerPanel.getIPTextFeild().getText().equals("")) 
                            && (!multiplayerPanel.getIPTextFeild().getText().equals("Enter Your IP Address"))) {
                        if ((!multiplayerPanel.getPortTextField().getText().equals("")) 
                                && (!multiplayerPanel.getPortTextField().getText().equals("Enter Port"))) {
                            try {
                                multiplayerPanel.addLineToMessageArea("Connecting...", Color.BLUE);
                                BlockClient c = new BlockClient(runner.getCanvas().getblockLogic(), multiplayerPanel);
                                c.start();
                                multiplayerPanel.addLineToMessageArea("Connected!", Color.GREEN);
                                runner.gameStart();
                                runner.getCanvas().getBlockLogic().setBlockClient(c);
                                multiplayerPanel.getMultiplayerButton().setText("Stop Multiplayer");
                                connected = true;
                                gameStarted = true;
                                multiplayerPanel.getIPTextFeild().setEnabled(false);
                                multiplayerPanel.getPortTextField().setEnabled(false);
                                multiplayerPanel.setFocusable(false);
                                multiplayerPanel.getMultiplayerButton().setFocusable(true);
                            } catch (IOException ex) {
                                multiplayerPanel.addLineToMessageArea("Could not connect to IP:" + multiplayerPanel.getIPAddress() , Color.RED);
                                // Just incase
                                multiplayerPanel.getMultiplayerButton().setText("Play Multiplayer");
                                multiplayerPanel.getIPTextFeild().setEnabled(true);
                                multiplayerPanel.getPortTextField().setEnabled(true);
                                multiplayerPanel.setFocusable(true);
                            }
                        }
                    }
                } else {
                        runner.getCanvas().getBlockLogic().clearBlockClient();
                        connected = false;
                        
                        multiplayerPanel.getMultiplayerButton().setText("Play Multiplayer");
                        multiplayerPanel.setFocusable(true);
                        multiplayerPanel.getIPTextFeild().setEnabled(true);
                        multiplayerPanel.getPortTextField().setEnabled(true);
                }
            }
        });

        //controllerPanel.add(difficultySelector);
        //controllerPanel.add(pause);
        //controllerPanel.add(restart);
        controllerPanel.add(multiplayerPanel);
        controllerPanel.add(exit);
        
        add(controllerPanel, BorderLayout.SOUTH);
        add(gamePanel, BorderLayout.CENTER);

    }

    public void stopGame() {
        runner.gameStop();
    }
    
    public void startGame() {
        
    }

    public void setResolution(int x, int y) {
        gamePanel.setPreferredSize(new Dimension(x, y));
        runner.getCanvas().setPanelSize(new Dimension(x, y));
        gamePanel.add(runner.getCanvas());
        setPreferredSize(new Dimension(x + 5, y + 100));
    }

    public void setDifficulty(int i) {
        difficulty = new Integer(i);
        runner.getGameSettings().setDifficulty(i);
    }

    public Dimension screenResolution;
    private JComboBox resolutionSelector;
    private JComboBox difficultySelector;
    private JPanel controllerPanel;
    private JPanel gamePanel;
    private MultiplayerPanel multiplayerPanel;
    private JButton pause;
    private JButton restart;
    private JButton exit;
    private Font menuFont = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    private BlockRunner runner;
    private Integer difficulty;
    private boolean gameStarted;
    public boolean connected = false;
    public Thread runnerThread;
    
}
