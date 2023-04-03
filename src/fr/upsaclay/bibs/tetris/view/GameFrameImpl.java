package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManagerVisual;
import fr.upsaclay.bibs.tetris.control.manager.ManagerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import java.util.Arrays;
import java.util.List;

public class GameFrameImpl extends JFrame implements GameFrame,GameViewPanel {

    GamePanelImpl gamePanel; // au centre : la grille
    JPanel controlPanel; // à droite : les options de jeu


    // les versions du panel de controle
    JPanel initialPanel;
    JPanel playPanel;
    JPanel pausePanel;
    JPanel endPanel;
    JPanel comandePanel;

    // boutons de bases
    ManagerButton startButton;

    ManagerButton keyboardButton;
    ManagerButton pauseButton;
    ManagerButton resumeButton;
    ManagerButton quitButton;
    ManagerButton restartButton;
    ManagerButton quit2Button;
    ManagerButton restart2Button;
    ManagerButton comandeButton;

    ManagerButton music;

    ManagerButton musicmute;

    ManagerButton qwerty;

    public GameFrameImpl(String name) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Create the drawPanel (where we draw the grid)
        gamePanel = new GamePanelImpl();

        // Create the control panel
        controlPanel = new JPanel();


    }
    @Override
    public void initialize(){

        /////////////////  General initialization /////////////////
        gamePanel.initialize();
        add(gamePanel,BorderLayout.WEST);
        controlPanel.setPreferredSize(new Dimension(200, gamePanel.getPreferredSize().height));
        add(controlPanel,BorderLayout.EAST);


        ///////////////// Creation des éléments de management//////////////
        createManagerComponents();

        ///////////////// The initial panel /////////////////
        initialPanel = new JPanel();
        initialPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        initialPanel.add(startButton);
        initialPanel.add(qwerty);
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
        pausePanel.add(music);
        pausePanel.add(musicmute);

        // pausePanel.add(comandeButton);
        controlPanel.add(pausePanel);

        /////////////////  The end panel (when the game is over) /////////////////
        endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        endPanel.add(restart2Button);
        endPanel.add(quit2Button);
        controlPanel.add(endPanel);
        ////////////////// comandePanel qui rappel quelle touche utilisé pour jouer///////////////////
        comandePanel = new JPanel();
        comandePanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        //comandePanel.add(quitButton);
        //comandePanel.add(resumeButton);
        //comandePanel.add(restartButton);
        //comandePanel.add(pausePanel);
        
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
        initialPanel.setVisible(true);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(false);
        gamePanel.drawManagementView();
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
        initialPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(false);
        playPanel.setVisible(true);
        gamePanel.drawGamePlayView();
        update();
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
        gamePanel.drawGamePauseView();
        update();
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
        gamePanel.drawEndGameView();
        update();
    }
    /**
     * Return the panel handling the game action
     *
     * @return a GamePanel
     */
    @Override
    public GamePanelImpl getGamePanel(){
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
        keyboardButton=new ManagerButton("QWERTY");
        keyboardButton.setManagerAction(ManagerAction.CHANGE);

        ImageIcon pauseIcon = new ImageIcon("pause.png");
        Image pauseImage = pauseIcon.getImage();
        Image smallPauseImage = pauseImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallPauseIcon = new ImageIcon(smallPauseImage);
        pauseButton = new ManagerButton(smallPauseIcon);
        pauseButton.setManagerAction(ManagerAction.PAUSE);


        resumeButton=new ManagerButton("Resume Game");
        resumeButton.setManagerAction(ManagerAction.RESUME);
        quitButton=new ManagerButton("Quit Tetris");
        quitButton.setManagerAction(ManagerAction.QUIT);
        restartButton=new ManagerButton("Restart a new game");
        restartButton.setManagerAction(ManagerAction.RESTART);
        quit2Button=new ManagerButton("Quit Tetris");
        quit2Button.setManagerAction(ManagerAction.QUIT);
        restart2Button=new ManagerButton("Restart a new game");
        restart2Button.setManagerAction(ManagerAction.RESTART);
        comandeButton=new ManagerButton("Control");

        ///// Bouton musique

        ImageIcon speakerIcon = new ImageIcon("speaker.png");
        Image speakerImage = speakerIcon.getImage();
        Image smallSpeakerImage = speakerImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallSpeakerIcon = new ImageIcon(smallSpeakerImage);
        music = new ManagerButton(smallSpeakerIcon);
        music.setManagerAction(ManagerAction.MUSIC);

        ImageIcon muteIcon = new ImageIcon("mute.png");
        Image muteImage = muteIcon.getImage();
        Image smallMuteImage = muteImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallMuteIcon = new ImageIcon(smallMuteImage);
        musicmute = new ManagerButton(smallMuteIcon);
        musicmute.setManagerAction(ManagerAction.MUSICMUTE);

        ///// QWERTY

        qwerty=new ManagerButton("qwerty");
        qwerty.setManagerAction(ManagerAction.QWERTY);



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
        restart2Button.addActionListener(listener);
        quit2Button.addActionListener(listener);
        music.addActionListener(listener);
        musicmute.addActionListener(listener);
        qwerty.addActionListener(listener);
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
        addKeyListener(listener);
        requestFocus(); //à ajouter si on voit que c'est necessaire
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
        removeKeyListener(listener);
    }

    /**
     * Shows an alert error message on screen
     * @param message
     */
    @Override
    public void showErrorMessage(String message){
        throw new UnsupportedOperationException("Not implemented ");
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
        throw new UnsupportedOperationException("Not implemented ");
    }
    /**
     * Updates the view
     */
    @Override
    public void update() {
        gamePanel.update();
    }


}
