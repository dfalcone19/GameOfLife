package mvc;

import main.Languages;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

/**
 * Handles user input and decides how to respond
 */
public class GameController {
    /**
     * The GameModel associated with this controller, representing the state and logic of the game.
     */
    private final GameModel model;

    /**
     * The GameView associated with this controller, responsible for displaying the game's user interface.
     */
    private final GameView view;

    /**
     * An integer representing the currently selected application or game mode.
     */
    private int selectedApp;

    /**
     * Timer used for managing game update tasks at regular intervals.
     */
    private Timer timer;

    /**
     * A String to hold the number of steps the simulation should run for.
     */
    private String steps;

    /**
     * An array used as a counter, often for keeping track of the number of executions in the simulation.
     */
    final int[] counter = {0};

    /**
     * A flag indicating whether the game is in multicolour mode, where cells may display in multiple colors.
     */
    private boolean multicolour = false;

    /**
     * The ActionListener for grid buttons, defining the action to take when grid elements are interacted with.
     */
    private ActionListener gridAL;

    /**
     * The currently selected color for drawing on the grid, defaulting to black.
     */
    private Color selectedColor = Color.BLACK;

    /**
     * A flag indicating whether the game is in draw mode, allowing the user to interact with the grid.
     */
    private boolean drawMode = true;

    /**
     * Constructs a GameController with the specified model and view.
     *
     * @param model the game model
     * @param view  the game view
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        initMainWindow();
    }

    /**
     * Initializes the actionListeners for the pop-up menu where the application is selected
     */
//    public void initFirstWindow() {
//        // adds action listeners to the list
//        view.getListOfLabs().addActionListener(e -> {
//            if (view.getListOfLabs().getSelectedIndex() == 0) {
//                selectedApp = 0;
//            } else {
//                selectedApp = 1;
//            }
//        });
//        // add action listeners to the enter button
//        view.getEnter().addActionListener(e -> {
//            view.createGameWindow(selectedApp, "en");
//            initMainWindow();
//        });
//        // add action listeners to the cancel button
//        view.getCancel().addActionListener(e -> System.exit(0));
//    }

    /**
     * Adds listeners to the grid, enabling interaction with individual cells.
     */
    private void addGridListeners() {
        // action listener that just changes the colour
        gridAL = e -> {
            JButton source = (JButton) e.getSource();
            if (!source.getBackground().equals(Color.WHITE)) {
                source.setBackground(Color.WHITE);
            } else {
                source.setBackground(selectedColor);
            }
            if (multicolour) {
                multiColour();
            }
        };
        // applies action listener
        for (int i = 0; i < view.getRows(); i++) {
            for (int j = 0; j < view.getCols(); j++) {
                view.getGrid()[i][j].addActionListener(gridAL);
                model.setGrid(view.getGrid());
            }
        }
    }

    /**
     * Removes action listeners from the grid to disable interaction.
     */
    private void removeGridListeners() {
        // sync the grid
        view.setGrid(model.getGrid());
        // remove all listeners
        for (int i = 0; i < view.getRows(); i++) {
            for (int j = 0; j < view.getCols(); j++) {
                view.getGrid()[i][j].removeActionListener(gridAL);
            }
        }
        // update the model
        model.setGrid(view.getGrid());
    }

    /**
     * Additional initialization for the main window after an application is selected.
     */
    public void initMainWindow() {
        view.createGameWindow("en");
        // update steps
        this.steps = view.getSteps().getText();
        // update the model
        model.setRows(view.getRows());
        model.setCols(view.getCols());
        addGridListeners();
        // validate input
        model.setBinaryRule(view.getModel().getText());
        view.getModel().addActionListener(e -> {
            if (view.getModel().getText().length() != 18) {
                view.getModel().setText("000100000001100000");
            }

            for (int i = 0; i < 18; i++) {
                if (view.getModel().getText().charAt(i) != '0' && view.getModel().getText().charAt(i) != '1') {
                    view.getModel().setText("000100000001100000");
                }
            }

            model.setBinaryRule(view.getModel().getText());
        });
        // add listeners to the various components throughout the window:
        view.getSteps().addActionListener(e -> this.steps = view.getSteps().getText());

        view.getStart().addActionListener(e -> startSim(false));

        view.getStop().addActionListener(e -> {
            timer.stop();
            if (!drawMode) {
                removeGridListeners();
            }
            view.getExec().setText("Exec: " + (counter[0]));
        });

        view.getRand().addActionListener(e -> {
            drawMode = false;
            removeGridListeners();
            for (int i = 0; i < model.getRows(); i++) {
                for (int j = 0; j < model.getCols(); j++) {
                    Random rand = new Random();
                    int deadOrAlive = rand.nextInt(4); // 25%
                    if (deadOrAlive == 1) {
                        view.getGrid()[i][j].setBackground(selectedColor);
                    } else {
                        view.getGrid()[i][j].setBackground(Color.WHITE);
                    }

                }
                if (view.getMultiColour().isSelected()) {
                    multiColour();
                }
                model.setGrid(view.getGrid());
            }
        });

        view.getManual().addActionListener(e -> {
            drawMode = true;
            addGridListeners();
            clearGrid();
            model.setGrid(view.getGrid());
        });

        view.getMultiColour().addActionListener(e -> {
            multicolour = view.getMultiColour().isSelected();

            if (multicolour) {
                multiColour();
            } else {
                removeColour();
            }
        });
        ActionListener colorChooser = e -> {
            selectedColor = JColorChooser.showDialog(null, "Pick a color: ", Color.WHITE);
            model.setSelectedColor(selectedColor);
            view.setGrid(model.getGrid());
        };

        view.getColour().addActionListener(colorChooser);
        view.getColours().addActionListener(colorChooser);

        view.getNewGame().addActionListener(e -> {
            counter[0] = 0;
            view.getExec().setText("Exec: " + (counter[0]));
            drawMode = true;
            clearGrid();
        });

        view.getSolution().addActionListener(e -> startSim(true));

        view.getExit().addActionListener(e -> view.getGameFrame().dispose());

        view.getAbout().addActionListener(e -> JOptionPane.showMessageDialog(null, Languages.DESCRIPTION.get(view.getLanguage())));

        view.getEng().addActionListener(e -> {
            view.setLanguage("en");
            JButton[][] previous = model.getGrid();
            view.getGameFrame().dispose();
            view.createGameWindow( "en");
            initMainWindow();
            view.setGrid(previous);
            model.setGrid(previous);
            view.updateGrid();
            if (multicolour) {
                view.getMultiColour().setSelected(true);
            }
        });

        view.getFr().addActionListener(e -> {
            view.setLanguage("fr");
            JButton[][] previous = model.getGrid();
            view.getGameFrame().dispose();
            view.createGameWindow( "fr");
            initMainWindow();
            view.setGrid(previous);
            model.setGrid(previous);
            view.updateGrid();
            if (multicolour) {
                view.getMultiColour().setSelected(true);
            }
        });
    }

    /**
     * Clears the grid cells and makes them all white.
     */
    private void clearGrid() {
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                view.getGrid()[i][j].setBackground(Color.WHITE);
            }
        }
        model.setGrid(view.getGrid());
    }

    /**
     * Applies the rules to make the grid multicoloured.
     */
    private void multiColour() {
        model.setGrid(view.getGrid());
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                if (model.getGrid()[i][j].getBackground() != Color.WHITE) {
                    if (model.checkNeighbours(i, j) == 0) {
                        model.getGrid()[i][j].setBackground(Color.RED);
                    } else if (model.checkNeighbours(i, j) == 1) {
                        model.getGrid()[i][j].setBackground(Color.GREEN);
                    } else if (model.checkNeighbours(i, j) == 2) {
                        model.getGrid()[i][j].setBackground(Color.BLUE);
                    } else if (model.checkNeighbours(i, j) == 3) {
                        model.getGrid()[i][j].setBackground(Color.YELLOW);
                    } else if (model.checkNeighbours(i, j) == 4) {
                        model.getGrid()[i][j].setBackground(Color.MAGENTA);
                    } else if (model.checkNeighbours(i, j) == 5) {
                        model.getGrid()[i][j].setBackground(Color.CYAN);
                    } else if (model.checkNeighbours(i, j) == 6) {
                        model.getGrid()[i][j].setBackground(Color.decode("#5aa1d6"));
                    } else if (model.checkNeighbours(i, j) == 7) {
                        model.getGrid()[i][j].setBackground(Color.decode("#e69873"));
                    } else if (model.checkNeighbours(i, j) == 8) {
                        model.getGrid()[i][j].setBackground(Color.decode("#8e7cc3"));
                    }
                }
            }
        }
        view.setGrid(model.getGrid());
    }

    /**
     * Removes colour from the cells.
     */
    private void removeColour() {
        model.setGrid(view.getGrid());
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                if (model.getGrid()[i][j].getBackground() != Color.BLACK && model.getGrid()[i][j].getBackground() != Color.WHITE) {
                    model.getGrid()[i][j].setBackground(selectedColor);
                }
            }
        }
        view.setGrid(model.getGrid());
    }

    /**
     * Begins the simulations
     * @param skip - Determines whether executions will be skipped
     */
    private void startSim(boolean skip) {
        if (!drawMode) {
            removeGridListeners();
        }
        timer = new Timer((skip) ? 2 : 200, e -> {
            counter[0]++;
            if (!skip) {
                view.getExec().setText("Exec: " + (counter[0]));
                view.setGrid(model.applyRule());
                view.updateGrid();
                addGridListeners();
            } else {
                view.setGrid(model.applyRule());
                if (counter[0] % 50 == 0) {
                    view.getExec().setText("Exec: " + (counter[0]));
                    view.updateGrid();
                    addGridListeners();
                }
            }

            if (counter[0] >= Integer.parseInt(steps)) {
                ((Timer) e.getSource()).stop();
            }

            if (multicolour) {
                multiColour();
            }
        });
        timer.start();
    }

}
