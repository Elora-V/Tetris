package fr.upsaclay.bibs.tetris.model.tetromino;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.List;

public class TetrominoProviderFromList implements TetrominoProvider {

    // Cette classe permet de crée un objet qui fourni des tetromino en suivant une liste donnée.

    //////////////////////  Element de classe ////////////////////////////////
    List<Tetromino> listTetromino;  // la liste contenant les tetromino à donner
    int maxTetromino; // le numéro du dernier tetromino de la liste
    int numeroTetromino; //le numéro du dernier tetromino fourni, appartient à [0 , maxTetromino], sinon vaut -1 (aucun tetromino sorti)


    //////////////////////  Constructeur  ////////////////////////////////
    public TetrominoProviderFromList(List<Tetromino> tetrominos){
        listTetromino= tetrominos;
        maxTetromino=listTetromino.size() -1;
        numeroTetromino=-1; // le premier tetromino (associé au numéro 0) n'a pas été tiré
    }

    //////////////////////  Actions  ////////////////////////////////
    /**
     * Return if the provider still has tetrominos to provide
     * A list tetromino provider says true until all the tetrominos
     * have been rendered
     *
     * @return true if the provider still has tetrominos to provide
     */
    @Override
    public boolean hasNext(){
        // vrai si le dernier numero de tetromino fourni est inférieur au numéro du dernier tetromino
        return numeroTetromino<maxTetromino ? true:false ;
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
            // on incremente le numero du dernier tétromino fourni
            numeroTetromino++;
            // et on le donne :
            return listTetromino.get(numeroTetromino);
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
        // si le suivant est dans la liste :
        if (numeroTetromino+n+1 <= maxTetromino){ // (on fait +1 car quand n=0 on veut pas l'actuel, mais le suivant)
            return listTetromino.get(numeroTetromino+n+1);
        }else {
            return  null;
        }
    }

}
