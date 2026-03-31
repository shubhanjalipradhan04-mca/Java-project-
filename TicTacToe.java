import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame {
 JButton[] buttons = new JButton[9];
 boolean isXTurn = true;
 boolean gameOver = false;
 JLabel statusLabel;

 public TicTacToe() {
 setTitle("Tic-Tac-Toe");
 setSize(300, 350);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(new GridLayout(5, 1, 10, 10));

 statusLabel = new JLabel("X's Turn");
 add(statusLabel);

 JPanel board = new JPanel();
 board.setLayout(new GridLayout(3, 3, 5, 5));

 for (int i = 0; i < 9; i++) {
 buttons[i] = new JButton();
 buttons[i].addActionListener(new ButtonListener(i));
 board.add(buttons[i]);
 }

 add(board);
 JButton newGame = new JButton("New Game");
 newGame.addActionListener(e -> newGame());
 add(newGame);

 setLocationRelativeTo(null);
 }

 class ButtonListener implements ActionListener {
 int index;
 ButtonListener(int index) { this.index = index; }
 
 public void actionPerformed(ActionEvent e) {
 if (!gameOver && buttons[index].getText().isEmpty()) {
 buttons[index].setText(isXTurn ? "X" : "O");
 
 if (checkWinner()) {
 gameOver = true;
 statusLabel.setText(isXTurn ? "X Wins!" : "O Wins!");
 return;
 } else if (isBoardFull()) {
 gameOver = true;
 statusLabel.setText("Draw!");
 return;
 }
 
 isXTurn = !isXTurn;
 statusLabel.setText(isXTurn ? "X's Turn" : "O's Turn");
 }
 }
 }

 boolean checkWinner() {
 String[][] patterns = {
 {"0","1","2"},{"3","4","5"},{"6","7","8"},
 {"0","3","6"},{"1","4","7"},{"2","5","8"},
 {"0","4","8"},{"2","4","6"}
 };
 for (String[] p : patterns) {
 if (!buttons[Integer.parseInt(p[0])].getText().isEmpty() &&
 buttons[Integer.parseInt(p[0])].getText().equals(buttons[Integer.parseInt(p[1])].getText()) &&
 buttons[Integer.parseInt(p[0])].getText().equals(buttons[Integer.parseInt(p[2])].getText())) {
 return true;
 }
 }
 return false;
 }

 boolean isBoardFull() {
 for (JButton b : buttons) if (b.getText().isEmpty()) return false;
 return true;
 }

 void newGame() {
 for (int i = 0; i < 9; i++) buttons[i].setText("");
 isXTurn = true;
 gameOver = false;
 statusLabel.setText("X's Turn");
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> new TicTacToe().setVisible(true));
 }
}