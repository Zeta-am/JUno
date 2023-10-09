package it.uniroma1.view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The {@code ColorChooserDialog} class is a dialog that asks the user to choose a color between red, blue, green and yellow
 * @author Andrea Musolino
 */
public class ColorChooserDialog
    extends JDialog {

    /**
     * The panel of the dialog
     */
   private ColorChooserPanel colorChooserPanel;

    /**
     * Constructor
     */
    public ColorChooserDialog() {
        colorChooserPanel = new ColorChooserPanel();
        setContentPane(colorChooserPanel);
        setSize(new Dimension(360, 120));
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(false);
    }

    /**
     * Get the color chooser panel
     * @return the color chooser panel
     */
    public ColorChooserPanel getColorChooserPanel() { return colorChooserPanel; }

    /**
     * The inner class {@code ColorChooserPanel} is the panel that will be added to the {@code ColorChooserDialog}
     * @author Andrea Musolino
     */
    public class ColorChooserPanel
        extends JPanel {
        /**
         * Label that ask which color choose
         */
        private JLabel label;

        /**
         * Color to choose
         */
        private final Color RED = new Color(224, 5, 0),
                BLUE = new Color(64, 159, 255),
                GREEN = new Color(65, 162, 41, 255),
                YELLOW = new Color(236, 212, 7);

        /**
         * The label that represent the color
         */
        private JLabel redLbl, blueLbl, greenLbl, yellowLbl;

        /**
         * Constructor
         */
        private ColorChooserPanel() {
            /* Creating components */
            initComponents();

            /* Set parameters */
            setupComponents();

            /* Adding components */
            layoutComponents();

            pack();
        }

        /**
         * Create the components
         */
        private void initComponents() {
            label = new JLabel("Choose a color");
            redLbl = new JLabel();
            blueLbl = new JLabel();
            greenLbl = new JLabel();
            yellowLbl = new JLabel();
        }

        /**
         * Set the parameters of the components
         */
        private void setupComponents() {
            Border border = BorderFactory.createRaisedBevelBorder();
            //red label
            redLbl.setBackground(RED);
            redLbl.setPreferredSize(new Dimension(50, 50));
            redLbl.setBorder(border);
            redLbl.setOpaque(true);

            //blue label
            blueLbl.setBackground(BLUE);
            blueLbl.setPreferredSize(new Dimension(50, 50));
            blueLbl.setBorder(border);
            blueLbl.setOpaque(true);

            //green label
            greenLbl.setBackground(GREEN);
            greenLbl.setPreferredSize(new Dimension(50, 50));
            greenLbl.setBorder(border);
            greenLbl.setOpaque(true);

            //yellow label
            yellowLbl.setBackground(YELLOW);
            yellowLbl.setPreferredSize(new Dimension(50, 50));
            yellowLbl.setBorder(border);
            yellowLbl.setOpaque(true);

            //label
            Font labelFont = label.getFont();
            label.setFont(new Font("Monospaced", Font.BOLD, 15));
        }

        /**
         * Lay out and add components to the panel
         */
        private void layoutComponents() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            //Question label
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4;
            gbc.weighty = 0.5;
            gbc.anchor = GridBagConstraints.CENTER;

            add(label, gbc);
            //Red label
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            gbc.weighty = 0.3;

            add(redLbl, gbc);
            //Blue label
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            gbc.weighty = 0.3;

            add(blueLbl, gbc);
            //Green label
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            gbc.weighty = 0.3;

            add(greenLbl, gbc);
            //Yellow label
            gbc = new GridBagConstraints();
            gbc.gridx = 3;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            gbc.weighty = 0.3;

            add(yellowLbl, gbc);
        }

        /**
         * Get the red label
         * @return the red label
         */
        public JLabel getRedLbl() { return redLbl; }

        /**
         * Get the blue label
         * @return the blue label
         */
        public JLabel getBlueLbl() { return blueLbl; }

        /**
         * Get the green label
         * @return the green label
         */
        public JLabel getGreenLbl() { return greenLbl; }

        /**
         * Get the yellow label
         * @return the yellow label
         */
        public JLabel getYellowLbl() { return yellowLbl; }

    }
}
