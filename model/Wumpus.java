package model;
/**
 * @author Gavin Daniel
 */
import java.util.Observable;
import java.util.Random;

public class Wumpus extends Observable {

	private char[][] board;
	private char[][] visited;
	private int size;
	private int playerLoc;
	private int wumpusLoc;
	private boolean wumpusAlive;
	private boolean playerAlive;
	private int numPits;

	private String clickMessage = "Make move";
	
	public Wumpus() {
		size = 12;
		startNewGame();
	}
	/**
	   * Start a new game and tell all observers to draw an new game
	   * with the string message startNewGame()
	   */
	  public void startNewGame() {
		clearBoard();
		randomizeWumpusLocation();
		generateBlood();
	    randomizePitLocations();
		randomizePlayerLocation();
	    // The state of this model just changed so tell any observer to update themselves
	    setChanged();
	    notifyObservers("startNewGame()");
	  }
	
	// Randomizes Player starting location
	private void randomizePlayerLocation() {
		Random random = new Random();
		playerLoc = random.nextInt(144);
		playerLoc = playerLoc + 1;
		
		while (checkBoard(playerLoc) != '_'){
			playerLoc = random.nextInt(144);
			playerLoc = playerLoc + 1;
		}
		moveCaves(-1,playerLoc);
		playerAlive = true;
	}
	
	// Randomizes Wumpus starting location
	private void randomizeWumpusLocation() {
		Random random = new Random();
		wumpusLoc = random.nextInt(144);
		wumpusLoc = wumpusLoc + 1;
		while (checkBoard(wumpusLoc) != '_'){
			wumpusLoc = random.nextInt(144);
			wumpusLoc = playerLoc + 1;
		}
		assignLetter(wumpusLoc, 'W');
		wumpusAlive = true;
	}
	
	// Randomizes Wumpus starting location
	private void randomizePitLocations() {
		Random random = new Random();
		int temp = -1;
		numPits = random.nextInt(3);
		 numPits = numPits + 3;
		 temp = random.nextInt(144);
		 temp = temp + 1;
		 for (int i = 0; i < numPits; i++){
			 while (checkBoard(temp) != '_'){
				 temp = random.nextInt(144);
				 temp = temp + 1; 
			 }
			 assignLetter(temp, 'P');
		 	 generateSlime(temp);
		 }
	 }
		
			
	// get game Message
	public String getMessage(){
		return clickMessage;
	}
			
			
	// checks board to see if spot is empty
	private char checkBoard(int Location){
		int counter = 0;
		for (int r = 0; r < 12; r++){
			for (int c = 0; c < 12; c++){
				counter++;
				if (Location == counter){
					return board[r][c];
				}
			}
		}
		return 'Z'; //error
	}
		
	// assigns leter to passed location on baord
	private void assignLetter(int Location, char Letter){
		int counter = 0;
		for (int r = 0; r < 12; r++){
			for (int c = 0; c < 12; c++){
				counter++;
				if (counter == Location){
					board[r][c] = Letter;
				}
			}
		}
	}
		
	// assigns letter to passed location on baord
	private void moveCaves(int prevLocation, int currLocation){
		int counter = 0;
		for (int r = 0; r < 12; r++){
			for (int c = 0; c < 12; c++){
				counter++;
				if (counter == currLocation){
					visited[r][c] = 'C';
				}
				else if (counter == prevLocation){
						visited[r][c] = 'Y';
				}
			}
		}
	}
		
	// check if the game is over
	public boolean gameOver() {
		return (!wumpusAlive || !playerAlive); // if wumpus is dead or the player is dead, game is over
	}
	
	
	// shoot arrow
	public void shootArrow(char direction) {
		int count = 0;
		
		for (int r = 0; r < size; r ++){
			for (int c = 0; c < size; c++){
				count++;
				if (count == playerLoc){
					for (int r2 = r; r2 >= 0; r2--){
						if (board[r2][c] == 'W'){
							wumpusAlive = false;
						}
					}
				}
				else if (direction == 'E'){
					for (int c2 = c; c2 < 12; c2++){
						if (board[r][c2] == 'W'){
							wumpusAlive = false;
						}
					}
				}
				else if (direction == 'W'){
					for (int c2 = c; c2 >= 0; c2--){
						if (board[r][c2] == 'W'){
							wumpusAlive = false;
						}
					}
				}
				else if (direction == 'S'){
					for (int r2 = r; r2 < 12; r2++){
						if (board[r2][c] == 'W'){
							wumpusAlive = false;	
						}
					}
				}
				if (!wumpusAlive){
					clickMessage = "The Wumpus has been shot!";
				}
			}
		}
		setChanged();
	    notifyObservers();
	}
				
	// Move Player 
	public void movePlayer(char direction) {
		int count = 0;
		char spot = 'z';
		int pos = 0;
		boolean flag = false;
	    for (int r = 0; r < size; r++) {
	      for (int c = 0; c < size; c++) {
	    	  count++;
	    	  if (count == playerLoc){
	    		  if (direction == 'U'){
	    			  if (r == 0){
	    				  spot = checkBoard(count+132);
	    				  pos = count+132;
	    			  }
	    			  else {
	    				  spot = checkBoard(count-12);
	    				  pos=count-12;
	    			  }
	    		  }
	    		  else if (direction == 'L'){
	    			  if (c == 0){
	    				  spot = checkBoard(count+11);
	    				  pos=count+11;
	    			  }
	    			  else {
	    				  spot = checkBoard(count-1);
	    				  pos=count-1;
	    			  }  
	    		  }
	    		  else if (direction == 'R'){
	    			  if (c == 11){
	    				  spot = checkBoard(count-11);
	    				  pos=count-11;
	    			  }
	    			  else {
	    				  spot = checkBoard(count+1);
	    				  pos=count+1;
	    			  }	    			  
	    		  }
	    		  else if (direction == 'D'){
	    			  if (r == 11){
	    				  spot = checkBoard(count-132);
	    				  pos=count-132;
	    			  }
	    			  else {
	    				  spot = checkBoard(count+12);
	    				  pos=count+12;
	    			  }  
	    		  }
	    		  
	    		  moveCaves(playerLoc
	    				  ,pos);
	    		  playerLoc = pos;
	    		  if (spot == '_'){
					  clickMessage = "Safe for now";
				  }
				  else if (spot == 'B'){
					  clickMessage = "I smell blood";
					  
				  }
				  else if (spot == 'G'){
					  clickMessage = "I don't even want to know what I just stepped in...";
				  }
				  else if (spot == 'S'){
					  clickMessage = "Eww I stepped in slime";
				  }
				  else if (spot == 'W'){
					  clickMessage = "You walked into the Wumpus Room";
					  playerAlive = false;
				  }
				  else if (spot == 'P'){
					  clickMessage = "You fell down a bottomless pit";
					  playerAlive = false;
				  }
	    		  flag = true;
	    	  }
	    	  if (flag){
	    		  break;
	    	  }
	      }
	      if (flag){
	    	  break;
	      }
	    }
	    setChanged();
	    notifyObservers();
	  }
		
	// Create Blood tiles based off Wumpus location
	private void generateBlood() {
		/*			  B
		 * 		    B B B
		 * 		  B B W B B
		 * 		    B B B
		 *			  B
		 */
		int count = 0;
		
	    for (int r = 0; r < size; r++) {
	      for (int c = 0; c < size; c++) {
	    	  count++;
	    	  if (count == wumpusLoc){
	    		  if ((r < 2 || r > 9) || (c < 2 || c > 9)){
    				  wrapBloodAround(r,c);
    			  }
	    		  if (r > 1){
	    			  if (checkBoard(count-24) == '_'){
		    			  assignLetter(count-24, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (r > 0 && c > 0){
	    			  if (checkBoard(count-13) == '_'){
		    			  assignLetter(count-13, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (r > 0){
	    			  if (checkBoard(count-12) == '_'){
		    			  assignLetter(count-12, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (r > 0 && c < 11){
	    			  if (checkBoard(count-11) == '_'){
		    			  assignLetter(count-11, 'B');	// 1st ROW		B
		    		  } 
	    		  }
	    		  if (c > 1){
	    			  if (checkBoard(count-2) == '_'){
		    			  assignLetter(count-2, 'B');	// 1st ROW		B
		    		  } 
	    		  }
	    		  if (c > 0){
	    			  if (checkBoard(count-1) == '_'){
		    			  assignLetter(count-1, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (c < 11){
	    			  if (checkBoard(count+1) == '_'){
		    			  assignLetter(count+1, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (c < 10){
	    			  if (checkBoard(count+2) == '_'){
		    			  assignLetter(count+2, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (r < 11 && c > 0){
	    			  if (checkBoard(count+11) == '_'){
		    			  assignLetter(count+11, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if (r < 11){
	    			  if (checkBoard(count+12) == '_'){
		    			  assignLetter(count+12, 'B');	// 1st ROW		B
		    		  } 
	    		  }
	    		  if (r < 11 && c < 11){
	    			  if (checkBoard(count+13) == '_'){
		    			  assignLetter(count+13, 'B');	// 1st ROW		B
		    		  }
	    		  }
	    		  if ( r < 10){
	    			  if (checkBoard(count+24) == '_'){
		    			  assignLetter(count+24, 'B');	// 1st ROW		B
		    		  } 
	    		  }
	    	  }
	      }
	    }  
	}
	
	
		
	// Create Slime tiles based off Pit location
		private void generateSlime(int pitLoc) {
			/*			  
			 * 		     S 
			 * 		   S P S 
			 * 		     S 
			 *			  
			 */
			
			int count = 0;
			
		    for (int r = 0; r < size; r++) {
		      for (int c = 0; c < size; c++) {
		    	  count++;
			    	  if (count == pitLoc){
			    		  if ((r < 1 || r > 10) || (c < 1 || c > 10)){
		    				  wrapSlimeAround(r,c);
		    			  }
			    		  if (r != 0){
			    			  if (checkBoard(count-12) == '_'){
			    				  assignLetter(count-12, 'S');
			    			  }  
			    			  else if (checkBoard(count-12) == 'B'){
			    				  assignLetter(count-12, 'G');
			    			  }
			    		  }
		    			  if (r != 11){
		    				  if (checkBoard(count+12) == '_'){
			    				  assignLetter(count+12, 'S');
			    			  }
			    			  else if (checkBoard(count+12) == 'B'){
			    				  assignLetter(count+12, 'G');
			    			  }
		    			  }
		    			  if (c != 0){
		    				  if (checkBoard(count-1) == '_'){
			    				  assignLetter(count-1, 'S');
			    			  }
			    			  else if (checkBoard(count-1) == 'B'){
			    				  assignLetter(count-1, 'G');
			    			  }
		    			  }
		    			  if (c != 11){
		    				  if (checkBoard(count+1) == '_'){
			    				  assignLetter(count+1, 'S');
			    			  }
			    			  else if (checkBoard(count+1) == 'B'){
			    				  assignLetter(count+1, 'G');
			    			  } 
		    			  }
			    	  }
		      }
		    }	
		}
		
	
	// Blank Board Constructor
	private void clearBoard() {
	    board = new char[size][size];
	    visited = new char[size][size];
	    for (int r = 0; r < size; r++) {
	      for (int c = 0; c < size; c++) {
	    		  board[r][c] = '_'; 
	    		  visited[r][c] = 'N';
	      }
	    }
	  }
	
	

	  /**
	   * Provide access to the selections '_' or 'O' or 'X' in a 2D array
	   * @return
	   */
	  public char[][] getWumpusBoard() {
	    return board;
	  }

	  /**
	   * Provide access to the selections '_' or 'O' or 'X' in a 2D array
	   * @return
	   */
	  public char[][] getCavesVisited() {
	    return visited;
	  }
	  
	  /**
	   * Proved a textual version of this tic tac toe board.
	   */
	  @Override
	  public String toString() {
	    String result = "";
	    for (int r = 0; r < size; r++) {
	      for (int c = 0; c < size; c++) {
	    	  if (gameOver()){
	    		  if (visited[r][c] == 'C'){
		    		  result += " H ";
		    	  }
	    		  else if (visited[r][c] == 'Y'){
	    			  if (board[r][c] == '_'){
		    			  result += "   ";
		    		  }
	    			  else {
		    			  result += " " + board[r][c] + " ";
		    		  }
		    	  }
	    		  else {
	    			  if (board[r][c] == '_'){
		    			  result += " _ ";
		    		  }
	    			  else {
		    			  result += " " + board[r][c] + " ";
		    		  }
	    		  }
	    		  
	    	  }
	    	  else {
	    		  if (visited[r][c] == 'Y'){
		    		  if (board[r][c] == '_'){
		    			  result += "   ";
		    		  }
		    		  else {
		    			  result += " " + board[r][c] + " ";
		    		  }
		    	  }
		    	  else if (visited[r][c] == 'C'){
		    		  result += " H ";
		    	  }
		    	  else {
		    		  result += " X ";
		    	  } 
	    	  }
	    	  
	      }
	      if (r < size-1)
	        result += "\n";
	    }
	    return result;
	  }

	// checks for Slime wrap-around 
			private void wrapSlimeAround(int r, int c) {
				
				// top-left corner (completed)
				int temp = -1;
				if (r < 2 && c < 2){
					if (r == 0 && c == 0){
						if (checkBoard(133) == '_'){
							assignLetter(133, 'S');
						}
						else if (checkBoard(133) == 'B'){
							assignLetter(133, 'G');
						}
						if (checkBoard(12) == '_'){
							assignLetter(12, 'S');
						}
						else if (checkBoard(12) == 'B'){
							assignLetter(12, 'G');
						}
					}
					if (r == 0 && c == 1){
						if (checkBoard(134) == '_'){
							assignLetter(134, 'S');
						}
						else if (checkBoard(134) == 'B'){
							assignLetter(134, 'G');
						}
					}
					if (r == 1 && c == 0){
						if (checkBoard(24) == '_'){
							assignLetter(24, 'S');
						}
						else if (checkBoard(24) == 'B'){
							assignLetter(24, 'G');
						}
					}
				}
				// top-right corner (completed)
				if (r < 2 && c > 9){
					if (r == 0 && c == 11){
						if (checkBoard(144) == '_'){
							assignLetter(144, 'S');
						}
						else if (checkBoard(144) == 'B'){
							assignLetter(144, 'G');
						}
						if (checkBoard(1) == '_'){
							assignLetter(1, 'S');
						}
						else if (checkBoard(1) == 'B'){
							assignLetter(1, 'G');
						}
					}
					if (r == 0 && c == 10){
						if (checkBoard(143) == '_'){
							assignLetter(143, 'S');
						}
						else if (checkBoard(143) == 'B'){
							assignLetter(143, 'G');
						}
					}
					if (r == 1 && c == 11){
						if (checkBoard(13) == '_'){
							assignLetter(13, 'S');
						}
						else if (checkBoard(1) == 'B'){
							assignLetter(13, 'G');
						}
					}
				}
				// bottom-left corner (completed)
				if (r > 9 && c < 2){
					if (r == 11 && c == 0){ //done
						if (checkBoard(1) == '_'){
							assignLetter(1, 'S');
						}
						else if (checkBoard(1) == 'B'){
							assignLetter(1, 'G');
						}
						if (checkBoard(144) == '_'){
							assignLetter(144, 'S');
						}
						else if (checkBoard(144) == 'B'){
							assignLetter(144, 'G');
						}
						if (checkBoard(12) == '_'){
							assignLetter(12, 'S');
						}
						else if (checkBoard(12) == 'B'){
							assignLetter(12, 'G');
						}
					}
					if (r == 11 && c == 1){ //done
						if (checkBoard(2) == '_'){
							assignLetter(2, 'S');
						}
						else if (checkBoard(2) == 'B'){
							assignLetter(2, 'G');
						}
					}
					if (r == 10 && c == 0){ //done
						if (checkBoard(132) == '_'){
							assignLetter(132, 'S');
						}
						else if (checkBoard(132) == 'B'){
							assignLetter(132, 'G');
						}
					}
				}
				// bottom-right corner (completed)
				if (r > 9 && c > 9){
					if (r == 11 && c == 11){ //done
						if (checkBoard(133) == '_'){
							assignLetter(133, 'S');
						}
						else if (checkBoard(133) == 'B'){
							assignLetter(133, 'G');
						}
						if (checkBoard(12) == '_'){
							assignLetter(12, 'S');
						}
						else if (checkBoard(12) == 'B'){
							assignLetter(12, 'G');
						}
					}
					if (r == 11 && c == 10){ //done 
						if (checkBoard(11) == '_'){
							assignLetter(11, 'S');
						}
						else if (checkBoard(11) == 'B'){
							assignLetter(11, 'G');
						}
					}
					if (r == 10 && c == 11){ //done
						if (checkBoard(121) == '_'){
							assignLetter(121, 'S');
						}
						else if (checkBoard(121) == 'B'){
							assignLetter(121, 'G');
						}
					}
				}
				// left-edge 
				temp = 12*(r)+c+1;
				
				if ((r > 0 && r < 11) && c == 0){
					if (checkBoard(temp+11) == '_'){
						assignLetter(temp+11, 'S');
					}
					else if (checkBoard(temp+11) == 'B'){
						assignLetter(temp+11, 'G');
					}
				}
				// top-edge
				if (r == 0 && (c > 1 && c < 11)){
					if (checkBoard(temp+132) == '_'){
						assignLetter(temp+132, 'S');
					}
					else if (checkBoard(temp+132) == 'B'){
						assignLetter(temp+132, 'G');
					}
				}
				// right edge
				if ((r > 0 && r < 11) && c == 11){
					if (checkBoard(temp-11) == '_'){
						assignLetter(temp-11, 'S');
					}
					else if (checkBoard(temp-11) == 'B'){
						assignLetter(temp-11, 'G');
					}
				}
				// bottom edge
				if (r == 11 && (c > 0 && c < 11)){
					if (checkBoard(temp-132) == '_'){
						assignLetter(temp-132, 'S');
					}
					else if (checkBoard(temp-132) == 'B'){
						assignLetter(temp-132, 'G');
					}
				}
			}
	  
	  
	// checks for Blood wrap-around 
		private void wrapBloodAround(int r, int c) {
			// top-left corner (completed)
			int temp = -1;
			if (r < 2 && c < 2){
				if (r == 0 && c == 0){
					assignLetter(121, 'B');	//	0
					assignLetter(144, 'B');	//	1
					assignLetter(133, 'B');	//	2
					assignLetter(134, 'B');	//	3
					assignLetter(11, 'B');	//	4
					assignLetter(12, 'B');	//	5
					assignLetter(24, 'B');	//	8
				}
				if (r == 0 && c == 1){
					assignLetter(122, 'B');	//	0
					assignLetter(133, 'B');	//	1
					assignLetter(134, 'B');	//	2
					assignLetter(135, 'B');	//	3
					assignLetter(12, 'B');	//	4
				}
				if (r == 1 && c == 0){
					assignLetter(133, 'B');	//	0
					assignLetter(12, 'B');	//	1
					assignLetter(23, 'B');	//	2
					assignLetter(24, 'B');	//	3
					assignLetter(36, 'B');	//	4
				}
				if (r == 1 && c == 1){
					assignLetter(134, 'B');	//	0
					assignLetter(24, 'B');	//	1
				}
			}
			// top-right corner (completed)
			if (r < 2 && c > 9){
				if (r == 0 && c == 11){
					assignLetter(132, 'B');	//	0
					assignLetter(143, 'B');	//	1
					assignLetter(144, 'B');	//	2
					assignLetter(133, 'B');	//	3
					assignLetter(1, 'B');	//	4
					assignLetter(2, 'B');	//	0
					assignLetter(13, 'B');	//	1
				}
				if (r == 0 && c == 10){
					assignLetter(131, 'B');	//	0
					assignLetter(142, 'B');	//	1
					assignLetter(143, 'B');	//	2
					assignLetter(144, 'B');	//	3
					assignLetter(1, 'B');	//	4
				}
				if (r == 1 && c == 11){
					assignLetter(144, 'B');	//	0
					assignLetter(1, 'B');	//	1
					assignLetter(13, 'B');	//	2
					assignLetter(14, 'B');	//	3
					assignLetter(25, 'B');	//	4
				}
				if (r == 1 && c == 10){
					assignLetter(143, 'B');	//	0
					assignLetter(13, 'B');	//	1
				}
			}
			// bottom-left corner (completed)
			if (r > 9 && c < 2){
				if (r == 11 && c == 0){ //done
					assignLetter(132, 'B');	//	0
					assignLetter(143, 'B');	//	1
					assignLetter(144, 'B');	//	2
					assignLetter(12, 'B');	//	3
					assignLetter(1, 'B');	//	4
					assignLetter(2, 'B');	//	0
					assignLetter(13, 'B');	//	1
				}
				if (r == 11 && c == 1){ //done
					assignLetter(144, 'B');	//	0
					assignLetter(1, 'B');	//	1
					assignLetter(2, 'B');	//	2
					assignLetter(3, 'B');	//	3
					assignLetter(14, 'B');	//	4
				}
				if (r == 10 && c == 0){ //done
					assignLetter(120, 'B');	//	0
					assignLetter(131, 'B');	//	1
					assignLetter(132, 'B');	//	2
					assignLetter(144, 'B');	//	3
					assignLetter(1, 'B');	//	4
				}
				if (r == 10 && c == 1){ //done
					assignLetter(132, 'B');	//	0
					assignLetter(2, 'B');	//	1
				}
			}
			// bottom-right corner (completed)
			if (r > 9 && c > 9){
				if (r == 11 && c == 11){ //done
					assignLetter(121, 'B');	//	0
					assignLetter(133, 'B');	//	1
					assignLetter(134, 'B');	//	2
					assignLetter(11, 'B');	//	3
					assignLetter(12, 'B');	//	4
					assignLetter(1, 'B');	//	0
					assignLetter(24, 'B');	//	1
				}
				if (r == 11 && c == 10){ //done
					assignLetter(133, 'B');	//	0
					assignLetter(10, 'B');	//	1
					assignLetter(11, 'B');	//	2
					assignLetter(12, 'B');	//	3
					assignLetter(23, 'B');	//	4
				}
				if (r == 10 && c == 11){ //done
					assignLetter(109, 'B');	//	0
					assignLetter(121, 'B');	//	1
					assignLetter(122, 'B');	//	2
					assignLetter(133, 'B');	//	3
					assignLetter(12, 'B');	//	4
				}
				if (r == 10 && c == 10){
					assignLetter(121, 'B');	//	0
					assignLetter(11, 'B');	//	1
				}
			}

			temp = 12*(r)+c+1;
			// left-edge 
			if ((r > 1 && r < 10) && c < 2){
				if (c == 0){
					assignLetter(temp+10, 'B');
					assignLetter(temp+11, 'B');
					assignLetter(temp-1, 'B');
					assignLetter(temp+23, 'B');
				}
				else if (c == 1){
					assignLetter(temp+10, 'B');
				}
			}
			// top-edge
			if (r < 2 && (c > 1 && c < 10)){
				if (r == 0){
					assignLetter(temp+133, 'B');
					assignLetter(temp+134, 'B');
					assignLetter(temp+132, 'B');
					assignLetter(temp+121, 'B');
				}
				else if (r == 1){
					assignLetter(temp+121, 'B');
				}
			}
			// right edge
			if ((r > 1 && r < 10) && c > 9){
				if (c == 11){
					assignLetter(temp-23, 'B');
					assignLetter(temp-11, 'B');
					assignLetter(temp-10, 'B');
					assignLetter(temp+1, 'B');
				}
				else if (c == 10){
					assignLetter(temp-10, 'B');
				}
			}
			// bottom edge
			if (r > 9 && (c > 1 && c < 10)){
				if (r == 11){
					assignLetter(temp-133, 'B');
					assignLetter(temp-134, 'B');
					assignLetter(temp-132, 'B');
					assignLetter(temp-121, 'B');
				}
				else if (r == 10){
					assignLetter(temp-121, 'B');
				}
			}
		}

}
