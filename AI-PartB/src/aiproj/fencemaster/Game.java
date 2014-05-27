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
import java.util.LinkedList;

public class Game {
	// static variables
	public static int instanceCount = 0;
	// private instance variables for game
	private int[][] board;
	private int boardSize;
	private int[] rowLength;
	private int maxSize;
	private int moveCount = 1;
	private int emptySpace = 0;
	
	private int player, enemy;
	
	/**
	 * CONSTRUCTOR: creates an instance of the game
	 * @param size, size of board
	 */
	public Game (int size, int piece) {
		this.maxSize = size * 2 - 1;
		this.boardSize = size; 
		this.rowLength = new int[maxSize];
		
		for (int n = 0; n < boardSize; n++) {
			rowLength[n] = boardSize + n;
		}
		for (int m = boardSize, c = 1; m < maxSize; m++, c++) {
			rowLength[m] = maxSize - c;
		}
		
		this.board = new int[maxSize][];
		
		// init for gameboard.
		for (int i = 0; i < maxSize; i++) {
			this.board[i] = new int[rowLength[i]];
			for (int j = 0; j < rowLength[i]; j++) {
				board[i][j] = Piece.EMPTY;
				emptySpace++;
			}
		}
		
		this.setPlayer(piece);
		if (this.player == Piece.WHITE)
			this.enemy = Piece.BLACK;
		else
			this.enemy = Piece.WHITE;
		
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
	public boolean inBound(int row, int col){
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
	public ArrayDeque<int[]> checkSurrounding(int row, int col, int player){
		ArrayDeque<int[]> links = new ArrayDeque<int[]>(6);
		int maxBoardSize = board.length;
		// upper area of board
		if (row < (maxBoardSize/2)){
			// Top-Left
			if (inBound(row-1, col-1) && 
					(board[row-1][col-1] == player))
				links.add(arrayFormat(row-1,col-1));
			// Top
			if (inBound(row-1, col) && 
					(board[row-1][col] == player))
				links.add(arrayFormat(row-1,col));
			// Left
			if (inBound(row, col-1) && 
					(board[row][col-1] == player))
				links.add(arrayFormat(row,col-1));
			// Right
			if (inBound(row, col+1) && 
					(board[row][col+1] == player))
				links.add(arrayFormat(row,col+1));
			// Bottom
			if (inBound(row+1, col) && 
					(board[row+1][col] == player))
				links.add(arrayFormat(row+1,col));
			// Bottom-Right
			if (inBound(row+1, col+1) && 
					(board[row+1][col+1] == player))
				links.add(arrayFormat(row+1,col+1));
		// Middle line of board
		}else if (row == (maxBoardSize/2)){
			// Top-Left
			if (inBound(row-1, col-1) && 
					(board[row-1][col-1] == player))
				links.add(arrayFormat(row-1,col-1));
			// Top
			if (inBound(row-1, col) && 
					(board[row-1][col] == player))
				links.add(arrayFormat(row-1,col));
			// Left
			if (inBound(row, col-1) && 
					(board[row][col-1] == player))
				links.add(arrayFormat(row,col-1));
			// Right
			if (inBound(row, col+1) && 
					(board[row][col+1] == player))
				links.add(arrayFormat(row,col+1));
			// Bottom-Left
			if (inBound(row+1, col-1) && 
					(board[row+1][col-1] == player))
				links.add(arrayFormat(row+1,col-1));
			// Bottom
			if (inBound(row+1, col) && 
					(board[row+1][col] == player))
				links.add(arrayFormat(row+1,col));
		// bottom area of board
		}else{
			// Top-Right
			if (inBound(row-1, col+1) && 
					(board[row-1][col+1] == player))
				links.add(arrayFormat(row-1,col+1));
			// Top
			if (inBound(row-1, col) && 
					(board[row-1][col] == player))
				links.add(arrayFormat(row-1,col));
			// Left
			if (inBound(row, col-1) && 
					(board[row][col-1] == player))
				links.add(arrayFormat(row,col-1));
			// Right
			if (inBound(row, col+1) && 
					(board[row][col+1] == player))
				links.add(arrayFormat(row,col+1));
			// Bottom
			if (inBound(row+1, col) && 
					(board[row+1][col] == player))
				links.add(arrayFormat(row+1,col));
			// Bottom-Right
			if (inBound(row+1, col-1) && 
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
	 * @param m, move to be updated
	 */
	public void update(Move m) {
		this.board[m.Row][m.Col] = m.P;
		emptySpace--;
		moveCount++;
	}
	
	/**
	 * Reverts the update made to game board
	 * 
	 * @param m, move to be reverted
	 */
	public void revertUpdate(Move m) {
		this.board[m.Row][m.Col] = Piece.EMPTY;
		emptySpace++;
		moveCount--;
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
	public ArrayList<int[]> boardDFS(int row, int col, int player, 
			ArrayList<int[]> path) {
		
		ArrayDeque<int[]> links = checkSurrounding(row, col, player);
		ArrayDeque<int[]> stack = new ArrayDeque<int[]>();
		stack.addAll(links);
		while (!stack.isEmpty()){
			int[] piece = stack.removeLast();
			path.add(piece);
			links = checkSurrounding(piece[0], piece[1], player);
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
	 * Generates all possible moves for current board configuration
	 * @param player
	 * @return
	 */
	public LinkedList<Move> generateMoves(int player) {
		int opponent = 0;
		
		if (player == Piece.WHITE)
			opponent = Piece.BLACK;
		else
			opponent = Piece.WHITE;
		
		LinkedList<Move> moves = new LinkedList<Move>();
		if (moveCount == 1) {
			for (int i = 0; i < this.maxSize; i++) {
				for (int j = 0; j < this.rowLength[i]; j++) {
					if (this.board[i][j] == opponent) {
						Move m = new Move(player, true, i, j);
						moves.push(m);
					}
					if (this.board[i][j] == Piece.EMPTY) {
						Move m = new Move(player, false, i, j);
						moves.push(m);
					}
				}
			}
		} else {
			for (int i = 0; i < this.maxSize; i++) {
				for (int j = 0; j < this.rowLength[i]; j++) {
					if (this.board[i][j] == Piece.EMPTY) {
						Move m = new Move(player, false, i, j);
						moves.push(m);
					}
				}
			}
		}
		
		return moves;
	}
	
	/**
	 * Counts the number of adjacent opponent pieces
	 * MOBILITY FEATURE
	 * 
	 * @param opponent
	 * @param r
	 * @param c
	 * @return
	 */
	public int adjacentCount(int piece, int r, int c) {
		int count = 0;
		
		// Top Left
		if (inBound(r - 1, c - 1) && board[r - 1][c - 1] == piece)
			count++;
		
		// Top
		if (inBound(r - 1, c) && board[r - 1][c] == piece)
			count++;
		
		// Top Right;
		if (inBound(r - 1, c + 1) && board[r - 1][c + 1] == piece)
			count++;
		
		// Bottom Left;
		if (inBound(r + 1, c - 1) && board[r + 1][c - 1] == piece)
			count++;
		// Bottom;
		if (inBound(r + 1, c) && board[r + 1][c] == piece)
			count++;
		
		// Bottom Right;
		if (inBound(r + 1, c + 1) && board[r + 1][c + 1] == piece)
			count++;
		
		return count;
	}
	
	/** 
	 * Checks if its possible to perform a swap move in current game state
	 * 
	 * @return true, if it is possible to swap.
	 * @return false, if it is NOT possible to swap.
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

	
	public boolean terminalGame() {
		int result = TestWin.checkWinner(this);
		
		if (result == Piece.INVALID)
			return false;
		else
			return true;
	}
}
