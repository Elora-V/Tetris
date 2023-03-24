package fr.upsaclay.bibs.tetris.control.player;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.GamePanel;
import fr.upsaclay.bibs.tetris.view.GamePanelImpl;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.ActionEvent;

public class GamePlayerVisual extends GamePlayerSimple implements KeyListener {

    int delay; // in ms
    GamePanel panel;

    public GamePlayerVisual(TetrisGrid grid, ScoreComputer scoreComputer, TetrominoProvider provider,PlayerType type){

       super(grid, scoreComputer, provider,type);
       panel= new GamePanelImpl();

    }
//communication game panel, calcul delay, score (recolter données pr score),debut-pause-fin,listener clavier,placer tetro,provider
    //cycle tetro,vitesse tetro

    /**
     *
     * utilise start de la classe mère pour
     * donner un premier tetromino
     */
    public void initialize(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the calculated delay, it depend on the level
     * and the state of softdrop (delay quicker if softdrop true)
     *
     */
    public void WhichDelay(){
        throw new UnsupportedOperationException("Not implemented");
        //utilisera probablement la variable delay, l'augmentera et change le delay avec setdelay (il faut aussi changer sa valeur dans le champs)

    }


    /**
     * do the actions after a merge (when tetromino from grid is null):
     * recalculate score,
     * change of delay if change of level,
     * set new tetromino
     *
     */
    public void ActionWhenMerge(){
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    /**
     * do the action from the keyboard
     *
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (super.getGridView().getTetromino() != null) // on ne fait les actions que si on a un tétromino surlequel les appliquer
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    super.performAction(TetrisAction.MOVE_LEFT);
                    break;

                //....faire les autres

            }

    }



}
