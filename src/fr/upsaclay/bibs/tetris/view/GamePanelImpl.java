
package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.model.grid.*;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePanelImpl extends JPanel implements GamePanel {

    int nblines= GameManager.DEFAULT_LINES;
    int nbcols=GameManager.DEFAULT_COLS;

    TetrisGridView grid;
    GamePlayer player;
    TetrominoProvider provider;
    int score;
    int lines;
    int level;


    JPanel gameInfoPanel; // pour le score, le tétromino hold et les tetrominos suivant
    GridPanel gridPanel; // pour la grille

    // les sous-panel de gameInfoPanel :
    //TetrominoPanel HoldTetroPanel;
    //TetrominoPanel nextTetroPanel;
    //JPanel scorepanel;

    Timer timer;
    public static final int INITIAL_DELAY=2000; // in ms
    public static final int MIN_DELAY=100;

    public GamePanelImpl() {
        gridPanel=new GridPanel(); // sous-panel pour la grille (droite)

        gameInfoPanel=new JPanel(); // sous panel avec le score et les tetrominos suivant (gauche)


        //HoldTetroPanel=new TetrominoPanel(); // sera dans nextTetroPanel en bas
        //HoldTetroPanel.setBackground(Color.DARK_GRAY);
        //nextTetroPanel=new TetrominoPanel(); // sera en haut de nextTetroPanel
        //nextTetroPanel.setBackground(Color.BLUE);
        //scorepanel=new JPanel(); //sera au milieu de nextTetroPanel
        //scorepanel.setBackground(Color.RED);
        // Create the loop timer
        timer = new Timer(INITIAL_DELAY, null);
    }

    /**
     * Run all needed initializations
     */
    @Override
    public void initialize(){
        ////////////// gridPanel ////////////////////////

        gridPanel.initialise();
        gridPanel.setGrid(grid);
        gridPanel.setDim(nblines,nbcols);


        ////////////// gameInfoPanel ////////////////////////

        gameInfoPanel.setPreferredSize(new Dimension(300,gridPanel.getPreferredSize().height));

        //gameInfoPanel.add(HoldTetroPanel,BorderLayout.SOUTH);
        //gameInfoPanel.add(nextTetroPanel,BorderLayout.NORTH);
        //gameInfoPanel.add(scorepanel,BorderLayout.CENTER);

        //////////// ADD panel ////////////

        add(gameInfoPanel,BorderLayout.WEST);
        add(gridPanel,BorderLayout.EAST);


    }


    /**
     * Draw itself for the "management view" (before a game is started,
     * or in between games)
     *
     * (add management buttons, menus and stuff)
     */
    @Override
    public void drawManagementView(){
        gameInfoPanel.setVisible(false);
        gridPanel.setVisible(false);
        update();
    }

    /**
     * Draw itself for the "game play view": the game is going on
     *
     * (remove management buttons, add needed buttons like "pause", draw
     * the actual game...)
     */
    @Override
    public void drawGamePlayView(){
        gameInfoPanel.setVisible(true);
        gridPanel.setVisible(true);
        update();
    }

    /**
     * Draw itself for when the game is on pause
     */
    @Override
    public void drawGamePauseView(){
        gameInfoPanel.setVisible(true);
        gridPanel.setVisible(true);
        update();
    }

    /**
     * Draw itself for when the game is over (show "Game over", adds a restart button, etc.)
     */
    @Override
    public void drawEndGameView(){
        gameInfoPanel.setVisible(false);
        gridPanel.setVisible(false);
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gridPanel.paintComponent(g);
    }

    public static Color ReturnColorCase(TetrisCell cell) {
        switch (cell){
            case I:
                return Color.BLUE;
            case J:
                return Color.red;
            case L:
                return Color.CYAN;
            case S:
                return Color.GREEN;
            case O:
                return Color.magenta;
            case Z:
                return  Color.ORANGE;
            case T:
                return Color.lightGray;
            default:
                return Color.black;
        }
    }

    /**
     * Sets the number of lines in the game
     * @param nblines
     */
    @Override
    public void setNumberOfLines(int nblines){
        this.nblines=nblines;
    }

    /**
     * Sets the number of cols in the game
     * @param nbcols
     */
    @Override
    public void setNumberOfCols(int nbcols){
        this.nbcols=nbcols;
    }

    /**
     * sets a TetrisGridView containing all synchronized information
     * about the grid (current tetromino, cells, etc)
     *
     * @param view
     */
    @Override
    public void setGridView(TetrisGridView view){
        grid=view;
    }

    public Timer getTimer(){
        return  timer;
    }
    public void setGamePlayer(GamePlayer player){
        this.player = player;
        this.grid = player.getGridView();
        this.provider=player.getProvider();
    }


    /**
     * Adds an action listener to be called at certain time
     * intervals
     * @param listener
     */
    @Override
    public void setLoopAction(ActionListener listener){
        timer.addActionListener(listener);
    }

    /**
     * starts the action loop
     */
    @Override
    public void startActionLoop(){
        timer.start();
    }

    /**
     * pause the action loop
     */
    @Override
    public void pauseActionLoop(){
        timer.stop();
    }

    /**
     * set / update the loop delay in milliseconds
     * @param ms
     */
    @Override
    public void setLoopDelay(int ms){
        timer.setDelay(ms);
    }



    /**
     * visual interface reaction to certain events in the game
     * (like making new lines)
     * @param event a GamePanelEvent
     * @param attach an attachaed objects (needed for certain events: for example the lines that are destroyed)
     */
    @Override
    public void launchGamePanelEvent(GamePanelEvent event, Object attach){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Update the score to be displayed
     * @param score
     */
    @Override
    public void updateScore(int score){
        this.score=score;
    }

    /**
     * Update the score lines to be displayed
     * @param lines
     */
    @Override
    public void updateScoreLines(int lines){
        this.lines=lines;
    }

    /**
     * Update the level to be displayed
     * @param level
     */
    @Override
    public void updateLevel(int level){
        this.level=level;
    }

    /**
     * Update the held tetromino to be displayed
     * @param tetromino
     */
    @Override
    public void updateHeldTetromino(Tetromino tetromino){
        //HoldTetroPanel.setTet(tetromino);
    }

    // ajout :
    public void updateGrid(TetrisGridView grid){
        gridPanel.setGrid(grid);
    }

    /**
     * update the list of upcomming tetrominos to be displayed
     * @param tetrominos
     */
    @Override
    public void updateNextTetrominos(List<Tetromino> tetrominos){
        // 1 seul tetromino suivant pour le moment
        //nextTetroPanel.setTet(tetrominos.get(0));
    }

    /**
     * update the view
     */
    @Override
    public void update(){

        // mis à jour grille
        updateGrid(grid);

        // mis à jour tetromino suivant
        //List<Tetromino> nextTetro=new ArrayList<Tetromino>();
        //nextTetro.add(provider.showNext(0));
        //updateNextTetrominos(nextTetro);

        // mis à jour holdtetromino
        //updateHeldTetromino(player.getHeldTetromino());

        // mis à jour score

                    // a faire

        // repaint
        gridPanel.repaint();
        //nextTetroPanel.repaint();
        //HoldTetroPanel.repaint();
        gameInfoPanel.repaint();
    }
}

