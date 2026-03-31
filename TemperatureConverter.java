import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverter extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;
    private JComboBox<String> fromCombo;
    private JComboBox<String> toCombo;
    private JButton convertButton, clearButton;

    public TemperatureConverter() {
        setTitle("Temperature Converter");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel fromLabel = new JLabel("From:");
        fromCombo = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        fromCombo.setSelectedIndex(0);

        JLabel toLabel = new JLabel("To:");
        toCombo = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        toCombo.setSelectedIndex(1);

        JLabel inputLabel = new JLabel("Temperature:");
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(150, 30));

        convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertActionListener());

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearAll());

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(Color.BLUE);

        inputPanel.add(fromLabel);
        inputPanel.add(fromCombo);
        inputPanel.add(toLabel);
        inputPanel.add(toCombo);
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(convertButton);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void clearAll() {
        inputField.setText("");
        resultLabel.setText("");
        inputField.requestFocus();
    }

    private class ConvertActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double temperature = Double.parseDouble(inputField.getText());
                String from = (String) fromCombo.getSelectedItem();
                String to = (String) toCombo.getSelectedItem();

                double result;

                if (from.equals("Celsius")) {
                    if (to.equals("Fahrenheit")) {
                        result = temperature * 9 / 5 + 32;
                    } else if (to.equals("Kelvin")) {
                        result = temperature + 273.15;
                    } else {
                        result = temperature;
                    }
                } else if (from.equals("Fahrenheit")) {
                    if (to.equals("Celsius")) {
                        result = (temperature - 32) * 5 / 9;
                    } else if (to.equals("Kelvin")) {
                        result = (temperature - 32) * 5 / 9 + 273.15;
                    } else {
                        result = temperature;
                    }
                } else if (from.equals("Kelvin")) {
                    if (to.equals("Celsius")) {
                        result = temperature - 273.15;
                    } else if (to.equals("Fahrenheit")) {
                        result = (temperature - 273.15) * 9 / 5 + 32;
                    } else {
                        result = temperature;
                    }
                } else {
                    result = temperature;
                }

                resultLabel.setText(String.format("%.2f", result) + " " + to);
                
                // Show appropriate symbol
                String symbol = "";
                if (to.equals("Celsius")) symbol = "°C";
                else if (to.equals("Fahrenheit")) symbol = "°F";
                else if (to.equals("Kelvin")) symbol = "K";
                
                resultLabel.setText(String.format("%.2f %s", result, symbol));
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TemperatureConverter.this, 
                    "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TemperatureConverter().setVisible(true);
        });
    }
}