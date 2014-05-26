package aiproj.fencemaster;

import java.util.LinkedList;

public class SearchAgent {
	
	public static int maxPly = 2; //we choose 8 because it takes 8 plys to beat an idiotic player.
	
	/**
	 * Negamax
	 * 
	 * @param g, the game 
	 * @param depth, current search depth
	 * @param player, current player to be evaluating for
	 * 
	 * @return best score for a given state
	 */
	public static int negamax(Game g, int depth, int player) {
		System.out.println("Current Depth:" + depth);
		if (depth == 0 || g.terminalGame()) {
			System.out.println("Returning");
			if (player == g.getEnemy())
				return -Evaluation.naiveEvaluator(g);
			else
				return Evaluation.naiveEvaluator(g);
		}
		
		LinkedList<Move> moves = g.generateMoves(player);
		
		if (player == Piece.WHITE)
			player = Piece.BLACK;
		else
			player = Piece.WHITE;
		
		int bestValue = Integer.MIN_VALUE;
		for (Move m : moves) {
			g.update(m);
			int val = -SearchAgent.negamax(g, depth - 1, player);
			g.revertUpdate(m);
			if (val > bestValue)
				bestValue = val;
		}
		
		return bestValue;
	}
	
}
