import java.util.ArrayList;

public class Board {
	/**
	INITIALIZATION:
		board -> 3x3 board for checking
		player -> symbol of player
		winnner -> symbol of the winner
		moves -> how many moves did the current player do
		finished -> checker if the game has already ended
		availableMoves
		takenMove
	**/

	char[][] board;
	char player;
	char winner;
	int moves;
	boolean finished;
	ArrayList<Integer> availableMoves;
	ArrayList<Integer> takenMove;

	public Board() {
		this.board = new char[3][3];
		this.player = 'x';
		this.winner = 'e';
		this.finished = false;
		this.moves = 0;
		this.availableMoves = new ArrayList<Integer>();
		this.takenMove = new ArrayList<Integer>();


		// set all board pieces with empty (no player has marked it yet)
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				this.board[i][j] = 'e';
			}
		}

		// initialize empty cells that could be marked
		for (int i=0; i<9; i++) {
			this.availableMoves.add(i);
		}
	}

	// command-line view of the board
	void printBoard() {
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				System.out.print(this.board[i][j] + " ");
			}
			System.out.println();
		}
	}

	void setPlayer(char player) {
		this.player = player;
		System.out.println("called");
	}

	// mark move of the player and remove it from all possible moves
	void move(int x, int y) {
		this.board[x][y] = this.player;
		this.moves++;

		this.availableMoves.remove(Integer.valueOf(x * 3 + y));
		if (this.player == 'o') {
			this.takenMove.add(Integer.valueOf(x * 3 + y));
		}

		this.checkWinner();
		if (!this.finished) this.player = this.player == 'x' ? 'o' : 'x';
	}

	void checkWinner() {
		// horizontal checking
		if (this.board[0][0] == this.board[0][1] && this.board[0][1] == this.board[0][2] && this.board[0][0] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}
		
		if (this.board[1][0] == this.board[1][1] && this.board[1][1] == this.board[1][2] && this.board[1][0] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		if (this.board[2][0] == this.board[2][1] && this.board[2][1] == this.board[2][2] && this.board[2][0] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		// vertical checking
		if (this.board[0][0] == this.board[1][0] && this.board[1][0] == this.board[2][0] && this.board[0][0] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		if (this.board[0][1] == this.board[1][1] && this.board[1][1] == this.board[2][1] && this.board[0][1] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		if (this.board[0][2] == this.board[1][2] && this.board[1][2] == this.board[2][2] && this.board[0][2] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		// diagonal checking
		if (this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2] && this.board[0][0] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		if (this.board[0][2] == this.board[1][1] && this.board[1][1] == this.board[2][0] && this.board[1][1] != 'e') {
			this.finished = true;
			this.winner = this.player;
		}

		// draw
		if (this.availableMoves.size() == 0 && this.winner == 'e') {
			this.finished = true;
		}
	}

	// command-line printing of the winner
	void printWinner() {
		if (this.winner != 'e') System.out.println(this.player + " WINS!");
		else System.out.println("DRAW!");
	}

	// returns a copy of the board
	Board copyThis() {
		Board copy = new Board();

		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				copy.board[i][j] = this.board[i][j];
			}
		}

		copy.player = this.player;
		copy.winner = this.winner;
		copy.availableMoves = new ArrayList<Integer>();
		copy.availableMoves.addAll(this.availableMoves);
		copy.moves = this.moves;
		copy.finished = this.finished;

		return copy;
	}
}
