package fr.upsaclay.bibs.tetris.model.tetromino;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.List;

public class TetrominoProviderFromList implements TetrominoProvider {


    List<Tetromino> listTetromino;
    int maxTetromino; //le numéro du dernier tetromino
    int numeroTetromino; //le numéro du dernier tetromino fourni, appartient à [0 , maxTetromino], sinon vaut -1 (aucun tetromino sorti)


    public TetrominoProviderFromList(List<Tetromino> tetrominos){
        listTetromino= tetrominos;
        maxTetromino=listTetromino.size() -1;
        numeroTetromino=-1; // le premier tetromino (associé au numéro 0) n'a pas été tiré
    }


    /**
     * Return if the provider still has tetrominos to provide
     * A list tetromino provider says true until all the tetrominos
     * have been rendered
     *
     * @return true if the provider still has tetrominos to provide
     */
    @Override
    public boolean hasNext(){
        return numeroTetromino<maxTetromino ? true:false ; //vrai si le dernier numero de tetromino fourni est inférieur au numéro du dernier tetromnino
    }

    /**
     * Return the next tetromino on the provider
     * on a list tetromino provider, it can throw an exception
     * if all the tetrominos have been returned.
     *
     * @return a tetromino
     */
    @Override
    public Tetromino next(){
        if (this.hasNext()) { // si il existe un tetromino suivant:
            numeroTetromino++; // on incremente le numero du dernier tétromino fourni
            return listTetromino.get(numeroTetromino); // et on le donne
        } else{
            throw new ArrayIndexOutOfBoundsException("All tetrominos have been provided.");
        }
    }

    /**
     * Show the tetromino to come in n step
     * for n = 0 this is the next tetromino
     * for n = 1 this is the next next tetromino
     *
     * If n is bigger than what the provider has left to provide,
     * it returns null.
     *
     * @param n the number of next steps
     * @return the tetromino to be returned by next in n steps
     */
    @Override
    public Tetromino showNext(int n){
        if (numeroTetromino+n+1 <= maxTetromino){ // si dans la liste : (on fait +1 car quand n=0 on veut pas l'actuel, mais le suivant)
            return listTetromino.get(numeroTetromino+n+1);
        }else {
            return  null;
        }
    }

}
