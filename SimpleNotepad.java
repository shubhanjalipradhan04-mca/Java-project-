import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleNotepad extends JFrame {
 private JTextArea textArea;
 private JMenuBar menuBar;
 private JMenu fileMenu, editMenu;
 private JMenuItem newItem, openItem, saveItem, saveAsItem, exitItem;
 private String currentFilePath = "";
 private boolean hasUnsavedChanges = false;

 public SimpleNotepad() {
 setTitle("Simple Notepad");
 setSize(600, 400);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(new BorderLayout());

 // Text area with scroll pane
 JScrollPane scrollPane = new JScrollPane(new JTextArea());
 textArea = (JTextArea) scrollPane.getViewport().getView();
 textArea.setFont(new Font("Arial", Font.PLAIN, 14));
 add(scrollPane, BorderLayout.CENTER);

 // Create menu bar
 createMenuBar();

 // Add window listener for unsaved changes
 addWindowListener(new WindowAdapter() {
 @Override
 public void windowClosing(WindowEvent e) {
 if (hasUnsavedChanges) {
 int choice = JOptionPane.showConfirmDialog(SimpleNotepad.this, 
 "Do you want to save changes before exiting?", 
 "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
 
 if (choice == JOptionPane.YES_OPTION) {
 saveFile();
 } else if (choice == JOptionPane.CANCEL_OPTION) {
 return;
 }
 }
 dispose();
 }
 });

 setLocationRelativeTo(null);
 }

 private void createMenuBar() {
 menuBar = new JMenuBar();

 // File menu
 fileMenu = new JMenu("File");
 newItem = new JMenuItem("New");
 newItem.addActionListener(e -> newFile());
 openItem = new JMenuItem("Open");
 openItem.addActionListener(e -> openFile());
 saveItem = new JMenuItem("Save");
 saveItem.addActionListener(e -> saveFile());
 saveAsItem = new JMenuItem("Save As...");
 saveAsItem.addActionListener(e -> saveFileAs());
 exitItem = new JMenuItem("Exit");
 exitItem.addActionListener(e -> System.exit(0));

 fileMenu.add(newItem);
 fileMenu.add(openItem);
 fileMenu.addSeparator();
 fileMenu.add(saveItem);
 fileMenu.add(saveAsItem);
 fileMenu.addSeparator();
 fileMenu.add(exitItem);

 // Edit menu
 editMenu = new JMenu("Edit");
JMenuItem cutItem = new JMenuItem("Cut");
JMenuItem copyItem = new JMenuItem("Copy");
JMenuItem pasteItem = new JMenuItem("Paste");

 cutItem.addActionListener(e -> textArea.cut());
 copyItem.addActionListener(e -> textArea.copy());
 pasteItem.addActionListener(e -> textArea.paste());

 editMenu.add(cutItem);
 editMenu.add(copyItem);
 editMenu.add(pasteItem);

 menuBar.add(fileMenu);
 menuBar.add(editMenu);

 setJMenuBar(menuBar);
 }

 private void newFile() {
 if (hasUnsavedChanges) {
 int choice = JOptionPane.showConfirmDialog(this, 
 "Save changes before creating new file?", 
 "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
 
 if (choice == JOptionPane.YES_OPTION) {
 saveFile();
 } else if (choice == JOptionPane.CANCEL_OPTION) {
 return;
 }
 }

 textArea.setText("");
 currentFilePath = "";
 hasUnsavedChanges = false;
 setTitle("Simple Notepad - New Document");
 }

 private void openFile() {
 JFileChooser fileChooser = new JFileChooser();
 int result = fileChooser.showOpenDialog(this);
 
 if (result == JFileChooser.APPROVE_OPTION) {
 currentFilePath = fileChooser.getSelectedFile().getAbsolutePath();
 try {
 String content = readFile(currentFilePath);
 textArea.setText(content);
 hasUnsavedChanges = false;
 setTitle("Simple Notepad - " + fileChooser.getSelectedFile().getName());
 } catch (IOException ex) {
 JOptionPane.showMessageDialog(this, 
 "Error opening file: " + ex.getMessage(), 
 "Error", JOptionPane.ERROR_MESSAGE);
 }
 }
 }

 private void saveFile() {
 if (currentFilePath.isEmpty()) {
 saveFileAs();
 } else {
 try {
 writeToFile(currentFilePath, textArea.getText());
 hasUnsavedChanges = false;
 } catch (IOException ex) {
 JOptionPane.showMessageDialog(this, 
 "Error saving file: " + ex.getMessage(), 
 "Error", JOptionPane.ERROR_MESSAGE);
 }
 }
 }

 private void saveFileAs() {
 JFileChooser fileChooser = new JFileChooser();
 int result = fileChooser.showSaveDialog(this);
 
 if (result == JFileChooser.APPROVE_OPTION) {
 currentFilePath = fileChooser.getSelectedFile().getAbsolutePath();
 try {
 writeToFile(currentFilePath, textArea.getText());
 hasUnsavedChanges = false;
 setTitle("Simple Notepad - " + fileChooser.getSelectedFile().getName());
 } catch (IOException ex) {
 JOptionPane.showMessageDialog(this, 
 "Error saving file: " + ex.getMessage(), 
 "Error", JOptionPane.ERROR_MESSAGE);
 }
 }
 }

 private String readFile(String filename) throws IOException {
 StringBuilder content = new StringBuilder();
 try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
 String line;
 while ((line = reader.readLine()) != null) {
 content.append(line).append("\n");
 }
 }
 return content.toString();
 }

 private void writeToFile(String filename, String content) throws IOException {
 try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
 writer.write(content);
 }
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> {
 new SimpleNotepad().setVisible(true);
 });
 }
}