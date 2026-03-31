import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ContactAddressBook extends JFrame {
    private Map<String, Contact> contacts;
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private JTextField nameField, phoneField, emailField, addressField;
    private JButton addButton, editButton, deleteButton, clearButton;
    private JLabel statusLabel;
    
    public ContactAddressBook() {
        contacts = new HashMap<>();
        contactListModel = new DefaultListModel<>();
        setupUI();
        loadSampleContacts();
    }

    public static class Contact {
        String name, phone, email, address;
        
        public Contact(String name, String phone, String email, String address) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
        }
        
        @Override
        public String toString() {
            return name + " - " + phone;
        }
        
        public void update(String name, String phone, String email, String address) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
        }
    }

    private void setupUI() {
        setTitle("Contact Address Book");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Contact list
        contactList = new JList<>(contactListModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadContactDetails();
            }
        });

        // Input fields
        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        addressField = new JTextField(20);

        // Buttons
        addButton = new JButton("Add Contact");
        editButton = new JButton("Edit Contact");
        deleteButton = new JButton("Delete Contact");
        clearButton = new JButton("Clear Fields");

        // Status label
        statusLabel = new JLabel("Welcome to Contact Address Book", SwingConstants.CENTER);

        // Button actions
        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());
        clearButton.addActionListener(e -> clearFields());

        // Main panel layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Contacts"));
        leftPanel.add(new JScrollPane(contactList), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Input fields layout
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        rightPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        rightPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(addressField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Status bar
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        statusPanel.setBorder(BorderFactory.createEtchedBorder());

        // Add everything to frame
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // Keyboard shortcuts
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();
        
        inputMap.put(KeyStroke.getKeyStroke("control N"), "add");
        actionMap.put("add", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control E"), "edit");
        actionMap.put("edit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control D"), "delete");
        actionMap.put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });
    }

    private void loadSampleContacts() {
        // Add sample contacts for demonstration
        Contact contact1 = new Contact("John Doe", "555-1234", "john@email.com", "123 Main St, City");
        Contact contact2 = new Contact("Jane Smith", "555-5678", "jane@email.com", "456 Oak Ave, Town");
        Contact contact3 = new Contact("Bob Johnson", "555-9012", "bob@email.com", "789 Pine Rd, Village");

        contacts.put("John Doe", contact1);
        contacts.put("Jane Smith", contact2);
        contacts.put("Bob Johnson", contact3);

        updateContactList();
    }

    private void updateContactList() {
        contactListModel.clear();
        for (Contact contact : contacts.values()) {
            contactListModel.addElement(contact.toString());
        }
    }

    private void loadContactDetails() {
        String selected = (String) contactList.getSelectedValue();
        if (selected == null) return;

        String name = selected.split(" - ")[0];
        Contact contact = contacts.get(name);
        
        if (contact != null) {
            nameField.setText(contact.name);
            phoneField.setText(contact.phone);
            emailField.setText(contact.email);
            addressField.setText(contact.address);
            
            addButton.setEnabled(false);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            statusLabel.setText("Contact loaded: " + name);
        }
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Name and Phone number are required!", 
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (contacts.containsKey(name)) {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Contact '" + name + "' already exists. Do you want to update it?", 
                "Contact Exists", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                editContact();
                return;
            }
        }

        Contact contact = new Contact(name, phone, email, address);
        contacts.put(name, contact);
        updateContactList();
        
        // Clear fields
        clearFields();
        statusLabel.setText("Contact added: " + name);
        
        JOptionPane.showMessageDialog(this, 
            "Contact added successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editContact() {
        String selected = (String) contactList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a contact to edit!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = selected.split(" - ")[0];
        Contact contact = contacts.get(name);
        
        nameField.setText(contact.name);
        phoneField.setText(contact.phone);
        emailField.setText(contact.email);
        addressField.setText(contact.address);
        
        addButton.setEnabled(false);
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        statusLabel.setText("Editing contact: " + name);
        
        // Ask user what to do next
        String message = "Editing " + name + "\n\n" +
                        "What would you like to do next?\n" +
                        "A. Save changes and stay in edit mode\n" +
                        "B. Save changes and return to normal mode\n" +
                        "C. Cancel edit";
        
        int choice = JOptionPane.showOptionDialog(this, message, "Edit Contact",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, new String[]{"Save & Stay", "Save & Exit", "Cancel"}, "Save & Stay");

        if (choice == 0) {
            // Stay in edit mode
        } else if (choice == 1) {
            commitEdit();
        } else {
            // Cancel edit
            loadContactDetails();
        }
    }

    private void commitEdit() {
        Contact contact = contacts.get(nameField.getText());
        if (contact != null) {
            contact.update(nameField.getText(), phoneField.getText(), 
                          emailField.getText(), addressField.getText());
            updateContactList();
            statusLabel.setText("Contact updated: " + nameField.getText());
            JOptionPane.showMessageDialog(this, 
                "Contact updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteContact() {
        String selected = (String) contactList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a contact to delete!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = selected.split(" - ")[0];
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete " + name + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            contacts.remove(name);
            updateContactList();
            clearFields();
            statusLabel.setText("Contact deleted: " + name);
            
            addButton.setEnabled(true);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            
            JOptionPane.showMessageDialog(this, 
                "Contact deleted successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        
        addButton.setEnabled(true);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        statusLabel.setText("Fields cleared");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactAddressBook addressBook = new ContactAddressBook();
            addressBook.setVisible(true);
        });
    }
}