package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.ManagerAction;

import javax.swing.*;

public class ManagerRadioButton extends JRadioButton implements ManagerComponent {
    private ManagerAction action;

    public ManagerRadioButton(String text) {
        super(text);
    }
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
