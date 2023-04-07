package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.model.grid.TetrisCell;
import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.tetromino.Tetromino;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TetrominoPanel extends JPanel {

    // Cette classe est un panneau représentant un tetromino.

    // ATTENTION inversion x et y !!

    //////////// Elements de la classe ////////////////

    Tetromino tet; // un tetromino (le modèle)

    // les dimensions du tetromino
    int width;
    int height;

    // le nom du tetromino
    String name;

    //////////// Constructeur ////////////////
    public TetrominoPanel(String name) {
        this.name = name;
    }


    //////////// Get et set ////////////////

    public void setTet(Tetromino tet) {
        this.tet = tet;
    }

    public void setDim(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    //////////// Actions ////////////////
    public void initialise() {
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
            // on parcours les cases du tetromino :
            for (int i = 0; i < tet.getBoxSize(); i++) {
                for (int j = 0; j < tet.getBoxSize(); j++) {
                    TetrisCell cell = tet.cell(i, j);
                    if (cell != TetrisCell.EMPTY) {
                        Color colcell = GamePanelImpl.ReturnColorCase(cell); // on récupère la couleur de la case
                        Color border = Color.lightGray;
                        // on calcul le nombre de pixel associé à une case en fonction de la dimension du panneau et du tetromino
                        int pixelsCellWidth = width / tet.getBoxSize();
                        int pixelsCellHeight = height / tet.getBoxSize();
                        // on colorie et dessine une bordure à la case
                        g.setColor(colcell);
                        g.fillRect(j * pixelsCellHeight, i * pixelsCellWidth, pixelsCellWidth, pixelsCellHeight);
                        g.setColor(border);
                        g.drawRect(j * pixelsCellHeight, i * pixelsCellWidth, pixelsCellWidth, pixelsCellHeight);
                    }
                }
            }
        }
    }


}
