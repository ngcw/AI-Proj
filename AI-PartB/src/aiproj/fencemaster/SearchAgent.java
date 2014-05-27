package aiproj.fencemaster;

import java.util.LinkedList;

public class SearchAgent {
	
	public static int maxPly = 2; //we choose 8 because it takes 8 plys to beat an idiotic player.
	
	public static int negamaxABP(Game g, int depth, int alpha, int beta, int player) {
		if (depth == 0 || g.terminalGame()) {
			return Evaluation.evaluator(g);
		}
		
		int bestValue = Integer.MIN_VALUE;
		LinkedList<Move> moves = g.generateMoves(player);
		// Order Moves
		
		for (Move m : moves) {
			g.update(m);
			int val = -SearchAgent.negamaxABP(g, depth - 1, alpha, beta, player);
			g.revertUpdate(m);
			if (val > bestValue) 
				bestValue = val;
			if (val > alpha)
				alpha = val;
			if (alpha > beta) {
				System.out.println("Pruning Occured!");
				break;
			}
		}
		return bestValue;
	}
	
}
