package it.uniroma1.model.deck;

import it.uniroma1.model.UnoCard;

import java.util.*;

/**
 * The {@code DrawDeck} class represent the draw deck. It consists of 108 {@link UnoCard} and provide
 * the usual behaviors of a deck of cards. A usual deck of Uno! is composed by:
 * <p> 19 {@code UnoCards} of color {@code ColorCard.BLUE}, numbered 1 to 9 (2 series) plus one 0
 * <p> 19 {@code UnoCards} of color {@code ColorCard.RED}, numbered 1 to 9 (2 series) plus one 0
 * <p> 19 {@code UnoCards} of color {@code ColorCard.GREEN}, numbered 1 to 9 (2 series) plus one 0
 * <p> 19 {@code UnoCards} of color {@code ColorCard.YELLOW}, numbered 1 to 9 (2 series) plus one 0
 * <p> 8 {@code UnoCards} of value {@code ValueCard.DRAW_TWO}, 2 for each color
 * <p> 8 {@code UnoCards} of value {@code ValueCard.SKIP}, 2 for each color
 * <p> 8 {@code UnoCards} of value {@code ValueCard.REVERSE}, 2 for each color
 * <p> 4 {@code UnoCards} of value {@code ValueCard.CHANGE_COLOR}
 * <p> 4 {@code UnoCards} of value {@code ValueCard.DRAW_FOUR}
 * <p>NOTE: CHANGE_COLOR and DRAW_FOUR are of color {@code ColorCard.WILD}</p>
 * @author Andrea Musolino
 */
public class DrawDeck {

    /**
     * The unique instance of the DrawDeck
     */
    private static DrawDeck instance;

    /**
     * The deck of cards
     */
    private List<UnoCard> deck;

    /**
     * Index of the card at the top of the deck
     */
    private int topCardID;

    public static DrawDeck getInstance() {
        if (instance == null) instance = new DrawDeck();
        return instance;
    }

    /**
     * Constructor
     */
    private DrawDeck() {
        //Initialize to 108 because a standard Uno deck is composed by 108 cards
        deck = new ArrayList<>(108);
        fillDeck();
        topCardID = deck.size()-1;
    }

    /**
     * Draw a card from the top of the deck
     * @return the card at the top of the deck
     */
    public UnoCard drawCard () {
        if (isEmpty()) {                     //the deck has no more cards?
            fillDeck();                      //refill the deck
            topCardID = deck.size()-1;        //reset the value to the index of the card at the top of the deck
        }
        return deck.remove(topCardID--);
    }

    /**
     * Overloading of the method {@code drawCard()}.
     * <p> Allows to draw more than one card (usually {@code n} is equal to two or four)</p>
     * @param n is the number of card that you want to draw
     * @return the n draws card
     * @throws IllegalArgumentException
     */
    public List<UnoCard> drawCard (int n) throws IllegalArgumentException {
        List<UnoCard> out = new ArrayList<>(n);
        // If the number is lower of 1 or higher of the size of the deck, then exception
        if (n < 1 || n > deck.size()) throw new IllegalArgumentException("Number invalid");
        for (int i = 0; i < n; i++) {
            out.add(drawCard());
        }
        return out;
    }

    /**
     * Return whether the deck is empty or not
     * @return true if the deck is empty, false otherwise
     */
    public boolean isEmpty() { return deck.isEmpty(); }

    /**
     * Fill {@code deck} with 108 {@code UnoCard}
     */
    private void fillDeck () {
        UnoCard.ColorCard[] colors = UnoCard.ColorCard.getColors();      //{ RED, BLUE, GREEN, YELLOW, WILD }
        UnoCard.ValueCard[] values = UnoCard.ValueCard.getValuesCards(); //{ ZERO, ..., NINE, REVERSE, SKIP, DRAW_TWO, CHANGE_COLOR, DRAW_FOUR }
        //Add all the cards excepts WILD card
        for (int i = 0; i < colors.length-1; i++) {                    //excluding WILD cards
            for (int j = 0; j < values.length-2; j++) {                 //excluding CHANGE_COLOR & DRAW_FOUR
                deck.add(new UnoCard(colors[i], values[j]));
                if (values[j] != UnoCard.ValueCard.ZERO)
                    deck.add(new UnoCard(colors[i], values[j]));
            }
        }
        //Add all the WILD card
        for (int k = 0; k < 4; k++) {
            deck.add(new UnoCard(UnoCard.ColorCard.WILD, UnoCard.ValueCard.CHANGE_COLOR));
            deck.add(new UnoCard(UnoCard.ColorCard.WILD, UnoCard.ValueCard.DRAW_FOUR));
        }
        //Shuffle the deck
        Collections.shuffle(deck);
    }

    /**
     * @return the size of the deck
     */
    public int size () { return deck.size(); }

    /**
     * @return the index of the card at the top of the deck
     */
    public int getTopCardID () { return topCardID; }

    /**
     * Puts the input card in a random position between 0 - 106
     * The method will be called only to re-enter the DRAW_FOUR card in the deck, in case it is the first discarded
     * If the input card is not a DRAW_FOUR the method does nothing
     * @param card to re-enter in the deck
     */
    public void putCard(UnoCard card) {
        if (card.getValue().equals(UnoCard.ValueCard.DRAW_FOUR)) {
            /*
            The method will be called only after the cards have been dealt to the players
            and discarded the card at the top of the deck (the size of the deck it will be 79)
             */
            int randPosition = new Random().nextInt(78);
            deck.add(randPosition, card);
        }
    }

    /**
     * Refill the deck
     */
    public void refillDeck() {
        deck.clear();
        fillDeck();
        topCardID = deck.size()-1;
    }
}
