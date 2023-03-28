package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerSimple;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputerImpl;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoShape;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractGameManager implements GameManager {

    // le modèle :
    private TetrominoProvider provider=DEFAULT_PROVIDER;
    private TetrisMode mode=DEFAULT_MODE;
    private PlayerType playerType=DEFAULT_PLAYER_TYPE ;
    private int nbline=DEFAULT_LINES;
    private int nbcol=DEFAULT_COLS;
    private GamePlayer gamePlayer;

    // la vue est dans la classe fille visual


    /////////// Actions ///////////////////

    public void initialize(){
        //les commandes suivantes n'ont pas fonctionnées, donc on a directement mis en valeur par default
        //les commandes suivantes n'ont pas fonctionnées, donc on a directement mis en valeur par default

//        setTetrominoProvider(DEFAULT_PROVIDER);
//        setGameMode(DEFAULT_MODE);
//        setPlayerType(DEFAULT_PLAYER_TYPE );
    }

    /**
     * Load a new empty game
     *
     * This creates a new player if needed and initialize the player with the new game
     *
     * It does not start the player, so the player should be on "pause" i.e. not active
     */
    public void loadNewGame(){
        createPlayer();
    }

    public abstract void loadPlayer(TetrisMode mode,TetrisGrid grid,int score, int level, int lines);
    /**
     * starts the player (i.e. the actual game)
     */
    public void startPlayer() {
        getPlayer().start();
    }

    /**
     * pause the player
     */
    public void pausePlayer(){
        getPlayer().pause();
    }

    public void actionPerformed(ManagerAction action) { //action 'logistique', le coté visuelle est ajoutée dans managerVisual

        switch ( action) {
            case START:
                gamePlayer.start();
                break;
            case PAUSE:
                gamePlayer.pause();
                break;
            case RESUME:
                gamePlayer.start();
                break;
            case RESTART:

                break;
            case QUIT:

                break;
            default:
                break;

        }
    }

    /**
     * Loads a game read from a file
     *
     * This creates a new player if needed and initialize the player with the new game
     *
     * It does not start the player, so the player should be on "pause" i.e. not active
     *
     * A game file contains information about the game state (grid, score, level, etc.)
     *
     * It does not contain: the tetromino provider, the held tetromino saved in game
     *
     * See project description for file format
     *
     * @param file a file
     * @throws FileNotFoundException if the file cannot be read
     * @throws IOException if there is an error while scanning the file following the file format
     */
    public void loadFromFile(File file) throws FileNotFoundException, IOException{
        Scanner scan = new Scanner(file);

        try {

            this.mode= TetrisMode.valueOf(scan.next()); // on récupère le mode
            int score = scan.nextInt(); // on récupère le score
            int level = scan.nextInt(); // on récupère le level
            int lines = scan.nextInt(); // on récupère le nombre de ligne complétée
            TetrominoShape shape= TetrominoShape.valueOf(scan.next()); // on récupère la forme du tetromino en cours
            int rotation= scan.nextInt(); // on récupère la rotation du tétromino
            int i = scan.nextInt(); // on récupère la ligne du tétromino
            int j = scan.nextInt(); // on récupère la colonne du tétromino

            // pour simplifier, on se place dans le cas où on a toujours le nombres de lignes et colonne de default
            int nbline=DEFAULT_LINES;
            int nbcol=DEFAULT_COLS;
            TetrisCell[][] gridFromFile=new TetrisCell[nbline][nbcol]; //on créer la grille vide
            for (int l=0;l<nbline;l++) {
                for (int c = 0; c < nbcol; c++){
                    gridFromFile[l][c]=TetrisCell.valueOf(scan.next()); // on la remplie
                }
            }
            // on peut alors créer l'objet grid de TetrisGrid :
            TetrisGrid grid =TetrisGrid.getEmptyGrid( nbline,nbcol);
            grid.initiateCells(gridFromFile);
            grid.setTetromino(shape.getTetromino(rotation)); // on donne le tetromino en cours
            grid.setCoordinates(new TetrisCoordinates(i,j)); // on le place

            // on créer le player avec ces informations :
            loadPlayer(mode, grid,score, level, lines);
            scan.close();

        } catch(Exception e) {
            scan.close();
            throw new IOException(e);
        }

    }



    /**
     * Save the game
     *
     * A game file contains information about the game state (grid, score, level, etc.)
     *
     * It does not contain: the tetromino provider, the held tetromino saved in game
     *
     * See project description for file format
     *
     * @param file a file
     * @throws FileNotFoundException if one cannot write in the file
     */
    public void save(File file) throws FileNotFoundException,IOException { //intellij demande à ajouter IOException

        FileWriter writer=new FileWriter(file);
        writer.write(mode.name()+"\n"); // le mode de jeu
        writer.write(gamePlayer.getScore()+"\n"); // le score
        writer.write(gamePlayer.getLevel()+"\n"); // le level
        writer.write(gamePlayer.getLineScore()+"\n"); // le nombre de ligne
        writer.write(gamePlayer.getGridView().getTetromino().getShape().name()+"\n"); //le tetromino en cours (shape)
        writer.write(gamePlayer.getGridView().getTetromino().getRotationNumber()+"\n"); // la rotation du tetromino
        writer.write(gamePlayer.getGridView().getCoordinates().getLine()+"\n"); // sa position (ligne)
        writer.write(gamePlayer.getGridView().getCoordinates().getCol()+"\n"); // sa position (colonne)
        // la grille :
        for (int i=0; i< gamePlayer.getGridView().numberOfLines();i++){
            for (int j=0; j< gamePlayer.getGridView().numberOfCols();j++){
                writer.write(gamePlayer.getGridView().gridCell(i,j).name()+' ');
            }
            writer.write("\n");
        }

        writer.close();
    }


    ///////// Get & Set /////////


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
     * Return the player
     * @return a GamePlayer
     */
    public GamePlayer getPlayer(){
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer){ //ajout de cette fonction
        this.gamePlayer=gamePlayer;
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



}
