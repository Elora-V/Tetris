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

    // Cette classe utilise toutes les méthodes de bases défini dans la classe mère abstraite.
    // On crée donc cette classe seulement pour pouvoir instancier un objet.

    //////////////////// Constructeur ///////////////

    public GameManagerSimple() {
        // on crée un player :
        super.loadNewGame();
        // On a pas besoin de definir les autres éléments de classe de AbstractGameManager car cela a été directement fait dans la classe abstraite
        // (au niveau des elements de classe).
    }


    /////////////////// Actions : création d'un player /////////////
    /**
     * Creates a player with the correct player type
     *
     * The specific class of the player will depend of the GameType: Simple or Visual
     *
     * If there is no implementation for player type in this game type, throws an
     * UnsupportedOperationException
     */
    @Override
    public void createPlayer(){
    	if (super.getPlayerType()!=PlayerType.HUMAN) {
    		throw new UnsupportedOperationException("playertype not implemented");
    	}
        // On donne à la classe mère une instance de player avec les informations de la classes mère (type de provider ...).
        // Tous est mis par default.
        super.setGamePlayer( new GamePlayerSimple(TetrisGrid.getEmptyGrid(super.getNumberOfLines(), super.getNumberOfCols()), ScoreComputer.getScoreComputer(DEFAULT_MODE), super.getTetrominoProvider(), super.getPlayerType()));
        
    }
    public void loadPlayer(TetrisMode mode,TetrisGrid grid,int score, int level, int lines){ // mettre erreur ??
        try {
            // On donne à la classe mère un player crée à partir d'un fichier de sauvegarde,
            // les informations du player sont en arguments.
            super.setGamePlayer( new GamePlayerSimple(grid, ScoreComputer.getScoreComputer(mode, score, level, lines), super.getTetrominoProvider(), super.getPlayerType()));

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }




}
