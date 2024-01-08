package main;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    /**
     * Swing components are serializable and require serialVersionUID
     */
    private static final long serialVersionUID = 6248477390124803341L;
    /**
     * Splash screen duration
     */
    private final int duration;


    /**
     * Default constructor. Sets the show time of the splash screen.
     *
     * @param duration Duration in seconds
     */
    public SplashScreen(int duration) {
        this.duration = duration * 1000;
    }

    /**
     * Shows a splash screen in the center of the desktop for the amount of time
     * given in the constructor.
     */
    public void showSplashWindow() {
        // create content pane
        JPanel content = new JPanel(new BorderLayout());

        // Set the window's bounds, position the window in the center of the screen
        int width = 534 + 10;
        int height = 263 + 10;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        // set the location and the size of the window
        setBounds(x, y, width, height);

        JLabel label;
        ClassLoader cl = this.getClass().getClassLoader();
        ImageIcon img = new ImageIcon(cl.getResource("resources/game.png"));
        try {
            label = new JLabel(img);
        } catch (Exception e) {
            // e.printStackTrace();
            label = new JLabel("No image");
        }

        JLabel demo = new JLabel("Daniel Falcone - CST8221", JLabel.CENTER);
        content.add(label, BorderLayout.CENTER);
        content.add(demo, BorderLayout.SOUTH);
        // replace the window content pane with the content JPanel
        setContentPane(content);
        // make the splash window visible
        setVisible(true);

        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            /* log an error here? *//* e.printStackTrace(); */
        }
        // destroy the window and release all resources
        dispose();
    }
}
