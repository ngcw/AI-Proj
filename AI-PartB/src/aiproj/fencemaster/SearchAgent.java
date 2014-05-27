package aiproj.fencemaster;

import java.util.LinkedList;

public class SearchAgent {
	
	public static int maxPly = 2;
	
	public static int negamaxABP(Game g, int depth, int alpha, int beta, int color) {		
		
		if (depth == 0 || g.terminalGame()) {
			return color * Evaluation.evaluator(g);
		}
		
		int bestValue = Integer.MIN_VALUE;
		int player;
		
		if (color > 0)
			player = g.getEnemy();
		else
			player = g.getPlayer();
		LinkedList<Move> moves = g.generateMoves(player);
		
		// Order Moves
		for (Move m : moves) {
			g.update(m);
			int val = SearchAgent.negamaxABP(g, depth - 1, -alpha, -beta, -color);
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
