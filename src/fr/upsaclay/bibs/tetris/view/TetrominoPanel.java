package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;

import javax.swing.*;
import java.awt.*;

public class TetrominoPanel extends JPanel {

    Tetromino tet;
    int width;
    int height;


    public TetrominoPanel(){
        super();
        setBackground(Color.white);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i =0 ; i < tet.getBoxSize(); i++) {
            for (int j = 0; j < tet.getBoxSize(); j++) {
                TetrisCell cell = tet.cell(i,j);
                if(cell!=TetrisCell.EMPTY){
                    Color colcell = GamePanelImpl.ReturnColorCase(cell);
                    int pixelsCellWidth = width/tet.getBoxSize();
                    int pixelsCellHeight =  height/tet.getBoxSize();
                    g.fillRect(i * pixelsCellWidth, j * pixelsCellHeight, pixelsCellWidth, pixelsCellHeight);
                    g.setColor(colcell);
                    g.drawRect(i * pixelsCellWidth, j * pixelsCellHeight, pixelsCellWidth, pixelsCellHeight);
                    // Definir deux autres classes
                    // colorier et la faire apparaitre sur Jpanel
                    // g.fillRect(i * GameFrame.PIXELS_PER_CELL, j * GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL, GameFrame.PIXELS_PER_CELL);
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



