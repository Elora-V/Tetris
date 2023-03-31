package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
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
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(nbcols*GameFrame.PIXELS_PER_CELL,nblines*GameFrame.PIXELS_PER_CELL));
    }

    public void setGrid(TetrisGridView grid){
        this.grid = grid;
    }

    public void setDim(int numligne,int numcol){
        this.nblines = numligne;
        this.nbcols = numcol;
    }
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);
        for(int i =0 ; i < grid.numberOfLines(); i++) {
            for (int j = 0; j < grid.numberOfCols(); j++) {
                // Definir deux autres classes
                // colorier et la faire apparaitre sur Jpanel
                // g.fillRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);

                Color colcell = GamePanelImpl.ReturnColorCase(grid.visibleCell(i, j));
                g.fillRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
                g.setColor(colcell);
                g.drawRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
            }
        }
    }
}
