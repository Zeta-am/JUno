package it.uniroma1.view.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code GamePanel} class represents the main panel with which the user will interact
 * It also contains a panel for each of the four players and the central table panel
 * @author Andrea Musolino
 */
public class GamePanel
    extends JPanel {

    /**
     * The panel of decks
     */
    private TablePanel tablePanel;

    /**
     * The list of the panel of the player { UserPanel, BotPanel(left), BotPanel(up), BotPanel(right) }
     */
    private List<PlayerPanel> playerPanelList;

    /**
     * Constructor
     */
    public GamePanel() {
        /* Creating components */
        initComponents();

        /* Layout */
        setLayout(null);

        /* Layout components */
        layoutComponents();
    }

    /**
     * Create the components
     */
    private void initComponents() {
        tablePanel = new TablePanel();
        playerPanelList = new ArrayList<>(4);
        playerPanelList.add(new UserPanel());       //User panel
        playerPanelList.add(new BotPanel());        //Left bot panel
        playerPanelList.add(new BotPanel());        //Up bot panel
        playerPanelList.add(new BotPanel());        //Right bot panel
    }

    /**
     * Lay out and add the components
     */
    private void layoutComponents() {
        //Table panel
        tablePanel.setBounds(540, 260, 360, 200);
        add(tablePanel);

        //User panel
        UserPanel userPanel = (UserPanel) playerPanelList.get(0);
        userPanel.setBounds(new Rectangle(400, 480, 640, 200));
        add(userPanel);

        //Left bot panel
        BotPanel leftBotPanel = (BotPanel) playerPanelList.get(1);
        leftBotPanel.setBounds(new Rectangle(10, 260, 530, 200));
        leftBotPanel.getGameHandPnl().setPreferredSize(new Dimension(500, 120));
        add(leftBotPanel);

        //Up bot panel
        BotPanel upBotPanel = (BotPanel) playerPanelList.get(2);
        upBotPanel.setBounds(new Rectangle(400, 20, 640, 200));
        add(upBotPanel);

        //Right bot panel
        BotPanel rightBotPanel = (BotPanel) playerPanelList.get(3);
        rightBotPanel.setBounds(new Rectangle(900, 260, 530, 200));
        rightBotPanel.getGameHandPnl().setPreferredSize(new Dimension(500, 120));
        add(rightBotPanel);
    }

    /**
     * Get the table panel
     * @return the table panel
     */
    public TablePanel getTablePanel() { return tablePanel; }

    /**
     * Get the user panel
     * @return the user panel
     */
    public UserPanel getUserPanel() { return (UserPanel) playerPanelList.get(0); }

    /**
     * Get the left bot panel
     * @return the left bot panel
     */
    public BotPanel getLeftBotPanel() { return (BotPanel) playerPanelList.get(1); }

    /**
     * Get the right bot panel
     * @return the right bot panel
     */
    public BotPanel getRightBotPanel() { return (BotPanel) playerPanelList.get(2); }

    /**
     * Get the up bot panel
     * @return the up bot panel
     */
    public BotPanel getUpBotPanel() { return (BotPanel) playerPanelList.get(3); }

    /**
     * Get the list of the player panel
     * @return the list of the player panel
     */
    public List<PlayerPanel> getPlayerPanelList() { return playerPanelList; }
}
