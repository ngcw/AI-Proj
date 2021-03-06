/**
 * @author Chin Wai Ng (ngcw)
 * @author Renlord Yang (rnyang)
 * 
 * Contains the game state checking functions.
 */
package ngcw.fencemaster;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TestWin {
	// constant variables for board pieces
	public static final char WHITE = 'W';
	public static final char BLACK = 'B';
	public static final char EMPTY = '-';
	
	/**
	 * checks the winner of the game and print results at the current game state
	 * throws an exception if more than one winner is detected
	 * @param game, an instance of the game
	 * @throws InvalidWinnerException
	 */
	public static void checkWinner(Game game) throws InvalidWinnerException{
		int winner = 0;
		ArrayList<String> winMethod = new ArrayList<String>();
		ArrayList<int[]> visited_w = new ArrayList<int[]>();
		ArrayList<int[]> visited_b = new ArrayList<int[]>();
		for (int i = 0; i < game.getBoard().length; i++){
			for (int j = 0; j < game.getBoard().length; j++){
				// check if piece in board is White player's piece and then 
				// traverse following pieces connected to form a path
				if ((game.getBoard()[i][j] == WHITE) && 
						(!game.checkVisit(visited_w,i,j))){
					ArrayList<int[]> path_w = new ArrayList<int[]>();
					path_w.add(game.arrayFormat(i, j));
					visited_w.addAll(game.boardDFS(game.getBoard(), 
							game.getRowLength(), i, j, WHITE, path_w));
					
					// check if there is a tripod win in the path
					if (checkTripod(game,path_w)){
						if (winner == 2){
							throw new InvalidWinnerException();
						}
						winner = 1;
						if (!winMethod.contains("Tripod")){
							winMethod.add("Tripod");
						}
					}
					// check if there is a loop win in the path
					if (checkLoop(game,path_w,WHITE)){
						if (winner == 2){
							throw new InvalidWinnerException();
						}
						winner = 1;
						if (!winMethod.contains("Loop")){
							winMethod.add("Loop");
						}
					}
					path_w.clear();
				// check if piece in board is Black player's piece and piece is
				// not visited then traverse following pieces connected to form 
				// a path
				}else if ((game.getBoard()[i][j] == BLACK) && 
						(!game.checkVisit(visited_b,i,j))){
					ArrayList<int[]> path_b = new ArrayList<int[]>();
					path_b.add(game.arrayFormat(i, j));
					visited_b.addAll(game.boardDFS(game.getBoard(), 
							game.getRowLength(), i, j, BLACK, path_b));
					// check if there is a tripod win in the path
					if (checkTripod(game,path_b)){
						if (winner == 1){
							throw new InvalidWinnerException();
						}
						winner = 2;
						if (!winMethod.contains("Tripod")){
							winMethod.add("Tripod");
						}
					}
					// check if there is a loop win in the path
					if (checkLoop(game,path_b,BLACK)){
						
						if (winner == 1){
							throw new InvalidWinnerException();
						}
						winner = 2;
						if (!winMethod.contains("Loop")){
							winMethod.add("Loop");
						}
					}
					path_b.clear();
				}
			}
		}
		printResult(game,winner,winMethod);
	}
	/**
	 * Prints the result winner of the current game state with method of winning
	 *  if there is one or check if game has ended with no winner.
	 * @param g, an instance of the game
	 * @param winner, winner value 1 for White player and 2 for Black player
	 * @param winMethod, method of winning if there is a winner
	 */
	public static void printResult(Game g, int winner, 
			ArrayList<String> winMethod){
		if (winner == 1){
			System.out.println("White");
			if (winMethod.size() == 1){
				System.out.println(winMethod.remove(0));
			}else{
				System.out.println("Both");
			}
		}else if (winner == 2){
			System.out.println("Black");
			if (winMethod.size() == 1){
				System.out.println(winMethod.remove(0));
			}else{
				System.out.println("Both");
			}
		}else{
			for (int i = 0; i < g.getBoard().length; i++){
				for (int j = 0; j < g.getBoard().length; j++){
					if (g.getBoard()[i][j] == EMPTY){
						System.out.println("None");
						return;
					}
				}
			}
			System.out.println("Draw");
		}
	}
	
	/**
	 * check if a piece is located at 6 corners of the board
	 * @param boardSize, size of board
	 * @param piece, piece in board to check
	 * @return boolean located at corner
	 */
	public static boolean checkCorner(int boardSize, int[] piece){
		int maxSize = boardSize*2-2;
		if ((piece[0]== 0 && piece[1] == 0) 
				|| (piece[0]== 0 && piece[1] == boardSize-1)
				|| (piece[0]== boardSize-1 && piece[1] == 0)
				|| (piece[0]== boardSize-1 && piece[1] == maxSize)
				|| (piece[0]== maxSize && piece[1] == 0)
				|| (piece[0]== maxSize && piece[1] == boardSize-1)){
			return true;
		}
		return false;
	}
	/**
	 * check if there is a tripod in win method in the list of pieces. A tripod 
	 * can only happen if there is at least 3 pieces at 3 different edges linked
	 * by pieces in the center
	 * @param g, an instance of the game
	 * @param path, a list of connected pieces
	 * @return boolean if a tripod exist in path
	 */
	public static boolean checkTripod(Game g, ArrayList<int[]> path){
		int boardSize = g.getSize();
		int maxSize = g.getMaxSize();
		// corner flags and variables to count number of pieces at egdes and 
		// center
		int sideCount = 0, sidePiece = 0, top = 0,left_top = 0,left_btm = 0, 
				right_top = 0,right_btm = 0,btm = 0, center = 0;
		// a tripod need to be at least length of boardSize + 1 pieces
		if (path.size() > (boardSize + 1)){
			for (int[] piece: path){
				if (piece[0] == 0 && piece[1] > 0 && piece[1] < boardSize - 1){
					top = 1;
					sidePiece++;
				}
				if (piece[0] > 0 && piece[0] < boardSize - 1 && piece[1] == 0 ){
					left_top = 1;
					sidePiece++;
				}
				if (piece[0] > boardSize -1 && piece[0] < maxSize - 1
						&& piece[1] == 0 ){
					left_btm = 1;
					sidePiece++;
				}
				if (piece[0] == maxSize - 1 && piece[1] > 0 
						&& piece[1] < boardSize - 1){
					btm = 1;
					sidePiece++;
				}
				if (piece[0] > 0 && piece[0] < boardSize - 1
						&& piece[1] == g.getRowLength()[piece[0]] - 1){
					right_top = 1;
					sidePiece++;
				}
				if (piece[0] > boardSize - 1 && piece[0] < maxSize - 1
						&& piece[1] == g.getRowLength()[piece[0]] -1){
					right_btm = 1;
					sidePiece++;
				}
				if ((piece[0] > 0 && piece[0] < maxSize -1 && piece[1] > 0 && 
						piece[1] < g.getRowLength()[piece[0]] -1) ||
						checkCorner(boardSize, piece)){
					center++;
				}
			}
		}
		sideCount = top+left_top+left_btm+right_top+right_btm+btm;
		return ((sideCount >= 3) && (center >= (path.size()-sidePiece)));
	}
	/**
	 * check if there is a loop win in the list of pieces. A loop win can only 
	 * happen when there is a cycle around at least one piece.
	 * @param g, an instance of the game
	 * @param path, a list of connected pieces
	 * @param player, player in check (White or Black)
	 * @return boolean, if there is a loop win
	 */
	public static boolean checkLoop(Game g, ArrayList<int[]> path, char player){
		// a loop needs to be at least of 6 pieces
		if (path.size() > 5){
			ArrayList<int[]> loop = new ArrayList<int[]>();
			ArrayList<int[]> loopVisited = new ArrayList<int[]>();
			ArrayDeque<int[]> junction = new ArrayDeque<int[]>();
			// a loop can only happen when the starting player piece have two 
			// other surrounding player pieces
			int[] move = path.get(0);
			ArrayDeque<int[]> links = g.checkSurrounding(g.getBoard(),
	    			g.getRowLength(),move[0],move[1],player);
			ArrayDeque<int[]> startLink = g.checkSurrounding(g.getBoard(),
	    			g.getRowLength(),move[0],move[1],player);
			for (int i = 1; links.size() < 2 && path.size()-i>5; i++){
				move = path.get(i);
				links = g.checkSurrounding(g.getBoard(),
		    			g.getRowLength(),move[0],move[1],player);
				startLink = g.checkSurrounding(g.getBoard(),
		    			g.getRowLength(),move[0],move[1],player);
			}
			loop.add(move);
			loopVisited.add(move);
			int count = 1;
			if (startLink.size() > 1){
				for (int i = 1; i < startLink.size();i++)
				{
					junction.add(g.arrayFormat(count, 0));
				}
			}
			// Depth First Search to find shortest possible loop
			
			ArrayDeque<int[]> stack = new ArrayDeque<int[]>();
			stack.addAll(links);
			for (int[] item: stack){
			}
			while (!stack.isEmpty()){
				int[] piece = stack.removeLast();
				// reset count to number at other branch
				if (!g.checkLink(links, piece[0], piece[1]) 
						&& !junction.isEmpty()){
					int prevCount = count;
					count = junction.removeLast()[0];
					System.out.println("back to:" +count);
					while(prevCount != count)
					{
						loop.remove(prevCount-1);
						prevCount--;
					}
				}else if (stack.size() < junction.size()){
					for (int i = 0; i < (junction.size()-stack.size());i++){
						count = junction.removeLast()[0];
					}
				}
				
				loop.add(piece);
				loopVisited.add(piece);
				links = g.checkSurrounding(g.getBoard(),g.getRowLength(),
						piece[0],piece[1],player);
				
				if (count > 3 && g.checkLoopConnect(links, startLink) &&
						circleConstrain(g,loop,player)){
					return true;
				}
				int link_count = 0;
				for (int[] item:links){
					if (!g.checkVisit(loopVisited, item[0],item[1]) && 
							!g.checkLink(stack, item[0],item[1])){
						System.out.println(player+":"+item[0] +"-" +item[1]);
						stack.add(item);
						link_count++;
					}
					
				}
				// if more than 1 branch is added to stack, add position to
				// junction
				if (link_count > 1){
					for (int i = 1; i < link_count;i++)
					{
						junction.add(g.arrayFormat(count, 0));
					}
					
				}
				count++;
			}
			loop.clear();
		}
		return false;
	}

	/**
	 * check for other pieces within a loop and return true if there is any
	 * @param g, instance of game
	 * @param loop, the sequence of pieces forming a loop
	 * @param player, player in check (White or Black)
	 */
	public static boolean circleConstrain(Game g, ArrayList<int[]> loop, 
			char player){
		int[] first = loop.get(0);
		int[] last = loop.get(loop.size()-1);
		ArrayList<int[]> loopTemp = new ArrayList<int[]>();
		ArrayList<int[]> pieceInLoop = new ArrayList<int[]>();
		for (int[] piece: loop){
			//System.out.println(piece[0] +"-" +piece[1]);
			loopTemp.add(piece);
		}
		// look for the link between second last piece and first piece in loop
		// then add into loopTemp Arraylist
		for (int[] piece: g.checkSurrounding(g.getBoard(), g.getRowLength(), 
				first[0], first[1], player)){
			for (int[] piece2: g.checkSurrounding(g.getBoard(), g.getRowLength(), 
				last[0], last[1], player)){
				if (piece[0] == piece2[0] && piece[1] == piece2[1]){
					//System.out.println("Link:"+piece[0] +"-" +piece[1]);
					loopTemp.add(piece);
				}
			}
			
		}
		// look for pieces within loop that are not player piece
		int[] rowFlag = new int[g.getMaxSize()];
		Arrays.fill(rowFlag, -1);
		for (int[] piece: loopTemp){
			if (rowFlag[piece[0]] != -1){
				if (piece[1] - rowFlag[piece[0]] >1){
					for (int i = rowFlag[piece[0]]+1; i < piece[1];i++){
						if (g.getBoard()[piece[0]][i] !=player){
							pieceInLoop.add(piece);
						}
					}
				}else{
					rowFlag[piece[0]] = piece[1];
				}
			}else{
				rowFlag[piece[0]] = piece[1];
			}
		}
		if (!pieceInLoop.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static void main(String [] args) 
			throws InvalidInputException, InvalidInputLengthException, 
			InvalidWinnerException
	{
		// reads in boardSize as first input following by lines of board states
		Scanner input = new Scanner(System.in);
		int boardSize = input.nextInt();
		Game game = new Game(boardSize);
		@SuppressWarnings("unused")
		String nextLine = input.nextLine();
		for (int i = 0; i < (boardSize * 2 - 1); i++){
			String boardInput = input.nextLine().replaceAll("\\s+","");
			if (boardInput.length() != game.getRowLength()[i]){
				throw new InvalidInputLengthException(i);
			}
			for (int c = 0; c < boardInput.length(); c++){
				if (Character.toString(boardInput.charAt(c)).matches("[WB-]")){
					game.getBoard()[i][c] = boardInput.charAt(c);
				}else{
					throw new InvalidInputException(boardInput.charAt(c),i,c);
				}
			}
		}
		input.close();
		checkWinner(game);
	}
}