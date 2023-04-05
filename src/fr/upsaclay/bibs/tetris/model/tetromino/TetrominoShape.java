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

	// Cette enum représente toutes les formes possibles de tétromino.

	//////////////////////  Formes de base  ////////////////////////////////

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


	//////////////////////  Element de la classe  ////////////////////////////////

	private static final Random RANDOM = new Random(); // un element pour avoir un chiffre aléatoire (non utilisé)
	
	final List<TetrisCell[][]> listRotation;  // une liste contenant toutes les rotations possibles pour un tetromino donné
	


	//////////////////////  Constructeur  ////////////////////////////////

	private TetrominoShape(TetrisCell[][] initialShape) {

		// creéation de la liste contenant les rotations :
		listRotation=new ArrayList<>();

		// ajout de la position initiale à la liste des rotations :
		listRotation.add(initialShape);

		// on veut ajouter les autres positions :
		// boucle qui fait les 3 rotations:
		for (int r=0;r<3;r++) {
			// création d'un nouveau tableau contenant des cellules de tetromino (nommé position, car c'est une manière de positionné le tetromino)
			TetrisCell[][] position = new TetrisCell[initialShape.length][initialShape.length];
			// on la remplit en appliquant une rotation à droite :
			for (int i = 0; i < initialShape.length; i++) {
				for (int j = 0; j < initialShape.length; j++) {
					position[i][j] = initialShape[initialShape.length - 1 - j][i];
				}
			}

			// on l'ajoute à la liste si elle n'y est pas déja : (cela ne marchait pas avec 'contains' pour un array 2D )
			boolean listContain=false;
			for (TetrisCell[][] p: listRotation){
				if (Arrays.deepEquals(position,p)){
					listContain=true;
				}
			}
			if ( !listContain) {
				listRotation.add(position);
			}
			//on indique que la version tournée est la nouvelle position initiale :
			initialShape=position.clone();
		}

	}

	//////////////////////  Get et Set  ////////////////////////////////

	public TetrisCell getType() {
		// on récupère une rotation (la première par exemple) :
		// [ On suppose qu'un tetromino n'est pas omposé de plusieurs type de cellules non vide]
		TetrisCell[][] position=listRotation.get(0);
		// on parcours le tetromino à la recherche d'une cellule non vide :
		for (int i=0;i<getBoxSize();i+=1){
			for (int j=0;j<getBoxSize();j+=1){
				// si c'est pas une cellule vide on renvoie le type de la cellule :
				if(position[i][j]!=TetrisCell.EMPTY){
					return position[i][j];
				}
			}
		}
		// sinon le tetromino est vide :
		return TetrisCell.EMPTY;
	}

	public int getNumberOfRotations() {
		return listRotation.size();
	}
	
	public int getBoxSize() {
		TetrisCell[][] position=listRotation.get(0);
		System.out.println();
		return position.length; // nombre de ligne d'une position
	}
	
	public Tetromino getTetromino(int rotationNumber) {
		return new TetrominoImpl( this ,rotationNumber);
		
	}


	//////////////////////  Random tetromino  ////////////////////////////////

	// Non utilisé, une autre méthode a été utilisé dans le random provideur

	public static Tetromino randomTetromino() {
		TetrominoShape randomShape = values()[RANDOM.nextInt(values().length)];
		return randomShape.getTetromino(RANDOM.nextInt(randomShape.getNumberOfRotations()));
	}
}


