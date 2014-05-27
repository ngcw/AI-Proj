package aiproj.fencemaster;

import java.util.LinkedList;

public class SearchAgent {
	
	public static int maxPly = 2;
	
	public static int negamaxABP(Game g, int depth, int alpha, int beta, int player) {
		int color = 0;
		// whenever recursivelly called into this function from MAXIMISER,
		// we will evaluate for MINIMISER and Vice-Versa.
		if (player == g.getPlayer()) {
			player = g.getEnemy();
			color = 1;
		} else {
			player = g.getPlayer();
			color = -1;
		}
		
		if (depth == 0 || g.terminalGame()) {
			return color * Evaluation.evaluator(g);
		}
		
		// whenever recursivelly called into this function from MAXIMISER,
		// we will evaluate for MINIMISER and Vice-Versa.
		
		int bestValue = Integer.MIN_VALUE;
		LinkedList<Move> moves = g.generateMoves(player);
		
		// Order Moves
		for (Move m : moves) {
			g.update(m);
			int val = SearchAgent.negamaxABP(g, depth - 1, -alpha, -beta, player);
			//System.out.println("Score:" + val);
			//System.out.println("ALPHA:" + alpha);
			g.revertUpdate(m);
			if (val > bestValue) 
				bestValue = val;
			if (val > alpha)
				alpha = val;
			if (alpha >= beta) {
				//System.out.println("Pruning Occured!");
				break;
			}
		}
		return bestValue;
	}
	
}
