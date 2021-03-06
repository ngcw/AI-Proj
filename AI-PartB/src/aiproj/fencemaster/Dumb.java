package aiproj.fencemaster;

import java.io.PrintStream;
import java.util.LinkedList;

public class Dumb implements Player, Piece {
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
		LinkedList<Move> moves = g.generateMoves(g.getPlayer());
		Move bestMove = moves.peekLast();
		g.update(bestMove);
		// System.out.println("BEST SCORE:" + bestScore);
		return bestMove;
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
				if (m.Row >= g.getMaxSize() || m.Col >= g.getRowLength()[m.Row] || g.getBoard()[m.Row][m.Col] == Piece.INVALID) {
					System.out.println("ERROR: Attempting to swap with Piece that does not exist!");
					return -1;
				}
				// attempting to swap with empty.
				if (g.getBoard()[m.Row][m.Col] == Piece.EMPTY) {
					System.out.println("ERROR: Attempting to swap with EMPTY piece");
					return -1;
				}
				// attempting to swap with self.
				if (g.getBoard()[m.Row][m.Col] == m.P) {
					System.out.println("ERROR: Attempting to swap with SELF");
					return -1;
				}
			} else {
				// Swap Move Update
				g.update(m);
				return 0;
			}
		}
		
		// ORDINARY MOVE CHECK
		// writting off the board.
		if (m.Row >= g.getMaxSize() || m.Col >= g.getRowLength()[m.Row] || g.getBoard()[m.Row][m.Col] == Piece.INVALID) {
			System.out.println("ERROR: Attempting to place outside board!");
			return -1;
		}
		// attempting to move to an occupied spot.
		if (g.getBoard()[m.Row][m.Col] != Piece.EMPTY) {
			System.out.println("ERROR: Attempting to place on an OCCUPIED piece");
			return -1;
		}
		
		// Ordinary Move Update
		g.update(m);
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		for (int i = 0; i < g.getMaxSize(); i++) {
			int whiteBuffer = g.getMaxSize() - g.getRowLength()[i];
			for (int j = 0; j < whiteBuffer; j++) 
				output.print(" ");
			for (int j = 0; j < g.getRowLength()[i]; j++) {
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
			output.print("\n");
		}
		output.print("\n");
	}

}
