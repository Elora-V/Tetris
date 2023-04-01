package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TetrominoPanel extends JPanel {

    // ATTENTION inversion x et y !!
    Tetromino tet;
    int width;
    int height;
    String name;


    public TetrominoPanel(String name){
        this.name=name;
    }
    public void initialise(){
        setBackground(Color.white);
        TitledBorder title;
        title = BorderFactory.createTitledBorder(name);
        title.setTitleColor(Color.decode("#6c7687"));
        setBorder(title);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tet != null) {
            for (int i = 0; i < tet.getBoxSize(); i++) {
                for (int j = 0; j < tet.getBoxSize(); j++) {
                    TetrisCell cell = tet.cell(i, j);
                    if (cell != TetrisCell.EMPTY) {
                        Color colcell = GamePanelImpl.ReturnColorCase(cell);
                        int pixelsCellWidth = width / tet.getBoxSize();
                        int pixelsCellHeight = height / tet.getBoxSize();
                        g.fillRect(j * pixelsCellHeight ,i * pixelsCellWidth , pixelsCellWidth, pixelsCellHeight);
                        g.setColor(colcell);
                        g.drawRect(j * pixelsCellHeight , i * pixelsCellWidth, pixelsCellWidth, pixelsCellHeight);
                    }
                }
            }
        }
    }

    public void setTet(Tetromino tet){
        this.tet = tet;
    }

    public void setDim(int width,int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width,height));
    }


    // Definir deux autres classes
    // colorier et la faire apparaitre sur Jpanel
    // g.fillRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);

}

//                if (){
//                    g.setColor(ReturnColorCase(TetrominoShape.ISHAPE));
//                    g.fillRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
//                    g.setColor(ReturnColorCase(TetrominoShape.ISHAPE));
//                    g.drawRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
//                }
//            }
//        }



