package aiproj.fencemaster;

public class Evaluation {
	public static int result = 0;
	
	public static int evaluator(Game g) {
		int result = 0;
		
		// Check if Winning Conditions Met 
		int winner = TestWin.checkWinner(g);
		
		if (winner == g.getPlayer())
			return Integer.MAX_VALUE;
		else if (winner == g.getEnemy())
			return -Integer.MAX_VALUE;
		
		for (int i = 0; i < g.getMaxSize(); i++) {
			for (int j = 0; j < g.getRowLength()[i]; j++) {
				
				// Adjacency Feature Scoring
				int adjacency = g.adjacentCount(g.getPlayer(), i, j);

				if (adjacency > 0 && adjacency < 3) {
					result += adjacency * 10;
				}
				
				if (g.getBoard()[i][j] == g.getPlayer() || 
						g.getBoard()[i][j] == Piece.EMPTY) {
					adjacency = g.adjacentCount(g.getEnemy(), i, j);
					if (adjacency > 3) {
						result -= adjacency * -10;
					}
				}
			
			
				
			}
		}
		//System.out.println(result);
		return result;
	}
	
}
