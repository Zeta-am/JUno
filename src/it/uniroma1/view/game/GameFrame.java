package it.uniroma1.view.game;

import it.uniroma1.Observer;
import it.uniroma1.controller.GameController;
import it.uniroma1.model.GameTable;
import it.uniroma1.model.UnoCard;
import it.uniroma1.model.deck.DiscardDeck;
import it.uniroma1.model.deck.DrawDeck;
import it.uniroma1.model.player.Player;
import it.uniroma1.model.player.UserPlayer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * The {@code GameFrame} class is the main frame of the game.
 * Inside it contains an instance of the {@link  GamePanel} class.
 * Implements the {@link Observer} interface and receives as parameter under construction an instance of {@link GameTable}
 * @author Andrea Musolino
 */
public class GameFrame
    extends JFrame
    implements Observer {

    /**
     * The game panel
     */
    private GamePanel gamePanel;

    /**
     * The dialog for asking color to choose
     */
    private ColorChooserDialog colorChooserDialog;

    /**
     * The observed object
     */
    private GameTable gameTable;



    /**
     * Constructor
     */
    public GameFrame(GameTable gameTable) {
        super("JUno");
        this.gameTable = gameTable;
        gamePanel = new GamePanel();
        colorChooserDialog = new ColorChooserDialog();
        setContentPane(gamePanel);
        setupFrame();
    }

    /**
     * Set the parameters of the frame
     */
    private void setupFrame() {
        setSize(new Dimension(1440, 720));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Get the game panel
     * @return the game panel
     */
    public GamePanel getGamePanel() { return gamePanel; }

    /**
     * Get the dialog
     * @return the dialog
     */
    public ColorChooserDialog getColorChooserDialog() { return colorChooserDialog; }

    /**
     * Update the state of the components of the GameFrame
     */
    @Override
    public void update() {

        /* Game table (Model) */
        DrawDeck drawDeck = gameTable.getDrawDeck();
        DiscardDeck discardDeck = gameTable.getDiscardDeck();
        List<Player> playerList = gameTable.getPlayerList();

        /* Game Panel (View) */
        TablePanel tablePnl = gamePanel.getTablePanel();
        JCard discardLbl = tablePnl.getDiscardDeckLbl();
        JLabel colorGroundLbl = tablePnl.getColorGroundLbl();
        List<PlayerPanel> playerPanelList = gamePanel.getPlayerPanelList();


        /*
        * Assign the players' names and score to the respective labels &
        * Add to each GameHandPanel the cards that each player holds
        */
        for (int i = 0; i < playerList.size(); i++) {
            //The i-th player
            Player player = playerList.get(i);
            //The i-th player panel
            PlayerPanel playerPanel = playerPanelList.get(i);
            //The i-th game hand player panel
            GameHandPanel gameHandPanel = playerPanel.getGameHandPnl();
            //The list of card of the i-th player
            List<UnoCard> gameHand = player.getGameHand();
            //Clear the panel
            gameHandPanel.clear();
            //Set the nameLbl with the name of the player
            playerPanel.getNameLbl().setText(player.getName());
            //If the i-th player is the hand player, change the color of his name label
            if (i == gameTable.getHandPlayer()) {
                //Change the color of the name label of the hand player
                playerPanel.getNameLbl().setForeground(Color.YELLOW);
            } else {
                playerPanel.getNameLbl().setForeground(Color.BLACK);
            }
            //Set the score label with the score of the player
            playerPanel.getScoreLbl().setText("Score: " + player.getScore());
            if (player instanceof UserPlayer) {
                for (UnoCard card: gameHand) {
                    JCard jCard = new JCard(card.getColor().getNameColor(), card.getValue().getNumberValue());
                    gameHandPanel.addJCard(jCard);
                }
            } else {
                for (UnoCard card: gameHand) {
                    JCard jCard = new JCard("back");
                    gameHandPanel.addJCard(jCard);
                }
            }
        }
        for (PlayerPanel pp: playerPanelList) {
            pp.getGameHandPnl().repaint();
            pp.getGameHandPnl().revalidate();
        }

        //Check if the draw deck is empty
        if (drawDeck.size() == 4) {
            drawDeck.refillDeck();
            discardDeck.clear();
            discardDeck.addDiscardedCard(drawDeck.drawCard());
        }

        /* Set the icon of the discard label and the color of the ground label according to the value of colorGround of GameTable*/
        UnoCard topDiscardCard = discardDeck.peek();
        gameTable.setGroundColor(topDiscardCard.getColor());
        URL cardUrl = Objects.requireNonNull(getClass().getResource("/resources/iconCards/"+ topDiscardCard.toString() +".png"));
        discardLbl.setIcon(new ImageIcon(cardUrl));
        switch (gameTable.getGroundColor()) {
            case RED -> colorGroundLbl.setBackground(GameController.RED);
            case BLUE -> colorGroundLbl.setBackground(GameController.BLUE);
            case YELLOW -> colorGroundLbl.setBackground(GameController.YELLOW);
            case GREEN -> colorGroundLbl.setBackground(GameController.GREEN);
        }

        //If any players han no more cards in his hand the game is over
        if (gameTable.hasPlayerZeroCard()) {
            //Show cards lefts in bot hands
            for (int i = 1; i < playerList.size(); i++) {
                //The i-th player
                Player player = playerList.get(i);
                //The i-th player panel
                PlayerPanel playerPanel = playerPanelList.get(i);
                //The i-th game hand player panel
                GameHandPanel gameHandPanel = playerPanel.getGameHandPnl();
                //The list of card of the i-th player
                List<UnoCard> gameHand = player.getGameHand();
                //Clear the panel
                gameHandPanel.clear();
                //Set the score label with the score of the player
                playerPanel.getScoreLbl().setText("Score: " + player.getScore());
                    for (UnoCard card: gameHand) {
                        JCard jCard = new JCard(card.getColor().getNameColor(), card.getValue().getNumberValue());
                        gameHandPanel.addJCard(jCard);
                    }
            }
            for (PlayerPanel pp: playerPanelList) {
                pp.getGameHandPnl().repaint();
                pp.getGameHandPnl().revalidate();
            }
            //The winner
            Player winnerPlayer = null;
            //Check who is the winner
            for (Player p: playerList) {
                if (p.hasEmptyHand()) {
                    winnerPlayer = p;
                    break;
                }
            }
            //The score of the game
            int scoreRound = gameTable.countScoreCards();
            //Add the score to the winner player
            winnerPlayer.addRoundScore(scoreRound);
            //Declare the winner of the round or the game
            if (winnerPlayer.hasWon()) {
                JOptionPane.showMessageDialog(this, winnerPlayer.getName() + " has won!", "Game over", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            else {
                int choice = JOptionPane.showConfirmDialog(this,
                        winnerPlayer.getName() + " won the round with a score of " + scoreRound +
                                "\nYou want to keep playing?",
                        "End Game",
                        JOptionPane.YES_NO_OPTION
                );
                //New Game
                if (choice == JOptionPane.YES_OPTION) {
                    //Reset the game
                    gameTable.reset();
                }
                //Quit
                else {
                    System.exit(0);
                }
            }
        }
    }
}
