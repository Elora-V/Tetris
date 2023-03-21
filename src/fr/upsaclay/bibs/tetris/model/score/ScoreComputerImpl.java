package fr.upsaclay.bibs.tetris.model.score;

import java.util.List;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;

public class ScoreComputerImpl implements ScoreComputer {
	TetrisMode scoreMode;
	int score;
	int level;
	int lines;
	
	int BeforeActionscore;
	int BeforeActionlevel;
	int BeforeActionlines;
	
	int AfterActionscore;
	int AfterActionlevel;
	int AfterActionlines;
	
	//int comboCount;
	
	public ScoreComputerImpl(TetrisMode mode) {
		this.scoreMode = mode;
		this.score = STARTING_SCORE;
		this.level = STARTING_LEVEL;
		this.lines = STARTING_LINES;
	}
	
	public ScoreComputerImpl(TetrisMode mode,int score, int level, int lines) {
		this.scoreMode = mode;
		this.score = score;
		this.level = level;
		this.lines = lines;
	}
	
	@Override 
	public int getLevel() {
		// renvoi le niveau actuel
		return level;
	}

	@Override
	public int getLines() {
		// renvoi le nombre de ligne occuper
		return lines;
	}

	@Override
	public int getScore() {
		// renvoi le score actuel
		return score;
	}

	@Override
	public int getComboCount() {
		/**
		 * The combo count (bonus qui multipli les gain des ligne complété)
		 * 
		 * par defaut -1.
		 * augmente de 1 si ligne completer
		 * sinon retombe à -1.
		 * 
		 * return ComboCount
		 */
		return 0;
	}

	@Override
	public void registerBeforeAction(TetrisAction action, TetrisGridView gridView) {
		/**
	

		 * Tlorsqu'une action est decidé et avant qu'elle soit execupter
		 * 
		 * Example: before a "hard drop", you need to save the tetromino position because
		 * it will be used to computer the score
		 * 
		 * @param action a Tetris action
		 * @param gridView a view of a tetris grid
		 */ BeforeActionscore = score;
			BeforeActionlevel = level;
			BeforeActionlines = lines;
		
	}

	@Override
	public void registerAfterAction(TetrisGridView gridView) {
		/**
		 * Update the score and internal state using the information
		 * 
		 * This is called once an action has been performed before potential merging / packing
		 * 
		 * It must be called after an action has been registered by registerBeforeAction,
		 * otherwise it throws an IllegalStateException
		 * 
		 * Example: after a hard drop, you can compute the number of lines the tetromino
		 * went down and update the score accordingly
		 * 
		 * @param gridView a view of a tetris grid
		 */
		AfterActionscore = score;
		AfterActionlevel = level;
		AfterActionlines = lines;
		
	}

	@Override
	public void registerMergePack(List<Integer> packResult, TetrisGridView gridView) {
		/**
		 * Update the score and internal state using the information
		 * 
		 * This is called after a merge / pack has been performed
		 * 
		 * @param packResult the result of the pack
		 * @param gridView a view of a tetris grid 
		 */
		
	}

}
