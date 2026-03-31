import javax.swing.*;
import java.awt.*;


public class BasicCalculator extends JFrame {
    private JTextField display;
    private JButton[] numberButtons = new JButton[10];
    private JButton[] operatorButtons = new JButton[4];
    private JButton equalButton, clearButton, decimalButton;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private char operator = '\0';
    private boolean firstNumberEntered = true;

    public BasicCalculator() {
        setTitle("Basic Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField("0", 20);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        // Number buttons (0-9)
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            final int index = i;
            numberButtons[i].addActionListener(e -> handleNumberInput(index));
            buttonPanel.add(numberButtons[i]);
        }

        // Operator buttons (+, -, *, /)
        String[] ops = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            operatorButtons[i] = new JButton(ops[i]);
            operatorButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            final int idx = i;
            operatorButtons[i].addActionListener(e -> handleOperator(ops[idx].charAt(0)));
            buttonPanel.add(operatorButtons[i]);
        }

        // Equal and Clear buttons
        equalButton = new JButton("=");
        equalButton.setFont(new Font("Arial", Font.BOLD, 18));
        equalButton.addActionListener(e -> calculateResult());
        buttonPanel.add(equalButton);

        clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.BOLD, 18));
        clearButton.addActionListener(e -> clearAll());
        buttonPanel.add(clearButton);

        decimalButton = new JButton(".");
        decimalButton.setFont(new Font("Arial", Font.BOLD, 18));
        decimalButton.addActionListener(e -> handleDecimal());
        buttonPanel.add(decimalButton);

        add(buttonPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void handleNumberInput(int number) {
        if (firstNumberEntered) {
            display.setText(String.valueOf(number));
            firstNumber = number;
            firstNumberEntered = false;
        } else {
            String current = display.getText();
            if (current.equals("0")) {
                display.setText(String.valueOf(number));
            } else {
                display.setText(current + number);
            }
        }
    }

    private void handleOperator(char op) {
        operator = op;
        firstNumber = Double.parseDouble(display.getText());
        firstNumberEntered = true;
    }

    private void handleDecimal() {
        if (firstNumberEntered) {
            display.setText("0.");
            firstNumber = 0.0;
            firstNumberEntered = false;
        } else {
            String current = display.getText();
            if (!current.contains(".")) {
                display.setText(current + ".");
            }
        }
    }

    private void calculateResult() {
        secondNumber = Double.parseDouble(display.getText());
        double result = 0;
        
        switch (operator) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }
        
        display.setText(String.valueOf(result));
        firstNumber = result;
        firstNumberEntered = true;
    }

    private void clearAll() {
        display.setText("0");
        firstNumber = 0;
        secondNumber = 0;
        operator = '\0';
        firstNumberEntered = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BasicCalculator().setVisible(true);
        });
    }
}