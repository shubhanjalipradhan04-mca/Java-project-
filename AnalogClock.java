import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class AnalogClock extends JPanel {
    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private Timer timer;
    
    public AnalogClock() {
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.WHITE);
        timer = new Timer(1000, e -> updateTime());
        timer.start();
        updateTime();
    }
    
    private void updateTime() {
        LocalTime now = LocalTime.now();
        hour = now.getHour();
        if (hour == 0) hour = 12; // Convert 24-hour to 12-hour format
        minute = now.getMinute();
        second = now.getSecond();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2 - 20;
        
        // Draw clock face
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        
        // Draw outer circle
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        
        // Draw hour markers
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30 - 90); // Start from top
            int x1 = centerX + (int)(radius * 0.9 * Math.cos(angle));
            int y1 = centerY + (int)(radius * 0.9 * Math.sin(angle));
            int x2 = centerX + (int)(radius * 0.85 * Math.cos(angle));
            int y2 = centerY + (int)(radius * 0.85 * Math.sin(angle));
            g2d.drawLine(x1, y1, x2, y2);
            
            // Draw hour numbers
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString(String.valueOf(i == 0 ? 12 : i), 
                          centerX + (int)(radius * 0.7 * Math.cos(angle)), 
                          centerY + (int)(radius * 0.7 * Math.sin(angle)));
        }
        
        // Draw center dot
        g2d.setColor(Color.BLACK);
        g2d.fillOval(centerX - 3, centerY - 3, 6, 6);
        
        // Draw hour hand
        double hourAngle = Math.toRadians(hour * 30 - 90 + minute * 0.5);
        int hourX = centerX + (int)(radius * 0.5 * Math.cos(hourAngle));
        int hourY = centerY + (int)(radius * 0.5 * Math.sin(hourAngle));
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(6));
        g2d.drawLine(centerX, centerY, hourX, hourY);
        
        // Draw minute hand
        double minuteAngle = Math.toRadians(minute * 6 - 90);
        int minuteX = centerX + (int)(radius * 0.7 * Math.cos(minuteAngle));
        int minuteY = centerY + (int)(radius * 0.7 * Math.sin(minuteAngle));
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(centerX, centerY, minuteX, minuteY);
        
        // Draw second hand
        double secondAngle = Math.toRadians(second * 6 - 90);
        int secondX = centerX + (int)(radius * 0.85 * Math.cos(secondAngle));
        int secondY = centerY + (int)(radius * 0.85 * Math.sin(secondAngle));
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(centerX, centerY, secondX, secondY);
        
        // Draw second tick
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(centerX, centerY, minuteX, minuteY);
        
        // Draw date and digital time
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        String date = java.time.LocalDate.now().toString();
        String digitalTime = String.format("%02d:%02d:%02d", hour, minute, second);
        g2d.drawString("Digital: " + digitalTime, 20, 30);
        g2d.drawString("Date: " + date, 20, 50);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Analog Clock");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new AnalogClock(), BorderLayout.CENTER);
            
            // Add some styling
            frame.getContentPane().setBackground(new Color(240, 248, 255));
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}