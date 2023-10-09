package it.uniroma1.view.game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * The {@code PlayerPanel} class serves to represent the game hand, name and score of a generic player.
 * Name and score are handled by {@code JLabel}, while the game hand is handled by {@link GameHandPanel}
 * @author Andrea Musolino
 */
public abstract class PlayerPanel
    extends JPanel {

    /**
     * Label for name and score
     */
    private JLabel nameLbl, scoreLbl;

    /**
     * The "game hand"
     */
    private GameHandPanel gameHandPnl;

    /**
     * Constructor
     */
    public PlayerPanel() {

        /* Components */
        nameLbl = new JLabel("",SwingConstants.CENTER);
        nameLbl.setFont(new Font("Monospaced", Font.BOLD, 15));
        scoreLbl = new JLabel("Score: ", SwingConstants.CENTER);
        scoreLbl.setFont(new Font("Monospaced", Font.BOLD, 15));
        gameHandPnl = new GameHandPanel();


        /* Layout */
        setLayout(new GridBagLayout());

        /* Dimensions */
        nameLbl.setPreferredSize(new Dimension(280, 40));
        scoreLbl.setPreferredSize(new Dimension(280, 40));

        /* Adding components */
        layoutComponents();
    }

    /**
     * Lay out and add components to the panel
     */
    protected void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        //Name label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;


        add(nameLbl, gbc);

        //Score label
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;


        add(scoreLbl, gbc);

        //Game hand layered pane
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        gbc.gridwidth = 2;

        add(gameHandPnl, gbc);
    }

    /**
     * Get the label of the name
     * @return the label of the name
     */
    public JLabel getNameLbl() { return nameLbl;  }

    /**
     * Get the label of the score
     * @return the label of the score
     */
    public JLabel getScoreLbl() { return scoreLbl; }

    /**
     * Get the panel of the game hand
     * @return the panel of the game hand
     */
    public GameHandPanel getGameHandPnl() { return gameHandPnl; }
}
