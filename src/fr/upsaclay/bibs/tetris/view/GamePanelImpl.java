package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GamePanelImpl extends JPanel implements GamePanel {

    int nblines;
    int nbcols;
    Timer timer;

    public GamePanelImpl() {
        super();
        setPreferredSize(new Dimension(700,700)); // a changer
        // Create the loop timer
        timer = new Timer(1, null);
    }

    /**
     * Run all needed initializations
     */
    @Override
    public void initialize(){
        setBackground(Color.WHITE);
        // et ajout de ligne verticale?
    }

    /**
     * Draw itself for the "management view" (before a game is started,
     * or in between games)
     *
     * (add management buttons, menus and stuff)
     */
    @Override
    public void drawManagementView(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Draw itself for the "game play view": the game is going on
     *
     * (remove management buttons, add needed buttons like "pause", draw
     * the actual game...)
     */
    @Override
    public void drawGamePlayView(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Draw itself for when the game is on pause
     */
    @Override
    public void drawGamePauseView(){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Draw itself for when the game is over (show "Game over", adds a restart button, etc.)
     */
    @Override
    public void drawEndGameView(){
        throw new UnsupportedOperationException("Not implemented");
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
        throw new UnsupportedOperationException("Not implemented");
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
        throw new UnsupportedOperationException("Not implemented");
    }
}
