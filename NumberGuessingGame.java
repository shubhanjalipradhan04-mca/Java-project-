import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private JLabel guessLabel;
    private JLabel hintLabel;
    private JTextField guessField;
    private JButton guessButton, clearButton;
    private JLabel attemptsLabel;
    private int attempts = 0;
    private int secretNumber;
    private boolean gameOver = false;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Game info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        guessLabel = new JLabel("Enter your guess (1-100):", SwingConstants.CENTER);
        guessLabel.setFont(new Font("Arial", Font.BOLD, 16));

        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 18));

        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton.addActionListener(new GuessActionListener());

        clearButton = new JButton("New Game");
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.addActionListener(e -> newGame());

        hintLabel = new JLabel("", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hintLabel.setForeground(Color.BLUE);

        attemptsLabel = new JLabel("Attempts: 0", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        infoPanel.add(guessLabel);
        infoPanel.add(guessField);
        infoPanel.add(guessButton);
        infoPanel.add(clearButton);
        infoPanel.add(hintLabel);
        infoPanel.add(attemptsLabel);

        add(infoPanel, BorderLayout.CENTER);
        newGame();
        setLocationRelativeTo(null);
    }

    private void newGame() {
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;
        attempts = 0;
        gameOver = false;
        hintLabel.setText("");
        guessField.setText("");
        attemptsLabel.setText("Attempts: 0");
        guessField.requestFocus();
    }

    private class GuessActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;

            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                attemptsLabel.setText("Attempts: " + attempts);

                if (guess < 1 || guess > 100) {
                    JOptionPane.showMessageDialog(NumberGuessingGame.this, 
                        "Please enter a number between 1 and 100!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    guessField.setText("");
                    return;
                }

                if (guess == secretNumber) {
                    gameOver = true;
                    hintLabel.setText("🎉 Congratulations! You found it in " + attempts + " attempts!");
                    hintLabel.setForeground(Color.GREEN);
                    guessButton.setEnabled(false);
                    guessField.setEditable(false);
                    JOptionPane.showMessageDialog(NumberGuessingGame.this, 
                        "Great job! You guessed the number " + secretNumber + " in " + attempts + " attempts!", 
                        "You Win!", JOptionPane.INFORMATION_MESSAGE);
                } else if (guess < secretNumber) {
                    hintLabel.setText("Hint: Try a higher number! 💪");
                    hintLabel.setForeground(Color.ORANGE);
                } else {
                    hintLabel.setText("Hint: Try a lower number! 💪");
                    hintLabel.setForeground(Color.ORANGE);
                }

                guessField.setText("");
                guessField.requestFocus();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(NumberGuessingGame.this, 
                    "Please enter a valid number!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NumberGuessingGame().setVisible(true);
        });
    }
}