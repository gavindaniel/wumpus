package views;
/**
 * @author Gavin Daniel
 */
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Wumpus;

public class TextView extends BorderPane implements Observer {

  private Wumpus theGame;
  private GridPane textPanel;
  private GridPane gp1;
  private GridPane gp2;
  private Button N_b;
  private Button W_b;
  private Button E_b;
  private Button S_b;
  private Label gameMessage;
  
  private TextArea gameDisplay;

  //constructor
  public TextView(Wumpus WumpusGame) {
    theGame = WumpusGame;
    textPanel = new GridPane();
    gp1 = new GridPane();
    gp2 = new GridPane();
    this.setCenter(textPanel);
    initializePane();
  }

  private void initializePane() {
	  N_b = new Button();
	  W_b = new Button();
	  E_b = new Button();
	  S_b = new Button();
	  gameMessage = new Label(theGame.getMessage());
	  gameDisplay = new TextArea(theGame.toString());
	  gameDisplay.setFont(new Font("Courier", 23));
	  gameDisplay.setEditable(false);
	  gp1.setPrefWidth(540.0);
	  gp1.setPrefHeight(340.0);
	  gp2.setPrefWidth(540.0);
	  gp2.setPrefHeight(150.0);
	  gameDisplay.setPrefHeight(340.0);
	  gameDisplay.setPrefWidth(540.0);
	  gameDisplay.setStyle("-fx-font-alignment: center");
	  gameMessage.setFont(new Font("Courier", 24));
	  
	  N_b.setText("N");
	  N_b.setFont(new Font("Courier", 14));
	  W_b.setText("W");
	  W_b.setFont(new Font("Courier", 14));
	  E_b.setText("E");
	  E_b.setFont(new Font("Courier", 14));
	  S_b.setText("S");
	  S_b.setFont(new Font("Courier", 14));
	  
	  
	  N_b.setMaxWidth(10.0);
	  W_b.setMaxWidth(10.0);
	  E_b.setMaxWidth(10.0);
	  S_b.setMaxWidth(10.0);
	  
	  ButtonListener handler = new ButtonListener();
	  N_b.setOnAction(handler);
	  W_b.setOnAction(handler);
	  E_b.setOnAction(handler);
	  S_b.setOnAction(handler);
	  
	  // (Node child, int col, int row)
	  GridPane.setConstraints(N_b, 1, 1);
	  GridPane.setConstraints(W_b, 0, 2);
	  GridPane.setConstraints(E_b, 2, 2);
	  GridPane.setConstraints(S_b, 1, 3);
	  GridPane.setConstraints(gameMessage, 4, 0);
	  gp1.getChildren().addAll(gameDisplay);
	  gp2.getChildren().addAll(N_b, W_b, E_b, S_b, gameMessage);
	  this.setTop(gp1);
	  this.setBottom(gp2);
  }

  @Override
  public void update(Observable o, Object arg) {
    theGame = (Wumpus) o;
    updateTextArea();
 
    gameMessage.setText(theGame.getMessage());

  }
  
  public void updateTextArea() {
	    gameDisplay.setText(theGame.toString());
	  }
  
  
  public class ButtonListener implements EventHandler<ActionEvent> {

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