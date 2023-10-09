package it.uniroma1.view.game;


import java.awt.*;

/**
 * The {@code BotPanel} class serves to represent the game hand, name and score of a bot.
 * Inherits components and their settings from {@link PlayerPanel}
 * @author Andrea Musolino
 */
public class BotPanel
    extends PlayerPanel {

    /**
     * Constructor
     */
    public BotPanel() {
        getGameHandPnl().setLayout(new FlowLayout(FlowLayout.CENTER, -55, 0));
    }

}
