import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CurrencyConverter extends JFrame {
 JComboBox<String> fromCombo, toCombo;
 JTextField amountField, resultField;
 JButton convertButton, clearButton;

 public CurrencyConverter() {
 setTitle("Currency Converter");
 setSize(350, 200);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(new GridLayout(6, 2, 10, 10));

 String[] currencies = {"USD", "EUR", "GBP", "INR", "JPY"};
 fromCombo = new JComboBox<>(currencies);
 toCombo = new JComboBox<>(currencies);
 fromCombo.setSelectedIndex(0);
 toCombo.setSelectedIndex(3);

 amountField = new JTextField();
 resultField = new JTextField();
 resultField.setEditable(false);

 convertButton = new JButton("Convert");
 clearButton = new JButton("Clear");

 convertButton.addActionListener(new ConvertListener());
 clearButton.addActionListener(e -> clearAll());

 add(new JLabel("From:"));
 add(fromCombo);
 add(new JLabel("To:"));
 add(toCombo);
 add(new JLabel("Amount:"));
 add(amountField);
 add(convertButton);
 add(clearButton);
 add(new JLabel("Result:"));
 add(resultField);

 setLocationRelativeTo(null);
 }

 void clearAll() {
 amountField.setText("");
 resultField.setText("");
 }

 class ConvertListener implements ActionListener {
 public void actionPerformed(ActionEvent e) {
 try {
 double amount = Double.parseDouble(amountField.getText());
 String from = (String) fromCombo.getSelectedItem();
 String to = (String) toCombo.getSelectedItem();
 
 double result = amount;
 if (from.equals("EUR")) result = amount * 1.18;
 else if (from.equals("GBP")) result = amount * 1.27;
 else if (from.equals("INR")) result = amount / 83;
 else if (from.equals("JPY")) result = amount / 149;
 
 if (to.equals("EUR")) result = result * 0.85;
 else if (to.equals("GBP")) result = result * 0.79;
 else if (to.equals("INR")) result = result * 83;
 else if (to.equals("JPY")) result = result * 149;
 
 resultField.setText(String.format("%.2f", result));
 } catch (Exception ex) {
 JOptionPane.showMessageDialog(null, "Invalid amount!");
 }
 }
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> new CurrencyConverter().setVisible(true));
 }
}