package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputerImpl;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractGameManager implements GameManager, ActionListener {

    private TetrominoProvider provider;
    private TetrisMode mode;
    private PlayerType playerType;
    private int nbline;
    private int nbcol;
  

    public abstract void actionPerformed(ActionEvent e);

    ///////// Get & Set


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

    /**
     * Return the number of cols
     * @return the number of cols
     */
    public int getNumberOfCols(){
        return nbcol;}

    public static AbstractGameManager getGameManager(GameType type,TetrominoProvider provider, TetrisMode mode, PlayerType playerType, int nbline, int nbcol) {
    	switch(type) {
		case SIMPLE:
			return new simpleGameManager(provider, mode, playerType,nbline,nbcol);
		default:
			throw new UnsupportedOperationException("Not implemented");
		
		}
    	
    }
    public static AbstractGameManager getGameManager(GameType type) {
    	return getGameManager(type,DEFAULT_PROVIDER, DEFAULT_MODE,DEFAULT_PLAYER_TYPE, DEFAULT_LINES,DEFAULT_COLS);
    }
    
}
