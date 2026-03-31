import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PizzaOrderSystem extends JFrame {
 private Map<String, Double> menuItems;
 private Map<String, Integer> quantities;
 private JList<String> orderList;
 private DefaultListModel<String> listModel;
 private JLabel totalLabel;
 private JLabel statusLabel;
 private JTextArea orderTextArea;
 private JButton addButton, removeButton, clearButton, finalizeButton;
 private JTextField quantityField;
 private String selectedItem = "";
 
 public PizzaOrderSystem() {
 menuItems = new HashMap<>();
 quantities = new HashMap<>();
 initializeMenu();
 setupUI();
 }

 private void initializeMenu() {
 menuItems.put("Margherita Pizza", 12.99);
 menuItems.put("Pepperoni Pizza", 14.99);
 menuItems.put("Veggie Pizza", 13.99);
 menuItems.put("BBQ Chicken Pizza", 15.99);
 menuItems.put("Cheese Garlic Bread", 4.99);
 menuItems.put("Garden Salad", 6.99);
 menuItems.put("Coke (330ml)", 2.99);
 menuItems.put("Sprite (330ml)", 2.99);
 menuItems.put("Pepsi (330ml)", 2.99);
 menuItems.put("Ice Cream", 3.99);
 }

 private void setupUI() {
 setTitle("Pizza Order System");
 setSize(800, 600);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);

 // Quantity input panel
 JPanel inputPanel = new JPanel(new BorderLayout());
 inputPanel.setBorder(BorderFactory.createTitledBorder("Order Item"));
 
 JLabel itemLabel = new JLabel("Select Item: ", SwingConstants.LEFT);
 quantityField = new JTextField("1", 5);
 quantityField.setToolTipText("Enter quantity (must be a positive number)");
 JButton selectButton = new JButton("Select Item");
 selectButton.addActionListener(e -> selectMenuItem());
 
 inputPanel.add(itemLabel, BorderLayout.NORTH);
 inputPanel.add(quantityField, BorderLayout.CENTER);
 inputPanel.add(selectButton, BorderLayout.EAST);

 // Order list
 listModel = new DefaultListModel<>();
 orderList = new JList<>(listModel);
 orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 

 // Order details panel
 orderTextArea = new JTextArea(8, 30);
 orderTextArea.setEditable(false);
 orderTextArea.setFont(new Font("Courier", Font.PLAIN, 12));

 // Button panel
 JPanel buttonPanel = new JPanel(new FlowLayout());
 addButton = new JButton("Add Item");
 removeButton = new JButton("Remove Selected");
 clearButton = new JButton("Clear Order");
 finalizeButton = new JButton("Generate Bill");
 
 addButton.addActionListener(e -> addItem());
 removeButton.addActionListener(e -> removeItem());
 clearButton.addActionListener(e -> clearOrder());
 finalizeButton.addActionListener(e -> generateBill());
 
 buttonPanel.add(addButton);
 buttonPanel.add(removeButton);
 buttonPanel.add(clearButton);
 buttonPanel.add(finalizeButton);

 // Total label
 totalLabel = new JLabel("Total: $0.00", SwingConstants.CENTER);
 totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
 totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

 // Status label
 statusLabel = new JLabel("Welcome! Select an item to order", SwingConstants.CENTER);
 statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

 // Main panel
 JPanel mainPanel = new JPanel(new BorderLayout());
 mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

 // GridBagLayout for main content
 JPanel contentPanel = new JPanel(new GridBagLayout());
 GridBagConstraints gbc = new GridBagConstraints();
 gbc.insets = new Insets(10, 10, 10, 10);
 gbc.fill = GridBagConstraints.HORIZONTAL;

 gbc.gridx = 0; gbc.gridy = 0;
 contentPanel.add(inputPanel, gbc);

 gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
 contentPanel.add(new JScrollPane(orderList), gbc);

 gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 0.0;
 contentPanel.add(orderTextArea, gbc);

 mainPanel.add(contentPanel, BorderLayout.CENTER);
 mainPanel.add(buttonPanel, BorderLayout.SOUTH);
 mainPanel.add(totalLabel, BorderLayout.NORTH);

 // Instructions
 JPanel instructionsPanel = new JPanel();
 instructionsPanel.setBorder(BorderFactory.createTitledBorder("Instructions"));
 JTextArea instructions = new JTextArea(
 "1. Enter quantity in the field\n" +
 "2. Click 'Select Item' (or choose from suggestions)\n" +
 "3. Click 'Add Item' to add to order\n" +
 "4. View your order in the list\n" +
 "5. Click 'Generate Bill' for final bill"
 );
 instructions.setEditable(false);
 instructions.setLineWrap(true);
 instructions.setWrapStyleWord(true);
 instructionsPanel.add(instructions);

 add(instructionsPanel, BorderLayout.PAGE_START);
 add(mainPanel);
 }

 private void selectMenuItem() {
 if (!selectedItem.isEmpty()) {
 try {
 String quantityText = quantityField.getText().trim();
 if (!quantityText.matches("\\d+")) {
 JOptionPane.showMessageDialog(this, 
 "Please enter a valid positive number!", 
 "Invalid Input", JOptionPane.ERROR_MESSAGE);
 quantityField.requestFocus();
 return;
 }
 
 int quantity = Integer.parseInt(quantityText);
 if (quantity <= 0) {
 JOptionPane.showMessageDialog(this, 
 "Quantity must be greater than 0!", 
 "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
 return;
 }
 
 quantities.put(selectedItem, quantity);
 updateOrderDisplay();
 statusLabel.setText("Added: " + quantity + " x " + selectedItem);
 
 } catch (NumberFormatException e) {
 JOptionPane.showMessageDialog(this, 
 "Please enter a valid quantity!", 
 "Error", JOptionPane.ERROR_MESSAGE);
 }
 } else {
 JOptionPane.showMessageDialog(this, 
 "Please select an item first!", 
 "No Item Selected", JOptionPane.WARNING_MESSAGE);
 }
 }

 private void addItem() {
 if (selectedItem.isEmpty() || quantityField.getText().trim().isEmpty()) {
 JOptionPane.showMessageDialog(this, 
 "Please select an item and enter quantity!", 
 "Missing Information", JOptionPane.WARNING_MESSAGE);
 return;
 }
 
 try {
 String quantityText = quantityField.getText().trim();
 int quantity = Integer.parseInt(quantityText);
 
 if (quantity <= 0) {
 JOptionPane.showMessageDialog(this, 
 "Quantity must be greater than 0!", 
 "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
 quantityField.requestFocus();
 return;
 }
 
 quantities.put(selectedItem, quantity);
 updateOrderDisplay();
 statusLabel.setText("Added: " + quantity + " x " + selectedItem);
 quantityField.setText("");
 quantityField.requestFocus();
 
 } catch (NumberFormatException e) {
 JOptionPane.showMessageDialog(this, 
 "Please enter a valid quantity!", 
 "Invalid Input", JOptionPane.ERROR_MESSAGE);
 quantityField.requestFocus();
 }
 }

 private void removeItem() {
 String selected = (String) orderList.getSelectedValue();
 if (selected != null) {
 listModel.removeElement(selected);
 quantities.remove(selected);
 updateOrderDisplay();
 statusLabel.setText("Removed: " + selected);
 }
 }

 private void clearOrder() {
 listModel.clear();
 quantities.clear();
 updateOrderDisplay();
 statusLabel.setText("Order cleared");
 quantityField.requestFocus();
 }

 private void generateBill() {
 if (listModel.isEmpty()) {
 JOptionPane.showMessageDialog(this, 
 "Your order is empty!", 
 "No Order", JOptionPane.WARNING_MESSAGE);
 return;
 }

 StringBuilder bill = new StringBuilder();
 bill.append("\n======================== BILL ========================\n");
 bill.append("Date: ").append(new Date()).append("\n\n");
 
 double total = 0.0;
 for (int i = 0; i < listModel.getSize(); i++) {
 String line = listModel.getElementAt(i);
 if (line.contains(" x ")) {
 String[] parts = line.split(" x ");
 String itemName = parts[0];
 int quantity = Integer.parseInt(parts[1]);
 double price = menuItems.get(itemName);
 double itemTotal = price * quantity;
 bill.append(String.format("%s x %d = $%.2f\n", itemName, quantity, itemTotal));
 total += itemTotal;
 }
 }
 
 bill.append("\n------------------------------------------------------\n");
 bill.append(String.format("TOTAL: $%.2f\n", total));
 bill.append("======================================================");

 JOptionPane.showMessageDialog(this, bill.toString(), "Final Bill", 
 JOptionPane.INFORMATION_MESSAGE);
 
 int choice = JOptionPane.showConfirmDialog(this, 
 "Generate another bill?", "Continue Ordering", 
 JOptionPane.YES_NO_OPTION);
 
 if (choice == JOptionPane.YES_OPTION) {
 clearOrder();
 quantityField.requestFocus();
 }
 }

 private void updateOrderDisplay() {
 orderTextArea.setText("");
 updateTotal();
 
 for (int i = 0; i < listModel.getSize(); i++) {
 String line = listModel.getElementAt(i);
 orderTextArea.append(line + "\n");
 }
 }

 private void updateTotal() {
 double total = 0.0;
 for (int i = 0; i < listModel.getSize(); i++) {
 String line = listModel.getElementAt(i);
 if (line.contains(" x ")) {
 String[] parts = line.split(" x ");
 String itemName = parts[0];
 int quantity = Integer.parseInt(parts[1]);
 double price = menuItems.get(itemName);
 total += price * quantity;
 }
 }
 totalLabel.setText(String.format("Total: $%.2f", total));
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> {
 PizzaOrderSystem pizzaOrder = new PizzaOrderSystem();
 pizzaOrder.setVisible(true);
 });
 }
}