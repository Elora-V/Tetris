package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerSimple;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerVisual;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.GameFrame;
import fr.upsaclay.bibs.tetris.view.GameFrameImpl;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameManagerVisual extends AbstractGameManager{

    // cette classe utilise les méthodes defini dans la classe mère abstraite et y ajoute les éléments graphiques
    private GameFrame view;


    public GameManagerVisual() {
        initialize();
        super.loadNewGame();
        view = new GameFrameImpl("View tetris"); // on donne au controleur la fenetre (en plus des action faite par SIMPLE)

    }
    /**
     * Initialize the game Manager
     *
     * Should set the necessary fields with their default values
     *
     * Note: the player is not created at initilization
     *
     * In visual mode, this is where the game frame can be launched
     */
    @Override
    public void initialize(){
        super.initialize();
        view.initialize();
        view.attachManagerActionListener(this);

    }


    // Actions


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
        try {
            super.setGamePlayer(new GamePlayerVisual(TetrisGrid.getEmptyGrid(super.getNumberOfLines(), super.getNumberOfCols()), ScoreComputer.getScoreComputer(DEFAULT_MODE), super.getTetrominoProvider(), super.getPlayerType()));
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }


    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e); // action à réaliser côté 'logistique'
        // puis ajoute les actions 'visuelles' :
        ManagerComponent comp = (ManagerComponent) e.getSource();
        switch (comp.getManagerAction()) {
            case START:
                view.drawGamePlayView();
                break;
            case PAUSE:
                view.drawGamePauseView();
                break;
            case RESUME:
                view.drawGamePlayView();
                break;
            case RESTART:
                view.drawManagementView();
                break;
            case QUIT:
                break;
            default:
                break;

        }
    }




    /**
     * starts the player (i.e. the actual game)
     */
    public void startPlayer() {
        super.getPlayer().start();
    }

    /**
     * pause the player
     */
    public void pausePlayer(){
        super.getPlayer().pause();
    }


}
