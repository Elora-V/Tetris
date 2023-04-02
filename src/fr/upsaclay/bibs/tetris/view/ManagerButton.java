package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.TetrisAction;
import fr.upsaclay.bibs.tetris.control.manager.ManagerAction;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
*  
* c'est une extension de JButton qui implemente ManagerCompoment
* et qui communique avec le controleur en utilisant Swing
*
*/
public class ManagerButton extends JButton implements ManagerComponent  {

    private ManagerAction action;

    public ManagerButton(String text) {
        super(text);
        setForeground(Color.DARK_GRAY);
        setBackground(Color.WHITE);
    }

    public ManagerButton(ImageIcon image){
        super(image);
        setForeground(Color.DARK_GRAY);
        setBackground(Color.WHITE);
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
