package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoShape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class AbstractGameManager implements GameManager {

    // Cette classe abstraite permet de gérer un manager par default (sans le côté visuel) . Le manager lance et stop le jeu.


    ///////////////////////// Elements de la classe ///////////////////


    ///////// le modèle (par default):

    // Le manager contient les elements du modèle pouvant être modifié (comme le provider, mais dans notre cas il reste toujours en random),
    // ainsi que le player qui gère le jeu.

    private TetrominoProvider provider=DEFAULT_PROVIDER;  // un fournisseur de tetromino
    private TetrisMode mode=DEFAULT_MODE;  // un mode de jeu
    private PlayerType playerType=DEFAULT_PLAYER_TYPE ;  // le type de joueur
    private int nbline=DEFAULT_LINES;  // nombre de lignes de la grille de jeu
    private int nbcol=DEFAULT_COLS;  // nombre de colonnes de la grille de jeu
    private GamePlayer gamePlayer;  // un player, gère le jeu en lui-même (voir classe Player)

    //////// la vue est dans la classe fille visual

    ///////////////////////// Get et Set /////////////////////////


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




    ///////////////////////// Actions ////////////////////////


    ///////// Creation/Lancement/Arret du Player :

    // fonction qui lance une erreur, un player est crée par les classe fille de la classe abstraite.
    public void createPlayer() {
        throw new UnsupportedOperationException();

    }

    /**
     * Load a new empty game
     *
     * This creates a new player if needed and initialize the player with the new game
     *
     * It does not start the player, so the player should be on "pause" i.e. not active
     */
    public void loadNewGame(){
        createPlayer(); // on créer juste un player en lui donnant les valeurs par défaults.
    }

    /**
     * Load a player from a saved game (ajout par rapport à l'interface GameManager)
     * (ajout)
     */
    public abstract void loadPlayer(TetrisMode mode,TetrisGrid grid,int score, int level, int lines);

    public void initialize(){
        gamePlayer.initialize(); // initialisation du manager = initialisation du player
        // La méthode appellée ne fait rien dans la cas SIMPLE (tous est fait par son constructeur),
        // elle indique le listener du timer dans le cas VISUAL.
    }

    /**
     * starts the player (i.e. the actual game)
     */
    public void startPlayer() {
        getPlayer().start();
        // On donne un premier tetromino au player si c'est le lancement du jeu,
        // et dans tous les cas on indique que le player est actif.
        // Dans le cas VISUAL, on ajoute une ligne qui met le player en listener du clavier (avec un request focus en plus)
    }

    /**
     * pause the player
     */
    public void pausePlayer(){
        getPlayer().pause();
        // On indique que le player n'est plus actif, et dans le cas du VISUAL on arrête l'écoute du clavier
    }

    //////// Actions possible du manager (via interface graphique en VISUAL)
    public void actionPerformed(ManagerAction action) { //action 'logistique', le coté visuelle est ajoutée dans managerVisual

        switch ( action) {

            // cas lancement du jeu :
            case START:
            case RESUME:
                gamePlayer.start(); // on lance le player
                break;
            // cas arrêt du jeu :
            case PAUSE:
            case QUIT:
            case RESTART:
                gamePlayer.pause(); // on stop le jeu
                break;
            // restart non fonctionnel en SIMPLE, ce n'est pas grave ici car on ne fera restart qu'avec bouton (donc en VISUAL)
            default:
                break;

        }
    }

    //////// Action de load et save

    // Elles ont été implémentées mais non utilisée, nous n'avons pas fait le boutons pour sauvergader et charger une partie
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

}
