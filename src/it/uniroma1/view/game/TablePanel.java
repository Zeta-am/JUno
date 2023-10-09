package it.uniroma1.view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;

/**
 * The {@code TablePanel} class is the center panel of the game frame. It contains three {@code JLabel} and one {@code JButton}
 * <p>The labels represents: draw deck, discard deck and the color of the top discard deck card</p>
 * <p>The button is the UNO! button</p>
 * @author Andrea Musolino
 */
public class TablePanel
    extends JPanel {

    /**
     * The top discard deck card color
     */
    private JLabel colorGroundLbl, drawDeckLbl;

    /**
     * The draw deck, discard deck label
     */
    private JCard discardDeckLbl;

    /**
     * The UNO! button
     */
    private JButton unoBtn;

    /**
     * Constructor
     */
    public TablePanel() {
        /* Creating components */
        initComponents();

        /* Dimension and layout of the panel */
        //setPreferredSize(new Dimension(400, 200));
        setLayout(new GridBagLayout());

        /* Border */
        Border border = BorderFactory.createRaisedBevelBorder();
        colorGroundLbl.setBorder(border);

        /* Background */
        //colorGroundLbl.setBackground(GameController.BLUE);
        colorGroundLbl.setOpaque(true);

        /* Parameters of the components */
        setupComponents();

        /* Adding the components */
        layoutComponents();
    }

    private void initComponents() {
        drawDeckLbl = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/iconCards/back.png"))));
        discardDeckLbl = new JCard("back");
        colorGroundLbl = new JLabel();
        unoBtn = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/logos/uno!.png"))));
    }

    private void setupComponents() {
        //Draw & Discard deck label
        drawDeckLbl.setPreferredSize(new Dimension(77, 120));
        discardDeckLbl.setPreferredSize(new Dimension(77, 120));
        //UNO! button
        unoBtn.setPreferredSize(new Dimension(80, 80));
        unoBtn.setContentAreaFilled(false);
        unoBtn.setFocusPainted(false);
        unoBtn.setBorderPainted(false);
        //Color label
        colorGroundLbl.setPreferredSize(new Dimension(60, 60));
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        //UNO! button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;

        add(unoBtn, gbc);

        //Draw deck label
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;

        add(drawDeckLbl, gbc);

        //Discard deck label
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;

        add(discardDeckLbl, gbc);

        //Color label
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;

        add(colorGroundLbl, gbc);
    }

    /**
     * Get the draw deck label
     * @return the draw deck label
     */
    public JLabel getDrawDeckLbl() {
        return drawDeckLbl;
    }

    /**
     * Get the discard deck label
     * @return the discard deck label
     */
    public JCard getDiscardDeckLbl() {
        return discardDeckLbl;
    }

    /**
     * Get the color of the top discard deck card
     * @return the color of the top discard deck card
     */
    public JLabel getColorGroundLbl() {
        return colorGroundLbl;
    }

    /**
     * Get the UNO! button
     * @return the UNO! button
     */
    public JButton getUnoBtn() {
        return unoBtn;
    }
}
