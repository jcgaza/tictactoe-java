import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI implements ActionListener {
	JFrame frame;
	JPanel mainPanel;
	JButton[][] boxes;
	JButton[][] buttons;
	JButton startButton;
	Board board;

	boolean isAI;
	boolean isX;
	boolean isFirst;

	public GUI() {
		this.boxes = new JButton[3][3];
		this.buttons = new JButton[3][2];
		this.board = null;

		this.isAI = false;
		this.isX = true;
		this.isFirst = true;

		frame = new JFrame("Minimax");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(300, 430));
		frame.setLayout(null);

		this.mainPanel = new JPanel();
		this.mainPanel.setSize(new Dimension(300, 425));
		this.mainPanel.setLayout(null);

		addBoxes();
		addButtons();

		frame.add(this.mainPanel);
		frame.pack();
		frame.setVisible(true);
	}

	// create clickable boxes
	void addBoxes() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.boxes[i][j] = new JButton();
				this.boxes[i][j].setBounds(i*100, j*100, 100, 100);
				this.boxes[i][j].setBackground(Color.WHITE);
				this.boxes[i][j].setEnabled(false);
				this.boxes[i][j].addActionListener(this);
				this.mainPanel.add(this.boxes[i][j]);
			}
		}
	}

	// create boxes for options
	void addButtons() {
		buttons[0][0] = new JButton("VS SELF");
		buttons[0][1] = new JButton("VS AI");
		buttons[1][0] = new JButton("AS RED");
		buttons[1][1] = new JButton("AS BLUE");
		buttons[2][0] = new JButton("FIRST");
		buttons[2][1] = new JButton("SECOND");

		startButton = new JButton("START!");
		startButton.setBounds(0,375, 300, 25);
		startButton.addActionListener(this);
		mainPanel.add(startButton);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				buttons[i][j].setBounds((150*j), (25*i)+300, 150, 25);
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].addActionListener(this);
				mainPanel.add(buttons[i][j]);
			}
		}
	}

	// enable/disable boxes
	void enableBoxes(boolean bool) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boxes[i][j].setEnabled(bool);
			}
		}

		if (!bool) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					boxes[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}

	// enable/disable buttons
	void enableButtons(boolean bool) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				buttons[i][j].setEnabled(bool);
			}
		}
		startButton.setEnabled(bool);

		if (bool) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					buttons[i][j].setBackground(Color.WHITE);
				}
			}

			startButton.setBackground(Color.WHITE);
		}
	}

	// recolor boxes depends on board placements
	void recolor() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.board[i][j] == 'x') {
					boxes[i][j].setBackground(Color.RED);
					boxes[i][j].setEnabled(false);
				} else if (board.board[i][j] == 'o') {
					boxes[i][j].setBackground(Color.BLUE);
					boxes[i][j].setEnabled(false);
				} else boxes[i][j].setBackground(Color.WHITE);
			}
		}
	}

	// Call the Minimax Program to start the Minimax algorithm
	void moveAI() {
		// Change this for Alpha-beta Pruning
		AlphaBeta m = new AlphaBeta();
		m.minimax(board, Integer.MIN_VALUE, Integer.MAX_VALUE, board.player);
		
		// Minimax m = new Minimax();
		// m.minimax(board, board.player);
		recolor();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (boxes[i][j].equals(e.getSource())) {
					boxes[i][j].setBackground(board.player == 'x' ? Color.RED : Color.BLUE);
					boxes[i][j].setEnabled(false);

					if (isAI && isX && board.player == 'x') {
						board.move(i, j);
						moveAI();
					}

					if (isAI && !isX && board.player == 'o') {
						board.move(i, j);
					 	moveAI();
					}

					if (!isAI) {
						board.move(i,j);
						board.setPlayer(board.player == 'x' ? 'x' : 'o');
					}

					if (board.finished) {
						JOptionPane.showMessageDialog(frame, 
							board.winner == 'x' ? 
								"RED WINS!" 
							: board.winner == 'o' ? 
								"BLUE WINS!" 
							: "DRAW!");
						enableBoxes(false);
						enableButtons(true);
					}
				}
			}
		}

		if (buttons[0][0].equals(e.getSource())) {
			isAI = false;
			buttons[0][0].setBackground(Color.ORANGE);
			buttons[0][1].setBackground(Color.WHITE);
		} else if (buttons[0][1].equals(e.getSource())) {
			isAI = true;
			buttons[0][1].setBackground(Color.ORANGE);
			buttons[0][0].setBackground(Color.WHITE);
		} else if (buttons[1][0].equals(e.getSource())) {
			isX = true;
			buttons[1][0].setBackground(Color.ORANGE);
			buttons[1][1].setBackground(Color.WHITE);
		} else if (buttons[1][1].equals(e.getSource())) {
			isX = false;
			buttons[1][1].setBackground(Color.ORANGE);
			buttons[1][0].setBackground(Color.WHITE);
		} else if (buttons[2][0].equals(e.getSource())) {
			isFirst = true;
			buttons[2][0].setBackground(Color.ORANGE);
			buttons[2][1].setBackground(Color.WHITE);
		} else if (buttons[2][1].equals(e.getSource())) {
			isFirst = false;
			buttons[2][1].setBackground(Color.ORANGE);
			buttons[2][0].setBackground(Color.WHITE);
		} else if (startButton.equals(e.getSource())) {
			board = new Board();

			if (isX) board.player = 'x';
			else board.player = 'o';

			if (isAI && !isFirst) {
				board.player = board.player == 'x' ? 'o' : 'x';
				board.move(1, 1);
				recolor();
			} 

			enableBoxes(true);
			enableButtons(false);
		}
	}
}
