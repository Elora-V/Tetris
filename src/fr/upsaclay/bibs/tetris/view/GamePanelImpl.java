
package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.model.grid.Mygrid;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCoordinates;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.Provider;
import java.util.List;

public class GamePanelImpl extends JPanel implements GamePanel {

    int nblines= GameManager.DEFAULT_LINES;
    int nbcols=GameManager.DEFAULT_COLS;

    TetrisGridView grid;
    Provider provider; // set

    Tetromino tetromino;
    TetrisCoordinates tcoord;

    JPanel gameInfoPanel; // pour le score, le tétromino hold et les tetrominos suivant


    JPanel gridPanel; // pour la grille

    // les sous-panel de gameInfoPanel :
    JPanel HoldTetroPanel;
    JPanel nextTetroPanel;
    JPanel scorepanel;

    Timer timer;
    public static final int INITIAL_DELAY=2000; // in ms
    public static final int MIN_DELAY=100;

    public GamePanelImpl() {
        gridPanel=new JPanel(); // sous-panel pour la grille (droite)

        gridPanel.setBackground(Color.BLACK);

        gridPanel.setPreferredSize(new Dimension(nbcols*GameFrame.PIXELS_PER_CELL,nblines*GameFrame.PIXELS_PER_CELL));
        gameInfoPanel=new JPanel(); // sous panel avec le score et les tetrominos suivant (gauche)
        gameInfoPanel.setBackground(Color.BLACK);
        gameInfoPanel.setPreferredSize(new Dimension(300,gridPanel.getPreferredSize().height));

        HoldTetroPanel=new JPanel(); // sera dans nextTetroPanel en bas
        HoldTetroPanel.setBackground(Color.DARK_GRAY);
        nextTetroPanel=new JPanel(); // sera en haut de nextTetroPanel
        nextTetroPanel.setBackground(Color.BLUE);
        scorepanel=new JPanel(); //sera au milieu de nextTetroPanel
        scorepanel.setBackground(Color.RED);

        // Create the loop timer
        timer = new Timer(INITIAL_DELAY, null);
    }

    /**
     * Run all needed initializations
     */
    @Override
    public void initialize(){

        ////////////// gridPanel ////////////////////////

        add(gridPanel,BorderLayout.EAST);

        ////////////// nextTetroPanel ////////////////////////
        gameInfoPanel.add(HoldTetroPanel,BorderLayout.SOUTH);
        gameInfoPanel.add(nextTetroPanel,BorderLayout.NORTH);
        gameInfoPanel.add(scorepanel,BorderLayout.CENTER);
        add(gameInfoPanel,BorderLayout.WEST);

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
    	gridPanel.setVisible(true);
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
    	gridPanel.setVisible(false);
    	update();
    }

    /**
     * Draw itself for when the game is on pause
     */
    @Override
    public void drawGamePauseView(){
    	gameInfoPanel.setVisible(false);
    	gridPanel.setVisible(false);
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

    //@Override
    public void paintComponent(Graphics g) {

        for (int i; i < tcoord.getLine(); i++) {
            for (int j; j < tcoord.getCol(); j++) {
                if (....){
                    g.fillRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                }
            }
            drawB
        }

        // public void paintComponent(Graphics g){
        //   throw new UnsupportedOperationException("Not implemented paintComponent");


        // la grille :
        //grid.drawLine(...);

        //double boucle i j
        // dans boucle: recup type cellule avec cellVisible(i,j)
        // appelé methode qui renvoie couleur
        // colorier le carré correpondant (sachant qu'il est de longueur et hauteur GameFrame.PIXELS_PER_CELL)

        // scorepanel : text affiche level et score

        // nexttetrominopanel : text: 'next :'
        // + tetromino (avec new function)

        // holdtetrominopanel : text: 'hold:'
        // + tetromino
        // }
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
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Update the score lines to be displayed
     * @param lines
     */
    @Override
    public void updateScoreLines(int lines){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Update the level to be displayed
     * @param level
     */
    @Override
    public void updateLevel(int level){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Update the held tetromino to be displayed
     * @param tetromino
     */
    @Override
    public void updateHeldTetromino(Tetromino tetromino){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * update the list of upcomming tetrominos to be displayed
     * @param tetrominos
     */
    @Override
    public void updateNextTetrominos(List<Tetromino> tetrominos){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * update the view
     */
    @Override
    public void update(){
        gridPanel.repaint();
        gameInfoPanel.repaint();
    }
}

