import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoList extends JFrame {
 JList<String> taskList;
 DefaultListModel<String> model;
 JTextField taskField;
 JButton addButton, deleteButton;

 public ToDoList() {
 setTitle("To-Do List");
 setSize(400, 300);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(new BorderLayout());

 model = new DefaultListModel<>();
 taskList = new JList<>(model);

 taskField = new JTextField();
 addButton = new JButton("Add");
 deleteButton = new JButton("Delete");

 addButton.addActionListener(new AddListener());
 deleteButton.addActionListener(new DeleteListener());

 add(new JScrollPane(taskList), BorderLayout.CENTER);
 add(taskField, BorderLayout.SOUTH);
 add(addButton, BorderLayout.EAST);
 add(deleteButton, BorderLayout.WEST);

 setLocationRelativeTo(null);
 }

 class AddListener implements ActionListener {
 public void actionPerformed(ActionEvent e) {
 String task = taskField.getText().trim();
 if (!task.isEmpty()) {
 model.addElement(task);
 taskField.setText("");
 }
 }
 }

 class DeleteListener implements ActionListener {
 public void actionPerformed(ActionEvent e) {
 int index = taskList.getSelectedIndex();
 if (index != -1) {
 model.removeElementAt(index);
 }
 }
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> new ToDoList().setVisible(true));
 }
}