import java.util.Scanner;

public class Minimax {
	int minimax(Board currentBoard, char currentPlayer) {
		if (currentBoard.finished) return utility(currentBoard, currentPlayer);
		if (currentBoard.player == currentPlayer) return max_node(currentBoard, currentPlayer);
		else return min_node(currentBoard, currentPlayer);
	}

	int utility(Board currentBoard, char player) {
		char vs = player == 'x' ? 'o' : 'x';
		if (currentBoard.finished && currentBoard.winner == player) return 1;
		else if (currentBoard.finished && currentBoard.winner == vs) return -1;
		else return 0;
	}

	int max_node(Board currentBoard, char currentPlayer) {
		int m = Integer.MIN_VALUE;
		int index = -1;

		for (Integer moves : currentBoard.availableMoves) {
			Board newBoard = currentBoard.copyThis();
			newBoard.move(moves/3, moves%3);

			int value = minimax(newBoard, currentPlayer);
			if (value >= m) {
				m = value;
				index = moves;
			}
		}

		currentBoard.move(index/3, index%3);
		return m;
	}

	int min_node(Board currentBoard, char currentPlayer) {
		int m = Integer.MAX_VALUE;
		int index = -1;

		for (Integer moves : currentBoard.availableMoves) {
			Board newBoard = currentBoard.copyThis();
			newBoard.move(moves/3, moves%3);

			int value = minimax(newBoard, currentPlayer);
			if (value <= m) {
				m = value;
				index = moves;
			}
		}

		currentBoard.move(index/3, index%3);
		return m;
	}

	public static void main(String[] args) {
		GUI g = new GUI();
	}
}
