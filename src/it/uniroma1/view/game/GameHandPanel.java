package it.uniroma1.view.game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameHandPanel} class represents the panel containing the player's cards
 * @author Andrea Musolino
 */
public class GameHandPanel
    extends JPanel {

    /**
     * The list of card of the player
     */
    private List<JCard> handGame;

    /**
     * Constructor
     */
    public GameHandPanel() {
        handGame = new ArrayList<>();
        /* Parameters of the panel */
        setPreferredSize(new Dimension(600, 120));
        setLayout(new FlowLayout(FlowLayout.CENTER, -45, 0));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    /**
     * Get the list of JCard
     * @return the list of JCard
     */
    public List<JCard> getHandGame() { return handGame; }

    /**
     * Remove a JCard from the list and remove from the panel
     * @param card the JCard to remove
     * @return the removed card
     */
    public JCard removeJCard(JCard card) {
        JCard jCardRemoved = handGame.remove(handGame.indexOf(card));
        this.remove(jCardRemoved);
        return jCardRemoved;
    }

    /**
     * Add the JCard to the list and to the panel
     * @param card the card to be added
     */
    public void addJCard(JCard card) {
        handGame.add(card);
        this.add(card);
    }

    /**
     * Add the JCard to the list and to the panel
     * @param cards the cards to be added
     */
    public void addJCard(List<JCard> cards) {
        handGame.addAll(cards);
        for (JCard jCard: cards) {
            this.add(jCard);
        }
    }

    /**
     * Remove all the JCard from the panel and the list
     */
    public void clear() {
        for (JCard jCard: handGame) {
            this.remove(jCard);
        }
        handGame.clear();
    }
}