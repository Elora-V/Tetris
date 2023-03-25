package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class AbstractGameManager implements GameManager, ActionListener {

    // le modèle :
    private TetrominoProvider provider;
    private TetrisMode mode;
    private PlayerType playerType;
    private int nbline;
    private int nbcol;
    private GamePlayer gamePlayer;

    // la vue est dans la classe fille visual


    /////////// Actions ///////////////////

    public void initialize(){
        setTetrominoProvider(DEFAULT_PROVIDER);
        setGameMode(DEFAULT_MODE);
        setPlayerType(DEFAULT_PLAYER_TYPE );
        setNumberOfLines(DEFAULT_LINES);
        setNumberOfCols(DEFAULT_COLS);
    }

    /**
     * Load a new empty game
     *
     * This creates a new player if needed and initialize the player with the new game
     *
     * It does not start the player, so the player should be on "pause" i.e. not active
     */
    public void loadNewGame(){
        createPlayer();
    }
    /**
     * starts the player (i.e. the actual game)
     */
    public void startPlayer() {
        getPlayer().start();
    }

    /**
     * pause the player
     */
    public void pausePlayer(){
        getPlayer().pause();
    }

    public void actionPerformed(ActionEvent e) { //action 'logistique', le coté visuelle est ajoutée dans managerVisual

        ManagerComponent comp = (ManagerComponent) e.getSource();
        switch (comp.getManagerAction()) {
            case START:
                gamePlayer.start();
                break;
            case PAUSE:
                gamePlayer.pause();
                break;
            case RESUME:
                gamePlayer.start();
                break;
            case RESTART:

                break;
            case QUIT:

                break;
            default:
                break;

        }
    }

    /**
     * Loads a game read from a file
     *
     * This creates a new player if needed and initialize the player with the new game
     *
     * It does not start the player, so the player should be on "pause" i.e. not active
     *
     * A game file contains information about the game state (grid, score, level, etc.)
     *
     * It does not contain: the tetromino provider, the held tetromino saved in game
     *
     * See project description for file format
     *
     * @param file a file
     * @throws FileNotFoundException if the file cannot be read
     * @throws IOException if there is an error while scanning the file following the file format
     */
    public void loadFromFile(File file) throws FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not implemented");
    }



    /**
     * Save the game
     *
     * A game file contains information about the game state (grid, score, level, etc.)
     *
     * It does not contain: the tetromino provider, the held tetromino saved in game
     *
     * See project description for file format
     *
     * @param file a file
     * @throws FileNotFoundException if one cannot write in the file
     */
    public void save(File file) throws FileNotFoundException {

        throw new UnsupportedOperationException("Not implemented");
    }


    ///////// Get & Set /////////


    /**
     * Sets the game mode
     * @param mode a TetrisMode
     */
    public void setGameMode(TetrisMode mode){
        this.mode=mode;
    }

    /**
     * Return the game mode
     * @return a TetrisMode
     */
    public TetrisMode getGameMode(){
        return mode;
    }

    /**
     * Return the player
     * @return a GamePlayer
     */
    public GamePlayer getPlayer(){
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer){ //ajout de cette fonction
        this.gamePlayer=gamePlayer;
    }
    /**
     * Sets the tetromino provider
     * @param provider a TetrominoProvider
     */
    public void setTetrominoProvider(TetrominoProvider provider){
        this.provider=provider;
    }

    /**
     * Return the current tetromino povider
     * @return the TetrominoProvider
     */
    public TetrominoProvider getTetrominoProvider(){
        return provider;
    }

    /**
     * Sets the player type
     * @param playerType a PlayerType
     */
    public void setPlayerType(PlayerType playerType){
        this.playerType=playerType;
    }

    /**
     * Return the current player type
     * @return a PlayerType
     */
    public PlayerType getPlayerType(){
        return playerType;
    }


    /**
     * Return the number of lines
     * @return the number of lines
     */
    public int getNumberOfLines(){
        return nbline;
    }
    public void setNumberOfLines(int nbline){
        this.nbline=nbline;
    }

    /**
     * Return the number of cols
     * @return the number of cols
     */
    public int getNumberOfCols(){
        return nbcol;}
    public void setNumberOfCols(int nbcol){
        this.nbcol=nbcol;
    }


}
