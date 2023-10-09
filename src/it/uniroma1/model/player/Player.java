package it.uniroma1.model.player;

import it.uniroma1.model.deck.DiscardDeck;
import it.uniroma1.model.UnoCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class need to represent the various features of a usual player of Uno!.
 * <p> The class will be extended by the class {@link UserPlayer} and {@link BotPlayer},
 * the main user class and the bot class, respectively
 * @author Andrea Musolino
 */
public abstract class Player {
    /**
     * Name of the player
     */
    protected String name;

    /**
     * Game hand of the player
     */
    protected List<UnoCard> gameHand;

    /**
     * Round score of the player
     */
    protected int score;

    /**
     * Whether UNO! has been said or not
     */
    protected boolean uno;

    /**
     * Constructor
     */
    public Player (String name) {
        this.name = name;
        gameHand = new ArrayList<>(7);          //initially a game hand is 7 cards
        score = 0;
        uno = false;
    }

    /**
     * Add the card to be discarded to the discard Deck
     * @param card the card to be discarded
     * @param discardDeck the pile on which the card will be added
     */
    public void discardsCard(UnoCard card, DiscardDeck discardDeck)
    {
        UnoCard chosenCard = gameHand.remove(gameHand.indexOf(card));
        discardDeck.addDiscardedCard(chosenCard);
    }

    /**
     * Allows to add a card to the hand
     * @param card the card to be added
     */
    public void addCard (UnoCard card) { gameHand.add(card); }

    /**
     * Overloading of the method {@code addCard()}.
     * <p> Allows to add more than one card to the hand (usually two or four)</p>
     * @param cards the cards to be added
     */
    public void addCard (List<UnoCard> cards) { gameHand.addAll(cards); }

    /**
     * Add the score of a round
     * @param n the score obtained in a round
     */
    public void addRoundScore (int n) { score += n; }

    /**
     * @return the name of the player
     */
    public String getName () { return name; }

    /**
     * @return the game hand
     */
    public List<UnoCard> getGameHand () { return gameHand; }

    /**
     * @return the score of the player
     */
    public int getScore () { return score; }

    /**
     * @return true if the {@code gameHand} is empty, false otherwise
     */
    public boolean hasEmptyHand () { return gameHand.isEmpty(); }

    /**
     * @return true if the player has one card, false otherwise
     */
    public boolean hasOneCard () { return gameHand.size() == 1; }

    /**
     * @return true if the player has won, false otherwise
     */
    public boolean hasWon () { return score >= 500; }

    /**
     * Set {@code uno} to {@code true} when you have a card in your hand
     */
    public void setUno(boolean b) { uno = b; }

    /**
     * Returns a boolean depending on whether the player said UNO! or not
     * @return true if he said it, false otherwise
     */
    public boolean isUno() { return uno; }

    /**
     * Method that checks if the hand has at least one card compatible with the one on the ground
     * @param card the top card of the {@code DiscardDeck}
     * @param colorCard the ground color (in case the card on the ground is a wild)
     * @return true if there is at least one card, false otherwise
     */
    public boolean hasValidCard(UnoCard card, UnoCard.ColorCard colorCard) {
        //If in the gameHand there is a change color card
        //if (gameHand.contains(new UnoCard(UnoCard.ColorCard.WILD, UnoCard.ValueCard.CHANGE_COLOR))) return true;
        //Check if in the hand there is a card with the same color or value of the input card
        for (UnoCard c: gameHand) {
            if (c.getColor().equals(colorCard) ||
                c.getValue().equals(card.getValue()) ||
                c.getValue().equals(UnoCard.ValueCard.CHANGE_COLOR)
            )
                return true;
        }
        return false;
    }

    /**
     * Method that check if the player has a DRAW_FOUR card in the game hand
     * @return true if he has it, false otherwise
     */
    public boolean hasDrawFour() {
        for (UnoCard c: gameHand) {
            if (c.getValue().equals(UnoCard.ValueCard.DRAW_FOUR))
                return true;
        }
        return false;
    }

    /**
     * Return a boolean indicating whether the player has wild cards in his hand
     * @return true if he has it, false otherwise
     */
    public boolean hasWild() {
        for (UnoCard card: gameHand) {
            if (card.getColor().equals(UnoCard.ColorCard.WILD))
                return true;
        }
        return false;
    }

    /**
     * Return the index where the card is located
     * @return the index where the card is located
     */
    public Integer getDrawFourIndex() {
        for (int i = 0; i < gameHand.size(); i++) {
            //The value of the card
            UnoCard.ValueCard valueCard = gameHand.get(i).getValue();
            if (valueCard.equals(UnoCard.ValueCard.DRAW_FOUR))
                return i;
        }
        return null;
    }

    /**
     * Reset the {@code uno} value and the {@code gameHand} when a turn ends
     * If the player has 500 of score set the game won
     */
    public void reset() {
        gameHand.clear();
        uno = false;
    }

    /**
     * @return the name of the player
     */
    @Override
    public String toString() { return name; }
}
