package fr.upsaclay.bibs.tetris.model.tetromino;
import java.util.ArrayList;
import java.util.List;

public class TetrominoProviderRandom implements TetrominoProvider {

    List<Tetromino> listTetromino; // liste dynamique (comme une file) contenant les tetrominos
    // le premier de la liste est le suivant à donner

    public TetrominoProviderRandom(){
        listTetromino=new ArrayList<>();
    }



    @Override
    public boolean hasNext(){
        return true;
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
        if(listTetromino.size()>=1){ // si la file n'est pas vide :
            Tetromino tetro=listTetromino.get(0); // on récupère le premier élément de la file
            listTetromino.remove(0); // on l'enlève de la file
            return tetro; // et on le donne
        } else{ // sinon on en renvoie un au hasard
            return TetrominoShape.randomTetromino();
        }
    }

    /**
     * Show the tetromino to come in n step
     * for n = 0 this is the next tetromino
     * for n = 1 this is the next next tetromino
     * @param n the number of next steps
     * @return the tetromino to be returned by next in n steps
     */
    @Override
    public Tetromino showNext(int n){
        if(listTetromino.size()<= n){ // si le n-ième élément de la liste n'existe pas :
            // on ajoute à la file le nombre de tétromino nécessaire pour avoir un n-ieme tetromino
            int nombreTetroAjout =n-(listTetromino.size()-1); //nombre de tetromino à ajouter
            for (int i=0; i< nombreTetroAjout ; i++){
                listTetromino.add(TetrominoShape.randomTetromino());
            }
        }
        return listTetromino.get(n); // on donne le n-ième tétromino de la liste
    }

}
