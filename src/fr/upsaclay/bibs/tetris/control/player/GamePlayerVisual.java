package fr.upsaclay.bibs.tetris.control.player;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.control.manager.GameManagerVisual;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.*;


import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.ActionEvent;

public class GamePlayerVisual extends GamePlayerSimple implements KeyListener,ActionListener {

    // listener clavier et timer
    int delay; // in ms
    Audio gameoverSound = new Audio(); // Création d'une instance d'un objet Audio pour jouer le son du gameover
    GameManagerVisual MusicPlayer;
    GameManagerVisual qwerty;

    GameFrameImpl view; // on en a besoin pour donner le focus au clavier
    GamePanelImpl panel;


    public GamePlayerVisual(TetrisGrid grid, ScoreComputer scoreComputer, TetrominoProvider provider,PlayerType type){

       super(grid, scoreComputer, provider,type);
       delay=GamePanelImpl.INITIAL_DELAY;

    }

    public void initialize(){
        super.initialize(); //fais rien, mais à mettre au cas où le game simple doit etre initialisé
        panel.setLoopAction(this);
        panel.setLoopDelay(delay);
        System.out.println("initialize");
    }

    public TetrominoProvider getProvider(){
        return super.getProvider();
    }

    @Override
    public void setView(GameFrameImpl view){
        this.view=view;
        this.panel=view.getGamePanel();
    }
    /**
     * Starts the player
     *
     * If it is the beginning of the game, it should put a new Tetromino on the grid
     */
    @Override
    public void start(){
        super.start();
        view.startGameKeyListener(this);
    }

    /**
     * Pause the player
     */
    @Override
    public void pause(){
        super.pause();
        view.stopGameKeyListener(this);
    }

    @Override
    public boolean isOver(){
        boolean isOver= super.isOver();
        if( isOver) {
            GameManagerVisual.stopMusic(); // Arrêt de la musique de fond lors d'un gameover
            view.drawEndGameView();
            gameoverSound.GameOverPlay(); // Son de fin de partie lorsqu'il y a un gameover
        }
        return isOver;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub


    }
    /**
     * Fait les differentes actions en fonction des touches relachée du clavier
     *on a preferé pour des raisons ergonomiques l'utilisation des touches zqsd aux fléches
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
                super.performAction(TetrisAction.END_SOFT_DROP);
                break;
        }
    }

    /**
     * Fait les differentes actions en fonction des touches pressées du clavier
     *on a preferé pour des raisons ergonomiques l'utilisation des touches zqsd aux fléches
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (super.getGridView().getTetromino() != null) // on ne fait les actions que si on a un tétromino surlequel les appliquer
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (GameManagerVisual.isQwertyLayout) {
                        super.performAction(TetrisAction.MOVE_LEFT);
                    } else {
                        super.performAction(TetrisAction.ROTATE_LEFT);
                    }
                    break;
                case KeyEvent.VK_D:
                    super.performAction(TetrisAction.MOVE_RIGHT);
                    break;
                case KeyEvent.VK_S:
                    super.performAction(TetrisAction.START_SOFT_DROP);
                    panel.setLoopDelay(super.whichDelay()); // change le delai
                    break;
                case KeyEvent.VK_Z:
                    super.performAction(TetrisAction.HOLD);
                    break;
                case KeyEvent.VK_Q:
                    if (GameManagerVisual.isQwertyLayout) {
                        super.performAction(TetrisAction.ROTATE_LEFT);
                    } else {
                        super.performAction(TetrisAction.MOVE_LEFT);
                    }
                    break;
                case KeyEvent.VK_E:
                    super.performAction(TetrisAction.ROTATE_RIGHT);
                    break;
                case KeyEvent.VK_SPACE:
                    super.performAction(TetrisAction.HARD_DROP);
                    break;
            }
        panel.update();
    }



    @Override
    public void actionPerformed(ActionEvent e) {  // action timer
        if(activeGame) {
            super.performAction(TetrisAction.DOWN);
            panel.setLoopDelay(super.whichDelay());
            panel.update();
        }
    }
}
