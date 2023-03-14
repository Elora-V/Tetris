package fr.upsaclay.bibs.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;

/**
 * The list of different tetromino shapes
 * 
 * Each shape receives its initial tetromino as double array of tetris cells
 * 
 * It should then be able to return any rotation of the initial cells
 * 
 * Suggestion: compute all rotations at the creation of the shape and keeps the tetromino
 * stored inside the shape object
 * 
 * You need to implement the private constructor (as it is an enum) as well as needed
 * methods. You can add methods and fields and all you need.
 * 
 * We provide you with the random method
 * 
 * @author Viviane Pons
 *
 */
public enum TetrominoShape {
	ISHAPE(new TetrisCell[][] {
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY},
		{TetrisCell.I,		TetrisCell.I,		TetrisCell.I,		TetrisCell.I},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	},4),
	OSHAPE(new TetrisCell[][] {
		{TetrisCell.O, TetrisCell.O},
		{TetrisCell.O, TetrisCell.O}
	},1),
	TSHAPE(new TetrisCell[][] {
		{TetrisCell.EMPTY,	TetrisCell.T,		TetrisCell.EMPTY},
		{TetrisCell.T,		TetrisCell.T,		TetrisCell.T},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	},4),
	LSHAPE(new TetrisCell[][] {
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.L},
		{TetrisCell.L,		TetrisCell.L,		TetrisCell.L},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	},4),
	JSHAPE(new TetrisCell[][] {
		{TetrisCell.J,		TetrisCell.EMPTY,	TetrisCell.EMPTY},
		{TetrisCell.J,		TetrisCell.J,		TetrisCell.J},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	},4),
	ZSHAPE(new TetrisCell[][] {
		{TetrisCell.Z,		TetrisCell.Z,		TetrisCell.EMPTY},
		{TetrisCell.EMPTY,	TetrisCell.Z,		TetrisCell.Z},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	},4),
	SSHAPE(new TetrisCell[][] {
		{TetrisCell.EMPTY,	TetrisCell.S,		TetrisCell.S},
		{TetrisCell.S,		TetrisCell.S,		TetrisCell.EMPTY},
		{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	},4);
	
	
	private static final Random RANDOM = new Random();
	
	final TetrisCell[][] tabShape; // ajout
	final int numberRotation;
	
	public static Tetromino randomTetromino() {
		TetrominoShape randomShape = values()[RANDOM.nextInt(values().length)];
		return randomShape.getTetromino(RANDOM.nextInt(randomShape.getNumberOfRotations()));
	}
	
	
	private TetrominoShape(TetrisCell[][] initialShape, int numberRotation) {
		this.tabShape=initialShape;
		this.numberRotation=numberRotation; // ajout d'un élément à shape pour stocker nombre rotation possible
	}
	
	
	public TetrisCell getType() {
		for (int i=0;i<tabShape.length;i+=1){
			for (int j=0;j<tabShape[i].length;j+=1){
				if(tabShape[i][j]!=TetrisCell.EMPTY){ // si c'est pas une cellule vide on renvoie le type de la cellule
					return tabShape[i][j];
				}
			}
		}
		return TetrisCell.EMPTY;
	}
	
	public int getNumberOfRotations() {
		return numberRotation;
	}
	
	public int getBoxSize() {
		return tabShape.length; // nombre de ligne
	}
	
	public Tetromino getTetromino(int rotationNumber) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	

}
