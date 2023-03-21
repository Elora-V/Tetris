package fr.upsaclay.bibs.tetris.model.grid;

import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;

import java.io.PrintStream;
import java.sql.Array;
import java.util.List;

public class Mygrid implements TetrisGrid,TetrisGridView {


    TetrisCell[][] Mygrid_case; // TetrisCell[][] comme int
    int numligne;
    int numcolonne;

    Tetromino tet;

    TetrisCoordinates tcoord; // coordinates of the left top corner of the tetromino in the grid



    // constructeur
    public Mygrid(int numligne,int numcolonne){
        this.numcolonne = numcolonne;
        this.numligne = numligne;
        this.tet = null;
        this.tcoord = null;

        Mygrid_case = new TetrisCell[numligne][numcolonne];

        for(int i = 0; i <numligne;i++){
            for (int j = 0; j<numcolonne;j++){
                Mygrid_case[i][j] = TetrisCell.EMPTY;
            }

        }
    }

    // Methods

    /**
     * Return the number of lines
     * @return the number of lines
     */

    // 18/03/2023
    public int numberOfLines(){
        return numligne;
    }

    /**
     * Return the number of cols
     * @return the number of cols
     */
    public int numberOfCols(){
        return numcolonne;
    }

    /**
     * Return the tetris cell on the grid at the given position
     * if the position is not on the grid (even negative), it should return TetrisCell.GREY
     * (as if the grid was infinite with grey cells all around)
     * @param i the line number, positive or negative
     * @param j the col number, positive or negative
     * @return the cell on the grid
     */

    // 18/03/2023
    public TetrisCell gridCell(int i, int j){
        if(i < 0 || i >= numligne || j<0 || j >= numcolonne){
            return TetrisCell.GREY;
        }
        return Mygrid_case[i][j];
    }

    /**
     * Prints the grid cells line by line on the out stream
     * (for example on the console or in a file)
     * @param out the out stream to print the grid
     */

    public void printGrid(PrintStream out) {
        for (int i = 0; i < numligne; i++) {
            for (int j = 0; j < numcolonne; j++) {
                out.print(Mygrid_case[i][j] + " ");
            }
            out.println();
        }
    }

    /**
     * Return if the grid has a tetromino
     * @return true if a tetromino has been attached
     */
//    public boolean hasTetromino(){
//            for (int i = 0; i < numligne; i++) {
//                for (int j = 0; j < numcolonne; j++) {
//                    if(Mygrid_case[i][j]!= TetrisCell.EMPTY){
//                        return true;
//                    }
//
//                }
//            }
//            return false;
//    }

    public boolean hasTetromino(){
        return tet != null;
    }

    /**
     * Return the current tetromino attached to the grid
     * @return a Tetromino
     */
    public Tetromino getTetromino(){
        return tet;
    }

    /**
     * Return the coordinated of the current tetromino
     * @return some TetrisCoordinates
     */
    public TetrisCoordinates getCoordinates(){
        return tcoord;
    }

    /**
     * Return the cell that is visible on the grid
     * this is the grid cell unless there is an non-empty tetromino cell
     * in front of it
     *
     * If there is a current tetromino but no coordinates, an IllegalStateException is thrown
     * @param i the line number
     * @param j the col number
     * @return the visible cell
     */
    public TetrisCell visibleCell(int i, int j){

        if(tet != null) { // si il y a un tetromino :
            if(tcoord == null){ // si on a pas les coordonnées :
                throw new IllegalStateException("No coordinates");
            }
            int it = tcoord.getLine();
            int jt = tcoord.getCol();

            // si la case demandée est au niveau d'un tetromino :
            if (it <= i && i <= it - 1 + tet.getBoxSize() && jt <= j && j <= jt - 1 + tet.getBoxSize()) {
                if (tet.cell(i - it, j - jt)!= TetrisCell.EMPTY){ // si la case du tetromino n'est pas vide:
                    return tet.cell(i - it, j - jt);
                }

            }
        }
        // si on est dans aucun des cas précedents :
        return gridCell(i, j);  // renvoir information de la grille

    }


    /**
     * Return if the current tetromino has a "conflict" with the grid
     * i.e. : if it is overlapping on some non empty cells
     *
     * If there is no current tetromino, there is no conflict
     * If there is a tetromino but bo coordinates, it raises an IllegalStateException
     *
     * @return true if some non empty cells are overlapping
     */
    public boolean hasConflicts(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return if a given line is full (no empty cell)
     * @param lineNumber the line number (top line is 0)
     * @return true if the line is full, false otherwise
     */
    public boolean isFull(int lineNumber){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return if a given line is empty
     * @param lineNumber the line number (top line is 0)
     * @return true if the line is empty, false otherwise
     */
    public boolean isEmpty(int lineNumber){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return the list of lines which are full
     * @return a list of line numbers (top line is 0)
     */
    public List<Integer> fullLines(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return if the full grid is empty
     * @return true if the full grid is empty
     */
    public boolean isEmpty(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Return a view of self that cannot be used to modify the grid
     * @return
     */

    // 18/03/2023
    public SynchronizedView getView(){
        return new SynchronizedView(this);
    }

    /**
     * Copies the values of cells into the grid
     *
     * Throws an IllegalArgumentException if the dimensions do not fit
     *
     * @param cells a double array of cells
     */

    // 18/03/2022
    public void initiateCells(TetrisCell[][] cells){
        if(cells.length != numligne || cells[0].length != numcolonne){
            throw new IllegalArgumentException("Dimensions do not fit");
        }
        for(int i = 0;i<numligne;i++){
            for(int j =0;j<numcolonne;j++){
                Mygrid_case[i][j] = cells[i][j];
            }
        }
    }



    /**
     * Sets a tetromino to the grid
     * @param tetromino a Tetromino
     */
    public void setTetromino(Tetromino tetromino){
        tet = tetromino;
    }

    /**
     * Sets the tetromino coordinates
     * @param coordinates some TetrisCoordinates for the tetromino
     */
    public void setCoordinates(TetrisCoordinates coordinates){
       tcoord = coordinates;
    }


    /**
     * Place the current tetromino in the top middle of the grid:
     * line -- 0
     * col -- (numberOfCols() - tetromino box size)/2
     */
    public void setAtStartingCoordinates(){
        setCoordinates(new TetrisCoordinates(0,(numberOfCols()- tet.getBoxSize())/2));
    }



    /**
     * Try to move the current tetromino by the given direction
     *
     *  The direction is given as a tetris coordinates that should be added to the current coordinate.
     *  For example, if the current tetromino is in position (0,3) (line 0 and col 3), and the given dir
     *  is (1,0) (TetrisCoordinates.DOWN), then the new position is (1,3).
     *
     *  The tetromino is moved only if the new position does not generate conflicts with the underlying
     *  grid. Otherwise, the tetromino stays at its initial position.
     *
     *  If this function is called when the grid has no tetromino / coordinates,
     *  it thows an IllegalStateException
     *
     * @param dir a TetrisCoordinates to be aded to the current tetromino coordinates
     * @return true if the tetromino has been moved, false otherwise
     */
    public boolean tryMove(TetrisCoordinates dir){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Try to rotate the current tetromino to the right
     *
     * If this function is called when the grid has no tetromino / coordinates,
     * it thows an IllegalStateException
     *
     * This uses the SPF "Super rotation System" from the Tetris official rules
     *
     * It first rotates the tetromino within its box and tests for conflicts. Then
     * it moves the tetromino using the specific kicks defined at the tetromino level:
     * for each kick, it tries to move the tetromino and comes back to initial position if
     * it fails.
     *
     * If all kicks fail, then the rotation fails and the grid is left unchanged.
     *
     * @return true if the tetromino has been rotated, false otherwise
     */
    public boolean tryRotateRight(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Try to rotate the current tetromino to the left
     *
     * If this function is called when the grid has no tetromino / coordinates,
     * it thows an IllegalStateException
     *
     * This uses the SPF "Super rotation System" from the Tetris official rules
     *
     * It first rotates the tetromino within its box and tests for conflicts. Then
     * it moves the tetromino using the specific kicks defined at the tetromino level:
     * for each kick, it tries to move the tetromino and comes back to initial position if
     * it fails.
     *
     * If all kicks fail, then the rotation fails and the grid is left unchanged.
     *
     * @return true if the tetromino has been rotated, false otherwise
     */
    public boolean tryRotateLeft(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Merge the current tetromino into the grid
     *
     * If there is no tetromino, the grid is left unchanged.
     *
     * An illegalStateException is thrown if there is a tetromino but no coordinates.
     *
     * If the tetromino is in conflict with the grid, the merge still happens: the tetromino
     * visible cell replace the grid cell.
     *
     * After the operation, the current tetromino and coordinates are set to null
     */
    public void merge(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Move the current tetromino as much down as possible without getting a conflict
     *
     * Note: this does NOT merge the tetromino to the grid
     *
     * If there is no tetromino or no coordinates, it throws an IllegalStateException
     */
    public void hardDrop(){
        throw new UnsupportedOperationException("Not implemented");
    }


    /**
     * "Pack" the grid
     * i.e. remove all full lines and replace them with empty lines on top
     *
     * No "gravity" is applied on remaining cells (a cell can be above an empty cell)
     *
     * @return the list of line indexes that have been "packed". These are the indices of lines
     *         that were full before packing
     */
    public List<Integer> pack(){
        throw new UnsupportedOperationException("Not implemented");
    }

}

