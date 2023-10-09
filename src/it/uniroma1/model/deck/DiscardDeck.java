package it.uniroma1.model.deck;

import it.uniroma1.model.UnoCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DiscardDeck} class represent the discard deck.
 * <p>Provide the methods of the discard deck
 * @author Andrea Musolino
 */
public class DiscardDeck {

    /**
     * The unique instance of DiscardDeck
     */
    private static DiscardDeck instance;

    /**
     * the pile which is the discard deck
     */
    private List<UnoCard> discardDeck;

    /**
     * Return the unique instance of DiscardDeck
     * @return the created instance if null, the instance itself otherwise
     */
    public static DiscardDeck getInstance() {
        if (instance == null) instance = new DiscardDeck();
        return instance;
    }
    /**
     * Constructor
     * <p>The discard deck is created after the first card in the {@code DrawDeck} is discarded
     */
    private DiscardDeck () {
        discardDeck = new ArrayList<>(1);
    }

    /**
     * Retrieves the first element of the discard deck
     * @return the head of the pile, null if the pile is empty
     */
    public UnoCard peek () { return discardDeck.isEmpty() ? null : discardDeck.get(discardDeck.size()-1); }

    /**
     * Adds a discarded card from a player to the {@code discardDeck}
     * @param card the card discarded
     */
    public void addDiscardedCard(UnoCard card) { discardDeck.add(card); }

    /**
     * The method returns the only card in the discard pile
     * @return a DRAW_FOUR card if is the first discarded card (game preparation), null otherwise
     */
    public UnoCard removeDrawFour() {
        if (this.peek().getValue().equals(UnoCard.ValueCard.DRAW_FOUR)) {
            return discardDeck.remove(0);
        }
        return null;
    }
    /**
     * Clear the discard deck
     */
    public void clear() { discardDeck.clear(); }

    /**
     * The size of the deck
     */
    public int size() { return discardDeck.size(); }
}
