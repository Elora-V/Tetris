package fr.upsaclay.bibs.tetris.control.player;

import fr.upsaclay.bibs.tetris.model.grid.TetrisGrid;
import fr.upsaclay.bibs.tetris.model.score.ScoreComputer;
import fr.upsaclay.bibs.tetris.model.tetromino.TetrominoProvider;

public class GamePlayerVisual extends GamePlayerSimple {

    int delay; // in ms
    public GamePlayerVisual(TetrisGrid grid, ScoreComputer scoreComputer, TetrominoProvider provider,PlayerType type){

       super(grid, scoreComputer, provider,type);

    }
//communication game panel, calcul delay, score (recolter donnée pr score),debut-pause-fin,listener clavier,placer tetro,provider
    //cycle tetro,vitesse tetro

    public int getDelay(){
        return delay;
    }

    public void setDelay(int delay){
        this.delay=delay;
    }


}
