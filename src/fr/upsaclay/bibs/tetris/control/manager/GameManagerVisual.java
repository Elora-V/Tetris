package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.GameFrame;
import fr.upsaclay.bibs.tetris.view.GameFrameImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameManagerVisual extends AbstractGameManager  {

    private GameFrame view;

    public GameManagerVisual() {
        view = new GameFrameImpl("View tetris"); // on donne au controlleur la fenetre

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
    public void initialize(){
        view.initialize();
        view.attachManagerActionListener(this);

    }

    /**
     * Sets the game mode
     * @param mode a TetrisMode
     */
    public void setGameMode(TetrisMode mode){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the game mode
     * @return a TetrisMode
     */
    public TetrisMode getGameMode(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Sets the tetromino provider
     * @param provider a TetrominoProvider
     */
    public void setTetrominoProvider(TetrominoProvider provider){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the current tetromino povider
     * @return the TetrominoProvider
     */
    public TetrominoProvider getTetrominoProvider(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Sets the player type
     * @param playerType a PlayerType
     */
    public void setPlayerType(PlayerType playerType){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the current player type
     * @return a PlayerType
     */
    public PlayerType getPlayerType(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the number of lines
     * @return the number of lines
     */
    public int getNumberOfLines(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the number of cols
     * @return the number of cols
     */
    public int getNumberOfCols(){
        throw new UnsupportedOperationException("Not implemented");}

    // Actions


    /**
     * Creates a player with the correct player type
     *
     * The specific class of the player will depend of the GameType: Simple or Visual
     *
     * If there is no implementation for player type in this game type, throws an
     * UnsupportedOperationException
     */
    public void createPlayer(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the player
     * @return a GamePlayer
     */
    public GamePlayer getPlayer(){
        throw new UnsupportedOperationException("Not implemented");
    }

    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("Not implemented");
    }


    /**
     * Load a new empty game
     *
     * This creates a new player if needed and initialize the player with the new game
     *
     * It does not start the player, so the player should be on "pause" i.e. not active
     */
    public void loadNewGame(){
        throw new UnsupportedOperationException("Not implemented");
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
     * starts the player (i.e. the actual game)
     */
    public void startPlayer(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * pause the player
     */
    public void pausePlayer(){
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

        throw new UnsupportedOperationException("Not implemented");}



}
