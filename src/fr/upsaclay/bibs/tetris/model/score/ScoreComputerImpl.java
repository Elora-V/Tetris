package fr.upsaclay.bibs.tetris.model.score;

import java.util.List;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;

public class ScoreComputerImpl implements ScoreComputer {
	TetrisMode scoreMode;
	int score;
	int level;
	int lines;
	int comboCount;
	
	boolean SoftDrop;
	boolean HardDrop;
	int scoreBefore;
	int linesBefore;
	boolean registerBeforeAction;
	

	public ScoreComputerImpl(TetrisMode mode,int score, int level, int lines) {
		this.scoreMode = mode;
		this.score = score;
		this.level = level;
		this.lines = lines;
		this.comboCount = STARTING_COMBO;
		
		this.SoftDrop = false;
		this.HardDrop = false;
		this.scoreBefore = 0;
		this.linesBefore = 0;
		this.registerBeforeAction =false;
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
		return comboCount;
	}
	@Override
	public int getScoreBefore() {
		// renvoi le score actuel
		return scoreBefore;
	}
	@Override
	public boolean getSoftDrop() {
		// renvoi le score actuel
		return SoftDrop;
	}
	@Override
	public void registerBeforeAction(TetrisAction action, TetrisGridView gridView) {
		/**
		 * met à jour le score et etat interne en utilisant les informations
		 * il est appelé lorsqu'une action est decidé et avant qu'elle soit execupté
		 * 
		 * Example: before a "hard drop", you need to save the tetromino position because
		 * it will be used to computer the score
		 * 
		 * @param action a Tetris action
		 * @param gridView a view of a tetris grid	
	 */
		registerBeforeAction =true;
			// sert pour les drop
		linesBefore = gridView.getCoordinates().getLine();
		switch (action){
		case START_SOFT_DROP:
			SoftDrop = true;
			scoreBefore = 0;
			break;
		case END_SOFT_DROP:
			SoftDrop = false;
			scoreBefore =0;
			break;
		case HARD_DROP:
			HardDrop = true;
			scoreBefore = 2;
			break;
		case DOWN:
			if (SoftDrop) {
			scoreBefore = 1;
			break;
			}
			scoreBefore = 0;
			break;
		default:
			break;
		}		
		
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
	
		// sert pour les drop 
		
		if(!registerBeforeAction) {
			throw new IllegalStateException(); 
		}
		int nbLigne= gridView.getCoordinates().getLine() - linesBefore ;
		score =score + scoreBefore * nbLigne ;
		scoreBefore = 0;
		HardDrop = false;
		registerBeforeAction =false;	
		
		
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
		// c'est ici que l'on calcule le score
		// 		1 (single) 	100
		// 		2 (double) 	300
		// 		3 (triple) 	500
		// 		4 (tetris) 	800 
		//fois le niveau
		
		boolean combo= false;
		
		
		if (packResult.size()==1) {
			score = score + 100 * level;
			combo = true;
			
		}
		else if (packResult.size()== 2) {
			score = score + 300 * level;
			combo = true;
			
		}
		else if (packResult.size()== 3) {
			score = score + 500 * level;
			combo = true;
			
		}
		else  if (packResult.size()== 4) {
			score = score + 800 * level;
			combo = true;
		}
		
		incComboCount(combo);	
		
		if (combo==true) {
			score = score + 50 * level * comboCount;
		}
		
		lines = lines + packResult.size();
		if (lines > 5 && lines % 5 == 0 ) {
			level ++;
		}
		
	}


	@Override
	public void incComboCount(boolean combo) {
		if (combo == true) {
			comboCount ++;
		}
		else {
			comboCount = -1;
		}
		
	}

}
