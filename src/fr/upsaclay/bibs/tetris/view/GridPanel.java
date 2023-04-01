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

    public GridPanel(){
        super();

    }

    public void initialise(){
        setBackground(Color.WHITE);
    }
    public void setGrid(TetrisGridView grid){
        this.grid = grid;
    }

    public void setDim(int numligne,int numcol){
        this.nblines = numligne;
        this.nbcols = numcol;
        setPreferredSize(new Dimension(nbcols*GameFrame.PIXELS_PER_CELL,nblines*GameFrame.PIXELS_PER_CELL));
    }
    @Override
    public void paintComponent(Graphics g) {

        // ATTENTION : x et y à inverser !!

       super.paintComponent(g);

        for(int i =0 ; i < grid.numberOfLines(); i++) {
            for (int j = 0; j < grid.numberOfCols(); j++) {
                TetrisCell cell=grid.visibleCell(i, j);
                if(cell!=TetrisCell.EMPTY) {
                    Color colcell = GamePanelImpl.ReturnColorCase(cell);
                    g.fillRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                    g.setColor(colcell);
                    g.drawRect(j * GameFrame.PIXELS_PER_CELL, i * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                }
            }
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
