package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.ManagerAction;

import javax.swing.*;
/*
 * JRadioButton pour créer un bouton radio. Le bouton radio est utilisé pour sélectionner une option parmi plusieurs options.
 * peut etre interesant pour la selection du mode de jeu , du joueur(human ou AI)
 */
public class ManagerRadioButton extends JRadioButton implements ManagerComponent {

    // Cette classe représente les boutons de reglages en cas d'option (par exemple pour le choix du clavier dans notre cas).

    ////// Element de la classe //////////
    private ManagerAction action; // une action

    ////// Constructeur //////////

    public ManagerRadioButton(String text) {
        super(text);
    }

    ////// Get et Set //////////

    /**
     * Set the Manager action attached to the component
     * @param action
     */
    @Override
    public void setManagerAction(ManagerAction action){
        this.action =action;
    }

    /**
     * Get the Manager action attached to the component
     * @return action
     */
    @Override
    public ManagerAction getManagerAction(){
        return action;
    }
}
