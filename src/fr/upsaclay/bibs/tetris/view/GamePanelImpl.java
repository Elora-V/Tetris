
package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.model.grid.*;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoShape;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    int nbNextTet=3;
    JLabel labelScore;
    JLabel labelLevel;


    JPanel gameInfoPanel; // pour le score, le tétromino hold et les tetrominos suivant
    GridPanel gridPanel; // pour la grille

    // les sous-panel de gameInfoPanel :
    TetrominoPanel HoldTetroPanel;
    JPanel nextTetroPanel;
    List<TetrominoPanel> listNextPanel;
    JPanel scorePanel;

    Timer timer;
    public static final int INITIAL_DELAY=1000; // in ms
    public static final int MIN_DELAY=100;

    public GamePanelImpl() {

        gridPanel=new GridPanel(); // sous-panel pour la grille (droite)

        gameInfoPanel=new JPanel(); // sous panel avec le score et les tetrominos suivant (gauche)

        // les sous-panels de gameInfoPanel
        HoldTetroPanel=new TetrominoPanel("Held tetromino"); // sera dans nextTetroPanel en bas

        listNextPanel= new ArrayList<>();
        nextTetroPanel=new JPanel(); // sera en haut de nextTetroPanel

        scorePanel=new JPanel(); //sera au milieu de nextTetroPanel

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

        gameInfoPanel.setPreferredSize(new Dimension(400,gridPanel.getPreferredSize().height));
        gameInfoPanel.setLayout(null);

        ///// next tetromino ///////
        for (int i=0;i<nbNextTet;i++) {
            TetrominoPanel panelPourListe=new TetrominoPanel(Integer.valueOf(nbNextTet-i)+"° Next");
            panelPourListe.initialise();
            panelPourListe.setDim(gameInfoPanel.getPreferredSize().width/(nbNextTet+1),gameInfoPanel.getPreferredSize().width/(nbNextTet+1));
            listNextPanel.add(panelPourListe); // ajout de panel pour tetromino vide dans la liste
            nextTetroPanel.add(panelPourListe); // et sur le panneau
        }

        gameInfoPanel.add(nextTetroPanel,BorderLayout.NORTH);
        nextTetroPanel.setBounds(0,gameInfoPanel.getPreferredSize().height/8,gameInfoPanel.getPreferredSize().width,gameInfoPanel.getPreferredSize().width/(nbNextTet+1));


        ///// score ///////

        TitledBorder titleScore;
        titleScore = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#6c7687"))," Score ");
        titleScore.setTitleColor(Color.decode("#6c7687"));
        scorePanel.setBorder(titleScore);
        scorePanel.setPreferredSize(new Dimension(gameInfoPanel.getPreferredSize().width,gridPanel.getPreferredSize().height/6));
        scorePanel.setLayout(null);
                        ////// score label //////
        labelScore=new JLabel("Score  :  "+ String.valueOf(score));
        scorePanel.add(labelScore);
        labelScore.setForeground(Color.decode("#6c7687"));
        labelScore.setBounds(scorePanel.getPreferredSize().width/3, scorePanel.getPreferredSize().height/3,labelScore.getPreferredSize().width,labelScore.getPreferredSize().height);
                        ////// level label //////
        labelLevel=new JLabel("Level  :  "+ String.valueOf(level));
        scorePanel.add(labelLevel);
        labelLevel.setForeground(Color.decode("#6c7687"));
        labelLevel.setBounds(scorePanel.getPreferredSize().width/3, scorePanel.getPreferredSize().height*2/3,labelLevel.getPreferredSize().width,labelLevel.getPreferredSize().height);

        gameInfoPanel.add(scorePanel,BorderLayout.CENTER);
        scorePanel.setBounds(0,gameInfoPanel.getPreferredSize().height *3/8,scorePanel.getPreferredSize().width,scorePanel.getPreferredSize().height);

        ///// held tetromino ///////

        HoldTetroPanel.initialise();
        HoldTetroPanel.setDim(gridPanel.getPreferredSize().height/4,gridPanel.getPreferredSize().height/4);
        HoldTetroPanel.setTet(null);
        gameInfoPanel.add(HoldTetroPanel,BorderLayout.SOUTH);
        HoldTetroPanel.setBounds(gameInfoPanel.getPreferredSize().width/4,gameInfoPanel.getPreferredSize().height *5/8, HoldTetroPanel.getPreferredSize().width,HoldTetroPanel.getPreferredSize().height);

        //////////// Add panel ////////////

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
        gridPanel.setVisualPlay();
        update();
    }

    /**
     * Draw itself for when the game is on pause
     */
    @Override
    public void drawGamePauseView(){

        gameInfoPanel.setVisible(true);
        gridPanel.setVisible(true);
        gridPanel.setVisualPause();
        update();
    }

    /**
     * Draw itself for when the game is over (show "Game over", adds a restart button, etc.)
     */
    @Override
    public void drawEndGameView(){
        gameInfoPanel.setVisible(false);
        gridPanel.setVisible(true);
        gridPanel.setVisualEnd();
        update();
    }

    public static Color ReturnColorCase(TetrisCell cell) {
        switch (cell){
            case I:
                return Color.decode("#7cf4d1");
            case J:
                return Color.decode("#f4c77c");
            case L:
                return Color.decode("#fa9bb5");
            case S:
                return Color.decode("#b5fa9b");
            case O:
                return Color.decode("#f8fa9b");
            case Z:
                return  Color.decode("#e06767");
            case T:
                return Color.decode("#ed76eb");
            default:
                return Color.white;
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
        this.score=player.getScore();
        this.lines= player.getLineScore();
        this.level=player.getLevel();
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
        HoldTetroPanel.setTet(tetromino);
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
        for (int i=0;i<nbNextTet;i++){
            if(i<tetrominos.size()){
                TetrominoPanel panelList=listNextPanel.get(i); // on récupère le panneau
                if (i>tetrominos.size()){
                    panelList.setTet(null);
                }else {
                    panelList.setTet(tetrominos.get(nbNextTet-1-i)); // on lui donne sont nouveau tetromino
                                                                     // de tel sorte que le dernier panel de la liste
                                                                     // ait le suivant , et le premier panel le 'dernier' des suivant.
                                                                     // On lit les panneaux de  droite à gauche
                }
                listNextPanel.set(i, panelList); // on le remet dans la liste
            }
        }

    }

    /**
     * update the view
     */
    @Override
    public void update(){

        // mis à jour grille
        updateGrid(grid);

        // mis à jour tetromino suivant
        List<Tetromino> nextTetro=new ArrayList<Tetromino>();
        for (int i=0;i<nbNextTet;i++){
            nextTetro.add(provider.showNext(i));
        }
        updateNextTetrominos(nextTetro);

        // mis à jour holdtetromino
        updateHeldTetromino(player.getHeldTetromino());

        // mis à jour score

        labelScore.setText("Score :"+ String.valueOf(score));
        labelLevel.setText("Level :"+ String.valueOf(level));

        // repaint
        gridPanel.repaint();
        //nextTetroPanel.repaint();
        //HoldTetroPanel.repaint();
        gameInfoPanel.repaint();
    }
}

