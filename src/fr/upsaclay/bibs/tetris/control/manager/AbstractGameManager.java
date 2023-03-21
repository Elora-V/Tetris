package fr.upsaclay.bibs.tetris.control.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractGameManager implements GameManager, ActionListener {


    public abstract void actionPerformed(ActionEvent e);


}
