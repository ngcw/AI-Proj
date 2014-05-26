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
	
	
}
