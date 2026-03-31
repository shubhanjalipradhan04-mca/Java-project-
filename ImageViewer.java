import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ImageViewer extends JFrame {
 private JLabel imageLabel;
 private JButton previousButton, nextButton, openButton;
 private JButton zoomInButton, zoomOutButton, fitToScreenButton;
 private JProgressBar progressBar;
 private String[] imageFilePaths;
 private int currentIndex = 0;
 private double scale = 1.0;

 public ImageViewer() {
 setTitle("Image Viewer");
 setSize(800, 600);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
 setupUI();
 }

 private void setupUI() {
 // Image display label
 imageLabel = new JLabel();
 imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
 imageLabel.setVerticalAlignment(SwingConstants.CENTER);
 imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

 // Control buttons
 openButton = new JButton("Open Image");
 previousButton = new JButton("Previous");
 nextButton = new JButton("Next");
 zoomInButton = new JButton("Zoom In (+)");
 zoomOutButton = new JButton("Zoom Out (-)");
 fitToScreenButton = new JButton("Fit to Screen");

 // Button actions
 openButton.addActionListener(e -> openImage());
 previousButton.addActionListener(e -> showPreviousImage());
 nextButton.addActionListener(e -> showNextImage());
 zoomInButton.addActionListener(e -> zoomIn());
 zoomOutButton.addActionListener(e -> zoomOut());
 fitToScreenButton.addActionListener(e -> fitToScreen());

 // Status label
 JLabel statusLabel = new JLabel("No image loaded");
 statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

 // Progress bar
 progressBar = new JProgressBar();
 progressBar.setStringPainted(true);

 // Main panel
 JPanel mainPanel = new JPanel(new BorderLayout());
 mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

 mainPanel.add(imageLabel, BorderLayout.CENTER);

 // Button panel
 JPanel buttonPanel = new JPanel(new FlowLayout());
 buttonPanel.add(openButton);
 buttonPanel.add(Box.createHorizontalStrut(10));
 buttonPanel.add(previousButton);
 buttonPanel.add(Box.createHorizontalStrut(10));
 buttonPanel.add(nextButton);
 buttonPanel.add(Box.createHorizontalStrut(10));
 buttonPanel.add(zoomInButton);
 buttonPanel.add(zoomOutButton);
 buttonPanel.add(fitToScreenButton);

 mainPanel.add(buttonPanel, BorderLayout.NORTH);
 mainPanel.add(progressBar, BorderLayout.SOUTH);

 add(mainPanel);
 }

 private void openImage() {
 JFileChooser fileChooser = new JFileChooser();
 FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
 "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)", 
 "jpg", "jpeg", "png", "gif", "bmp");
 fileChooser.addChoosableFileFilter(imageFilter);
 fileChooser.setFileFilter(imageFilter);
 
 int result = fileChooser.showOpenDialog(this);
 
 if (result == JFileChooser.APPROVE_OPTION) {
 File selectedFile = fileChooser.getSelectedFile();
 loadImage(selectedFile);
 }
 }

 private void loadImage(File file) {
 try {
 Image image = new ImageIcon(file.getAbsolutePath()).getImage();
 
 // Reset zoom
 scale = 1.0;
 imageLabel.setIcon(new ImageIcon(image));
 
 // Update status
 String fileName = file.getName();
 String fileSize = String.format("%.2f KB", file.length() / 1024.0);
 imageLabel.setText(fileName + "\n" + fileSize);
 
 // Update progress bar
 if (image != null) {
 progressBar.setMaximum((int) image.getWidth(null) * image.getHeight(null));
 } else {
 progressBar.setMaximum(1000);
 }
 progressBar.setValue(0);
 
 // Update button states
 previousButton.setEnabled(true);
 nextButton.setEnabled(true);
 
 // Center image
 centerImage();
 
 } catch (Exception e) {
 JOptionPane.showMessageDialog(this, 
 "Error loading image: " + e.getMessage(), 
 "Error", JOptionPane.ERROR_MESSAGE);
 }
 }

 private void showPreviousImage() {
 if (imageFilePaths != null && imageFilePaths.length > 1) {
 if (currentIndex > 0) {
 currentIndex--;
 if (imageFilePaths[currentIndex] != null) {
 loadImage(new File(imageFilePaths[currentIndex]));
 }
 }
 }
 }

 private void showNextImage() {
 if (imageFilePaths != null && imageFilePaths.length > 1) {
 if (currentIndex < imageFilePaths.length - 1) {
 currentIndex++;
 if (imageFilePaths[currentIndex] != null) {
 loadImage(new File(imageFilePaths[currentIndex]));
 }
 } else {
 JOptionPane.showMessageDialog(this, 
 "This is the last image in the directory.", 
 "End of Images", JOptionPane.INFORMATION_MESSAGE);
 }
 }
 }

 private void zoomIn() {
 scale = Math.min(scale + 0.2, 3.0);
 displayCurrentImage();
 }

 private void zoomOut() {
 scale = Math.max(scale - 0.2, 0.2);
 displayCurrentImage();
 }

 private void fitToScreen() {
 scale = 1.0;
 displayCurrentImage();
 }

 private void displayCurrentImage() {
 if (imageLabel.getIcon() == null) return;
 
 int labelWidth = imageLabel.getWidth();
 int labelHeight = imageLabel.getHeight();
 
 int iconWidth = imageLabel.getIcon().getIconWidth();
 int iconHeight = imageLabel.getIcon().getIconHeight();
 
 // Calculate scaled dimensions
 int scaledWidth = (int) (iconWidth * scale);
 int scaledHeight = (int) (iconHeight * scale);
 
 // Center image in label
 int x = (labelWidth - scaledWidth) / 2;
 int y = (labelHeight - scaledHeight) / 2;
 
 imageLabel.setBounds(x, y, scaledWidth, scaledHeight);
 }

 private void centerImage() {
 // Simple center - displayCurrentImage handles positioning
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> {
 ImageViewer viewer = new ImageViewer();
 viewer.setVisible(true);
 });
 }
}