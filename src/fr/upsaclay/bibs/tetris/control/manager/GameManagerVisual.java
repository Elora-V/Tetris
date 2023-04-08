package fr.upsaclay.bibs.tetris.control.manager;

import fr.upsaclay.bibs.tetris.TetrisMode;
import fr.upsaclay.bibs.tetris.control.player.GamePlayerVisual;
import fr.upsaclay.bibs.tetris.control.player.PlayerType;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.view.Audio;
import fr.upsaclay.bibs.tetris.view.GameFrameImpl;
import fr.upsaclay.bibs.tetris.view.ManagerComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class GameManagerVisual extends AbstractGameManager implements ActionListener{

    // Cette classe hérite de la classe abstraite du gameManager, elle gère donc le lancement d'une partie de jeu.
    // La différence avec gameManagerSimple est qu'elle gère en plus le côté visuel du jeu.
    // La gestion logistique se fait en appelant les méthodes de la classe mère (qui sont donc aussi les méthodes du mode SIMPLE),
    // et on y ajoute la gestion visuelle.

    ///////////////////////// Elements de classe /////////////////

    // le modèle est dans la classe mère

    // la vue :
    private GameFrameImpl view; // fenêtre graphique

    // options :
    private static Audio musicPlayer = new Audio(); // Création d'une variable musicPlayer pour la musique de fond
    public static boolean isQwertyLayout;  // type de clavier (azerty/qwerty ?)


    //////////////////// Construteur /////////////////////


    public GameManagerVisual() {
        super.loadNewGame(); // creation du player (par default)
        view = new GameFrameImpl("--- Tetris Game ---"); // on crée la fenetre du jeu (la vue)
        super.getPlayer().setView(view); // on donne aussi au gameplayer la vue
    }


    ///////////////////// Actions ////////////////////////////


    ///////////// Creation du player et initialisation :
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
        // On donne à la classe mère une instance de player avec les informations de la classes mère (type de provider ...).
        // Tous est mis par default.
        super.setGamePlayer( new GamePlayerVisual(TetrisGrid.getEmptyGrid(super.getNumberOfLines(), super.getNumberOfCols()), ScoreComputer.getScoreComputer(DEFAULT_MODE), super.getTetrominoProvider(), super.getPlayerType()));
        
    }

    public void loadPlayer(TetrisMode mode,TetrisGrid grid,int score, int level, int lines){
        try {
            // On donne à la classe mère un player crée à partir d'un fichier de sauvegarde,
            // les informations du player sont en arguments.
            super.setGamePlayer( new GamePlayerVisual(grid, ScoreComputer.getScoreComputer(mode, score, level, lines), super.getTetrominoProvider(), super.getPlayerType()));

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
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
        super.initialize(); // initialisation du player : elle indique le listener du timer et met le delai de celui-ci avec sa valeur par default
        view.getGamePanel().setGamePlayer(super.getPlayer()); // on donne le modèle à la vue
        view.initialize(); // initialisation de la vue (création et agencement des elements graphique comme les panneaux et boutons)
        view.attachManagerActionListener(this); // ajout du listener à la vue pour les boutons start/stop (gestion de lancement de jeu)

    }


    /////////// Actions du manager VISUAL via les boutons :

    public static void stopMusic(){
        musicPlayer.musicStop(); // Une méthode statique utilisée pour arrêter la lecture de la musique
        // dans la classe GameManagerVisual
    }

    public void actionPerformed(ActionEvent e) {
        ManagerComponent comp = (ManagerComponent) e.getSource(); // on récupère le boutton clické
        ManagerAction action = comp.getManagerAction(); // on récupère l'action
        super.actionPerformed(action); // action à réaliser côté 'logistique' (voir la classe mère)

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
                // Nous avons remis la ligne du main qui lance le jeu, il y avait probablement un meilleur moyen de relancer le jeu sans tous relancer,
                // mais nous étions un peu perdu et nous avions des bug quand on essayait de remettre seulement les commandes qui relancaient ce qui été necessaire.
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

}

