package aiproj.fencemaster;

public class SearchAgent {
	
	public static int maxPly = 8; //we choose 8 because it takes 8 plys to beat an idiotic player.
	
	public static int maxMove(Game g, int d, int a, int b) {
		if (d == 0 || g.terminalGame())
			return Evaluation.naiveEvaluator(g);
		
		LinkedList<Move> moves = g.generateMoves(g.getPlayer());
		
		
		
		return 0;
	}
	
	public static int minMove(Game g, int d, int a, int b) {
		
		return 0;
	}
	
	public static Move negamax(Game g, int depth, int player) {
				
		return null;
	}
	
	public static Move negamaxabp(Game g, int depth, int player) {
		Move bestMove = null;
		
		return bestMove;
	}
	
	
}
