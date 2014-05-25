package aiproj.fencemaster;

import java.io.PrintStream;


public class Ngcw implements Player, Piece{
	Game g;
	static int instanceCount = 0;
	static final int maxInstanceCount = 2;
	
	@Override
	public int getWinner() {
		return TestWin.checkWinner(g);
	}

	@Override
	public int init(int n, int p) {
		this.g = new Game(n, p);
		if (Ngcw.instanceCount >= Ngcw.maxInstanceCount) 
			return -1;
		else 
			Ngcw.instanceCount++;
		return 0;
	}

	@Override
	public Move makeMove() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public int opponentMove(Move m) {		
		// Legal Opponent Check
		if (m.P != g.getEnemy())
			return -1;
		
		// SWAP CHECK
		if (g.canSwap()) {
			if (m.IsSwap) {
				// writing off the board.
				if (m.Row >= g.getSize() || m.Col >= g.getMaxSize() || g.getBoard()[m.Row][m.Col] == Piece.INVALID)
					return -1;
				// attempting to swap with empty.
				if (g.getBoard()[m.Row][m.Col] == Piece.EMPTY)
					return -1;
				// attempting to swap with self.
				if (g.getBoard()[m.Row][m.Col] == m.P)
					return -1;
				
			} else {
				// Swap Move Update
				g.update(m);
				return 0;
			}
		}
		
		// ORDINARY MOVE CHECK
		// writting off the board.
		if (m.Row >= g.getSize() || m.Col >= g.getMaxSize() || g.getBoard()[m.Row][m.Col] == Piece.INVALID)
			return -1;
		// attempting to move to an occupied spot.
		if (g.getBoard()[m.Row][m.Col] != Piece.EMPTY)
			return -1;
		
		// Ordinary Move Update
		g.update(m);
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		for (int i = 0; i < g.getMaxSize(); i++) {
			for (int j = 0; i < g.getRowLength()[i]; i++) {
				switch(g.getBoard()[i][j]) {
					case Piece.BLACK:
						output.print("B ");
						break;
					case Piece.WHITE:
						output.print("W ");
						break;
					case Piece.EMPTY:
						output.print("- ");
						break;
					default:
						output.print("? ");
						break;
				}
			}
		}
		output.print("\n");
	}

}
