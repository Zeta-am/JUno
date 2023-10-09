package it.uniroma1.model;

/**
 * The {@code UnoCard} class is used to represent the different characteristics of a Uno card, namely:
 * color, value, score and behavior.
 * <p>Color: is represented by {@link ColorCard} {@code enum}
 * <p>Value: is represented by {@link ValueCard} {@code enum}
 * <p>Score: is represented by an {@code int}
 * @author Andrea Musolino
 */
public class UnoCard {

    /**
     * Declares the possibles color that a {@code UnoCard} can assume: red, blue, green, yellow, wild
     */
    public enum ColorCard {

        /**
         * Each constant represent the possible color that a card can have
         */
        RED("red"),
        BLUE("blue"),
        GREEN("green"),
        YELLOW("yellow"),
        WILD("wild");

        /**
         * The name of a color
         */
        private String nameColor;

        /**
         * Array of color constant
         */
        private static ColorCard[] colors = ColorCard.values();

        /**
         * Constructor
         * @param nameColor the name of the color
         */
        ColorCard (String nameColor) { this.nameColor = nameColor; }

        /**
         * Return the name associate to the constant
         * @return the name String associate to the constant
         */
        public String getNameColor () { return nameColor; }

        /**
         * Return an array of constant
         * @return an array of constant
         */
        public static ColorCard[] getColors () { return colors; }


    }

    /**
     * Declares the possible values that a {@code UnoCard} can assume: 0,...,9, DrawTwo, Skip, Reverse,
     * ChangeColor and DrawFour
     */
    public enum ValueCard {

        /**
         * Each constant represent the possible values that a card can have
         */
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        REVERSE(10),
        SKIP(11),
        DRAW_TWO(12),
        CHANGE_COLOR(13),
        DRAW_FOUR(14);

        /**
         * The number associate to a constant
         */
        private int numberValue;

        /**
         * Array of value constant
         */
        private static ValueCard[] valuesCards = ValueCard.values();

        /**
         * Constructor
         * @param numberValue the number associate to the constant
         */
        ValueCard (int numberValue) {
            this.numberValue = numberValue;
        }

        /**
         * Return the value associate to a specific constant
         * @return the value associate to a specific constant
         */
        public int getNumberValue () { return numberValue; }

        /**
         * Return an array of constant
         * @return an array of constant
         */
        public static ValueCard[] getValuesCards () { return valuesCards; }
    }

    /**
     * The value of the card
     */
    private ValueCard value;

    /**
     * The color of the card
     */
    private ColorCard color;

    /**
     * The score of the card
     */
    private int score;

    /**
     * Constructor
     * @param color the color of the card
     * @param value the value of the card
     */
    public UnoCard (ColorCard color, ValueCard value) {
        //Assign the color
        this.color = color;

        //Assign the value
        this.value = value;

        //Assign the score
        this.score = switch (value) {
            case ZERO -> 0;
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case CHANGE_COLOR, DRAW_FOUR -> 50;
            default -> 20;
        };
    }

    /**
     * Return the color of the card
     * @return the color of the card
     */
    public ColorCard getColor () { return color; }

    /**
     * Return the value of the card
     * @return the value of the card
     */
    public ValueCard getValue () { return value; }

    /**
     * Return the score of the card
     * @return the score of the card
     */
    public int getScore () { return score; }

    /**
     * Set the color of a card, only if his initial color is WILD
     * @param color the color of the card
     */
    public void setColor(ColorCard color) {
        if (this.color.equals(ColorCard.WILD)) {
            this.color = color;
        }
    }

    /**
     * @return a formatted string to represent color and value of a card
     */
    @Override
    public String toString() {
        return getColor().nameColor + "" + getValue().numberValue;
    }
}
