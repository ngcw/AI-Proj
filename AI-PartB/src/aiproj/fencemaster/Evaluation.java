package aiproj.fencemaster;

public class Evaluation {

	/**
	 * OBJECTIVE: favour piece count.
	 * 
	 * @param g, game
	 * 
	 * @return score of game state.
	 */
	public static int naiveEvaluator(Game g) {
		
		int result = TestWin.checkWinner(g);
		
		if (result == g.getPlayer() || result == g.getEnemy())
			return Integer.MAX_VALUE;
		else
			return 0;
			
	}
	
	
}
