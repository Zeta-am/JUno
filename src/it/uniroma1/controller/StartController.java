package it.uniroma1.controller;

import it.uniroma1.view.start.StartFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * The {@code StartController} class manages, and add listener to the {@link StartFrame}
 * @author Andrea Musolino
 */
public class StartController
    implements ActionListener {

    /**
     * The start frame
     */
    private StartFrame startFrame;

    /**
     * The button of the frame
     */
    private JButton startBtn, exitBtn;

    /**
     * Constructor
     */
    public StartController() {
        startFrame = new StartFrame();

        startBtn = startFrame.getStartPanel().getStartBtn();
        exitBtn = startFrame.getStartPanel().getExitBtn();

        /* Adding listener to the button */
        startBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }

    /**
     * Performs certain instructions depending on the object solicited
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actComm = e.getActionCommand();
        JButton src = (JButton)e.getSource();

        if (src.equals(startBtn)) {
            startBtn();
        } else {
            exitBtn();
        }

    }
    /**
     * Asks the player’s name and closes the start frame by opening the game frame
     */
    private void startBtn() {
        String nick = "";
        while (true) {
            nick = JOptionPane.showInputDialog("Choose a nickname");
            if (nick == null) break;
            else if (!nick.equals("")) {
                startFrame.dispose();
                new GameController(nick);
                break;
            }
            JOptionPane.showMessageDialog(startFrame, "YOU HAVE TO ENTER A NICKNAME!");
        }
    }
    /**
     * Close the frame
     */
    private void exitBtn() {
        System.exit(0);
    }
}
