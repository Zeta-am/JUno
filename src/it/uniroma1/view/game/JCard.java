package it.uniroma1.view.game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The {@code JCard} class is a custom JButton that builds us with a string (the name of the card)
 * @author Andrea Musolino
 */
public class JCard
    extends JLabel {

    /**
     * The name of the card
     */
    private String colorCard;

    /**
     * The number of the card
     */
    private Integer numberCard;

    /**
     * Constructor
     * @param colorCard the name of the card
     * @param numberCard the number of the card
     */
    public JCard(String colorCard, Integer numberCard) {
        this.colorCard = colorCard;
        this.numberCard = numberCard;
        if (numberCard == null) {
            super.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/iconCards/" + colorCard + ".png"))));
        } else {
            super.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/iconCards/" + colorCard + numberCard + ".png"))));
        }
        super.setPreferredSize(new Dimension(77, 120));
        if (!this.colorCard.equals("back")) {
            super.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        }
    }

    /**
     * Constructor (Will be called when you want to build a JCard with the back of a card)
     * @param colorCard the name of the card
     */
    public JCard(String colorCard) {
        this(colorCard, null);
    }

    /**
     * Get the name of the card
     * @return the name of the card
     */
    public String getColorCard() { return colorCard; }

    /**
     * Get the number of the card
     * @return the number of the card
     */
    public Integer getNumberCard() { return numberCard; }

    @Override
    public String toString() { return colorCard + numberCard; }
}
