package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGridView;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    TetrisGridView grid;
    int nblines;
    int nbcols;
    boolean projection=true;

    boolean pause=false;
    JLabel pauseLabel;
    boolean end=false;
    JLabel endLabel;

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

    public void initialise(){
        setBackground(Color.WHITE);
    }
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
    @Override
    public void paintComponent(Graphics g) {

        // ATTENTION : x et y à inverser !!

       super.paintComponent(g);

       /* for(int i = 0 ; i < grid.numberOfLines(); i++) {
            System.out.println(i);
            for (int j = 1; j < grid.numberOfCols(); j++) {

                TetrisCell cell = grid.visibleCell(i, j);
                if (cell != TetrisCell.EMPTY) {
                    // =Color.decode("#6c7687")
                    Color colcell; // couleur par default
                    if(!(pause || end)) {
                        colcell = GamePanelImpl.ReturnColorCase(cell); // si le jeu est pas en pause : vrai couleur
                        g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                        g.setColor(colcell);
                        g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    }
                }
            }
        }*/

        for(int i = 0 ; i < grid.numberOfLines(); i++) {
            for (int j = 0; j < grid.numberOfCols(); j++) {
                TetrisCell cell = grid.visibleCell(i,j);
                if(projection){
                    cell = grid.projectionVisibleCell(i,j);
                }

                Color colcell = Color.decode("#6c7687"); // couleur par default

                if (cell != TetrisCell.EMPTY) {
                    if (!(pause || end)) {
                        colcell = GamePanelImpl.ReturnColorCase(cell); // si le jeu est pas en pause : vrai couleur
                    }
                    g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.setColor(colcell);
                    g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.setColor(colcell);
                    g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                }

                if(pause){
                    g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.setColor(colcell);
                    g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.setColor(colcell);
                    g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                }
            }
        }

        if(pause){
            pauseLabel.setVisible(true);
            endLabel.setVisible(false);
        } else if (end) {
            pauseLabel.setVisible(false);
            endLabel.setVisible(true);
        }else{
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
