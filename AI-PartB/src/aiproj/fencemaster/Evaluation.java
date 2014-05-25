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
		
		int score = 0;
		
		for (int i = 0; i < g.getSize(); i++) {
			for (int j = 0; j < g.getRowLength()[i]; j++) {
				if (g.getBoard()[i][j] == g.getPlayer())
					score++;
				else if (g.getBoard()[i][j] == g.getEnemy())
					score--;
			}
		}
			
		return score;
	}
	
	
}
