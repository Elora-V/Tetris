package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerVisual;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.view.GameFrameImpl;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class GameManagerVisual extends AbstractGameManager implements ActionListener{


    //ActionListener des boutons
    // cette classe utilise les méthodes defini dans la classe mère abstraite et y ajoute les éléments graphiques
    private GameFrameImpl view;

    private static Audio musicPlayer = new Audio(); // Création d'une variable musicPlayer pour la musique de fond
    public static boolean isQwertyLayout;


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
                musicPlayer.musicPlay(); // La musique est jouée automatiquement dès que le jeu commence
                view.drawGamePlayView();
                view.getGamePanel().startActionLoop();
                break;
            case RESUME:
                view.drawGamePlayView();
                view.getGamePanel().startActionLoop();
                break;

            case PAUSE:
                view.drawGamePauseView();
                view.getGamePanel().pauseActionLoop();
               
                break;
            case CONTROL:
                view.commandeview();
                break;
            case SAVESCORE:

                view.saveScore();
                view.drawManagementView();
                view.getGamePanel().pauseActionLoop();
                
                view.dispose();
                SwingUtilities.invokeLater(() -> GameManager.getGameManager(GameType.VISUAL).initialize());
                break;

            case RESTART:
                if(musicPlayer.musicRunning()){
                    musicPlayer.musicStop(); // Si la musique est en cours de lecture et que le joueur souhaite
                                             // redémarrer le jeu, la musique s'arrête.
                }
                view.drawManagementView();
                view.getGamePanel().pauseActionLoop();
                
                view.dispose();
                SwingUtilities.invokeLater(() -> GameManager.getGameManager(GameType.VISUAL).initialize());
                /*
                 * A ameliorer : on peut surement réussir à réinitialisé de la bonne façon pour que la loop reboot
                 */
                
                //super.loadNewGame(); // nouveau player
                //super.getPlayer().setView(view);  // on donne au player la vue
                //super.initialize(); // initialisation du player
                
                //view.getGamePanel().setGamePlayer(super.getPlayer()); // on donne le player à la vue
                
                
                break;

            case QUIT:
                view.getGamePanel().pauseActionLoop();
                view.setVisible(false);
                view.dispose();
                System.exit(1);
                break;
            case MUSIC:
                musicPlayer.musicPlay(); // La musique est jouée si le joueur le souhaite.
                break;
            case MUSICMUTE:
                System.out.println("gggg");
                musicPlayer.musicStop(); // La musique s'arrête si le joueur le souhaite.
                break;
            case QWERTY:
                isQwertyLayout = true;
                break;
            case AZERTY:
                isQwertyLayout = false;
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

    public static void stopMusic(){
        musicPlayer.musicStop(); // Une méthode statique utilisée pour arrêter la lecture de la musique
                                 // dans la classe GameManagerVisual
    }


    //class UpdateActionListener implements ActionListener {
    //public void actionPerformed(ActionEvent e) {
    //field.evolve();
    //view.update();
    //	}
   // }

}

