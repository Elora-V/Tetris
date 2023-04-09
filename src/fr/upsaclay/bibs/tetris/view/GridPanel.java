package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    // Cette classe est un panneau représentant la grille de jeu.

    /////////////// Elements de classe //////////////
    TetrisGridView grid;  // la grille à dessiner (le modèle)

    // les dimensions de la grille :
    int nblines;
    int nbcols;

    // booleen d'etat du jeu
    boolean projection=true; // on met ou non la projection des tetrominos  en fonction de la veleur de ce booleen (faut l'enlever à la fin, sinon ça bug)
    boolean pause=false;
    boolean end=false;

    // textes :
    JLabel pauseLabel;
    JLabel endLabel;


    /////////////// Constructeur //////////////
    public GridPanel(){
        super();

        pauseLabel=new JLabel(" Game in pause ");
        pauseLabel.setForeground(Color.black);
        add(pauseLabel);
        pauseLabel.setVisible(false);

        endLabel=new JLabel(" Game Over ");
        pauseLabel.setForeground(Color.black);
        add(endLabel);
        endLabel.setVisible(false);
    }

    /////////////// Get et Set  //////////////

    public void setGrid(TetrisGridView grid){
        this.grid = grid;
    }

    public void setVisualPause(){
        pause=true;
        end=false;
    }
    public void setVisualPlay(){
        pause=false;
        end=false;
    }
    public void setVisualEnd(){
        pause=false;
        end=true;
    }

    public void setDim(int numligne,int numcol){
        this.nblines = numligne;
        this.nbcols = numcol;
        setPreferredSize(new Dimension(nbcols*GameFrame.PIXELS_PER_CELL,nblines*GameFrame.PIXELS_PER_CELL));
    }

    public void setProjection(boolean projection){
        this.projection=projection;
    }

    /////////////// Actions  //////////////

    public void initialise(){
        setBackground(Color.WHITE);

    }

    @Override
    public void paintComponent(Graphics g) {

        // ATTENTION : x et y à inverser !!

       super.paintComponent(g);

        TetrisCell cell;
        Color colcell;

        // on parcours la grille si le jeu n'est pas en pause:
        if(!pause) {
            for (int i = 0; i < grid.numberOfLines(); i++) {
                for (int j = 0; j < grid.numberOfCols(); j++) {

                    if (projection) {  // si on veut afficher la projection :
                        cell = grid.projectionVisibleCell(i, j); // on recupère la case tetris correspondante
                    } else { // sinon sans projection :
                        cell = grid.visibleCell(i, j);
                    }

                    if (cell != TetrisCell.EMPTY) {  // si la case n'est pas vide :

                        if (!(end || pause)) {
                            colcell = GamePanelImpl.ReturnColorCase(cell); // si le jeu n'est pas fini (et pas en pause) : on recupère la vrai couleur
                        } else {
                            colcell = Color.decode("#6c7687"); // sinon couleur par default
                        }
                        // on colorie le rectangle avec la bonne couleur:
                        g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                        g.setColor(colcell);
                        g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    }

                }
            }
        }

        if(pause){
            // si on est en pause : on colorie toutes nos cases en gris
            g.drawRect(0, 0, nbcols*GameFrame.PIXELS_PER_CELL,nblines* GameFrame.PIXELS_PER_CELL);
            g.setColor(Color.decode("#6c7687"));
            g.fillRect(0, 0, nbcols*GameFrame.PIXELS_PER_CELL, nblines*GameFrame.PIXELS_PER_CELL);
            // et on met un texte
            pauseLabel.setVisible(true);
            endLabel.setVisible(false);
        } else if (end) {
            // en fin de jeu on ajoute un texte (coloration dans la boucle ci-dessus)
            pauseLabel.setVisible(false);
            endLabel.setVisible(true);
        }else{
            // pas de label de pause ou game over pendant le jeu sinon :
            pauseLabel.setVisible(false);
            endLabel.setVisible(false);
        }

        // traits verticaux :
        for (int j = 0; j < grid.numberOfCols(); j++) {
            g.setColor(Color.lightGray);
            g.drawLine(j * GameFrame.PIXELS_PER_CELL, 0,j * GameFrame.PIXELS_PER_CELL , nblines*GameFrame.PIXELS_PER_CELL);

        }
        // traits horizontaux :
        for (int i = 0; i < grid.numberOfLines(); i++) {
            g.setColor(Color.lightGray);
            g.drawLine(0, i * GameFrame.PIXELS_PER_CELL,nbcols*GameFrame.PIXELS_PER_CELL , i * GameFrame.PIXELS_PER_CELL);
        }

    }
}
