package fr.upsaclay.bibs.tetris.model.grid;

import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;

import java.io.PrintStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Mygrid implements TetrisGrid,TetrisGridView {


    TetrisCell[][] Mygrid_case; // TetrisCell[][] comme int
    int numligne;
    int numcolonne;

    Tetromino tet;

    TetrisCoordinates tcoord; // coordinates of the left top corner of the tetromino in the grid


    // constructeur
    public Mygrid(int numligne, int numcolonne) {
        this.numcolonne = numcolonne;
        this.numligne = numligne;
        this.tet = null;
        this.tcoord = null;

        Mygrid_case = new TetrisCell[numligne][numcolonne];

        for (int i = 0; i < numligne; i++) {
            for (int j = 0; j < numcolonne; j++) {
                Mygrid_case[i][j] = TetrisCell.EMPTY;
            }

        }
    }

    // Methods

    /**
     * Return the number of lines
     *
     * @return the number of lines
     */

    // 18/03/2023
    public int numberOfLines() {
        return numligne;
    }

    /**
     * Return the number of cols
     *
     * @return the number of cols
     */
    public int numberOfCols() {
        return numcolonne;
    }

    /**
     * Return the tetris cell on the grid at the given position
     * if the position is not on the grid (even negative), it should return TetrisCell.GREY
     * (as if the grid was infinite with grey cells all around)
     *
     * @param i the line number, positive or negative
     * @param j the col number, positive or negative
     * @return the cell on the grid
     */

    // 18/03/2023
    public TetrisCell gridCell(int i, int j) {
        if (i < 0 || i >= numligne || j < 0 || j >= numcolonne) {
            return TetrisCell.GREY;
        }
        return Mygrid_case[i][j];
    }

    /**
     * Prints the grid cells line by line on the out stream
     * (for example on the console or in a file)
     *
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
     *
     * @return true if a tetromino has been attached
     */

    public boolean hasTetromino() {
        return tet != null;
    }

    /**
     * Return the current tetromino attached to the grid
     *
     * @return a Tetromino
     */
    public Tetromino getTetromino() {
        return tet;
    }

    /**
     * Return the coordinated of the current tetromino
     *
     * @return some TetrisCoordinates
     */
    public TetrisCoordinates getCoordinates() {
        return tcoord;
    }

    /**
     * Return the cell that is visible on the grid
     * this is the grid cell unless there is an non-empty tetromino cell
     * in front of it
     * <p>
     * If there is a current tetromino but no coordinates, an IllegalStateException is thrown
     *
     * @param i the line number
     * @param j the col number
     * @return the visible cell
     */
    public TetrisCell visibleCell(int i, int j){

        if(hasTetromino()) { // si il y a un tetromino :
            if(tcoord == null){ // si on a pas les coordonnées :
                throw new IllegalStateException("No coordinates"); // on lève une erreur
            }
            // sinon on peut récupérer les coordonnées
            int it = tcoord.getLine();
            int jt = tcoord.getCol();

            // si la case demandée est au niveau d'un tetromino :
            if (it <= i && i <= it - 1 + tet.getBoxSize() && jt <= j && j <= jt - 1 + tet.getBoxSize()) {
                if (tet.cell(i - it, j - jt)!= TetrisCell.EMPTY){ // si la case du tetromino n'est pas vide:
                    return tet.cell(i - it, j - jt); // on renvoie la case du tétromino
                }

            }
        }
        // si on est dans aucun des cas précedents :
        // (pas de tétromino, pas sur une case avec le tétromino, sur une case avec le tétromino qui est empty)
        return gridCell(i, j);  // renvoie la case de la grille

    }


    /**
         * Return if the current tetromino has a "conflict" with the grid
         * i.e. : if it is overlapping on some non-empty cells
         *
         * If there is no current tetromino, there is no conflict
         * If there is a tetromino but no coordinates, it raises an IllegalStateException
         *
         * @return true if some non-empty cells are overlapping
         */

        public boolean hasConflicts () {
            if (tet == null) {
                return false;
            }
            if (tcoord == null) {
                throw new IllegalStateException("No coordinates");
            }

            int itetromino = tcoord.getLine(); // i du tetromino
            int jtetromino = tcoord.getCol();  // j du tetromino

            for (int i = itetromino; i <= itetromino - 1 + tet.getBoxSize(); i++) {
                for (int j = jtetromino; j <= jtetromino - 1 + tet.getBoxSize(); j++) {

                    if (tet.cell(i - itetromino, j - jtetromino) != TetrisCell.EMPTY) {
                        if (i >= 0 && i < numligne && j >= 0 && j < numcolonne) {
                            if (gridCell(i, j) != TetrisCell.EMPTY) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Return if a given line is full (no empty cell)
         * @param lineNumber the line number (top line is 0)
         * @return true if the line is full, false otherwise
         */
        /*
         * fonction qui verifie si une ligne donnée est rempli
         * on parcourt la ligne
         * si une case est vide on retourne faux
         * sinon vrai
         */
        public boolean isFull ( int lineNumber){
            boolean flag = true;
            for (int i = 0; i < numcolonne; i++) {
                if (Mygrid_case[lineNumber][i] == TetrisCell.EMPTY) {
                    flag = false;
                }
            }
            return flag;

        }

        /**
         * Return if a given line is empty
         * @param lineNumber the line number (top line is 0)
         * @return true if the line is empty, false otherwise
         */
        /*
         * fonction qui verifie si une ligne donnéé est rempli
         * on parcour la ligne
         * si une case est pleine on retourne faux
         * sinon vrai
         */
        public boolean isEmpty ( int lineNumber){
            boolean flag = true;
            for (int i = 0; i < numcolonne; i++) {
                if (Mygrid_case[lineNumber][i] != TetrisCell.EMPTY) {
                    flag = false;
                }
            }
            return flag;
        }

        /**
         * Return the list of lines which are full
         * @return a list of line numbers (top line is 0)
         */
        public List<Integer> fullLines () {
            List<Integer> list1 = new ArrayList<Integer>();
            for (int i = 0; i < numligne; i++) {
                if (isFull(i) == true) {
                    list1.add(i);
                }
            }
            return list1;
        }

        /**
         * Return if the full grid is empty
         * @return true if the full grid is empty
         */
        public boolean isEmpty () {
            boolean flag = true;
            for (int i = 0; i < numligne; i++) {
                if (isEmpty(i) == false) {
                    flag = false;
                }
            }
            return flag;
        }

        /**
         * Return a view of self that cannot be used to modify the grid
         * @return
         */

        // 18/03/2023
        public SynchronizedView getView () {
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
        public void initiateCells (TetrisCell[][]cells){
            if (cells.length != numligne || cells[0].length != numcolonne) {
                throw new IllegalArgumentException("Dimensions do not fit");
            }
            for (int i = 0; i < numligne; i++) {
                for (int j = 0; j < numcolonne; j++) {
                    Mygrid_case[i][j] = cells[i][j];
                }
            }
        }


        /**
         * Sets a tetromino to the grid
         * @param tetromino a Tetromino
         */
        public void setTetromino (Tetromino tetromino){
            tet = tetromino;
        }

        /**
         * Sets the tetromino coordinates
         * @param coordinates some TetrisCoordinates for the tetromino
         */
        public void setCoordinates (TetrisCoordinates coordinates){
            tcoord = coordinates;
        }


        /**
         * Place the current tetromino in the top middle of the grid:
         * line -- 0
         * col -- (numberOfCols() - tetromino box size)/2
         */
        public void setAtStartingCoordinates () {
            setCoordinates(new TetrisCoordinates(0, (numberOfCols() - tet.getBoxSize()) / 2));
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
         * @param dir a TetrisCoordinates to be added to the current tetromino coordinates
         * @return true if the tetromino has been moved, false otherwise
         */
        public boolean tryMove (TetrisCoordinates dir){
            if (tet == null || tcoord == null) {
                throw new IllegalStateException("No tetromino or coordinates");
            }

            int Old_line = tcoord.getLine();
            int Old_col = tcoord.getCol();

            int New_line = tcoord.getLine() + dir.getLine();
            int New_col = tcoord.getCol() + dir.getCol();

            setCoordinates(new TetrisCoordinates(New_line,New_col));

            if (hasConflicts()) {
                setCoordinates(new TetrisCoordinates(Old_line,Old_col));
                return false;
            }
            return true;
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
        public boolean tryRotateRight () {
            // si on n'a pas de tetromino ou de coordonées :
            if (tet == null || tcoord == null) {
                throw new IllegalStateException("No tetromino or coordinates"); // on lève une erreur
            }

            setTetromino(tet.rotateRight()); // on tourne le tétromino
            if(!hasConflicts()){ // si il n'y a pas de problème : on sort de la fonction (et on renvoit vrai)
                return true;
            }

            // sinon on doit appliquer wallkicks :
            List<TetrisCoordinates> kicks=tet.wallKicksFromRight(); // on récupère les mouvements à tester

            for (TetrisCoordinates kick : kicks) { // on les test un par un :
               boolean moveDone= tryMove(kick); // soit on a bougé et moveDone=true, soit on n'a pas bougé et moveDone=false
               if(moveDone){ // si on a bougé on ne veut pas essayé les autres conditions : on fait un return
                   return true;
               }
            }
            // si on a tout testé et qu'aucun mouvement n'est bon : on annule la rotation à droite
            setTetromino(tet.rotateLeft());
            return false;
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
        public boolean tryRotateLeft () {
            // si on n'a pas de tetromino ou de coordonées :
            if (tet == null || tcoord == null) {
                throw new IllegalStateException("No tetromino or coordinates"); // on lève une erreur
            }

            setTetromino(tet.rotateLeft()); // on tourne le tétromino
            if(!hasConflicts()){ // si il n'y a pas de problème : on sort de la fonction (et on renvoit vrai)
                return true;
            }

            // sinon on doit appliquer wallkicks :
            List<TetrisCoordinates> kicks=tet.wallKicksFromLeft(); // on récupère les mouvements à tester

            for (TetrisCoordinates kick : kicks) { // on les test un par un :
                boolean moveDone= tryMove(kick); // soit on a bougé et moveDone=true, soit on n'a pas bougé et moveDone=false
                if(moveDone){ // si on a bougé on ne veut pas essayé les autres conditions : on fait un return
                    return true;
                }
            }
            // si on a tout testé et qu'aucun mouvement n'est bon : on annule la rotation à droite
            setTetromino(tet.rotateRight());
            return false;

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
        public void merge () {

            if (hasTetromino()) { // si il y a un tetromino :

                if (tcoord == null) { // si on a pas les coordonnées :
                    throw new IllegalStateException("No coordinates"); // on lève une erreur
                }

                // sinon on peut récupérer les coordonnées
                int it = tcoord.getLine(); // i du tetromino
                int jt = tcoord.getCol();  // j du tetromino
                // pour chaque case au niveau d'un tetromino :
                for (int i = it; i <= it - 1 + tet.getBoxSize(); i++) {
                    for (int j = jt; j <= jt - 1 + tet.getBoxSize(); j++) {
                        // verification que i et j dépasse pas de la grille, de plus on veut pas remplacer une valeur par le empty d'un tetromino
                        if (i >= 0 && i < numligne && j >= 0 && j < numcolonne && tet.cell(i - it, j - jt) != TetrisCell.EMPTY) {

                            Mygrid_case[i][j] = tet.cell(i - it, j - jt); // on remplace par la case du tétromino
                        }
                    }
                }
                // après avoir merge on peut 'enlever' le tetromino
                tet = null;
                tcoord = null;
            }

        }

        /**
         * Move the current tetromino as much down as possible without getting a conflict
         *
         * Note: this does NOT merge the tetromino to the grid
         *
         * If there is no tetromino or no coordinates, it throws an IllegalStateException
         */
        public void hardDrop () {
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
    	
    	List<Integer> listfullLines = fullLines();//liste des ligne complete avant pack
    	
    	for (int i=0 ; i < listfullLines.size(); i++) {
    		for (int j=0 ;j< numcolonne; j++) {
    			int ic = listfullLines.get(i);
    			Mygrid_case[ic][j]=TetrisCell.EMPTY;// remplace les ligne pleine par des ligne vide
    		}
    	}
    	// on doit faire dessendre les ligne audessu des ligne suprimer
    	for (int i=0 ; i < listfullLines.size(); i++) {
    		for (int j=0 ;j< numcolonne; j++) {
    			int ig = listfullLines.get(i);
    			while (ig!=0) {
    				Mygrid_case[ig][j]=Mygrid_case[ig-1][j];
    				ig--;
    			}
    			Mygrid_case[0][j]=TetrisCell.EMPTY;
    		}
    	}
        return listfullLines;//renvoie la liste des ligne pleine
        
    }

}
