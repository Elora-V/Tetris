package fr.upsaclay.bibs.tetris.control.player;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputerImpl;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.GamePanelImpl;

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

    int delay;


    public GamePlayerSimple(TetrisGrid grid, ScoreComputer scoreComputer, TetrominoProvider provider,PlayerType type){
        this.initialize(grid, scoreComputer, provider);
        this.typeHuman=type;
        delay=GamePanelImpl.INITIAL_DELAY;
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
     * Return if the game is over
     *
     * A game is over if adding a new tetromino created a conflict or if the tetromino provider is empty
     *
     * @return true if the game is over
     */
    public boolean isOver(){
        // utilisation de cette fonction avant de redonner un tetromino
        if( !provider.hasNext()){
            return true;
        }
        Tetromino nextTetro=provider.showNext(0); // regarde le tetromino suivant
        grid.setTetromino(nextTetro); // on le met dans la grille (pour tester si il tient)
        grid.setAtStartingCoordinates(); // on le place
        boolean conflict= grid.hasConflicts(); // on regarde si il y a un conflict
        // on enlève le tetromino de la grille dans tout les cas, car le role de cette fonction n,'est pas le placement de tetromino
        grid.setTetromino(null);
        grid.setCoordinates(null);
        // si il y a eu conflict, le jeu est fini
        if (conflict){
            return true;
        }
        return false;
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
     * Return a grid view of the TetrisGrid
     * @return a TetrisGridView
     */
    public TetrisGridView getGridView(){
        return grid;
    }

    /**
     * Return the held tetromino (saved for later)
     * @return the held tetromino if it exists or null
     */
    public Tetromino getHeldTetromino(){
        return tetroHold;
    }

    /**
     * do the actions after a merge (when tetromino from grid is null):
     * recalculate score,
     * change of delay if change of level,
     * set new tetromino
     *
     */
    public void ActionWhenMerge(){
        alreadyHold=false; //on peut hold le prochain tetromino car on a fait un merge
        score.registerMergePack(grid.pack(), grid);
        // changement de delai si changement de niveau à faire (ici ou pas ) ??

        // on donne le tétromino suivant
        if(isOver()){ // on vérifie d'abord que le jeu n'est pas fini
            activeGame=false;
            System.out.println("passage en false");
        }
        if (activeGame) {
            grid.setTetromino(provider.next());
            grid.setAtStartingCoordinates();
        }

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
                score.registerBeforeAction(TetrisAction.DOWN,grid);
                boolean moveDown= grid.tryMove(TetrisCoordinates.DOWN);
                score.registerAfterAction(grid);

                // si on a pas pu descendre le tetromino :
                if(!moveDown) {
                    grid.merge(); // alors on merge car on touche le sol (ou le tetromino d'en dessous)
                    ActionWhenMerge();
                    return false; // et on dit qu'on a pas fait le mouvement
                }
                return true; // si on est descendu : on renvoie vrai

            case START_SOFT_DROP:
                score.registerBeforeAction(TetrisAction.START_SOFT_DROP, grid);
                softDrop=true;
                return true;

            case END_SOFT_DROP:   // A FAIRE DANS BOUCLE
                softDrop=false;
                score.registerBeforeAction(TetrisAction.END_SOFT_DROP, grid);
                score.registerAfterAction(grid);
                return true;

            case HARD_DROP:
                score.registerBeforeAction(TetrisAction.HARD_DROP, grid);
                grid.hardDrop();
                score.registerAfterAction(grid);
                grid.merge();
                ActionWhenMerge();
                return true;

            case ROTATE_RIGHT:
                return grid.tryRotateRight();
            case ROTATE_LEFT:
                return grid.tryRotateLeft();

            case HOLD:
                if(!alreadyHold) {
                    Tetromino tetroInHold = getHeldTetromino(); // tetromino retenu
                    Tetromino actualTetro = grid.getTetromino(); // tetromino qui tombe
                    tetroHold = actualTetro; // tetromino retenu change, il devient celui qui tombe
                    // on donne un tetromino à la grille
                    if (tetroInHold != null) {
                        grid.setTetromino(tetroInHold); // celui retenu
                        grid.setAtStartingCoordinates();
                    } else {
                        grid.setTetromino(provider.next()); // ou le suivant si aps de tetromino retenu
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


    protected int whichDelay() {
        delay=GamePanelImpl.INITIAL_DELAY-100;
        if(softDrop){
            delay=delay-100; // si on est en softdrop le delay diminue
        }
        if(delay <= GamePanelImpl.MIN_DELAY){ // on ne vaut pas descendre en dessous de cette valeur
            delay=GamePanelImpl.MIN_DELAY;
        }
        return delay;
    }
}
