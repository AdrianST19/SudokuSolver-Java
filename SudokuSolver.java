import java.util.Scanner;

public class SudokuSolver {
	
	private static final int GRID_SIZE = 9;
	
	public static void main(String[]args) {
		
		//created a default board for a quick test
		int[][] default_board= {
			  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
			  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
			  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
			  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
			  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
			  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
			  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
			  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
			  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
		      };
		int[][] board=new int[9][9];
		
		Scanner input = new Scanner(System.in);
		System.out.println("Go with the default board or input your own?\nType \"d\" for default board\n"
				+ "Type \"i\" to introduce your own");
		char option= input.next().charAt(0);
		
		// if the user choose 'd' then the board will point to the same location in memory as the default_board
		if(option=='d') {
			for(int i=0;i<GRID_SIZE;i++) // I chose to do a deep copy here instead of a shallow copy
				for(int j=0;j<GRID_SIZE;j++) 
					board[i][j]=default_board[i][j];
			
			printBoard(board); 
			System.out.println("The board was solved succesfully!");
			solveBoard(board);
			printBoard(board); // we call the funtion in order to print the board
		}
		// if the user choose 'i' then he has to introduce his own numbers for the board
		else if(option=='i') {
			int row=0;
			for(int i=0;i<GRID_SIZE;i++) { // i represents the rows
				System.out.println("Insert row "+(i+1));
				for(int j=0;j<GRID_SIZE;j++) { // j represents the columns
					board[row][j]= input.nextInt(); // we read wach value on the row
				}
				row++;
			}
			System.out.println( (solveBoard(board)? "The board was solved successfully!":"The board has no solving solution!"));
			printBoard(board); // we call the funtion in order to print the board
		}
		else System.out.println("Invalid choice!"); // if the user introduces anything but 'd' or 'i' then we will print this
		input.close();
	}

	private static void printBoard(int[][] board) {
		for(int row=0;row<GRID_SIZE;row++) {
			if(row % 3 == 0 && row != 0) 
				System.out.println("------------"); // prints this for every third row
			for(int column=0;column<GRID_SIZE;column++) {
				if(column % 3 == 0 && column != 0) 
					System.out.print("|"); // prints this for every third column
				System.out.print(board[row][column]);
			}
			System.out.println();
		}

	}

	private static boolean isNumInRow(int[][] board, int number, int row) { // it checks if the number is present in the row
	
		for(int i=0;i<GRID_SIZE;i++) 
			if(board[row][i]==number) return true;
		return false;
	}
	
	private static boolean isNumInCol(int[][] board, int number, int column) { // it checks if the number is present in the column

		for(int i=0;i<GRID_SIZE;i++) 
			if(board[i][column]==number) return true;
		return false;
	}
	
	private static boolean isNumInBox(int[][] board, int number, int row, int column) { // it checks if the number is present in a 3 by 3 box
		int localBoxRow= row - row % 3;
		int localBoxCol= column - column % 3;
		
		for(int i= localBoxRow; i<localBoxRow + 3; i++) 
			for(int j= localBoxCol; j<localBoxCol + 3; j++) 
				if(board[i][j]== number) return true; // check every 3 by 3 box
		return false;
	}
	
	private static boolean isValidPlacement(int[][] board, int number, int row, int column) { // this is a method which checks if there is no number in the row, column and 3 by 3 box
		return !isNumInRow(board,number,row)&&!isNumInCol(board,number,column)&&
			   !isNumInBox(board,number,row,column);
	}
	
	private static boolean solveBoard(int[][] board) { // this is the method that actually solves the board
		
		for(int row=0; row< GRID_SIZE;row++) 
			for(int column=0; column<GRID_SIZE;column++) 
				if(board[row][column]== 0) {
					for(int tryNum=1; tryNum<=GRID_SIZE; tryNum++) {
						
						if(isValidPlacement(board,tryNum,row,column)) {
							board[row][column]= tryNum;
							if(solveBoard(board)) return true; // this is a recursive call
							else board[row][column]= 0; // is backtracking if there is no solution, so it checks all the possibilities
						}
						
					}
					return false; // returns false if it finds no solution
				}
		return true;
	}
}
