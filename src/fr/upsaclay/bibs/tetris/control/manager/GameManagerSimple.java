package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerVisual;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerSimple;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.GameFrameImpl;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameManagerSimple extends AbstractGameManager {

    // cette classe utilise toutes les méthodes de bases défini dans la classe mère abstraite

    public GameManagerSimple() {
        initialize();
        super.loadNewGame();
    }


    /**
     * Creates a player with the correct player type
     *
     * The specific class of the player will depend of the GameType: Simple or Visual
     *
     * If there is no implementation for player type in this game type, throws an
     * UnsupportedOperationException
     */
    @Override
    public void createPlayer(){ // mettre erreur ??
    	if (super.getPlayerType()!=PlayerType.HUMAN) {
    		throw new UnsupportedOperationException("playertype not implemented");
    	}

        super.setGamePlayer( new GamePlayerSimple(TetrisGrid.getEmptyGrid(super.getNumberOfLines(), super.getNumberOfCols()), ScoreComputer.getScoreComputer(DEFAULT_MODE), super.getTetrominoProvider(), super.getPlayerType()));
        
    }
    public void loadPlayer(TetrisMode mode,TetrisGrid grid,int score, int level, int lines){ // mettre erreur ??
        try {
            super.setGamePlayer( new GamePlayerSimple(grid, ScoreComputer.getScoreComputer(mode, score, level, lines), super.getTetrominoProvider(), super.getPlayerType()));

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }




}
