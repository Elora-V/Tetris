package fr.upsaclay.bibs.tetris.control.player;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputerImpl;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;

import java.io.PrintStream;

public class GamePlayerSimple implements GamePlayer{

    // contient le modèle
    PlayerType typeHuman;
    ScoreComputer score;
    TetrisGrid grid;
    TetrominoProvider provider;
    Tetromino tetroHold=null;

    boolean activeGame=false;
    boolean beginning=true;
    boolean softDrop=false;
    boolean alreadyHold=false;


    public GamePlayerSimple(TetrisGrid grid, ScoreComputer scoreComputer, TetrominoProvider provider,PlayerType type){
        this.initialize(grid, scoreComputer, provider);
        this.typeHuman=type;
    }
    /**
     * Initialiaze the player
     * @param grid a TetrisGris
     * @param scoreComputer a ScoreComputer
     * @param provider a TetrominoProvider
     */
    public void initialize(TetrisGrid grid, ScoreComputer scoreComputer, TetrominoProvider provider){
        score=scoreComputer;
        this.grid=grid;
        this.provider=provider;
    }

    /**
     * Return the player type (HUMAN / AI)
     * @return a PlayerType
     */
    public PlayerType getType(){
        return typeHuman;
    }

    public void setAlreadyHold(boolean alreadyHold){
        this.alreadyHold=alreadyHold;
    }
    public boolean getAlreadyHold(){
        return alreadyHold;
    }
    /**
     * Sets a print stream for logging player actions
     * @param out
     */
    public void setLogPrintStream(PrintStream out){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the current level
     * @return the levle
     */
    public int getLevel(){
        return score.getLevel();
    }



    public boolean getsoftdrop(){
        return softDrop;
    }

    /**
     * Return the current score
     * @return the score
     */
    public int getScore(){
        return score.getScore();
    }


    /**
     * Return the line score (the number of completed lines)
     * @return the lines score
     */
    public int getLineScore(){
        return  score.getLines();
    }

    /**
     * Return if the player is active (not on pause, and not over)
     * @return true if the player is active
     */
    public boolean isActive(){
        return activeGame;
    }

    /**
     * Starts the player
     *
     * If it is the beginning of the game, it should put a new Tetromino on the grid
     */
    public void start(){
        activeGame=true;
        if(beginning) {
            grid.setTetromino(provider.next());
            grid.setAtStartingCoordinates();
            beginning=false;
        }

    }

    /**
     * Pause the player
     */
    public void pause(){
        activeGame=false;
    }

    /**
     * Return if the game is over
     *
     * A game is over if adding a new tetromino created a conflict or if the tetromino provider is empty
     *
     * @return true if the game is over
     */
    public boolean isOver(){
        if( !provider.hasNext()){
            return true;
        }
        if ( grid.hasConflicts()){ //???
            return true;
        }
        return false;
    }

    /**
     * Return a grid view of the TetrisGrid
     * @return a TetrisGridView
     */
    public TetrisGridView getGridView(){
        return grid;
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
     * verify conditions to see if the grid is ready to do a merge
     * @return  true if the merge has been done, false otherwise
     */
    public boolean TryMerge(){

       boolean moveDown= performAction(TetrisAction.DOWN);
    // si on a pu descendre le tetromino :
       if(moveDown){
           boolean moveUp= grid.tryMove(TetrisCoordinates.UP); // on annule le mouvement (on remonte)
           return false; // on est pas en bas, donc on merge pas
       }else{
           // si on a pas pu faire le mouvement :
           grid.merge();
           return true;
       }

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

    /**
     * Performs the received TetrisAction
     *
     * If the player is not active (pause or over), it throws an IllegalStateException
     *
     * @param action a TetrisAction
     * @return true if the action has been successfully performed
     */
    public boolean performAction(TetrisAction action){
        if(!activeGame){
            throw new IllegalStateException("not in play mode");
        }
        switch (action){
            case MOVE_LEFT:
                return grid.tryMove(TetrisCoordinates.LEFT);
            case MOVE_RIGHT:
                return grid.tryMove(TetrisCoordinates.RIGHT);
            case DOWN:
                return grid.tryMove(TetrisCoordinates.DOWN);
            case START_SOFT_DROP:
                softDrop=true;
                return true;
            case END_SOFT_DROP:   // A FAIRE DANS BOUCLE
                softDrop=false;
                return true;
            case HARD_DROP:
                grid.hardDrop();
                return true;
            case ROTATE_RIGHT:
                return grid.tryRotateRight();
            case ROTATE_LEFT:
                return grid.tryRotateLeft();
            case HOLD:                                         // REPASSER A FALSE BOUCLE
                if(!alreadyHold) {
                    Tetromino tetroInHold = getHeldTetromino(); // tetromino retenu
                    Tetromino actualTetro = grid.getTetromino(); // tetromino qui tombe
                    tetroHold = actualTetro; // tetromino retenu change, il devient celui qui tombe
                    if (tetroInHold != null) {
                        grid.setTetromino(tetroInHold);
                        grid.setAtStartingCoordinates();
                    } else {
                        grid.setTetromino(provider.next());
                        grid.setAtStartingCoordinates();
                    }
                    alreadyHold=true;
                    return true;
                }
                return false;
            default:
                return false;
        }
    }


    /**
     * Return the held tetromino (saved for later)
     * @return the held tetromino if it exists or null
     */
    public Tetromino getHeldTetromino(){
        return tetroHold;
    }

}
