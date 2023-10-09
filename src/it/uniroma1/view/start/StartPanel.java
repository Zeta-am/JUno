package it.uniroma1.view.start;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
/**
 * The {@code StartPanel} class is used to lay components out with which the user will interact in the {@link StartFrame}
 * @author Andrea Musolino
 */
public class StartPanel
    extends JPanel {

    /**
     * The start and exit button
     */
    private JButton startBtn, exitBtn;

    /**
     * The label of the logo and the background
     */
    private JLabel logoLbl, backLbl;

    /**
     * Constructor
     */
    public StartPanel() {

        /* Creating components */
        initComponents();

        /* Set font */
        startBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
        exitBtn.setFont(new Font("Monospaced", Font.BOLD, 15));

        /* Set layout */
        backLbl.setLayout(new GridBagLayout());
        startBtn.setPreferredSize(new Dimension(125, 75));
        exitBtn.setPreferredSize(new Dimension(125,75));

        /* Lay out components */
        layoutComponents();
    }

    /**
     * Create the components
     */
    private void initComponents() {
        //Images
        ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/start-background.jpg")));
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/logos/uno-logo.png")));
        //Components
        backLbl = new JLabel(backgroundIcon);
        startBtn = new JButton("New Game");
        exitBtn = new JButton("Quit");
        logoLbl = new JLabel(logoIcon);
    }

    /**
     * Lay out and add the components
     */
    private void layoutComponents() {
        //The background
        this.add(backLbl);

        //Logo label
        GridBagConstraints gbc = new GridBagConstraints();
        //Logo label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;

        backLbl.add(logoLbl, gbc);

        //Start button
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.3;

        backLbl.add(startBtn, gbc);

        //Exit button
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.2;

        backLbl.add(exitBtn, gbc);
    }

    /**
     * Get the start button
     * @return the start button
     */
    public JButton getStartBtn() {
        return startBtn;
    }

    /**
     * Get the exit button
     * @return the exit button
     */
    public JButton getExitBtn() {
        return exitBtn;
    }
}
