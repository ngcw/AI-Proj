package aiproj.fencemaster;

public class Evaluation {
	public static int result = 0;
	
	/**
	 * OBJECTIVE: favour piece count.
	 * 
	 * @param g, game
	 * 
	 * @return score of game state.
	 */
	public static int naiveEvaluator(Game g) {
		
		int result = TestWin.checkWinner(g);
		
		if (result == g.getPlayer())
			return Integer.MAX_VALUE;
		else if (result == g.getEnemy())
			return Integer.MIN_VALUE;
		else 
			return 0;
	}
	
	public static int evaluator(Game g) {
		int result = 0;
		
		// Check if Winning Conditions Met 
		int winner = TestWin.checkWinner(g);
		
		if (winner == g.getPlayer())
			return Integer.MAX_VALUE;
		else if (winner == g.getEnemy())
			return Integer.MIN_VALUE;
		
		// Award Adjacency up to 2.
		for (int i = 0; i < g.getMaxSize(); i++) {
			for (int j = 0; j < g.getRowLength()[i]; j++) {
				int adjacency = g.adjacentEnemyCount(g.getPlayer(), i, j);
				if (adjacency > 0 && adjacency < 3) {
					result += adjacency * 10;
				}
				adjacency = g.adjacentEnemyCount(g.getEnemy(), i, j);
				if (adjacency > 1) {
					result -= adjacency * -10;
				}
				
			}
		}
		return result;
	}
	
}
