package fr.upsaclay.bibs.tetris.control.manager;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;

public class simpleGameManager extends AbstractGameManager{
	 private TetrominoProvider provider;
	 private TetrisMode mode;
	 private PlayerType playerType;
	 private int nbline;
	 private int nbcol;

	public simpleGameManager(TetrominoProvider provider2, TetrisMode mode2, PlayerType playerType2, int nbline2,
			int nbcol2) {
		this.provider= provider2;
		this.mode = mode2;
		this.playerType =playerType2;
		this.nbline = nbline2;
		this.nbcol= nbcol2;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		this.provider= DEFAULT_PROVIDER;
		this.mode =DEFAULT_MODE;
		this.playerType =DEFAULT_PLAYER_TYPE;
		this.nbline =DEFAULT_LINES;
		this.nbcol= DEFAULT_COLS;
	}

	@Override
	public void createPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GamePlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadNewGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFromFile(File file) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pausePlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(File file) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
