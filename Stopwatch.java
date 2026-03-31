import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Stopwatch extends JFrame {
 private JLabel timeLabel;
 private JButton startButton, stopButton, resetButton;
 private JTextField lapField;
 private JButton lapButton;
 private long elapsedTime = 0;
 private boolean isRunning = false;
 private Timer timer;
 private String[] lapTimes = new String[100];
 private int lapCount = 0;

 public Stopwatch() {
 setTitle("Stopwatch");
 setSize(300, 250);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(new BorderLayout());

 timeLabel = new JLabel("00:00:00.00", SwingConstants.CENTER);
 timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
 add(timeLabel, BorderLayout.NORTH);

 JPanel controlPanel = new JPanel();
 startButton = new JButton("Start");
 stopButton = new JButton("Stop");
 resetButton = new JButton("Reset");
 resetButton.setEnabled(false);

 controlPanel.add(startButton);
 controlPanel.add(stopButton);
 controlPanel.add(resetButton);
 add(controlPanel, BorderLayout.CENTER);

 // Lap times panel
 JPanel lapPanel = new JPanel();
 lapPanel.setLayout(new GridLayout(0, 2, 10, 10));

 JLabel lapLabel = new JLabel("Lap Times:");
 lapField = new JTextField();
 lapField.setEditable(false);
 lapField.setHorizontalAlignment(JTextField.RIGHT);
 lapButton = new JButton("Lap");
 lapButton.addActionListener(new LapListener());

 lapPanel.add(lapLabel);
 lapPanel.add(lapField);
 lapPanel.add(lapButton);
 add(lapPanel, BorderLayout.SOUTH);

 startButton.addActionListener(new StartListener());
 stopButton.addActionListener(new StopListener());
 resetButton.addActionListener(new ResetListener());

 setLocationRelativeTo(null);
 }

 private class StartListener implements ActionListener {
 @Override
 public void actionPerformed(ActionEvent e) {
 if (!isRunning) {
 isRunning = true;
 elapsedTime = 0;
 elapsedTime = System.currentTimeMillis();
 timer = new Timer(10, new TimerListener());
 timer.start();
 
 startButton.setEnabled(false);
 stopButton.setEnabled(true);
 resetButton.setEnabled(false);
 }
 }
 }

 private class StopListener implements ActionListener {
 @Override
 public void actionPerformed(ActionEvent e) {
 if (isRunning) {
 isRunning = false;
 timer.stop();
 
 startButton.setEnabled(true);
 stopButton.setEnabled(false);
 resetButton.setEnabled(true);
 }
 }
 }

 private class ResetListener implements ActionListener {
 @Override
 public void actionPerformed(ActionEvent e) {
 isRunning = false;
 if (timer != null) {
 timer.stop();
 }
 elapsedTime = 0;
 updateTimeDisplay();
 
 startButton.setEnabled(true);
 stopButton.setEnabled(false);
 resetButton.setEnabled(false);
 lapField.setText("");
 lapCount = 0;
 }
 }

 private class LapListener implements ActionListener {
 @Override
 public void actionPerformed(ActionEvent e) {
 // Calculate current time for lap
 long currentTime = System.currentTimeMillis();
 double elapsedSeconds = (currentTime - elapsedTime) / 1000.0;
 
 int hours = (int) (elapsedSeconds / 3600);
 int minutes = (int) ((elapsedSeconds % 3600) / 60);
 int secs = (int) (elapsedSeconds % 60);
 int hundredths = (int) ((elapsedSeconds - Math.floor(elapsedSeconds)) * 100);
 
 String lapTime = String.format("%02d:%02d:%02d.%02d", hours, minutes, secs, hundredths);
 
 if (lapCount < lapTimes.length) {
 lapTimes[lapCount] = lapTime;
 lapCount++;
 }
 updateLapDisplay();
 }
 }

 private class TimerListener implements ActionListener {
 @Override
 public void actionPerformed(ActionEvent e) {
 if (isRunning) {
 long currentTime = System.currentTimeMillis();
 double elapsed = (currentTime - elapsedTime) / 1000.0;
 
 elapsedTime = currentTime;
 formatAndDisplayTime(elapsed);
 }
 }
 }

 private void formatAndDisplayTime(double seconds) {
 int hours = (int) (seconds / 3600);
 int minutes = (int) ((seconds % 3600) / 60);
 int secs = (int) (seconds % 60);
 int hundredths = (int) ((seconds - Math.floor(seconds)) * 100);
 
 timeLabel.setText(String.format("%02d:%02d:%02d.%02d", hours, minutes, secs, hundredths));
 }

 private void updateTimeDisplay() {
 // Calculate elapsed time in seconds from stored milliseconds
 double seconds = elapsedTime / 1000.0;
 formatAndDisplayTime(seconds);
 }

 private void updateLapDisplay() {
 StringBuilder lapText = new StringBuilder();
 for (int i = 0; i < lapCount; i++) {
 if (i > 0) lapText.append("\n");
 lapText.append(String.format("%d. %s", i + 1, lapTimes[i]));
 }
 lapField.setText(lapText.toString());
 }

 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> {
 new Stopwatch().setVisible(true);
 });
 }
}