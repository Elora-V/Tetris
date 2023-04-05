package fr.upsaclay.bibs.tetris.view;

import fr.upsaclay.bibs.tetris.control.manager.ManagerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GameFrameImpl extends JFrame implements GameFrame,GameViewPanel {

    GamePanelImpl gamePanel; // au centre : la grille
    JPanel controlPanel; // à droite : les options de jeu
   
    



    // les versions du panel de controle
    JPanel initialPanel;
    JPanel playPanel;
    JPanel pausePanel;
    JPanel endPanel;
    //JPanel ;
    JFrame comandePanel;

    // boutons de bases
    ManagerButton startButton;
    ManagerButton pauseButton;
    ManagerButton resumeButton;
    ManagerButton quitButton;
    ManagerButton restartButton;
    ManagerButton quit2Button;
    ManagerButton restart2Button;
    ManagerButton comandeButton;
    ManagerButton saveScoreButton;
    
    // affichage pour l'enregistrement du score
    JTextField textArea;
    JTable tableauScore;
    String[][]tableau;
    

    ManagerButton music;

    public GameFrameImpl(String name) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Create the drawPanel (where we draw the grid)
        gamePanel = new GamePanelImpl();

        // Create the control panel
        controlPanel = new JPanel();


    }
    @Override
    public void initialize(){

        /////////////////  General initialization /////////////////
        gamePanel.initialize();
        add(gamePanel,BorderLayout.WEST);
        controlPanel.setPreferredSize(new Dimension(200, gamePanel.getPreferredSize().height));
        add(controlPanel,BorderLayout.EAST);


        ///////////////// Creation des éléments de management//////////////
        createManagerComponents();

        ///////////////// The initial panel /////////////////
        initialPanel = new JPanel();
        initialPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        initialPanel.add(startButton);
        
        controlPanel.add(initialPanel);

        /////////////////  The play panel (when the game is running) /////////////////
        playPanel = new JPanel();
        playPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        playPanel.add(pauseButton);
        playPanel.add(music);
        controlPanel.add(playPanel);


        /////////////////  The pause panel (when the game is on pause) /////////////////
        pausePanel = new JPanel();
        pausePanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        pausePanel.add(quitButton);
        pausePanel.add(resumeButton);
        pausePanel.add(restartButton);
        pausePanel.add(comandeButton);
        controlPanel.add(pausePanel);

        /////////////////  The end panel (when the game is over) /////////////////
        endPanel = new JPanel();
        endPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        
        textArea = new JTextField();
        textArea.setPreferredSize( new Dimension( controlPanel.getPreferredSize().width - 10, 25 ));
       
        //String[][]tableau = new String[100][4];
        //loadtableau score
        Object[][] tableau = {
        	      {"Cysboy", "1", "1", "1"},
        	      {"BZHHydde", "2", "1", "1"},
        	      {"IamBow", "2", "1", "3"},
        	      {"FunMan", "3", "1", "4"}
        	    };
        String  title[] = {"Pseudo", "score", "lines", "level"};
        tableauScore = new JTable(tableau, title);
        tableauScore.setBounds(290, 40, 300, 120);
        
        endPanel.add(restart2Button);
        endPanel.add(quit2Button);
        endPanel.add(tableauScore);
        endPanel.add(textArea);
        endPanel.add(saveScoreButton);
        controlPanel.add(endPanel);
        ////////////////// comandePanel qui rappel quelle touche utilisé pour jouer///////////////////
       // comandePanel = new JPanel();
        //comandePanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, controlPanel.getPreferredSize().height));
        comandePanel = new JFrame();//Remplacer JFrame par ta nouvelle fenetre
    	JButton btn1 = new JButton("A");  
        JButton btn2 = new JButton("Z");
        JButton btn3 = new JButton("E");
        JButton btn4 = new JButton("Q");
        JButton btn5 = new JButton("S");
        JButton btn6 = new JButton("D");
        JButton btn7 = new JButton("SPACE");
        
        
        
        //JButton btn1b = new JButton("ROT G");  
        ImageIcon RotDIcon = new ImageIcon("RotD.png");
        Image RotDImage = RotDIcon.getImage();
        Image smallRotDImage = RotDImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon smallRotDIcon = new ImageIcon(smallRotDImage);
        
        ImageIcon RotGIcon = new ImageIcon("RotG.png");
        Image RotGImage = RotGIcon.getImage();
        Image smallRotGImage = RotGImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon smallRotGIcon = new ImageIcon(smallRotGImage);
        
        ImageIcon GchIcon = new ImageIcon("gch.png");
        Image GchImage = GchIcon.getImage();
        Image smallGchImage = GchImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon smallGchIcon = new ImageIcon(smallGchImage);
        
        ImageIcon DrtIcon = new ImageIcon("drt.png");
        Image DrtImage = DrtIcon.getImage();
        Image smallDrtImage = DrtImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon smallDrtIcon = new ImageIcon(smallDrtImage);
        
        ImageIcon HIcon = new ImageIcon("hold.png");
        Image HImage = HIcon.getImage();
        Image smallHImage = HImage.getScaledInstance(100, 45, Image.SCALE_SMOOTH);
        ImageIcon smallHIcon = new ImageIcon(smallHImage);
        
        ImageIcon HDIcon = new ImageIcon("HD.png");
        Image HDImage = HDIcon.getImage();
        Image smallHDImage = HDImage.getScaledInstance(295, 90, Image.SCALE_SMOOTH);
        ImageIcon smallHDIcon = new ImageIcon(smallHDImage);
        
        ImageIcon SDIcon = new ImageIcon("SD.png");
        Image SDImage = SDIcon.getImage();
        Image smallSDImage = SDImage.getScaledInstance(100, 45, Image.SCALE_SMOOTH);
        ImageIcon smallSDIcon = new ImageIcon(smallSDImage);
        
        ManagerButton btn1b = new ManagerButton(smallRotGIcon);
        ManagerButton btn2b = new ManagerButton(smallHIcon);
        ManagerButton btn3b = new ManagerButton(smallRotDIcon);
        ManagerButton btn4b = new ManagerButton(smallGchIcon);
        ManagerButton btn5b = new ManagerButton(smallSDIcon);
        ManagerButton btn6b = new ManagerButton(smallDrtIcon);
        ManagerButton btn7b = new ManagerButton(smallHDIcon);
        
        
        //Spécifier la position et la taille du bouton    
        btn1.setBounds(90,30,50,50);  
        btn2.setBounds(150,30,50,50);
        btn3.setBounds(210,30,50,50);
        
        btn4.setBounds(100,90,50,50);  
        btn5.setBounds(160,90,50,50);
        btn6.setBounds(220,90,50,50);
        btn7.setBounds(90,150,180,50);
        
        btn1b.setBounds(30,230,100,50);  
        btn2b.setBounds(140,230,75,50);
        btn3b.setBounds(225,230,100,50);
        
        btn4b.setBounds(30,290,100,50);  
        btn5b.setBounds(140,290,75,50);
        btn6b.setBounds(225,290,100,50);
        btn7b.setBounds(30,350,295,50);
       
        
        //Spécifier la couleur d'arrière-plan du bouton
      //  btn1.setBackground(Color.WHITE);    
      //  btn2.setBackground(Color.RED); 
        //Ajouter les deux boutons au JPanel
       comandePanel.add(btn1); 
       comandePanel.add(btn2);
       comandePanel.add(btn3);
       comandePanel.add(btn4); 
       comandePanel.add(btn5);
       comandePanel.add(btn6);
       comandePanel.add(btn7);
       
       comandePanel.add(btn1b); 
       comandePanel.add(btn2b);
       comandePanel.add(btn3b);
       
       comandePanel.add(btn4b); 
       comandePanel.add(btn5b);
       comandePanel.add(btn6b);
       comandePanel.add(btn7b);
        //Ajouter le JPanel au JFrame
        //comandePanel.add(panel);
        comandePanel.setSize(350,500);  
        comandePanel.setLayout(null);  
       
        
        pack();
        drawManagementView();
        setVisible(true);
    }

    /**
     * Draw itself for the "management view" (before a game is started,
     * or in between games)
     *
     * (add management buttons, menus and stuff)
     */
    @Override
    public void drawManagementView(){
        initialPanel.setVisible(true);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(false);
        gamePanel.drawManagementView();
        update();
    }

    /**
     * Draw itself for the "game play view": the game is going on
     *
     * (remove management buttons, add needed buttons like "pause", draw
     * the actual game...)
     */
    @Override
    public void drawGamePlayView(){
        initialPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(false);
        playPanel.setVisible(true);
        gamePanel.drawGamePlayView();
        update();
    }

    /**
     * Draw itself for when the game is on pause
     */
    @Override
    public void drawGamePauseView(){
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(true);
        
        gamePanel.drawGamePauseView();
        update();
    }

    /**
     * Draw itself for when the game is over (show "Game over", adds a restart button, etc.)
     */
    @Override
    public void drawEndGameView(){
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        endPanel.setVisible(true);
        gamePanel.drawEndGameView();
        update();
    }
    /**
     * sauvegarde du score
     */
    public void saveScore(){
    	String content ;
   	 
		content= textArea.getText();
	  
		int  score = 1 ;
		int  lines = 2 ;
		int  level = 3 ;
		
		try {
		 File file = new File("save.txt");

		 // créer le fichier s'il n'existe pas
		 if (!file.exists()) {
		 file.createNewFile();
		 }

		 FileWriter fw = new FileWriter(file.getAbsoluteFile());
		 PrintWriter bw = new PrintWriter(fw);
		 bw.println("score");
		 
		 int i = 0;
		 int j = 0;
		 while(tableau[i][j]!=null) {
			 bw.println(tableau[i][j]);  
			 
			 j++;
		       if (j>3) {
		    	   i++;
		    	   j = 0;
		       }
		 }
		 bw.println(content);
		 bw.println(score);
		 bw.println(lines);
		 bw.println(level);  
		 bw.close();

		 System.out.println("save");
		} 
		catch (IOException e) {
		 e.printStackTrace();
		 }
		 
		
		 
		
    }
    /**
     * Return the panel handling the game action
     *
     * @return a GamePanel
     */
    @Override
    public GamePanelImpl getGamePanel(){
        return gamePanel;
    }

    /**
     * Creates all the manager components needed for game management
     */
    @Override
    public void createManagerComponents(){

        /////// Bouton 'classique'
        startButton=new ManagerButton("Start Game");
        startButton.setManagerAction(ManagerAction.START);

        ImageIcon pauseIcon = new ImageIcon("pause.png");
        Image pauseImage = pauseIcon.getImage();
        Image smallPauseImage = pauseImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallPauseIcon = new ImageIcon(smallPauseImage);
        pauseButton = new ManagerButton(smallPauseIcon);
        pauseButton.setManagerAction(ManagerAction.PAUSE);


        resumeButton=new ManagerButton("Resume Game");
        resumeButton.setManagerAction(ManagerAction.RESUME);
        quitButton=new ManagerButton("Quit Tetris");
        quitButton.setManagerAction(ManagerAction.QUIT);
        restartButton=new ManagerButton("Restart a new game");
        restartButton.setManagerAction(ManagerAction.RESTART);
        quit2Button=new ManagerButton("Quit Tetris");
        quit2Button.setManagerAction(ManagerAction.QUIT);
        restart2Button=new ManagerButton("Restart a new game");
        restart2Button.setManagerAction(ManagerAction.RESTART);
        comandeButton=new ManagerButton("Control");
        comandeButton.setManagerAction(ManagerAction.CONTROL);
        saveScoreButton=new ManagerButton("Savescore");
        saveScoreButton.setManagerAction(ManagerAction.SAVESCORE);

        ///// Bouton musique

        ImageIcon speakerIcon = new ImageIcon("speaker.png");
        Image speakerImage = speakerIcon.getImage();
        Image smallSpeakerImage = speakerImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallSpeakerIcon = new ImageIcon(smallSpeakerImage);
        music = new ManagerButton(smallSpeakerIcon);
        music.setManagerAction(ManagerAction.MUSIC);



        // quand on aura des options (fichier/random ?)
        // ManagerRadioButton r1=new ManagerRadioButton("option A");
        // ManagerRadioButton r2=new ManagerRadioButton("option B");
//        ButtonGroup bg=new ButtonGroup();
//        bg.add(r1);
//        bg.add(r2);

    }
    public void commandeview() {
    	
        comandePanel.setVisible(true);  
     
    }

    /**
     * All manager components are listened to by a single action listenner,
     * typically the game manager
     *
     * the action depends on which component was trigered
     * @param listener
     */
    @Override
    public void attachManagerActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        restartButton.addActionListener(listener);
        pauseButton.addActionListener(listener);
        resumeButton.addActionListener(listener);
        quitButton.addActionListener(listener);
        restart2Button.addActionListener(listener);
        quit2Button.addActionListener(listener);
        comandeButton.addActionListener(listener);
        music.addActionListener(listener);
        saveScoreButton.addActionListener(listener);
    }

    /** 
     * Add a key listener (for the keyboard actions) to the
     * frame
     *
     * (typically called when the player starts a game)
     *
     * @param listener
     */
    @Override
    public void startGameKeyListener(KeyListener listener){
        addKeyListener(listener);
        requestFocus(); //à ajouter si on voit que c'est necessaire
    }

    /**
     * Remove the sent listener from the frame
     *
     * (for example, at the end of a game)
     *
     * @param listener
     */
    @Override
    public void stopGameKeyListener(KeyListener listener){
        removeKeyListener(listener);
    }

    /**
     * Shows an alert error message on screen
     * @param message
     */
    @Override
    public void showErrorMessage(String message){
        throw new UnsupportedOperationException("Not implemented ");
    }

    /**
     * Puts back the default settings for a given manager action
     *
     * (for example for menus and radio buttons)
     *
     * @param action
     */
    @Override
    public void setDefaultSetting(ManagerAction action){
        throw new UnsupportedOperationException("Not implemented ");
    }
    /**
     * Updates the view
     */
    @Override
    public void update() {
        gamePanel.update();
    }


}
