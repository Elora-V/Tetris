package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayer;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerSimple;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerVisual;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;
import fr.upsaclay.bibs.tetris.view.GameFrame;
import fr.upsaclay.bibs.tetris.view.GameFrameImpl;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;

public class GameManagerVisual extends AbstractGameManager implements ActionListener{

    //ActionListener des boutons
    // cette classe utilise les méthodes defini dans la classe mère abstraite et y ajoute les éléments graphiques
    private GameFrameImpl view;

    private Clip currentClip; // Keeps in memory the state of the music

    public GameManagerVisual() {
        super.loadNewGame(); // creation du player
        view = new GameFrameImpl("--- Tetris Game ---"); // on donne au manager la fenetre view
        super.getPlayer().setView(view); // on donne au gameplayer un sous panel de view
    }
    /**
     * Initialize the game Manager
     *
     * Should set the necessary fields with their default values
     *
     * Note: the player is not created at initilization
     *
     * In visual mode, this is where the game frame can be launched
     */
    @Override
    public void initialize(){
        super.initialize(); // initialisation du player
        view.getGamePanel().setGamePlayer(super.getPlayer()); // on donne le modèle à la vue
        view.initialize(); // initialisation de la vue
        view.attachManagerActionListener(this); // ajout de listener à la vue

    }


    // Actions


    /**
     * Creates a player with the correct player type
     *
     * The specific class of the player will depend of the GameType: Simple or Visual
     *
     * If there is no implementation for player type in this game type, throws an
     * UnsupportedOperationException
     */
    @Override
    public void createPlayer(){
        if (super.getPlayerType()!=PlayerType.HUMAN) {
            throw new UnsupportedOperationException("playertype not implemented");
        }
        super.setGamePlayer( new GamePlayerVisual(TetrisGrid.getEmptyGrid(super.getNumberOfLines(), super.getNumberOfCols()), ScoreComputer.getScoreComputer(DEFAULT_MODE), super.getTetrominoProvider(), super.getPlayerType()));
        
    }

    public void loadPlayer(TetrisMode mode,TetrisGrid grid,int score, int level, int lines){ // mettre erreur ??
        try {
            super.setGamePlayer( new GamePlayerVisual(grid, ScoreComputer.getScoreComputer(mode, score, level, lines), super.getTetrominoProvider(), super.getPlayerType()));

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    public void actionPerformed(ActionEvent e) {
        ManagerComponent comp = (ManagerComponent) e.getSource(); // on récupère le boutton clické
        ManagerAction action = comp.getManagerAction(); // on récupère l'action
        super.actionPerformed(action); // action à réaliser côté 'logistique'         // block ici
        // puis ajoute les actions 'visuelles' :

        switch (action) {
            case START:
                view.drawGamePlayView();
                view.getGamePanel().startActionLoop();
                break;

            case PAUSE:
                view.drawGamePauseView();
                view.getGamePanel().pauseActionLoop();
                break;

            case RESUME:
                view.drawGamePlayView();
                view.getGamePanel().startActionLoop();
                break;

            case RESTART:
                if(currentClip!=null){
                    currentClip.stop();
                    currentClip = null;
                }
                view.getGamePanel().pauseActionLoop();
                view.drawManagementView();
                super.loadNewGame(); // nouveau player
                super.getPlayer().setView(view);  // on donne au player la vue
                super.initialize(); // initialisation du player
                view.getGamePanel().setGamePlayer(super.getPlayer()); // on donne le player à la vue
                break;

            case QUIT:
                view.getGamePanel().pauseActionLoop();
                view.setVisible(false);
                view.dispose();
                System.exit(1);
                break;
            case MUSIC:
                try {
                        if(currentClip!=null){
                            currentClip.stop();
                            currentClip = null;
                        }
                        else{
                            File file = new File("03. A-Type Music (Korobeiniki).wav");
                            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                            Clip newCLip = AudioSystem.getClip();
                            newCLip.open(audioStream);
                            System.out.println("TET");
                            newCLip.start();
                            currentClip = newCLip;
                        }
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                break;

            default:
                break;

        }
    }

    /**
     * starts the player (i.e. the actual game)
     */
    public void startPlayer() {
        super.getPlayer().start();
    }

    /**
     * pause the player 
     */
    public void pausePlayer(){
        super.getPlayer().pause();
    }


}
