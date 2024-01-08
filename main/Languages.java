package main;

/**
 * Enum that contains all the text for the program
 */
public enum Languages {

    GAME("Game", "Jeu"), LANGUAGE("Language", "Langue"),
    HELP("Help", "Aide"),
    RANDOM("Random", "Aléatoire"), MANUAL("Manual", "Manuel"),
    MODEL("Model:", "Modèle :"), MULTICOLOUR("Multicolour", "Multicolore"),
    COLOUR("Color", "Couleur"), START("Start", "Commencer"), STEPS("Steps:", "Pas :"),
    EXEC("Exec: ", "Exéc"), STOP("Stop", "Arrêt:"), NEW("New", "Nouvelle"), SOLUTION("Solution", "Solution"),
    EXIT("Exit", "Sortie"), COLOURS("Colours", "Couleurs"), ABOUT("About", "À propos"),
    ENGLISH("English", "Anglais"), FRENCH("French", "Français"), DESCRIPTION("Conway's Game of Life is a cellular automaton, invented by John Conway.\nIt is made up of a grid of cells which live or die based on a mathematical rule.", "Le jeu de la vie de Conway est un automate cellulaire inventé par John Conway.\nIl est constitué d'une grille de cellules qui vivent ou meurent selon une règle mathématique.");

    /**
     * English translation
     */
    private final String en;
    /**
     * French translation
     */
    private final String fr;

    /**
     * Constructor that sets the languages
     *
     * @param en - English translation
     * @param fr - French translation
     */
    private Languages(String en, String fr) {
        this.en = en;
        this.fr = fr;
    }

    /**
     * Getter for the text
     *
     * @param language - Determines the language
     * @return - The text in the correct language
     */
    public String get(String language) {
        // returns the correct language
        if (language.equals("en")) {
            return this.en;
        } else {
            return this.fr;
        }
    }

}
