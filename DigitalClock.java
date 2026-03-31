import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DigitalClock extends JFrame {
    private JLabel timeLabel;
    private Timer timer;

    public DigitalClock() { 
        setTitle("Digital Clock");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Digital-7", Font.PLAIN, 48));
        timeLabel.setForeground(Color.BLUE);
        add(timeLabel,  BorderLayout.CENTER);

        timer = new Timer();
        timer.scheduleAtFixedRate (new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> { 
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                     timeLabel.setText(timeFormat.format(new Date()));
                });
            }
        }, 0, 1000);

        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.cancel();
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DigitalClock().setVisible(true);
        });
    }
}