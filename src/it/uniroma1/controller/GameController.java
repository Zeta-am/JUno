package it.uniroma1.controller;

import it.uniroma1.model.GameTable;
import it.uniroma1.model.UnoCard;
import it.uniroma1.model.deck.DiscardDeck;
import it.uniroma1.model.deck.DrawDeck;
import it.uniroma1.model.player.BotPlayer;
import it.uniroma1.model.player.Player;
import it.uniroma1.model.player.UserPlayer;
import it.uniroma1.view.game.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;


/**
 * The {@code GameController} class manage a complete game of UNO.
 * Add listener to all components of the {@link GameFrame} class, and allows interaction with the data contained in the {@link GameTable} class
 * @author Andrea Musolino
 */
public class GameController
    extends MouseAdapter
    implements ActionListener{

    /**
     * The game frame
     */
    private GameFrame gameFrame;


    /**
     * The game table
     */
    private GameTable gameTable;

    /**
     * The list of the player
     */
    private List<Player> playerList;

    /**
     * The list of the panel of the player
     */
    private List<PlayerPanel> playerPanelList;

    /**
     * The timer
     */
    private Timer timer;

    /**
     * The action command for the UNO! button
     */
    public static final String UNO_BTN = "uno!";

    /**
     * The action command for the timer
     */
    public static final String TIMER = "timer";

    /**
     * The color of the cards
     */
    public static final Color RED = new Color(224, 5, 0),
                    BLUE = new Color(64, 159, 255),
                    YELLOW = new Color(236, 212, 7),
                    GREEN = new Color(65, 162, 41, 255);

    /**
     * Constructor
     * @param username the name of the user player
     */
    public GameController(String username){
        /* Initialize Model and View */
        createGameTable(username);
        gameFrame = new GameFrame(gameTable);
        //gameListener = new GameListener(this);
        playerPanelList = new ArrayList<>(gameFrame.getGamePanel().getPlayerPanelList());

        /* Register the GameFrame (View) as observer of the GameTable (Model) */
        gameTable.addObserver(gameFrame);

        ////////////////////////////////////////////////////////////////////////

        TablePanel tablePnl = gameFrame.getGamePanel().getTablePanel();
        UserPanel userPnl = gameFrame.getGamePanel().getUserPanel();
        ColorChooserDialog.ColorChooserPanel chooserPanel = gameFrame.getColorChooserDialog().getColorChooserPanel();
        /* Adding listener to the component */

        //The UNO! button
        tablePnl.getUnoBtn().addActionListener(this);
        tablePnl.getUnoBtn().setActionCommand(UNO_BTN);
        tablePnl.getUnoBtn().setToolTipText("Click the button when you hold only one card");

        //The label of the draw deck
        tablePnl.getDrawDeckLbl().addMouseListener(this);

        //The four label colors of the color chooser panel
        chooserPanel.getRedLbl().addMouseListener(this);
        chooserPanel.getBlueLbl().addMouseListener(this);
        chooserPanel.getGreenLbl().addMouseListener(this);
        chooserPanel.getYellowLbl().addMouseListener(this);

        //The timer
        timer = new Timer(2500, this);
        timer.setActionCommand(TIMER);

        /* Start the game */
        startGame();
    }

    /**
     *  Initialize the hand game of the player and check if the first discarded card is an action card
     */
    private void startGame() {
        //Prepare the game
        gameTable.gamePreparation();
        //Check if the top discard deck card is an action card
        UnoCard topDiscardCard = gameTable.getDiscardDeck().peek();
        switch (topDiscardCard.getValue()) {
            case REVERSE -> reverse();
            case SKIP -> skip();
            case DRAW_TWO -> drawTwo();
            case CHANGE_COLOR -> changeColor();
            case DRAW_FOUR -> {
                //If the draw four is the first card to be discarded, is reinserted into the deck
                DrawDeck drawDeck = gameTable.getDrawDeck();
                DiscardDeck discardDeck = gameTable.getDiscardDeck();
                //Remove the top card
                UnoCard drawFour = discardDeck.removeDrawFour();
                //Reinsert in the deck
                drawDeck.putCard(drawFour);
                //Draw another card
                discardDeck.addDiscardedCard(drawDeck.drawCard());
            }
        }
        //Notify the observer of the changes
        gameTable.notifyObserver();
        //Add the listener to the new card added
        addListenerToJCards();
        //Rounds
        if (gameTable.getHandPlayer() != 0) {
            timer.start();
        }
    }

    /**
     * Handle the turn of a player
     */
    private void botPlayerTurn() {
        //The index of the hand player
        int indexHandPlayer = gameTable.getHandPlayer();
        //The hand player
        Player handPlayer = playerList.get(indexHandPlayer);
        //The top discard deck card
        UnoCard topDiscardCard = gameTable.getDiscardDeck().peek();
        //The hand game of the hand player
        List<UnoCard> handPlayerHand = handPlayer.getGameHand();
        //The ground color (in case the card on the ground is a wild)
        UnoCard.ColorCard groundColor = gameTable.getGroundColor();
        if (handPlayer instanceof BotPlayer) {
            //Check if the hand player has valid card in the hand
            if (handPlayer.hasValidCard(topDiscardCard, groundColor)) {
                //List of the possible choices
                List<UnoCard> possibleChoices = new ArrayList<>();
                for (UnoCard card: handPlayer.getGameHand()) {
                    if (card.getColor().equals(groundColor) ||
                        card.getValue().equals(topDiscardCard.getValue()) ||
                        card.getValue().equals(UnoCard.ValueCard.CHANGE_COLOR)) {
                        //Add to the list all the possible choices
                        possibleChoices.add(card);
                    }
                }
                //Randomly choose a card from among the possible
                int randIndex = new Random().nextInt(possibleChoices.size());
                //The chosen card
                UnoCard cardChosen = possibleChoices.get(randIndex);
                //Discard the card
                handPlayer.discardsCard(cardChosen, gameTable.getDiscardDeck());
                //Notify the observer
                gameTable.notifyObserver();
            } else if (handPlayer.hasDrawFour()) {      //The player have a draw four?
                //Remove the draw four from the hand of the player
                UnoCard drawFour = handPlayer.getGameHand().remove((int)handPlayer.getDrawFourIndex());
                //Add to the discard pile
                gameTable.getDiscardDeck().addDiscardedCard(drawFour);
                //Notify the observer
                gameTable.notifyObserver();
            } else {       //Draw a card
                //The drawn card
                UnoCard drawnCard = gameTable.getDrawDeck().drawCard();
                //Add the card to the hand game
                handPlayer.addCard(drawnCard);
                //Notify the observers
                gameTable.notifyObserver();
                //If the card is compatible with the one on the ground or is a wild, discard the card
                if (drawnCard.getColor().equals(groundColor) ||
                    drawnCard.getValue().equals(topDiscardCard.getValue()) ||
                    drawnCard.getColor().equals(UnoCard.ColorCard.WILD)) {
                    //Remove the card from the hand of the player
                    handPlayer.discardsCard(drawnCard, gameTable.getDiscardDeck());
                } else {    //Pass the turn
                    //Next player
                    gameTable.nextPlayer();
                    //Notify the observers
                    gameTable.notifyObserver();
                    //Add the listener to the added card
                    addListenerToJCards();
                    return;
                }
            }
            //Check if the card discarded is an action card
            checkIsActionCard();


            //Check if there is a card left in your hand
            if (handPlayer.hasOneCard()) {
                checkSaidUno(handPlayer);
            }

            //Pass the turn
            gameTable.nextPlayer();

            //Notify the observers
            gameTable.notifyObserver();

            //Add the listener to the cards
            addListenerToJCards();
        }
    }

    /**
     * Check if the last discarded card is an action card
     */
    private void checkIsActionCard() {
        switch (gameTable.getDiscardDeck().peek().getValue()) {
            case SKIP -> skip();
            case DRAW_TWO -> drawTwo();
            case REVERSE -> reverse();
            case CHANGE_COLOR -> changeColor();
            case DRAW_FOUR -> drawFour();
        }
    }

    /**
     * Check if the user has a card in his hand and if he said UNO!
     * @param handPlayer the hand player
     */
    private void checkSaidUno(Player handPlayer) {
        //The bot has a one in ten chance to not say UNO!
        if (handPlayer instanceof BotPlayer) {
            int randInt = new Random().nextInt(10);
            handPlayer.setUno(randInt != 0);
        }
            //If it has not been said UNO, the bot draw two card
            if (!handPlayer.isUno()) {
                JOptionPane.showMessageDialog(gameFrame, handPlayer.getName() + " did not say UNO!", "Button not clicked", JOptionPane.PLAIN_MESSAGE);
                handPlayer.addCard(gameTable.getDrawDeck().drawCard(2));

            }
            else {
                JOptionPane.showMessageDialog(gameFrame, handPlayer.getName() + " said UNO!", "Button clicked", JOptionPane.PLAIN_MESSAGE);
                handPlayer.setUno(false);
            }
    }

    /**
     * If is the first card to be discarded, the hand player skip the turn
     * If played by the hand player, the next player skip the turn
     */
    private void skip() { gameTable.nextPlayer(); }

    /**
     * The game round change (clockwise <-> anticlockwise)
     */
    private void reverse() { gameTable.changeDirection(); }

    /**
     * A color is chosen if the player is the user, otherwise random is chosen
     * If is the first card to be discarded, the hand player skip the turn
     */
    private void changeColor() {
        /* The hand player chooses a color from the following...
         * {RED, BLUE, GREEN, YELLOW}
         */
        UnoCard.ColorCard[] colorCards = Arrays.copyOfRange(UnoCard.ColorCard.getColors(), 0, 4);
        //The index of the hand player
        int indexHandPlayer = gameTable.getHandPlayer();
        //The hand player
        Player handPlayer = playerList.get(indexHandPlayer);
        if (handPlayer instanceof UserPlayer) {
            //Make visible the dialog
            gameFrame.getColorChooserDialog().setVisible(true);
        } else {
            //Pick a random number between 0 and 3
            int randInt = new Random().nextInt(4);
            //The color chosen
            UnoCard.ColorCard colorChosen = colorCards[randInt];
            //Set the background color according to the chosen color
            gameTable.setGroundColor(colorChosen);
        }
        //Check if is the first round
        if (gameTable.getDiscardDeck().size() == 1) {
            gameTable.nextPlayer();
        }
    }

    /**
     * If played by the hand player, the next player draws two card and skip the turn
     * If is the first card to be discarded, the hand player draws two card and skip the turn
     */
    private void drawTwo() {
        //The hand player draw two card and skip the turn
        List<UnoCard> drawnCard;
        //The index of the hand player
        int indexHandPlayer = gameTable.getHandPlayer();
        //The hand player
        Player handPlayer = playerList.get(indexHandPlayer);
        //The index of the next player
        int nextPlayerIndex = gameTable.getNextPlayer();
        //The next player
        Player nextPlayer = playerList.get(nextPlayerIndex);

        //Draw two cards and skip the turn if is the first round
        if (gameTable.getDiscardDeck().size() == 1) {
            //Draw two card
            drawnCard = gameTable.getDrawDeck().drawCard(2);
            //Add the cards to the hand of current player
            handPlayer.addCard(drawnCard);
        } else {    //the next player draw two card and skip the turn
            //Draw two card
            drawnCard = gameTable.getDrawDeck().drawCard(2);
            //Add the card to the hand of the next player
            nextPlayer.addCard(drawnCard);
        }
        //Skip the turn
        gameTable.nextPlayer();
    }

    /**
     * If played by the hand player, the next player draw four card and skip the turn
     * Also the hand player choose a color
     */
    private void drawFour() {
        //The next player
        Player nextPlayer = gameTable.getPlayerList().get(gameTable.getNextPlayer());
        //Choose a color
        changeColor();
        //The next player draw four cards
        nextPlayer.addCard(gameTable.getDrawDeck().drawCard(4));
        //Skip the turn
        gameTable.nextPlayer();
    }

    /**
     * Add a mouse listener for each card in the hand game of the user player
     */
    private void addListenerToJCards() {
        List<JCard> userJCardList = gameFrame.getGamePanel().getUserPanel().getGameHandPnl().getHandGame();
        for (JCard jCard: userJCardList) {
            jCard.addMouseListener(this);
        }
    }

    /**
     * Create the player and assign to the {@code GameTable}
     * @param username the name of the {@code UserPlayer}
     */
    private void createGameTable(String username) {
        //Pick three random name for bots
        List<String> botNames = new ArrayList<>();
        //The path of the file
        String path = new File("BotNameList.txt").getAbsolutePath();
        //Add random names to the list of bot names
        for(int i = 0; i < 3; i++) {
            botNames.add(readRandomName(path));
        }
        //Creates the list of player
        playerList = List.of(
                new UserPlayer(username),           //User player
                new BotPlayer(botNames.get(0)),     //Left Bot player
                new BotPlayer(botNames.get(1)),     //Up Bot player
                new BotPlayer(botNames.get(2))      //Right Bot player
        );
        //create the game table
        gameTable = new GameTable(playerList);
    }

    /**
     * Read from a file three random names
     * @param path the file containing the names
     * @return a random name
     */
    private String readRandomName(String path){
        //The list of names
        List<String> out = new ArrayList<>();
        //An instance of random
        Random rand = new Random();
        try {
            out = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.get(rand.nextInt(out.size()));
    }

    /**
     * Performs certain instructions depending on the object solicited
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String srcActionComm = e.getActionCommand();

        switch (srcActionComm) {
            case UNO_BTN -> unoBtn();
            case TIMER -> timerAction();
        }
    }

    /**
     * The task performed by the timer
     */
    private void timerAction() {
        botPlayerTurn();

        //If there is any player with zero cards in hand stops the timer
        if (gameTable.hasPlayerZeroCard()) {
            timer.stop();
        }
    }

    /**
     * Method perform when mouse click on frame
     * @param e the event to be processed
     */
    public void mouseClicked(MouseEvent e) {
        //The source of the event
        Object source = e.getSource();
        //The label of the draw deck
        JLabel drawLabel = gameFrame.getGamePanel().getTablePanel().getDrawDeckLbl();
        //The user player
        Player usrPlayer = gameTable.getPlayerList().get(0);
        //The top discard deck card
        UnoCard topDiscardCard = gameTable.getDiscardDeck().peek();
        //The color of the top discard deck card
        UnoCard.ColorCard groundColor =  gameTable.getGroundColor();

        /* Management of various events */
        //Related to the TablePanel
        if (source.equals(drawLabel) && gameTable.getHandPlayer() == 0 && !usrPlayer.hasValidCard(topDiscardCard, groundColor)) {
            drawCard(source);
        }

        //Related to the UserPanel
        else if (gameTable.getHandPlayer() == 0 &&
                (usrPlayer.hasValidCard(topDiscardCard, groundColor) || usrPlayer.hasDrawFour()) &&
                (source.getClass() != JLabel.class)) {
            userPlayerTurn(source);
        }

        // Related to the ColorChooserDialog
        chooseColor(source);
    }

    /**
     * Choose a color from {@code ColorChooserDialog} and make not visible
     * @param src is the component that had generated the event
     */
    private void chooseColor(Object src) {
        ColorChooserDialog.ColorChooserPanel colorChooserPanel = gameFrame.getColorChooserDialog().getColorChooserPanel();
        JLabel redLbl = colorChooserPanel.getRedLbl(),
                blueLbl = colorChooserPanel.getBlueLbl(),
                greenLbl = colorChooserPanel.getGreenLbl(),
                yellowLbl = colorChooserPanel.getYellowLbl();
        JLabel colorGroundLbl = gameFrame.getGamePanel().getTablePanel().getColorGroundLbl();
        //Check which label have been clicked
        if (src.equals(redLbl)) {
            //Set the ground color
            gameTable.setGroundColor(UnoCard.ColorCard.RED);
            //Make not visible the dialog
            gameFrame.getColorChooserDialog().setVisible(false);
        } else if (src.equals(blueLbl)) {
            //Set the ground color
            gameTable.setGroundColor(UnoCard.ColorCard.BLUE);
            //Make not visible the dialog
            gameFrame.getColorChooserDialog().setVisible(false);
        } else if (src.equals(greenLbl)) {
            //Set the ground color
            gameTable.setGroundColor(UnoCard.ColorCard.GREEN);
            //Make not visible the dialog
            gameFrame.getColorChooserDialog().setVisible(false);
        } else if (src.equals(yellowLbl)) {
            //Set the ground color
            gameTable.setGroundColor(UnoCard.ColorCard.YELLOW);
            //Make not visible the dialog
            gameFrame.getColorChooserDialog().setVisible(false);
        }
    }

    /**
     * Allows to the {@code UserPlayer} to draw a card
     * @param src is the component that had generated the event (in this case the JCard of the draw deck)
     */
    private void drawCard(Object src) {
        //The index of the hand player
        int indexHandPlayer = gameTable.getHandPlayer();
        //The hand player
        Player handPlayer = gameTable.getPlayerList().get(indexHandPlayer);
        //The top discard pile card
        UnoCard topDiscardCard = gameTable.getDiscardDeck().peek();
        //If the player has neither cards to play nor wild cards
        if (!(handPlayer.hasWild())) {
            //Draw a card
            UnoCard drawnCard = gameTable.getDrawDeck().drawCard();
            //Add to the hand
            handPlayer.addCard(drawnCard);
            //If the card is not compatible
            if (!drawnCard.getColor().equals(gameTable.getGroundColor()) &&
                    !drawnCard.getValue().equals(topDiscardCard.getValue()) &&
                    !drawnCard.getColor().equals(UnoCard.ColorCard.WILD)) {
                //Pass the turn
                gameTable.nextPlayer();
                //Notify the observers
                gameTable.notifyObserver();
                //Add the listener to the added card
                addListenerToJCards();
                return;
            }
        }
        //Notify the observer
        gameTable.notifyObserver();
        //Add the listener to the card
        addListenerToJCards();
    }

    /**
     * Manage the turn of the UserPlayer
     * @param src the clicked card
     */
    private void userPlayerTurn(Object src) {
        //Stops the timer
        timer.stop();
        //The clicked card
        JCard clickedCard = (JCard)src;
        //The user player
        Player userPlayer = gameTable.getPlayerList().get(0);
        //The index of the card clicked in the user’s hand game
        int indexCard = 0;
        //The name of the JCard
        String nameCard = clickedCard.toString();
        //Check which index the card is in
        for (UnoCard card: userPlayer.getGameHand()) {
            if (card.toString().equals(nameCard)) {
                indexCard = userPlayer.getGameHand().indexOf(card);
                break;
            }
        }
        //The clicked UnoCard
        UnoCard selectedUnoCard = userPlayer.getGameHand().get(indexCard);
        if (isValidCard(clickedCard)) {
            //Discard the card
            userPlayer.discardsCard(selectedUnoCard, gameTable.getDiscardDeck());
            //Check that the discarded card is an action card
            checkIsActionCard();
            //Notify the observer
            gameTable.notifyObserver();
            /* If the user has one card run the checkSaidUno() method after 2sec
            * If within two seconds the user does not click the UNO button
            * Draw two cards
            */
            //Check if it has one card
            if (userPlayer.hasOneCard()) {
                new java.util.Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                checkSaidUno(userPlayer);
                            }
                        },
                        2000
                );
            }
            //checkOneCard(userPlayer);
            //Skip the turn
            gameTable.nextPlayer();
            //Notify the observers
            gameTable.notifyObserver();
            //Add the listeners to the cards
            addListenerToJCards();
            //Restart the timer
            timer.start();
        }
        //Else if the selected card is a DRAW_FOUR and the player has no valid cards in the hand
        else if (selectedUnoCard.getValue().equals(UnoCard.ValueCard.DRAW_FOUR) &&
                !(userPlayer.hasValidCard(gameTable.getDiscardDeck().peek(), gameTable.getGroundColor()))) {
            //Discard draw four card
            userPlayer.discardsCard(selectedUnoCard, gameTable.getDiscardDeck());
            //Check that the discarded card is an action card
            checkIsActionCard();
            //Notify the observers
            gameTable.notifyObserver();
            /* If the user has one card run the checkSaidUno() method after 2sec
             * If within two seconds the user does not click the UNO button
             * Draw two cards
             */
            if (userPlayer.hasOneCard()) {
                new java.util.Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                checkSaidUno(userPlayer);
                            }
                        },
                        2000
                );
            }
            //Skip the turn
            gameTable.nextPlayer();
            //Notify the observers
            gameTable.notifyObserver();
            //Add the listeners to the cards
            addListenerToJCards();
            //Restart the timer
            timer.start();
        }
    }

    /**
     * Check if the input JCard can be discarded
     * @param card the clicked card
     * @return true if you can discard, false otherwise
     */
    private boolean isValidCard(JCard card) {
        return card.getColorCard().equals(gameTable.getGroundColor().getNameColor()) ||
                card.getNumberCard() == gameTable.getDiscardDeck().peek().getValue().getNumberValue() ||
                card.getNumberCard() == UnoCard.ValueCard.CHANGE_COLOR.getNumberValue();
    }

    /**
     * The method performed after the click of {@code unoBtn}
     */
    private void unoBtn() {
        //The UNO! button
        JButton unoButton = gameFrame.getGamePanel().getTablePanel().getUnoBtn();
        //The hand player
        Player usrPlayer = gameTable.getPlayerList().get(0);
        //If the user pull the button and has one card in his hand
        if (usrPlayer.hasOneCard()) {
            usrPlayer.setUno(true);
        }
    }
}