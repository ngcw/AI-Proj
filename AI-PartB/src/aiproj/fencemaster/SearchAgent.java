package aiproj.fencemaster;

import java.util.LinkedList;

public class SearchAgent {
	
	public static int maxPly = 3;
	
	public static int negamaxABP(Game g, int depth, int alpha, int beta, int color) {		
		
		if (depth == 0 || g.terminalGame()) {
			return color * Evaluation.evaluator(g);
		}
		
		int bestValue = Integer.MIN_VALUE;
		int player = color == 1? g.getPlayer() : g.getEnemy();
		
		LinkedList<Move> moves = g.generateMoves(player);
		
		for (Move m : moves) {
			g.update(m);
			int val = - SearchAgent.negamaxABP(g, depth - 1, -beta, -alpha, -color);
			//System.out.println("Score:" + val);
			//System.out.println("ALPHA:" + alpha);
			bestValue = Math.max(bestValue, val);
			alpha = Math.max(alpha, val);
			g.revertUpdate(m);
			if (alpha >= beta) {
				//System.out.println("Pruning Occured!");
				break;
			}
		}
		return bestValue;
	}
	
}
