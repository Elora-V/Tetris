package fr.upsaclay.bibs.tetris.model.tetromino;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TetrominoProviderRandom implements TetrominoProvider {

    //////////////////////  Elements de la classe  ////////////////////////////////

    List<Tetromino> listTetromino; // liste dynamique (comme une file) contenant les tetrominos
    // le premier de la liste est le suivant à donner
    List<TetrominoShape> fullListShape; // contient toutes les formes possibles
    List<TetrominoShape> listShape; // liste des formes de tetromino non donné
    // elle est réinitialisé avec fullListShape quand on a donné 1 tetromino de chaque forme

    Random random ; // element pour utilisé des fonctions aléatoires

    //////////////////////  Constructeur  ////////////////////////////////

    public TetrominoProviderRandom(){
        listTetromino=new ArrayList<Tetromino>();
        listShape=new ArrayList<TetrominoShape>();
        fullListShape=new ArrayList<TetrominoShape>();
        random=  new Random();

        // on doit remplir à la main la liste contenant toutes les formes,
        // en effet la convertion de array à liste ne permet pas d'enlever un élément de la liste par la suite
        for (int i=0;i< TetrominoShape.values().length;i++){
            fullListShape.add(TetrominoShape.values()[i]);
        }
    }

    //////////////////////  Actions  ////////////////////////////////


    // la méthode suivant est utilisé à la place de ramdomTetromino de TetrominoShape, cette version a de la mémoire
    public Tetromino semiRandomTetromino(){
        // si la liste de shape est vide :
        if(listShape.size()==0){
            // on la remplie :
            listShape.addAll(fullListShape);
        }
        // puis on pioche une shape au hasard :
        int i =random.nextInt(listShape.size());
        TetrominoShape shapeRand=listShape.get(i);
        // on l'enlève de la liste :
        listShape.remove(i);
        // on envoie un tetromino de la forme piochée
        return shapeRand.getTetromino(random.nextInt(shapeRand.getNumberOfRotations()));
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
            return semiRandomTetromino();
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
        // si le n-ième élément de la liste n'existe pas :
        if(listTetromino.size()<= n){
            // on ajoute à la file le nombre de tétromino nécessaire pour avoir un n-ieme tetromino :
            int nombreTetroAjout =n-(listTetromino.size()-1); //nombre de tetromino à ajouter
            for (int i=0; i< nombreTetroAjout ; i++){
                listTetromino.add(semiRandomTetromino());
            }
        }
        // on donne le n-ième tétromino de la liste :
        return listTetromino.get(n);
    }

}
