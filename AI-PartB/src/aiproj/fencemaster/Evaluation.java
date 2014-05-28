package aiproj.fencemaster;

public class Evaluation {
	public static int result = 0;	
	
	public static int evaluator(Game g) {
		int adjacencyScore = 0;
		// Check if Winning Conditions Met 
		int winner = TestWin.checkWinner(g);
		
		if (winner == g.getPlayer()) return Integer.MAX_VALUE;
		
		if (winner == g.getEnemy()) return -Integer.MAX_VALUE;
		
	
		//System.out.println(result);
		return 5 * control(g) + position(g);
	}
	
	public static int position(Game g) {
		int score = 0;
		
		for (int i = 0; i < g.getMaxSize(); i++) {
			for (int j = 0; j < g.getRowLength()[i]; j++) {
				if (g.getBoard()[i][j] == g.getPlayer()) {
					score += g.positionScoreArray[i][j];
				}
				if (g.getBoard()[i][j] == g.getEnemy()) {
					score -= g.positionScoreArray[i][j];
				}
			}
		}
		
		return score;
	}
	
	/**
	 * FEATURE: Control 
	 * This measures the control we have over a piece's mobility to connect. 
	 * 
	 * @param g, Game
	 * @return Control Score
	 */
	public static int control(Game g) {
		int score = 0;
		int clusterCount = 0;
		for (int i = 0; i < g.getMaxSize(); i++) {
			for (int j = 0; j < g.getRowLength()[i]; j++) {
				
				if (g.getBoard()[i][j] == g.getPlayer()) {
					// we penalise clustering around player piece
					clusterCount = g.adjacentCount(g.getEnemy(), i, j);
					if (clusterCount > 1)
						score -= factorial(clusterCount);
				}
				
				if (g.getBoard()[i][j] == Piece.EMPTY || g.getBoard()[i][j] == g.getEnemy()) {
					// we award clustering around these pieces
					clusterCount = g.adjacentCount(g.getPlayer(), i, j);
					score += factorial(clusterCount);
				}
			}
		}
		
		return score;
	}
	
	public static int factorial(int n) {
		if (n == 0)
			return 1;
		else
			return n * factorial(n-1);
	}
}
