package main;

import mvc.GameController;
import mvc.GameModel;
import mvc.GameView;

/**
 * Main class that serves as the entry point of the Java application.
 */
public class Game {

    /**
     * The main method that will be executed when the program starts
     */
    public Game() {
        startGame();
    }

    public void startGame() {
        // Creating a new instance of SplashScreen with a duration of 3 seconds
        SplashScreen splash = new SplashScreen(3);
        // Displaying the splash screen
        splash.showSplashWindow();
        // Creating a new instance of GameView, which will handle the display of the game
        GameView gameView = new GameView();
        // Creating a new instance of GameModel, which will manage the game's data and state
        GameModel gameModel = new GameModel();
        // The GameController will handle the interaction between the model and view
        GameController gameController = new GameController(gameModel, gameView);
    }


}
