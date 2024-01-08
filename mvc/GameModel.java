package mvc;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

/**
 * Represents the data and logic
 */
public class GameModel {

    /**
     * Holds the binary rule as a string which dictates the cell transformations in the game logic.
     */
    private String binaryRule;

    /**
     * The number of rows in the game grid.
     */
    private int rows;

    /**
     * The number of columns in the game grid.
     */
    private int cols;

    /**
     * A 2D array of JButtonss that represents the game grid.
     */
    private JButton[][] grid;

    /**
     * A list of integers that define the number of neighbors that cause a dead cell to become alive.
     */
    private final ArrayList<Integer> deadToAliveRule = new ArrayList<>(1);

    /**
     * A list of integers that define the number of neighbors that cause an alive cell to become dead.
     */
    private final ArrayList<Integer> aliveToDeadRule = new ArrayList<>(1);

    /**
     * The currently selected color for alive cells.
     */
    private Color selectedColor = Color.BLACK;

    /**
     * Constructor for creating a new GameModel with initial settings.
     */
    public GameModel() {

        binaryRule = "";
    }

    /**
     * Sets the binary rule as a string representation for the game logic.
     *
     * @param binaryRule A string representing the binary rules for cell transformation.
     */
    public void setBinaryRule(String binaryRule) {
        this.binaryRule = binaryRule;

    }

    /**
     * Gets the number of columns in the game grid.
     *
     * @return The number of columns.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns in the game grid.
     *
     * @param cols The number of columns to set.
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Sets the number of rows in the game grid.
     *
     * @param rows The number of rows to set.
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Gets the number of rows in the game grid.
     *
     * @return The number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the grid of buttons representing the game state.
     *
     * @param grid A 2D array of {@code JButton} representing the game grid.
     */
    public void setGrid(JButton[][] grid) {
        this.grid = grid;
    }

    /**
     * Processes the binary rule and populates the rules for dead to alive and alive to dead transformations.
     */
    private void processRule() {
        for (int i = 0; i < 9; i++) {
            if (binaryRule.charAt(i) == '1') {
                deadToAliveRule.add(i);
            }
        }

        for (int i = 9; i < 18; i++) {
            if (binaryRule.charAt(i) == '1') {
                aliveToDeadRule.add(i - 9);
            }
        }
    }

    /**
     * Makes a deep copy of the provided 2D JButton array.
     *
     * @param original The original grid to be copied.
     * @return A new copy of the provided grid.
     */
    public JButton[][] makeCopy(JButton[][] original) {
        int rows = original.length;
        int cols = original[0].length;
        JButton[][] copy = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                copy[i][j] = button;
                copy[i][j].setBackground(original[i][j].getBackground());
            }
        }
        return copy;
    }

    /**
     * Applies the game rules to the current grid and returns the next state of the grid.
     *
     * @return The grid after applying the transformation rules.
     */
    public JButton[][] applyRule() {
        processRule();
        JButton[][] next = makeCopy(grid);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveNeighbours = checkNeighbours(i, j);
                if (!grid[i][j].getBackground().equals(Color.WHITE)) {
                    if (!aliveToDeadRule.contains(aliveNeighbours)) {
                        next[i][j].setBackground(Color.WHITE);
                    }
                } else {
                    if (deadToAliveRule.contains(aliveNeighbours)) {
                        next[i][j].setBackground(selectedColor);
                    }
                }
            }
        }
        setGrid(next);
        return next;
    }

    /**
     * Applies the selected color to all non-white buttons in the grid.
     */
    public void applyColour() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!grid[i][j].getBackground().equals(Color.WHITE)) {
                    grid[i][j].setBackground(selectedColor);
                }
            }
        }
    }

    /**
     * Counts the number of non-white neighbors around a given cell in the grid.
     *
     * @param row The row of the cell to check.
     * @param col The column of the cell to check.
     * @return The count of non-white neighboring cells.
     */
    public int checkNeighbours(int row, int col) {
        int aliveNeighbours = 0;

        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, rows - 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, cols - 1); j++) {
                if ((i == row) && (j == col)) {
                    // continue
                } else {
                    if (!grid[i][j].getBackground().equals(Color.WHITE)) {
                        aliveNeighbours++;
                    }
                }
            }
        }
        return aliveNeighbours;
    }

    /**
     * Gets the current game grid.
     *
     * @return A 2D array of {@code JButton} representing the current game grid.
     */
    public JButton[][] getGrid() {
        return grid;
    }

    /**
     * Sets the selected color for alive cells and applies it to the current grid.
     *
     * @param selectedColor The {@code Color} to be applied to alive cells.
     */
    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        applyColour();
    }
}
