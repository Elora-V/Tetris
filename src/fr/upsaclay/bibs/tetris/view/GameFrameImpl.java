package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.ManagerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import java.util.Arrays;
import java.util.List;

public class GameFrameImpl extends JFrame implements GameFrame,GameViewPanel {

    GamePanel gamePanel; // au centre : la grille
    JPanel controlPanel; // à droite : les options de jeu



    // les versions du panel de controle
    JPanel initialPanel;
    JPanel playPanel;
    JPanel pausePanel;
    JPanel endPanel;

    // boutons de bases
    ManagerButton startButton;
    ManagerButton pauseButton;
    ManagerButton resumeButton;
    ManagerButton quitButton;
    ManagerButton restartButton;

    public GameFrameImpl(String name) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        // Create the drawPanel (where we draw the grid)
        gamePanel = new GamePanelImpl();

        // Create the control panel
        controlPanel = new JPanel();

        // Creating the parameter lists (a faire)


    }
    @Override
    public void initialize(){

        /////////////////  General initialization /////////////////
        gamePanel.initialize();

        //add( gamePanel, BorderLayout.CENTER );
        controlPanel.setPreferredSize(new Dimension(200, 700 ));
        add( controlPanel, BorderLayout.EAST);
            // height a def 'gamePanel.getPreferredSize().height )' quand on aura dimension game panel

        ///////////////// Creation des éléments de management//////////////
        createManagerComponents();

        ///////////////// The initial panel /////////////////
        initialPanel = new JPanel();
        initialPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        initialPanel.add(startButton);
        controlPanel.add(initialPanel);

        /////////////////  The play panel (when the game is running) /////////////////
        playPanel = new JPanel();
        playPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        playPanel.add(pauseButton);
        controlPanel.add(playPanel);

        /////////////////  The pause panel (when the game is on pause) /////////////////
        pausePanel = new JPanel();
        pausePanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        pausePanel.add(quitButton);
        pausePanel.add(resumeButton);
        pausePanel.add(restartButton);
        controlPanel.add(pausePanel);

        /////////////////  The end panel (when the game is over) /////////////////
        endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        endPanel.add(restartButton);
        endPanel.add(quitButton);
        controlPanel.add(endPanel);


        pack();
        drawManagementView();
        setVisible(true);
    }

    /**
     * Draw itself for the "management view" (before a game is started,
     * or in between games)
     *
     * (add management buttons, menus and stuff)
     */
    @Override
    public void drawManagementView(){
        //gamePanel.drawManagementView();
        initialPanel.setVisible(true);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(false);
    }

    /**
     * Draw itself for the "game play view": the game is going on
     *
     * (remove management buttons, add needed buttons like "pause", draw
     * the actual game...)
     */
    @Override
    public void drawGamePlayView(){
        initialPanel.setVisible(false);
        playPanel.setVisible(true);
        pausePanel.setVisible(false);
        endPanel.setVisible(false);
    }

    /**
     * Draw itself for when the game is on pause
     */
    @Override
    public void drawGamePauseView(){
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(true);
        endPanel.setVisible(false);
    }

    /**
     * Draw itself for when the game is over (show "Game over", adds a restart button, etc.)
     */
    @Override
    public void drawEndGameView(){
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(true);
    }
    /**
     * Return the panel handling the game action
     *
     * @return a GamePanel
     */
    @Override
    public GamePanel getGamePanel(){
        return gamePanel;
    }

    /**
     * Creates all the manager components needed for game management
     */
    @Override
    public void createManagerComponents(){

        /////// Bouton 'classique'
        startButton=new ManagerButton("Start Game");
        startButton.setManagerAction(ManagerAction.START);
        pauseButton=new ManagerButton("Pause");
        pauseButton.setManagerAction(ManagerAction.PAUSE);
        resumeButton=new ManagerButton("Resume Game");
        resumeButton.setManagerAction(ManagerAction.RESUME);
        quitButton=new ManagerButton("Quit Tetris");
        quitButton.setManagerAction(ManagerAction.QUIT);
        restartButton=new ManagerButton("Restart a new game");
        restartButton.setManagerAction(ManagerAction.RESTART);

        ///// Bouton radio

                // quand on aura des options (fichier/random ?)
        // ManagerRadioButton r1=new ManagerRadioButton("option A");
        // ManagerRadioButton r2=new ManagerRadioButton("option B");
//        ButtonGroup bg=new ButtonGroup();
//        bg.add(r1);
//        bg.add(r2);

    }

    /**
     * All manager components are listened to by a single action listenner,
     * typically the game manager
     *
     * the action depends on which component was trigered
     * @param listener
     */
    @Override
    public void attachManagerActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        restartButton.addActionListener(listener);
        pauseButton.addActionListener(listener);
        resumeButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    /**
     * Add a key listener (for the keyboard actions) to the
     * frame
     *
     * (typically called when the player starts a game)
     *
     * @param listener
     */
    @Override
    public void startGameKeyListener(KeyListener listener){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Remove the sent listener from the frame
     *
     * (for example, at the end of a game)
     *
     * @param listener
     */
    @Override
    public void stopGameKeyListener(KeyListener listener){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Shows an alert error message on screen
     * @param message
     */
    @Override
    public void showErrorMessage(String message){
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Puts back the default settings for a given manager action
     *
     * (for example for menus and radio buttons)
     *
     * @param action
     */
    @Override
    public void setDefaultSetting(ManagerAction action){
        throw new UnsupportedOperationException("Not implemented");
    }

}
