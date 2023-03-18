package fr.upsaclay.bibs.tetris;


import javax.swing.SwingUtilities;

import fr.upsaclay.bibs.tetris.control.manager.GameManager;
import fr.upsaclay.bibs.tetris.control.manager.GameType;

/**
 * The main class for launching the game
 * 
 * @author Viviane Pons
 *
 */

public class Tetris {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ...().initialize()); // indiquer la classe du controller qui lance le jeu
		
	}

}
