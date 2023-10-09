package it.uniroma1.view.start;

import javax.swing.*;

/**
 * The {@code StartFrame} is the frame that will be opened at the launch of the program.
 * @author Andrea Musolino
 */
public class StartFrame
    extends JFrame {

    /**
     * The panel
     */
    private StartPanel startPanel;

    /**
     * Constructor
     */
    public StartFrame() {
        super("Welcome");

        /* Components */
        startPanel = new StartPanel();

        /* Frame parameters */
        this.setupFrame();
    }

    /**
     * Set the parameters of the frame
     */
    private void setupFrame() {
        super.setContentPane(startPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Get the start panel
     * @return the start panel
     */
    public StartPanel getStartPanel() { return startPanel; }
}
