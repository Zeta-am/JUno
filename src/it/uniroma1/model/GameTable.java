package it.uniroma1.model;

import it.uniroma1.Observer;
import it.uniroma1.model.deck.DiscardDeck;
import it.uniroma1.model.deck.DrawDeck;
import it.uniroma1.model.player.Player;
import it.uniroma1.Observable;
import it.uniroma1.model.player.UserPlayer;

import java.util.*;

/**
 * The {@code GameTable} class provides methods that allow you to manage a match of UNO
 * Implements the {@link Observable} interface that allows you to notify observers who will implement the interface {@link Observer}
 * @author Andrea Musolino
 */
public class GameTable
    implements Observable {

    /**
     * The deck from which you draw
     */
    private DrawDeck drawDeck;

    /**
     * The deck in which you discard
     */
    private DiscardDeck discardDeck;

    /**
     * The players
     */
    private List<Player> playerList;

    /**
     * The index of the first player of the turn
     */
    private int firstPlayerID;

    /**
     * The index of the hand player
     */
    private int handPlayer;

    /**
     * The sense of play
     */
    private boolean clockwiseDirection;

    /**
     * The ground color
     */
    private UnoCard.ColorCard groundColor;

    /**
     * The list of observer
     */
    private List<Observer> observers;

    /**
     * Constructor
     * @param playerList the list of the player
     */
    public GameTable(List<Player> playerList) {
        this.playerList = playerList;
        drawDeck = DrawDeck.getInstance();
        discardDeck = DiscardDeck.getInstance();
        clockwiseDirection = true;                            //Initially the turn is clockwise
        firstPlayerID = new Random().nextInt(4);        // Choose randomly the first player
        handPlayer = firstPlayerID;
        observers = new ArrayList<>();
    }

    /**
     * The method deals seven {@code UnoCard} for each {@code Player}, after which the top card of the {@code DrawDeck}
     * that will create the {@code DiscardDeck} is discarded
     */
    public void gamePreparation()  {
        //For each player add 7 cards
        for (Player p: playerList) {
            p.addCard(drawDeck.drawCard(7));
        }
        //Discard the top card of the draw deck
        discardDeck.addDiscardedCard(drawDeck.drawCard());
        //Set the ground color
        setGroundColor(discardDeck.peek().getColor());
    }

    /**
     * Method that changes the game turn
     */
    public void changeDirection() { clockwiseDirection = !clockwiseDirection; }

    /**
     * Is managed the updating of the indexes in case the turn is clockwise or counter-clockwise
     */
    public void nextPlayer() {
        //Clockwise direction 0->1->2->3->0
        if (isClockwiseDirection()) {
            if (handPlayer == 3) {
                handPlayer = 0;
            } else {
                handPlayer++;
            }
        }
        //Anticlockwise direction 0->3->2->1->0
        else {
            if (handPlayer == 0) {
                handPlayer = 3;
            }
            else {
                handPlayer--;
            }
        }
    }

    /**
     * Return the index of the next player
     * @return the index of the next player
     */
    public int getNextPlayer() {
        //Clockwise direction 0->1->2->3->0
        if (isClockwiseDirection()) {
            if (handPlayer == 3) {
                return 0;
            } else {
                return handPlayer+1;
            }
        }
        //Anticlockwise direction 0->3->2->1->0
        else {
            if (handPlayer == 0) {
                return 3;
            }
            else {
                return handPlayer-1;
            }
        }
    }

    /**
     * Return the draw deck
     * @return {@code drawDeck}
     */
    public DrawDeck getDrawDeck() { return drawDeck; }

    /**
     * Return the discard deck
     * @return {@code discardDeck}
     */
    public DiscardDeck getDiscardDeck() { return discardDeck; }

    /**
     * Return the players
     * @return {@code playerList}
     */
    public List<Player> getPlayerList() { return playerList; }

    /**
     * Return if the turn is clockwise or not
     * @return true if the turn is clockwise, false otherwise
     */
    public boolean isClockwiseDirection() { return clockwiseDirection; }

    /**
     * Return the index of hand player
     * @return the index of hand player
     */
    public int getHandPlayer() { return handPlayer; }

    /**
     * Return the ground color
     * @return the ground color
     */
    public UnoCard.ColorCard getGroundColor() { return groundColor; }

    /**
     * Set the ground color
     * @param color the new ground color
     */
    public void setGroundColor(UnoCard.ColorCard color) { if (!color.equals(UnoCard.ColorCard.WILD)) groundColor = color; }

    /**
     * The method says if there’s a player out of cards in hand
     * @return true if there's one player with zero card, false otherwise
     */
    public boolean hasPlayerZeroCard() { return playerList.stream().anyMatch(Player::hasEmptyHand); }

    /**
     * Method that counts the total score of cards left in the hands of the players
     */
    public int countScoreCards() {
        //The stream sum the scores of the cards left in the hands of the players
        return (int)playerList.stream()
                .map(Player::getGameHand)
                .flatMap(List::stream)
                .map(UnoCard::getScore)
                .reduce(0, Integer::sum);
    }

    /**
     * Returns a boolean that indicates if a player has 500 of score
     * @return true if it has 500 of score, false otherwise
     */
    public boolean playerHasWon() {
        for (Player p: playerList) {
            return p.hasWon();
        }
        return false;
    }


    /**
     * Method that is called at the end of a round
     * <p>Players' hands are emptied, direction reset, draw deck filled and discard deck emptied</p>
     */
    public void reset() {
        //Clear the hand of the player
        for (Player p: playerList) { p.reset(); }
        //Refill the deck
        drawDeck.refillDeck();
        //Empty the discard deck
        discardDeck.clear();
        //Reset the clockwise direction
        clockwiseDirection = true;
        //Goes to the next first player
        handPlayer = firstPlayerID == 3 ? firstPlayerID = 0 : ++firstPlayerID;
        //Prepare the new game
        gamePreparation();
        //Notify the observers of the changes
        notifyObserver();
    }

    /**
     * Add an Observer to observer list
     * @param o the observer to be added
     */
    @Override
    public void addObserver(Observer o) { observers.add(o); }

    /**
     * Remove an Observer to observer list
     * @param o the observer to be removed
     */
    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    /**
     * Notify all the Observer that GameTable it has been updated
     */
    @Override
    public void notifyObserver() { for (Observer o: observers) { o.update(); } }
}
