import java.util.Scanner;
import java.lang.Math.*;

public class Main{

	private final static int WIDTH = 7, HEIGHT = 6;
	private final static int max = HEIGHT-1, min = 1;
	private final static char empty = '.';
	private final static int err = -1;
	
	private static String winMessage = null;
	
	private static int row = 0, column = 0;
	private static char token='x';  
	private static int turn = 1;

	public static char[][]board = new char[WIDTH][HEIGHT];

	public static void main(String[] args){
		clear();
		while (!isBoardFull() && !isWinner(token, winMessage)){
		System.out.println("\n"+token+"'s turn");
		
			do{
				userInput();
			} 
			while (!placeToken(column));

			winMessage = checkForWin(token, row, column);
			displayBoard();
			token = switchPlayer(token);
		}
	} 

private static void clear(){
	for(int width=0; width < WIDTH; width++){
		for(int height=0; height < HEIGHT; height++){
			board[width][height]=empty;
		}
	}	
}

private static boolean isWinner(char token, String winMessage){
	if(winMessage == null){
		return false;
	}
	else{
		System.out.println("\n"+token+" won the game ("+winMessage+")!");
		return true;
	}
}

private static boolean isBoardFull(){
	for(int width=0; width < WIDTH; width++){
		for(int height=0; height < HEIGHT; height++){
			if(board[width][height]==empty){
				return false;
			}
		}
	}
	System.out.println("GAME OVER: The board is full");
	return true;	
}

private static boolean placeToken(int column){
	for(int height=0; height < HEIGHT; height++){
		if(board[column][max]!= empty){
			System.out.println("That column is full! Please select another one.");
			return false;
		}
		else if (board[column][height]==empty){
			board[column][height] = token;
			return true;
		}
	}

	return false;
	
}

private static int userInput(){
	int response;
	boolean flag = false;
		
	Scanner input = new Scanner(System.in);
		
	System.out.println("\nEnter a column (1-7)");
	
	while(flag == false){
		while (!input.hasNextInt()) {
				System.out.println("Enter an integer");
				input.nextLine();
			}
			
			response = input.nextInt()- min;
			
			if (response < WIDTH+1 && response >= 0){
				column = response;
				flag = true;
			}
			else  {
				System.out.println("Not a valid column, try (1-7)");
				flag = false;
			}
		}
		return column;
}


private static String checkForWin(char token, int row, int column){
	if(checkVert(token, column)== true){
		winMessage = "on a vertical";
	}
	else if(checkHor(token, row) == true){
		winMessage = "on a horizontal";
	}
	else if(checkDiagonal(token, row, column) == true){
		winMessage="on a diagonal";
	}
	else if(checkVert(token, column) == true && checkHor(token, row) == true){
		winMessage="on a vertical and a horizontal";
	}
	else if(checkVert(token, column) == true && checkDiagonal(token, row, column) == true){
		winMessage="on a vertical and a diagonal";
	}
	else if(checkHor(token, row) == true && checkDiagonal(token, row, column) == true){
		winMessage="on a horizontal and a diagonal";	
	}
	else if(checkVert(token, column) == true && checkHor(token, row) == true && checkDiagonal(token, row, column) == true){
		winMessage="on a vertical and a horizontal  a diagonal";
	}
	else{
		winMessage=null;
	}
	
	return winMessage;
	
}



private static boolean checkHor(char token, int row){
	int horizontal=0;
	for(int width=0; width < WIDTH; width++){
		if(board[width][row]==token){
			horizontal++;
			if(horizontal>=4){
				return true;
			}
		}
		else{
			horizontal=0;
		}
	}
	
	return false;
	
}

private static boolean checkVert(char token, int column){
	int vert=0;
	for(int height=0; height < HEIGHT; height++){
		if(board[column][height]==token){
			vert++;
			if(vert>=4){
				return true;
			}
		}
		else{
			vert=0;
		}
	}
	
	return false;
	
}

private static boolean checkDiagonal(char token, int row, int column){
	int diagonal=0, antidiagonal=0;

	for(int extra = 0; extra < HEIGHT; extra++){
		for(int height = 0; height < HEIGHT; height++){
			for(int width = 0; width < WIDTH; width++){
				if(diagonal >= 4){
					return true;
				}
				else if((width+extra)==height && board[width][height]==token){
					diagonal++;
				}
				else if((height+extra)== width && board[width][height]==token){
					diagonal++;
				}
				else if((height+extra)== width || (width+extra)==height && board[width][height]!=token){
					diagonal = 0;
				}
			}
		}
	}

	for(int height = 0; height < HEIGHT; height++){
		for(int width = WIDTH-1; width >= 0; width--){
			int difference = 0;
			int count = 0;

			difference = width - height;
			count = Math.abs(difference);

				if(antidiagonal >= 4){
					return true;
				}
				else if((count%2)==0 && board[width][height]==token){
					antidiagonal++;
					height++;
				}
				else if((count%2)!=0 && board[width][height]==token){
					antidiagonal = 0;
				}
			}
		}
	
	return false;
}

private static void displayBoard(){
	for(int height = HEIGHT-1; height >= 0; height--){
		for(int width = 0; width < WIDTH; width++){
			System.out.print(board[width][height]);
		}
		System.out.println("");
	}	
}

private static char switchPlayer(char token){
	if(winMessage==null){
		turn++;
		if(turn%2 == 1){
			token='x';
			return token;
		}
		else{
			token='o';
			return token;
		}
	}
	else{
		return token;
	}
}

}
