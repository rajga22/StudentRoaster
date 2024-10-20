import java.util.*;

/**
Program name: GuessingGame

Author: Written by self

Date created: April 12, 2022

Version: Java SE 16.0.2

Program description:
- Allow any number of users to play a guessing game.
- At the start of each round of the game, the players specify the bounds within which their guesses should lie.
- A number is generated for each player. Each player then tries guess their respective generated number. The game
play will keep looping until the guess is correct. The user(s) can add players to the game in the first round,
and the program will keep track the the number of guesses made by each player.
- The winner of the round is the player(s) with the least amount of guesses. Each win is worth a point. Players
can replay rounds until they want to stop, at which point the winner (the player with the most amount of points)
is found.
    - If there is only one user, the user will play against the admin.
    - The users have the option of adding a human player or computer player as new players.

Program classes:
GuessingGame methods:
    - Main method: Adds players to the game, allows players to make guesses, and finds winners of the round and game.
    - printWinners: Finds and prints either the grand winners of the game or the winners of the round depending on the
    parameters. If finding the round winners, the program also increases the score of the round winner.
    - findMaxOrMin: Finds the indexes of the maximum/ minimum values in a list.
GuessPlayer methods:
    - Class variables:
        - Static: numPlayers: number of players in the fame; lowerBound and upperBound: The lower bound and upper
        bounds for the range of the users' guesses; roundNum: The number of rounds played;
        - Non-static: playerNum: The player number of each player; playerScore: Each player's score; guessCount: The
        number of guesses each player has made.
    - Constructors: GuessPlayer() default to initialise the each player's number, increase the number of players, and
    initialise each player's score.
    - Setter and getter methods:
    - Setters: setLowerBound(int) and setUpperBound(int): Set the lower and upper bounds of the guess range;
    increaseRoundNum(): increase the number of rounds played; increasePlayerScore(): Increase a particular player's
    score in the game.
    - Getters: getNumPlayers(): Get the number of players in the game; getRoundNum(): Get the number of rounds
    played; getPlayerScore(): Get the current player's score; getGuessCount(): Get the number of guesses a particular
    player has made.
    - Guess methods: bounds(): Set the range for the numbers in the guess game; startGame(): Allow user to play the
    guessing game, generate random numbers and allow user to guess what it is; checkWin(int, int): Print a
    statement of whether a user's guess is too low, too high, or correct.
    - User-input verification: integerFinder(String, String): Allow a user to enter an integer and ensure that it is
    formatted correctly; yesNoFinder(String): Allow a user to enter a yes/ no response to a question, yes=true.
GuessComputer (extends GuessPlayer) methods:
    - Constructors: GuessComputer() default method, invokes parent default and has the same functionality.
    - Guess methods: startGame(): Allow computer to play the guessing game, generate random numbers and guess a random
    number within the bounds; checkWin(int, int, int[]): Print a statement of whether the computer's guess is too high,
    low, or correct, and provides new bounds within which the computer should guessed based on that, as the program
    cannot reason like a human that it should guess higher/ lower.
*/

public class GuessingGame {

    public static void main (String[] args) {

        // Local variables
        ArrayList<GuessPlayer> players = new ArrayList<GuessPlayer>(); // Players of the guessing game
        GuessPlayer admin = new GuessComputer(); // Helps run the game. The computer is player 0.
        players.add(new GuessPlayer()); // Create first player (player number 1)
        boolean playGame; // Should a new round be played? yes=true
        boolean singleMode; // Is the game in single-player mode? yes=true

        // Play game
        do {

            playGame = false;

            // Increase number of rounds played
            admin.increaseRoundNum();

            System.out.println("---------------------------------------------");
            System.out.println("ROUND " + admin.getRoundNum());
            System.out.println("---------------------------------------------");

            // Set bounds (range of guesses) for the guessing game
            admin.bounds();

            System.out.println("---------------------------------------------");

            // For each player, play the guessing game
            for (int i=0; i<players.size(); i++) {

                players.get(i).startGame(); // Make player make guesses

                // If round 1, add more players to the game if players would like
                // If only 1 player, make player play against the computer
                if ((admin.getRoundNum() == 1 && players.size() == 1) || (admin.getRoundNum() == 1 && players.size() != 1 && players.get(1).getPlayerNum() != 0)) {
                    // If it's round 1 AND there's only 1 player OR if it's round 1, there's more than one player, and the other player is not the admin (single-player)
                    // Ask if the user wants to add an opponent
                    if (admin.yesNoFinder("Add new player?")) { // If yes, add player
                        // Add either a player or computer
                        if (admin.yesNoFinder("Add human?"))  // If yes, add human, else, add computer
                            players.add(new GuessPlayer()); // Add new player
                        else
                            players.add(new GuessComputer());
                    } else {  // Stop adding players
                        if (players.size() == 1) {
                            System.out.println("Entering single player mode.");
                            players.add(admin);
                            System.out.println("The computer admin, Player 0, has been added as your opponent.");
                        }
                    }
                }
            }

            // Find the winner of the round
            // The winner of the round has the least number of guesses
            // Increase the round winners' scores
            System.out.println(printWinners(players, false));

            // Print player scores
            for (GuessPlayer player : players)
                System.out.println("Player " + player.getPlayerNum() + "'s current score is " + player.getPlayerScore() + ".");

            // Keep playing more rounds?
            playGame = admin.yesNoFinder("Keep playing more rounds?");

        } while (playGame);

        System.out.println("---------------------------------------------");
        System.out.println("GAME OVER");
        System.out.println("---------------------------------------------");
        // Find winner(s) of the game
        // Winner(s) of the game has the highest score
        System.out.println(printWinners(players, true)); // Find and print Grand winners

    }

    /**
     * Find the maximum or minimum value(s) in an int[] and the players who have that maximum/ minumum value(s)
     * @param lst: The list whose max/ min is being searched for
     * @param max: Whether the maximum or minimum value(s) is being searched for
     * @return selected: The indexes (index == player number) of the max/min values in the list
     */
    public static int[] findMaxOrMin(int[] lst, boolean max) {

        // Local variables
        ArrayList<Integer> selectIndexes = new ArrayList<Integer>(); // Indexes of the higest/ lowest values
        int extreme; // Highest/ lowest value in the list

        extreme = lst[0]; // Lowest number of guesses
        int currVal; // Current player's number of guesses
        for (int value : lst) {
            if (max) { // If greater than greatest, current value is the highest in the list
                if (value > extreme)
                    extreme = value;
            } else { // If less than least, current value is the lowest in the list
                if (value < extreme)
                    extreme = value;
            }
        }

        // Find indices with highest value
        for (int i=0; i<lst.length; i++)
            if (lst[i] == extreme) selectIndexes.add(i);

        return selectIndexes.stream().mapToInt(i -> i).toArray();

    }

    /**
     * Find and print the winners in the game (either the round winners or the grand winners)
     * Increase round winners' scores
     * @param players: The list of the players in the guessing game
     * @param grand: Is the program looking for the grand winners or the round winners? (grand = true)
     */
    public static String printWinners(ArrayList<GuessPlayer> players, boolean grand) {

        // Local variables
        StringBuilder winStatement = new StringBuilder(); // Statement to print about winners

        if (grand) { // Find Grand winners
            ArrayList<Integer> playerScores = new ArrayList<Integer>(); // List of all player scores
            for (GuessPlayer player : players) playerScores.add(player.getPlayerScore());
            int[] gameWinners = findMaxOrMin(playerScores.stream().mapToInt(i -> i).toArray(), true); // Winners of the game
            // Print Grand winners
            winStatement.append("The Grand winner(s) of this game is... Player(s): ");
            for (int winner : gameWinners)
                winStatement.append(players.get(winner).getPlayerNum() + ", ");
            winStatement.append("CONGRATS for your victory!!!\n");

        } else { // Find round winners
            ArrayList<Integer> playerGuesses = new ArrayList<Integer>(); // List of all player guesses
            for (GuessPlayer player : players) playerGuesses.add(player.getGuessCount());
            int[] roundWinners = findMaxOrMin(playerGuesses.stream().mapToInt(i -> i).toArray(), false); // Winners of the round
            // Print winners and increase their scores
            winStatement.append("The winner(s) of this round is... Player(s): ");
            for (int winner : roundWinners) {
                winStatement.append(players.get(winner).getPlayerNum() + ", ");
                players.get(winner).increasePlayerScore(); // Increase round winner's score
            }
            winStatement.append("great job.\n");
        }

        return winStatement.toString();

    }

}

class GuessPlayer {

    /*********************** CLASS VARIABLES ***********************/

    protected static int lowerBound = -1, upperBound = -1; // Range of users' guesses
    protected static int roundNum = 0; // Number of rounds played
    protected static int numPlayers = -1; // Number of players in the game
    protected int playerNum = -1; // Current player's number
    protected int playerScore = -1; // Current player's score
    protected int guessCount = -1; // Number of guesses the player has made

    /************************ CONSTRUCTORS *************************/

    /**
     * Default constructor for GuessPlayer class. Adds a player to the game and initialises player's score.
     */
    public GuessPlayer() {
        this.numPlayers++; // New player has been added
        this.playerNum = this.numPlayers;
        this.playerScore = 0;
    }

    /********************* SETTERS AND GETTERS *********************/

    public static void setLowerBound(int lb) {
        lowerBound = lb; }
    public static void setUpperBound(int ub) {
        upperBound = ub; }
    public static void increaseRoundNum() {
        roundNum++; }
    public void increasePlayerScore() {
        this.playerScore++; }

    public static int getNumPlayers() {
        return numPlayers; }
    public static int getRoundNum() {
        return roundNum; }
    public int getPlayerNum() {
        return this.playerNum; }
    public int getPlayerScore() {
        return this.playerScore; }
    public int getGuessCount() {
        return this.guessCount; }

    /*********************** GUESS METHODS *************************/

    /**
     * Set lower and upper bounds of the user's guesses
     */
    public void bounds() {

        // Local variables
        int lb = -1, ub = -1; // lower and upper bounds
        String lbQuestion = "Enter the lower bound of your guesses: ";
        String lbException = "Please enter an integer number.";
        String ubQuestion = "Enter the upper bound of your guesses: ";
        String ubException = "Please enter an integer number greater than the lower bound.";

        // Set lower bound
        lb = integerFinder(lbQuestion, lbException);

        // Set upper bound
        // Loop until upper bound is higher than lower bound
        do {
            ub = integerFinder(ubQuestion, ubException);
        } while (ub <= lb);

        setLowerBound(lb);
        setUpperBound(ub);

    }

    /**
     * Print a statement of whether a user's guess is a winning guess, too low, or too high
     * @param guess: The number that the user guessed
     * @param answer: The number that the computer generated
     * @return didWin: A statement of whether the user has won or not
     */
    public void checkWin(int guess, int answer) {
        if (guess < answer)
            System.out.println("Too low, guess again!");
        else if (guess > answer)
            System.out.println("Too high, guess again!");
        else
            System.out.println("Bingo, you got it!");
    }

    /**
     * Generate a random guess within a user-specified range. Keep track of the number of guesses a user takes to
     find the answer.
     */
    public void startGame() {

        // Local variables
        Random rand = new Random(); // Generates random number
        int answer; // Random number that was generated
        int guess; // User's guess
        String guessQuestion = "Player " + this.playerNum + " , please enter your guess: ";
        String guessException = "Please enter an integer number.";

        this.guessCount = 0; // Reset number of guesses

        // Generate random number
        answer = rand.nextInt((this.upperBound - this.lowerBound) + 1) + this.lowerBound;

        // Allow user to guess the random number
        do {
            this.guessCount++;

            // Allow user to guess a number
            guess = integerFinder(guessQuestion, guessException);
            // Try again if guess is not in range
            if (guess < this.lowerBound || guess > this.upperBound) {
                System.out.println("Please enter a number within the range " + this.lowerBound + " to " + this.upperBound + ".");
                guessCount--; // Re-try guess
                continue; // Guess must be within range
            }

            // Is the answer too low, too high, or just right?
            checkWin(guess, answer);

        } while (guess != answer); // End round if user makes the guess correctly

        // Finish turn
        System.out.println("Turn over. Player " + this.playerNum + " took " + this.guessCount + " guesses to find the answer.");

    }

    /******************* USER INPUT VERIFICATION *********************/

    /**
     * Allow user to enter an integer number and verify that it is an acceptable input
     * @param question: What the user should be asked when entering the number
     * @param exceptStatement: What should be printed if the user enters an invalid number.
     * @return userInteger: The integer number entered by the user
     */
    private int integerFinder(String question, String exceptStatement) {

        // Local variables
        Scanner keyboard = new Scanner(System.in); // Reads user input
    	String userInput; // Initial unassigned user input
        int userInteger; // Initialise user integer value

        // Input validation loop: ask for number and ensure that it is a number greater than 0
        while (true) {
            try {
                System.out.print(question);
                userInput = keyboard.nextLine();
            	userInteger = Integer.parseInt(userInput); // ticketsToSelly Integer
                break; // End validation loop if entry is an acceptable numeric value
            } catch (Exception e) {
                System.out.println(exceptStatement);
            }
        }

        return userInteger;
    }

    /**
     * Allow user to indicate Yes(Y) or No(N) to a question
     * @param question: What the user should be asked when entering Yes or No
     * @return true=Y, false=N
     */
    public boolean yesNoFinder(String question) {

        Scanner keyboard = new Scanner(System.in); // Reads user input

        while(true) {
            System.out.print(question + " (Y/N): ");
            switch(keyboard.nextLine().toUpperCase()) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Please enter \"Y\" for Yes or \"N\" for No.");
            }
        }

    }

}

class GuessComputer extends GuessPlayer {

    /************************ CONSTRUCTORS *************************/

    /**
     * Default constructor for GuessComputer class. Adds a player to the game and initialises player's score through super.
     */
    public GuessComputer() {
        super();
    }

    /*********************** GUESS METHODS *************************/

    /**
     * Print a statement of whether a user's guess is a winning guess, too low, or too high
     * Return the new bounds of where the computer should guess (if guess is too low, don't go lower than it again)
     * @param guess: The number that the user guessed
     * @param answer: The number that the computer generated
     * @param bounds: The current bounds within which the computer is guessing (upper bound, lower bound)
     * @return: New bounds within which a computer should guess
     */
    public int[] checkWin(int guess, int answer, int[] bounds) {
        if (guess < answer) {
            System.out.println("Too low, guess again!");
            return new int[] {bounds[0], guess+1}; // If too low, upper bound is same, but lower bound is increased
        } else if (guess > answer) {
            System.out.println("Too high, guess again!");
            return new int[] {guess-1, bounds[1]}; // If too high, lower bound is same, but upper bound is decreased
        } else {
            System.out.println("Bingo, you got it!");
            return bounds; // Bounds remain the same
        }
    }

    /**
     * Generate a random guess within a user-specified range. Keep track of the number of guesses a user takes to
     find the answer.
     */
    public void startGame() {

        // Local variables
        Random rand = new Random(); // Generates random number
        int answer; // Random number that was generated
        int guess; // Computer player's guess
        String guessQuestion = "Player " + this.playerNum + " , please enter your guess: ";
        String guessException = "Please enter an integer number.";
        int[] bounds = {this.upperBound, this.lowerBound}; // The bounds for where the guess should lie (upper bound, lower bound)

        this.guessCount = 0; // Reset number of guesses

        // Generate random number
        answer = rand.nextInt((this.upperBound - this.lowerBound) + 1) + this.lowerBound;

        // Allow user to guess the random number
        do {
            this.guessCount++;

            // Allow human user to enter a number, otherwise have the computer generate a number
            System.out.print(guessQuestion);
            guess = rand.nextInt((bounds[0] - bounds[1]) + 1) + bounds[1];
            System.out.print(guess + "\n");

            // Is the answer too low, too high, or just right?
            bounds = checkWin(guess, answer, bounds);

        } while (guess != answer); // End round if user makes the guess correctly

        // Finish turn
        System.out.println("Turn over. Player " + this.playerNum + " took " + this.guessCount + " guesses to find the answer.");

    }


}

/**
 *
 Sample program output:
 *
---------------------------------------------
ROUND 1
---------------------------------------------
Enter the lower bound of your guesses:
Please enter an integer number.
Enter the lower bound of your guesses: 5
Enter the upper bound of your guesses: 99
---------------------------------------------
Player 1 , please enter your guess: 4
Please enter a number within the range 5 to 99.
Player 1 , please enter your guess: 7
Too low, guess again!
Player 1 , please enter your guess: 40
Too low, guess again!
Player 1 , please enter your guess: 45
Too low, guess again!
Player 1 , please enter your guess: 49
Too low, guess again!
Player 1 , please enter your guess: 80
Too high, guess again!
Player 1 , please enter your guess: 70
Too high, guess again!
Player 1 , please enter your guess: 60
Too low, guess again!
Player 1 , please enter your guess: 65
Too high, guess again!
Player 1 , please enter your guess: 64
Too high, guess again!
Player 1 , please enter your guess: 62
Too low, guess again!
Player 1 , please enter your guess: 61
Too low, guess again!
Player 1 , please enter your guess: 63
Bingo, you got it!
Turn over. Player 1 took 12 guesses to find the answer.
Add new player? (Y/N): yy
Please enter "Y" for Yes or "N" for No.
Add new player? (Y/N): y
Add human? (Y/N): y
Player 2 , please enter your guess: 35
Too low, guess again!
Player 2 , please enter your guess: 45
Too low, guess again!
Player 2 , please enter your guess: 48
Too low, guess again!
Player 2 , please enter your guess: 49
Too low, guess again!
Player 2 , please enter your guess: 55
Too high, guess again!
Player 2 , please enter your guess: 50
Too low, guess again!
Player 2 , please enter your guess: 53
Too high, guess again!
Player 2 , please enter your guess: 52
Bingo, you got it!
Turn over. Player 2 took 8 guesses to find the answer.
Add new player? (Y/N): y
Add human? (Y/N): n
Player 3 , please enter your guess: 5
Too low, guess again!
Player 3 , please enter your guess: 99
Too high, guess again!
Player 3 , please enter your guess: 70
Too low, guess again!
Player 3 , please enter your guess: 78
Too low, guess again!
Player 3 , please enter your guess: 87
Too high, guess again!
Player 3 , please enter your guess: 83
Too high, guess again!
Player 3 , please enter your guess: 81
Too high, guess again!
Player 3 , please enter your guess: 79
Too low, guess again!
Player 3 , please enter your guess: 80
Bingo, you got it!
Turn over. Player 3 took 9 guesses to find the answer.
Add new player? (Y/N): y
Add human? (Y/N): n
Player 4 , please enter your guess: 15
Too low, guess again!
Player 4 , please enter your guess: 36
Too low, guess again!
Player 4 , please enter your guess: 92
Too low, guess again!
Player 4 , please enter your guess: 94
Too low, guess again!
Player 4 , please enter your guess: 98
Too high, guess again!
Player 4 , please enter your guess: 96
Too high, guess again!
Player 4 , please enter your guess: 95
Bingo, you got it!
Turn over. Player 4 took 7 guesses to find the answer.
Add new player? (Y/N): n
The winner(s) of this round is... Player(s): 4, great job.

Player 1's current score is 0.
Player 2's current score is 0.
Player 3's current score is 0.
Player 4's current score is 1.
Keep playing more rounds? (Y/N): y
---------------------------------------------
ROUND 2
---------------------------------------------
Enter the lower bound of your guesses: 1
Enter the upper bound of your guesses: 1000
---------------------------------------------
Player 1 , please enter your guess: 100
Too low, guess again!
Player 1 , please enter your guess: 500
Too low, guess again!
Player 1 , please enter your guess: 700
Too low, guess again!
Player 1 , please enter your guess: 900
Too high, guess again!
Player 1 , please enter your guess: 800
Too low, guess again!
Player 1 , please enter your guess: 850
Too high, guess again!
Player 1 , please enter your guess: 830
Bingo, you got it!
Turn over. Player 1 took 7 guesses to find the answer.
Player 2 , please enter your guess: 500 
Too high, guess again!
Player 2 , please enter your guess: 400
Too high, guess again!
Player 2 , please enter your guess: 300
Too low, guess again!
Player 2 , please enter your guess: 350
Too high, guess again!
Player 2 , please enter your guess: 325
Too high, guess again!
Player 2 , please enter your guess: 315
Too low, guess again!
Player 2 , please enter your guess: 320
Too low, guess again!
Player 2 , please enter your guess: 322
Too low, guess again!
Player 2 , please enter your guess: 323
Too low, guess again!
Player 2 , please enter your guess: 324
Bingo, you got it!
Turn over. Player 2 took 10 guesses to find the answer.
Player 3 , please enter your guess: 74
Too low, guess again!
Player 3 , please enter your guess: 543
Too low, guess again!
Player 3 , please enter your guess: 975
Too high, guess again!
Player 3 , please enter your guess: 826
Too high, guess again!
Player 3 , please enter your guess: 640
Too high, guess again!
Player 3 , please enter your guess: 618
Too high, guess again!
Player 3 , please enter your guess: 611
Too high, guess again!
Player 3 , please enter your guess: 587
Too low, guess again!
Player 3 , please enter your guess: 593
Too low, guess again!
Player 3 , please enter your guess: 610
Too high, guess again!
Player 3 , please enter your guess: 597
Too low, guess again!
Player 3 , please enter your guess: 609
Too high, guess again!
Player 3 , please enter your guess: 604
Too high, guess again!
Player 3 , please enter your guess: 599
Too low, guess again!
Player 3 , please enter your guess: 601
Too low, guess again!
Player 3 , please enter your guess: 603
Too high, guess again!
Player 3 , please enter your guess: 602
Bingo, you got it!
Turn over. Player 3 took 17 guesses to find the answer.
Player 4 , please enter your guess: 316
Too low, guess again!
Player 4 , please enter your guess: 627
Too low, guess again!
Player 4 , please enter your guess: 931
Too low, guess again!
Player 4 , please enter your guess: 939
Too low, guess again!
Player 4 , please enter your guess: 973
Too low, guess again!
Player 4 , please enter your guess: 994
Too high, guess again!
Player 4 , please enter your guess: 976
Too low, guess again!
Player 4 , please enter your guess: 991
Too high, guess again!
Player 4 , please enter your guess: 979
Too low, guess again!
Player 4 , please enter your guess: 988
Too high, guess again!
Player 4 , please enter your guess: 984
Too high, guess again!
Player 4 , please enter your guess: 983
Bingo, you got it!
Turn over. Player 4 took 12 guesses to find the answer.
The winner(s) of this round is... Player(s): 1, great job.

Player 1's current score is 1.
Player 2's current score is 0.
Player 3's current score is 0.
Player 4's current score is 1.
Keep playing more rounds? (Y/N): n
---------------------------------------------
GAME OVER
---------------------------------------------
The Grand winner(s) of this game is... Player(s): 1, 4, CONGRATS for your victory!!!
 */
