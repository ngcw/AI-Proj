/**
 * @author Chin Wai Ng (ngcw)
 * @author Renlord Yang (rnyang)
 * 
 * Contains the basic game functions.
 * 
 */
package aiproj.fencemaster;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Game {
	// static variables
	public static int instanceCount = 0;
	// private instance variables for game
	private int[][] board;
	private int boardSize;
	private int[] rowLength;
	private int maxSize;
	private int moveCount = 1;
	
	private int player, enemy;
	
	/**
	 * CONSTRUCTOR: creates an instance of the game
	 * @param size, size of board
	 */
	public Game (int size, int piece){
		this.maxSize = size*2-1;
		this.boardSize = size;
		this.rowLength = new int[maxSize];
		
		for (int n = 0; n < boardSize; n++){
			rowLength[n] = boardSize + n;
		}
		for (int m = boardSize, c = 1; m < maxSize; m++, c++){
			rowLength[m] = (maxSize) - c;
		}
		
		this.board = new int[maxSize][maxSize];
		
		// init for gameboard.
		for (int i = maxSize; i < maxSize; i++) {
			for (int j = maxSize; j < maxSize; j++) {
				if ( j < rowLength[i] ) {
					board[i][j] = Piece.EMPTY;

				} else
					board[i][j] = Piece.INVALID;
			}
		}
		
		this.setPlayer(piece);
		
		Game.instanceCount++;
	}
	

	/**
	 * check if input of board is in bound of board size
	 * 
	 * @param rowLength, the length of each row in board
	 * @param row, row of piece being checked
	 * @param col, column of piece being checked
	 * @return boolean if piece is in bound
	 */
	public static boolean inBound(int[] rowLength, int row, int col){
		return (row >= 0 && col >= 0 && (row < rowLength[0]*2-1) && 
				col < rowLength[row]);
	}
	/**
	 * check if surrounding of player piece contains more player pieces
	 * 
	 * @param board, board of game
	 * @param rowLength, the length of each row in board
	 * @param row, row of piece being checked
	 * @param col, column of piece being checked
	 * @param player, player in check (White or Black)
	 * @return ArrayDeque<int[]>, returns an ArrayDeque of surrounding pieces
	 */
	public ArrayDeque<int[]> checkSurrounding(char[][] board, int[] rowLength, 
			int row, int col, int player){
		ArrayDeque<int[]> links = new ArrayDeque<int[]>(6);
		int maxBoardSize = board.length;
		// upper area of board
		if (row < (maxBoardSize/2)){
			// Top-Left
			if (inBound(rowLength, row-1, col-1) && 
					(board[row-1][col-1] == player))
				links.add(arrayFormat(row-1,col-1));
			// Top
			if (inBound(rowLength, row-1, col) && 
					(board[row-1][col] == player))
				links.add(arrayFormat(row-1,col));
			// Left
			if (inBound(rowLength, row, col-1) && 
					(board[row][col-1] == player))
				links.add(arrayFormat(row,col-1));
			// Right
			if (inBound(rowLength, row, col+1) && 
					(board[row][col+1] == player))
				links.add(arrayFormat(row,col+1));
			// Bottom
			if (inBound(rowLength, row+1, col) && 
					(board[row+1][col] == player))
				links.add(arrayFormat(row+1,col));
			// Bottom-Right
			if (inBound(rowLength, row+1, col+1) && 
					(board[row+1][col+1] == player))
				links.add(arrayFormat(row+1,col+1));
		// Middle line of board
		}else if (row == (maxBoardSize/2)){
			// Top-Left
			if (inBound(rowLength, row-1, col-1) && 
					(board[row-1][col-1] == player))
				links.add(arrayFormat(row-1,col-1));
			// Top
			if (inBound(rowLength, row-1, col) && 
					(board[row-1][col] == player))
				links.add(arrayFormat(row-1,col));
			// Left
			if (inBound(rowLength, row, col-1) && 
					(board[row][col-1] == player))
				links.add(arrayFormat(row,col-1));
			// Right
			if (inBound(rowLength, row, col+1) && 
					(board[row][col+1] == player))
				links.add(arrayFormat(row,col+1));
			// Bottom-Left
			if (inBound(rowLength, row+1, col-1) && 
					(board[row+1][col-1] == player))
				links.add(arrayFormat(row+1,col-1));
			// Bottom
			if (inBound(rowLength, row+1, col) && 
					(board[row+1][col] == player))
				links.add(arrayFormat(row+1,col));
		// bottom area of board
		}else{
			// Top-Right
			if (inBound(rowLength, row-1, col+1) && 
					(board[row-1][col+1] == player))
				links.add(arrayFormat(row-1,col+1));
			// Top
			if (inBound(rowLength, row-1, col) && 
					(board[row-1][col] == player))
				links.add(arrayFormat(row-1,col));
			// Left
			if (inBound(rowLength, row, col-1) && 
					(board[row][col-1] == player))
				links.add(arrayFormat(row,col-1));
			// Right
			if (inBound(rowLength, row, col+1) && 
					(board[row][col+1] == player))
				links.add(arrayFormat(row,col+1));
			// Bottom
			if (inBound(rowLength, row+1, col) && 
					(board[row+1][col] == player))
				links.add(arrayFormat(row+1,col));
			// Bottom-Right
			if (inBound(rowLength, row+1, col-1) && 
					(board[row+1][col-1] == player))
				links.add(arrayFormat(row+1,col-1));
			
		}
		return links;
	}
	/**
	 * Format the row and column position of a piece into an array format
	 * 
	 * @param row, row of piece
	 * @param col, column of piece
	 * @return int[], an integer array of [0] for row and [1] for column
	 */
	public int[] arrayFormat(int row, int col)
	{
		int[] loc = new int[2];
		loc[0] = row;
		loc[1] = col;
		return loc;
	}
	
	/**
	 * Updates the game board
	 * 
	 * @param m, opponent move
	 */
	public void update(Move m) {
		this.board[m.Row][m.Col] = m.P;
	}
	
	/**
	 * check if a piece is already in a visited ArrayList
	 * 
	 * @param list, an ArrayList of visited pieces
	 * @param row, row of piece in check
	 * @param col, column of piece in check
	 * @return boolean, if the piece exist in the list
	 */
	public boolean checkVisit(ArrayList<int[]> list, int row, int col){
		for (int[] item: list){
			if ((item[0] == row) && (item[1] == col)){
				return true;
			}
		}
		return false;
	}
	/**
	 * check if a piece is already in a visited ArrayDeque
	 * 
	 * @param list, an ArrayDeque of visited pieces
	 * @param row, row of piece in check
	 * @param col, column of piece in check
	 * @return boolean, if the piece exist in the list
	 */
	public boolean checkLink(ArrayDeque<int[]> list, int row, int col){
		for (int[] item: list){
			if ((item[0] == row) && (item[1] == col)){
				return true;
			}
		}
		return false;
	}
	/**
	 * check if there is any similar pieces in both ArrayDeque
	 * 
	 * @param list, first ArrayDeque list
	 * @param list2, second ArrayDeque list
	 * @return boolean, if there exist a similar piece
	 */
	public boolean checkLoopConnect(ArrayDeque<int[]> list, 
			ArrayDeque<int[]> list2){
		for (int[] item: list){
			for (int[] item2: list2){
				if ((item[0] == item2[0]) && (item[1] == item2[1])){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Performs Chaining Search
	 * 
	 * @param board, board of game
	 * @param rowLength, the length of each row in board
	 * @param row, row of piece being checked
	 * @param col, column of piece being checked
	 * @param player, player in check (White or Black)
	 * @param path, a list of connected pieces
	 * @return ArrayList<int[]>, a list of connected pieces (path)
	 */
	public ArrayList<int[]> boardDFS(char[][] board,int[] rowLength, int row, 
			int col, char player,ArrayList<int[]> path){
		ArrayDeque<int[]> links = checkSurrounding(board,rowLength,row,col,
				player);
		ArrayDeque<int[]> stack = new ArrayDeque<int[]>();
		stack.addAll(links);
		while (!stack.isEmpty()){
			int[] piece = stack.removeLast();
			path.add(piece);
			links = checkSurrounding(board,rowLength,piece[0],piece[1],player);
			for (int[] item:links){
				if (!checkVisit(path, item[0],item[1]) && 
						!checkLink(stack, item[0],item[1])){
					stack.add(item);
				}
			}
		}
		return path;
	}
	
	/** 
	 * Checks if its possible to perform a swap move in current game state
	 */
	public boolean canSwap() {
		if (moveCount == 1)
			return true;
		
		return false;
	}
	
	/**
	 * @return Array of row lengths of board
	 */
	public int[] getRowLength(){
		return this.rowLength;
	}
	/**
	 * @return the board
	 */
	public int[][] getBoard(){
		return this.board;
	}
	/**
	 * @return size of board
	 */
	public int getSize(){
		return this.boardSize;
	}
	/**
	 * @return maximum length of row in board
	 */
	public int getMaxSize(){
		return this.maxSize;
	}


	public int getPlayer() {
		return player;
	}


	public void setPlayer(int player) {
		this.player = player;
	}


	public int getEnemy() {
		return enemy;
	}


	public void setEnemy(int enemy) {
		this.enemy = enemy;
	}
}
