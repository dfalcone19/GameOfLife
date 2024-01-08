package mvc;

import main.Languages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * Presents data to the user
 */
public class GameView {

    /**
     * Array of buttons representing the grid on the UI.
     */
    private JButton[][] grid;

    /**
     * Button to confirm an action or input.
     */
    private JButton enter;

    /**
     * Button to cancel an action or clear input.
     */
    private JButton cancel;

    /**
     * Dropdown list for selecting from a set of predefined labs.
     */
    private JComboBox<String> listOfLabs;

    /**
     * Text field for inputting the model number or name.
     */
    private JTextField model;

    /**
     * The number of rows in the grid.
     */
    private final int rows = 50;

    /**
     * The number of columns in the grid.
     */
    private final int cols = 50;

    /**
     * Button to initiate the start of a process or operation.
     */
    private JButton start;

    /**
     * Button to stop an ongoing process or operation.
     */
    private JButton stop;

    /**
     * Text field for entering the number of steps or iterations.
     */
    private JTextField steps;

    /**
     * Label to display execution status or information.
     */
    private JLabel exec;

    /**
     * Button to initialize the grid with random values or positions.
     */
    private JButton rand;

    /**
     * Button to switch to manual configuration or input.
     */
    private JButton manual;

    /**
     * Checkbox to enable or disable multi-color mode.
     */
    private JCheckBox multiColour;

    /**
     * Button to open a color picker or apply a color.
     */
    private JButton colour;

    /**
     * Menu item to start a new game.
     */
    private JMenuItem newGame;

    /**
     * Menu item to show the solution or solve the current game.
     */
    private JMenuItem solution;

    /**
     * Menu item to exit the application or close the window.
     */
    private JMenuItem exit;

    /**
     * Main frame of the game application.
     */
    private JFrame gameFrame;

    /**
     * Menu item to select different color options or themes.
     */
    private JMenuItem colours;

    /**
     * Menu item for the "About" information or help.
     */
    private JMenuItem about;

    /**
     * Menu item to switch language to English.
     */
    private JMenuItem eng;

    /**
     * Menu item to switch language to French.
     */
    private JMenuItem fr;

    /**
     * Current language selection for the UI.
     */
    private String language;

    /**
     * Panel containing game-related controls and information.
     */
    private JPanel gamePanel;

    /**
     * Panel that holds the grid of buttons.
     */
    private JPanel gridPanel;

    /**
     * Constructs the GameView
     */
    public GameView() {
        // set look and feel to work on macOS
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Issue with the look and feel");
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    /**
     * Creates the main window of the game
     * @param language - language to use
     */
    public void createGameWindow(String language) {
        // Access image
        ClassLoader cl = this.getClass().getClassLoader();
        // Frame creation:
        gameFrame = createFrame(575, 800, "Game of Life", 2);

        // Main panel creation:
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        // Grid panel creation:
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        grid = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                grid[i][j] = button;
                gridPanel.add(grid[i][j]);
            }
        }

        // Footer panel creation:
        JPanel footer = new JPanel();
        footer.setPreferredSize(new Dimension(gameFrame.getWidth(), 75));
        footer.setLayout(new FlowLayout());

        // Menu creation:
        JMenuBar mb = new JMenuBar();
        gameFrame.setJMenuBar(mb);
        JMenu gameMenu = new JMenu(Languages.GAME.get(language));
        newGame = new JMenuItem(Languages.NEW.get(language));
        solution = new JMenuItem(Languages.SOLUTION.get(language));
        exit = new JMenuItem(Languages.EXIT.get(language));
        gameMenu.add(newGame);
        gameMenu.add(solution);
        gameMenu.add(exit);
        JMenu lang = new JMenu(Languages.LANGUAGE.get(language));
        eng = new JMenuItem(Languages.ENGLISH.get(language));
        fr = new JMenuItem(Languages.FRENCH.get(language));
        lang.add(eng);
        lang.add(fr);
        JMenu helpMenu = new JMenu(Languages.HELP.get(language));
        colours = new JMenuItem(Languages.COLOURS.get(language));
        about = new JMenuItem(Languages.ABOUT.get(language));
        helpMenu.add(colours);
        helpMenu.add(about);
        mb.add(gameMenu);
        mb.add(lang);
        mb.add(helpMenu);

        // Component creation:
        ImageIcon img = new ImageIcon(cl.getResource("resources/gl.png"));
        JLabel imgLabel = new JLabel(img);
        rand = new JButton(Languages.RANDOM.get(language));
        manual = new JButton(Languages.MANUAL.get(language));
        JLabel modelLbl = new JLabel(Languages.MODEL.get(language));
        model = new JTextField(14);
        model.setText("000100000001100000");
        multiColour = new JCheckBox(Languages.MULTICOLOUR.get(language));
        colour = new JButton(Languages.COLOUR.get(language));
        start = new JButton(Languages.START.get(language));
        JLabel stepsLbl = new JLabel(Languages.STEPS.get(language));
        steps = new JTextField(5);
        steps.setText("100");
        exec = new JLabel(Languages.EXEC.get(language) + " 0");
        stop = new JButton(Languages.STOP.get(language));

        // Add components to the footer:
        footer.add(rand);
        footer.add(manual);
        footer.add(modelLbl);
        footer.add(model);
        footer.add(multiColour);
        footer.add(colour);
        footer.add(start);
        footer.add(stepsLbl);
        footer.add(steps);
        footer.add(exec);
        footer.add(stop);

        // Add components to main panel:
        gamePanel.add(imgLabel, BorderLayout.NORTH);
        gamePanel.add(gridPanel, BorderLayout.CENTER);
        gamePanel.add(footer, BorderLayout.SOUTH);

        // Add panel to frame:
        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
    }

    /**
     * Updates the grid of cells
     */
    public void updateGrid() {
        gridPanel.removeAll();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridPanel.add(grid[i][j]);
            }
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * Creates a frame
     *
     * @param w              - Width of the frame
     * @param h              - Height of the frame
     * @param title          - Title of the frame
     * @param closeOperation - Close operation of the frame
     * @return - The newly created frame
     */
    private JFrame createFrame(int w, int h, String title, int closeOperation) {
        JFrame frame = new JFrame(title);
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(closeOperation);
        frame.setResizable(false);

        return frame;
    }

    /**
     * Retrieves the "Enter" button.
     *
     * @return the enter button
     */
    public JButton getEnter() {
        return enter;
    }

    /**
     * Retrieves the "Cancel" button.
     *
     * @return the cancel button
     */
    public JButton getCancel() {
        return cancel;
    }

    /**
     * Retrieves the combo box containing the list of labs.
     *
     * @return the combo box with list of labs
     */
    public JComboBox<String> getListOfLabs() {
        return listOfLabs;
    }

    /**
     * Retrieves the grid of buttons.
     *
     * @return a 2D array of buttons
     */
    public JButton[][] getGrid() {
        return grid;
    }

    /**
     * Retrieves the number of rows in the grid.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retrieves the number of columns in the grid.
     *
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Retrieves the model input field.
     *
     * @return the model text field
     */
    public JTextField getModel() {
        return model;
    }

    /**
     * Retrieves the "Start" button.
     *
     * @return the start button
     */
    public JButton getStart() {
        return start;
    }

    /**
     * Retrieves the "Stop" button.
     *
     * @return the stop button
     */
    public JButton getStop() {
        return stop;
    }

    /**
     * Sets the grid of buttons.
     *
     * @param grid the 2D array of buttons to set
     */
    public void setGrid(JButton[][] grid) {
        this.grid = grid;
    }

    /**
     * Retrieves the steps input field.
     *
     * @return the steps text field
     */
    public JTextField getSteps() {
        return steps;
    }

    /**
     * Retrieves the label for execution status.
     *
     * @return the execution label
     */
    public JLabel getExec() {
        return exec;
    }

    /**
     * Retrieves the "Randomize" button.
     *
     * @return the randomize button
     */
    public JButton getRand() {
        return rand;
    }

    /**
     * Retrieves the "Manual" button.
     *
     * @return the manual button
     */
    public JButton getManual() {
        return manual;
    }

    /**
     * Retrieves the multicolor mode checkbox.
     *
     * @return the multicolor checkbox
     */
    public JCheckBox getMultiColour() {
        return multiColour;
    }

    /**
     * Retrieves the "Color" button.
     *
     * @return the color button
     */
    public JButton getColour() {
        return colour;
    }

    /**
     * Retrieves the menu item for starting a new game.
     *
     * @return the new game menu item
     */
    public JMenuItem getNewGame() {
        return newGame;
    }

    /**
     * Retrieves the menu item for showing the solution.
     *
     * @return the solution menu item
     */
    public JMenuItem getSolution() {
        return solution;
    }

    /**
     * Retrieves the menu item for exiting the application.
     *
     * @return the exit menu item
     */
    public JMenuItem getExit() {
        return exit;
    }

    /**
     * Retrieves the main game frame.
     *
     * @return the game frame
     */
    public JFrame getGameFrame() {
        return gameFrame;
    }

    /**
     * Retrieves the menu item for color options.
     *
     * @return the colors menu item
     */
    public JMenuItem getColours() {
        return colours;
    }

    /**
     * Retrieves the "About" menu item.
     *
     * @return the about menu item
     */
    public JMenuItem getAbout() {
        return about;
    }

    /**
     * Retrieves the menu item for switching to English language.
     *
     * @return the English language menu item
     */
    public JMenuItem getEng() {
        return eng;
    }

    /**
     * Retrieves the menu item for switching to French language.
     *
     * @return the French language menu item
     */
    public JMenuItem getFr() {
        return fr;
    }

    /**
     * Retrieves the currently selected language.
     *
     * @return the current language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the current language for the UI.
     *
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
