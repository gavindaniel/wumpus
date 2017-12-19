package views;
/**
 * 
 * @author Gavin Daniel
 */
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Wumpus;


public class GraphicView extends BorderPane implements Observer {

  private Wumpus theGame;
  private Canvas canvas;
  private GraphicsContext gc;

  private Image ground;
  private Image player;
  private Image slime;
  private Image blood;
  private Image wumpus;
  private Image pit;
  private Image goop;
  
  private GridPane gp1;
  private GridPane gp2;
  
  private Button N_b;
  private Button W_b;
  private Button E_b;
  private Button S_b;
  private Label gameMessage;
  
  //constructor
  public GraphicView(Wumpus WumpusGame) {
    theGame = WumpusGame;
    canvas = new Canvas(540, 540);
    
    gp1 = new GridPane();
    gp2 = new GridPane();
    
    
    initializePane();
    
  }

  //initialize Pane for first time
  private void initializePane() {
	
	  gc = canvas.getGraphicsContext2D();
    ground = new Image("/images/Ground.png");
    player = new Image("/images/TheHunter.png");
    blood = new Image("/images/Blood.png");
    goop = new Image("/images/Goop.png");
    slime = new Image("/images/Slime.png");
    pit = new Image("/images/SlimePit.png");
    wumpus = new Image("/images/Wumpus.png");

    gameMessage = new Label(theGame.getMessage());
    gameMessage.setFont(new Font("Courier", 24));
    
	  N_b = new Button();
	  W_b = new Button();
	  E_b = new Button();
	  S_b = new Button();
	  
	  
	  gp1.setPrefWidth(540.0);
	  gp1.setPrefHeight(540.0);
	  gp2.setPrefWidth(540.0);
	  gp2.setPrefHeight(150.0);
	  
	  N_b.setText("N");
	  N_b.setFont(new Font("Courier", 14));
	  W_b.setText("W");
	  W_b.setFont(new Font("Courier", 14));
	  E_b.setText("E");
	  E_b.setFont(new Font("Courier", 14));
	  S_b.setText("S");
	  S_b.setFont(new Font("Courier", 14));
	  
	  
	  N_b.setMaxHeight(15.0);
	  N_b.setMaxWidth(15.0);
	  W_b.setMaxWidth(15.0);
	  W_b.setMaxHeight(15.0);
	  E_b.setMaxWidth(15.0);
	  E_b.setMaxHeight(15.0);
	  S_b.setMaxWidth(15.0);
	  S_b.setMaxHeight(15.0);
	  
	  ButtonListener handler = new ButtonListener();
	  N_b.setOnAction(handler);
	  W_b.setOnAction(handler);
	  E_b.setOnAction(handler);
	  S_b.setOnAction(handler);
	  
	  // (Node child, int col, int row)
	  GridPane.setConstraints(N_b, 1, 0);
	  GridPane.setConstraints(W_b, 0, 1);
	  GridPane.setConstraints(E_b, 2, 1);
	  GridPane.setConstraints(S_b, 1, 2);
	  GridPane.setConstraints(gameMessage, 4, 0);
	  
	  gp1.getChildren().addAll(canvas);
	  gp2.getChildren().addAll(N_b, W_b, E_b, S_b, gameMessage);
	  
	    this.setTop(gp1);
	    this.setBottom(gp2);


    updateImages();
  }

@Override
public void update(Observable o, Object arg) {

	gc.clearRect(0, 0, 600, 600);
	
	theGame = (Wumpus) o;
    updateImages();
    gameMessage.setText(theGame.getMessage());
    
}

public void updateImages() {
    char[][] gameBoard = theGame.getWumpusBoard();
    char[][] cavesVisited = theGame.getCavesVisited();
    
    for (int r = 0; r < 12; r++) {
      for (int c = 0; c < 12; c++) {
    	  // draw a blank image
    	  if (theGame.gameOver()){
        		  if ( gameBoard[r][c] == '_'){
        			  gc.setGlobalAlpha(0);
            		  gc.drawImage(ground, (c*45), (r*45));
        		  }
        		  else if (gameBoard[r][c] == 'W'){
        			  gc.setGlobalAlpha(100);
            		  gc.drawImage(wumpus, (c*45), (r*45));
        		  }
        		  else if (gameBoard[r][c] == 'B' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(blood, (c*45), (r*45));
            	  }
        		  else if (gameBoard[r][c] == 'P' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(pit, (c*45), (r*45));
            	  }
        		  else if (gameBoard[r][c] == 'S' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(slime, (c*45), (r*45));
            	  }
        		  else if (gameBoard[r][c] == 'G' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(goop, (c*45), (r*45));
            	  }
        	  
	        	  else if (cavesVisited[r][c] == 'C'){
	            		  gc.setGlobalAlpha(100);
	            		  gc.drawImage(player, (c*45), (r*45));
	        	  }
	    		  else {
	    			  gc.setGlobalAlpha(100);
	        		  gc.drawImage(ground, (c*45), (r*45));
	    		  }
    	  }
    	  else {
    		  if (cavesVisited[r][c] == 'Y'){
        		  if ( gameBoard[r][c] == '_'){
        			  gc.setGlobalAlpha(0);
            		  gc.drawImage(ground, (c*45), (r*45));
        		  }
        		  else if (gameBoard[r][c] == 'W'){
        			  gc.setGlobalAlpha(100);
            		  gc.drawImage(wumpus, (c*45), (r*45));
        		  }
        		  else if (gameBoard[r][c] == 'B' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(blood, (c*45), (r*45));
            	  }
        		  else if (gameBoard[r][c] == 'P' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(pit, (c*45), (r*45));
            	  }
        		  else if (gameBoard[r][c] == 'S' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(slime, (c*45), (r*45));
            	  }
        		  else if (gameBoard[r][c] == 'G' ){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(goop, (c*45), (r*45));
            	  }
        	  }
    		  else if (cavesVisited[r][c] == 'C'){
            		  gc.setGlobalAlpha(100);
            		  gc.drawImage(player, (c*45), (r*45));
        	  }
    		  else {
    			  gc.setGlobalAlpha(100);
        		  gc.drawImage(ground, (c*45), (r*45));
    		  }
    	  }
    	  
    	  
      }
    }
  }

private class ButtonListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {
    	
    	String button_text = arg0.getSource().toString();
    	if (!theGame.gameOver()){
	    	if (button_text.contains("'N'")){
	    		System.out.println("button: N");
	    		theGame.shootArrow('N');
	    	}
	    	if (button_text.contains("'W'")){
	    		System.out.println("button: W");
	    		theGame.shootArrow('W');
	    	}
	    	if (button_text.contains("'E'")){
	    		System.out.println("button: E");
	    		theGame.shootArrow('E');
	    	}
	    	if (button_text.contains("'S'")){
	    		System.out.println("button: S");
	    		theGame.shootArrow('S');
	    	}
    	}     
    } // end handle    
  }
}
