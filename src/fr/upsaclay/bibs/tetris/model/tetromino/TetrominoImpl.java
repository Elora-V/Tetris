package fr.upsaclay.bibs.tetris.model.tetromino;

import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoShape;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TetrominoImpl implements Tetromino{

    TetrominoShape typeShape;
    TetrisCell[][] shape;
    int rotation;

    public TetrominoImpl(TetrominoShape type,int rotation){
        this.rotation=rotation;
        this.typeShape=type;
        this.shape= type.listPosition.get(rotation); //recup la version tournée dans la liste des positions possibles
    }



    /**
     * Return the Tetromino shape
     *
     * @return the shape of the tetromino
     */
    @Override
    public TetrominoShape getShape(){
        return typeShape;
    }

    /**
     * Return the rotation number
     *
     * @return the rotation number
     */
    @Override
    public int getRotationNumber(){
        return this.rotation;
    }

    /**
     * Return the tetromino obtained by
     * rotation to the right
     *
     * @return a tetromino
     */
    @Override
    public Tetromino rotateRight(){
       return new TetrominoImpl(this.typeShape, (this.rotation +1 ) % typeShape.getNumberOfRotations() ) ;
    }

    /**
     * Return the tetromino obtained by
     * rotation to the left
     *
     * @return a tetromino
     */
    @Override
    public Tetromino rotateLeft(){
        return new TetrominoImpl(this.typeShape, (this.rotation -1 +typeShape.getNumberOfRotations()) % typeShape.getNumberOfRotations()) ;
    }

    /**
     * The cell at position line, col
     * the top line is 0
     * the left colmun is 0
     *
     * @param line
     * @param col
     * @return a tetris cell (can be empty tetris cell)
     */
    @Override
    public TetrisCell cell(int line, int col){
        return shape[line][col];
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TetrominoImpl.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        // comparaison d'objet tetromino :
        TetrominoImpl tetro= (TetrominoImpl) obj; // conversion en tetromino
        if (!this.typeShape.equals(tetro.typeShape)){
            return false;
        }
        if (this.rotation!=tetro.rotation){
            return false;
        }
        if ( ! Arrays.deepEquals(this.shape,tetro.shape) ){
            return false;
        }
        else{ return true;}

    }


    @Override
    public String toString(){
        String tetro="\n";
        for (int i=0;i<typeShape.getBoxSize();i++){
            for (int j=0;j<typeShape.getBoxSize();j++){
                if(cell(i,j).equals(TetrisCell.EMPTY)){
                    tetro+=' ';
                }else {
                    tetro += cell(i, j).toString();
                }
            }
            tetro+='\n';
        }
        return tetro;
    }
    /**
     * The box size
     *
     * a tetromino has cells for
     * 0 <= line < boxSize
     * 0 <= col < boxSize
     *
     * @return the box size
     */
    @Override
    public int getBoxSize(){
        return typeShape.getBoxSize();
    }

    /**
     * Wall kicks are certain "trick" to handle tetromino
     * rotations inside a tetris grid
     *
     * See Tetris wiki for more information
     *
     * wallKicks can be hard coded within the Tetromino class
     * (see the tests for accurate values to be returned)
     *
     * @return a list of TetrisCoordinates to be applied when arriving at this tetromino after
     *         a right rotation
     */
    @Override
    public List<TetrisCoordinates> wallKicksFromRight(){
        // il faut faire attention à nos coordonnées : sur le site x et y sont inversés par rapport à nous

        //de plus changement de signe de la seconde coordonnée ?? (non fait dans test, donc j'ai mis pareil)
        switch (typeShape) {
            case ISHAPE:
                switch (rotation) {
                    case 0:
                        return Arrays.asList(new TetrisCoordinates(0,1), new TetrisCoordinates(0,-2), new TetrisCoordinates(2,1), new TetrisCoordinates(-1,-2));
                    case 1:
                        return Arrays.asList(new TetrisCoordinates(0,-2), new TetrisCoordinates(0,1), new TetrisCoordinates(1,-2), new TetrisCoordinates(-2,1));
                    case 2:
                        return Arrays.asList(new TetrisCoordinates(0,-1), new TetrisCoordinates(0,2), new TetrisCoordinates(-2,-1), new TetrisCoordinates(1,2)) ;
                    case 3:
                        return Arrays.asList(new TetrisCoordinates(0,2), new TetrisCoordinates(0,-1), new TetrisCoordinates(-1,2), new TetrisCoordinates(2,-1));
                }

            case TSHAPE:
            case SSHAPE:
            case ZSHAPE:
            case JSHAPE:
            case LSHAPE:
                switch (rotation) {
                    case 0:
                        return Arrays.asList(TetrisCoordinates.LEFT, new TetrisCoordinates(1,-1), new TetrisCoordinates(-2,0), new TetrisCoordinates(-2,-1));
                    case 1:
                        return Arrays.asList(TetrisCoordinates.LEFT, new TetrisCoordinates(-1,-1), new TetrisCoordinates(2,0), new TetrisCoordinates(2,-1));
                    case 2:
                        return Arrays.asList(TetrisCoordinates.RIGHT, new TetrisCoordinates(1,1), new TetrisCoordinates(-2,0), new TetrisCoordinates(-2,1));
                    case 3:
                        return Arrays.asList(TetrisCoordinates.RIGHT, new TetrisCoordinates(-1,1), new TetrisCoordinates(2,0), new TetrisCoordinates(2,1));
                }

            case OSHAPE:
            default:
                return Arrays.asList();

        }

    }

    /**
     * Wall kicks are certain "trick" to handle tetromino
     * rotations inside a tetris grid
     *
     * See Tetris wiki for more information
     *
     * wallKicks can be hard coded within the Tetromino class
     * (see the tests for accurate values to be returned)
     *
     * @return a list of TetrisCoordinates to be applied when arriving at this tetromino after
     *         a left rotation
     */

    @Override
    public List<TetrisCoordinates> wallKicksFromLeft(){
        switch (typeShape) {
            case ISHAPE:
                switch (rotation) {
                    case 0:
                        return Arrays.asList(new TetrisCoordinates(0,2), new TetrisCoordinates(0,-1), new TetrisCoordinates(-1,2), new TetrisCoordinates(2,-1));
                    case 1:
                        return Arrays.asList(new TetrisCoordinates(0,1), new TetrisCoordinates(0,-2), new TetrisCoordinates(2,1), new TetrisCoordinates(-1,-2));
                    case 2:
                        return Arrays.asList(new TetrisCoordinates(0,-2), new TetrisCoordinates(0,1), new TetrisCoordinates(1,-2), new TetrisCoordinates(-2,1));
                    case 3:
                        return Arrays.asList(new TetrisCoordinates(0,-1), new TetrisCoordinates(0,2), new TetrisCoordinates(-2,-1), new TetrisCoordinates(1,2));
                }

            case TSHAPE:
            case SSHAPE:
            case ZSHAPE:
            case JSHAPE:
            case LSHAPE:
                switch (rotation) {
                    case 0:
                        return Arrays.asList(TetrisCoordinates.RIGHT, new TetrisCoordinates(1,1), new TetrisCoordinates(-2,0), new TetrisCoordinates(-2,1));
                    case 1:
                        return Arrays.asList(TetrisCoordinates.LEFT, new TetrisCoordinates(-1,-1), new TetrisCoordinates(2,0), new TetrisCoordinates(2,-1));

                    case 2:
                        return Arrays.asList(TetrisCoordinates.LEFT, new TetrisCoordinates(1,-1), new TetrisCoordinates(-2,0), new TetrisCoordinates(-2,-1));

                    case 3:
                        return Arrays.asList(TetrisCoordinates.RIGHT, new TetrisCoordinates(-1,1), new TetrisCoordinates(2,0), new TetrisCoordinates(2,1));
                }

            case OSHAPE:
            default:
                return Arrays.asList();

        }

    }
}
