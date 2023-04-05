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
    }

}
