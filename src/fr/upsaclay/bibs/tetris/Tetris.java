package fr.upsaclay.bibs.tetris;


import javax.swing.SwingUtilities;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.control.manager.GameType;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoShape;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The main class for launching the game
 *
 * @author Viviane Pons
 *
 */

public class Tetris {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> GameManager.getGameManager(GameType.VISUAL).initialize());

        // getGameManager -> interface GameManager : return gameManagerVisual:
        //                          -> loadGame : création du player avec les valeurs par default
        //                          -> création de la vue (dans le manager)
        //                          -> on donne la vue au player crée
        //  => création du modèle via le controleur (et modèle mis dans le controleur) + création vue (et vue mise dans le controleur)

        // initialize -> GameManagerVisual
        //                      -> initialisation du player : elle indique le listener du timer et met le delai de celui-ci avec sa valeur par default
        //                      -> on donne le modèle à la vue
        //                      -> initialisation de la vue (création et agencement des elements graphique comme les panneaux et boutons)
        //                      -> ajout du listener à la vue pour les boutons start/stop (gestion de lancement de jeu)
        //  => mise des listener et initialisation de la vue (elle recupère le modèle et dessine les panneaux initiaux)
    }

}
