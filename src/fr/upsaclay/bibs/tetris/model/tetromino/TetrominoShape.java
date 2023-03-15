package fr.upsaclay.bibs.tetris.model.tetromino;

import java.util.*;

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
	}),
	OSHAPE(new TetrisCell[][] {
			{TetrisCell.O, TetrisCell.O},
			{TetrisCell.O, TetrisCell.O}
	}),
	TSHAPE(new TetrisCell[][] {
			{TetrisCell.EMPTY,	TetrisCell.T,		TetrisCell.EMPTY},
			{TetrisCell.T,		TetrisCell.T,		TetrisCell.T},
			{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	}),
	LSHAPE(new TetrisCell[][] {
			{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.L},
			{TetrisCell.L,		TetrisCell.L,		TetrisCell.L},
			{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	}),
	JSHAPE(new TetrisCell[][] {
			{TetrisCell.J,		TetrisCell.EMPTY,	TetrisCell.EMPTY},
			{TetrisCell.J,		TetrisCell.J,		TetrisCell.J},
			{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	}),
	ZSHAPE(new TetrisCell[][] {
			{TetrisCell.Z,		TetrisCell.Z,		TetrisCell.EMPTY},
			{TetrisCell.EMPTY,	TetrisCell.Z,		TetrisCell.Z},
			{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	}),
	SSHAPE(new TetrisCell[][] {
			{TetrisCell.EMPTY,	TetrisCell.S,		TetrisCell.S},
			{TetrisCell.S,		TetrisCell.S,		TetrisCell.EMPTY},
			{TetrisCell.EMPTY,	TetrisCell.EMPTY,	TetrisCell.EMPTY}
	});

	
	
	private static final Random RANDOM = new Random();
	
	final List<TetrisCell[][]> listPosition;
	
	public static Tetromino randomTetromino() {
		TetrominoShape randomShape = values()[RANDOM.nextInt(values().length)];
		return randomShape.getTetromino(RANDOM.nextInt(randomShape.getNumberOfRotations()));
	}
	
	
	private TetrominoShape(TetrisCell[][] initialShape) {

		listPosition=new ArrayList<>();

		// ajout de la position initiale à la liste des positions :
		listPosition.add(initialShape);

		// on veut ajouter les autres positions :
		for (int r=0;r<3;r++) { // fait les 3 rotations
			TetrisCell[][] position = new TetrisCell[initialShape.length][initialShape.length]; //nouvelle position crée
			// on la remplit en appliquant la rotation à droite
			for (int i = 0; i < initialShape.length; i++) {
				for (int j = 0; j < initialShape.length; j++) {
					position[i][j] = initialShape[initialShape.length - 1 - j][i];
				}
			}

			// on l'ajoute à la liste si elle y est pas déja : (marchait pas avec contains pour array 2D ??)
			boolean listContain=false;
			for (TetrisCell[][] p: listPosition){
				if (Arrays.deepEquals(position,p)){
					listContain=true;
				}
			}
			if ( !listContain) {
				listPosition.add(position);
			}
			initialShape=position.clone(); //on indique que la version tournée est la nouvelle position initiale
		}

	}
	
	
	public TetrisCell getType() {
		TetrisCell[][] position=listPosition.get(0); //récupère une position
		for (int i=0;i<getBoxSize();i+=1){
			for (int j=0;j<getBoxSize();j+=1){
				if(position[i][j]!=TetrisCell.EMPTY){ // si c'est pas une cellule vide on renvoie le type de la cellule
					return position[i][j];
				}
			}
		}
		return TetrisCell.EMPTY;
	}

	public int getNumberOfRotations() {
		return listPosition.size();
	}
	
	public int getBoxSize() {
		TetrisCell[][] position=listPosition.get(0);
		System.out.println();
		return position.length; // nombre de ligne d'une position
	}
	
	public Tetromino getTetromino(int rotationNumber) {
		//throw new UnsupportedOperationException("Not implemented");
		return new TetrominoImpl( this ,rotationNumber);
		
	}


}

//note pour moi (à supprimer lors du rendu)

// throw new UnsupportedOperationException("Not implemented");

// calcul rotation droite:
//copyTabShape =...
//for (int i = 0; i < tabShape.length; i++) {
//	for (int j = 0; j < tabShape.length; j++) {
//		tabShape[i][j] = copyTabShape[tabShape.length - 1 - j][i];
//	}
//}
// calcul rotation gauche:
// avec copyTabShape[j][tabShape.length - 1 - i];

