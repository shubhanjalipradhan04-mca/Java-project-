import javax.swing.*;
import java.awt.*;
import java.util.*;

public class QuizApplication extends JFrame {
    private int currentQuestion = 0;
    private int score = 0;
    private int totalQuestions;
    private Map<Integer, String> questions;
    private Map<Integer, String[]> options;
    private Map<Integer, Integer> answers;
    private JRadioButton[] radioButtons;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton nextButton;
    private JButton submitButton;
    private JLabel scoreLabel;
    private JLabel feedbackLabel;

    public QuizApplication() {
        initializeQuiz();
        setupUI();
    }

    private void initializeQuiz() {
        // Sample quiz data
        questions = new HashMap<>();
        options = new HashMap<>();
        answers = new HashMap<>();

        totalQuestions = 5;
        
        questions.put(0, "What is the capital of France?");
        options.put(0, new String[]{"London", "Berlin", "Paris", "Madrid"});
        answers.put(0, 2);

        questions.put(1, "Which planet is known as the Red Planet?");
        options.put(1, new String[]{"Venus", "Mars", "Jupiter", "Saturn"});
        answers.put(1, 1);

        questions.put(2, "What is 2 + 2?");
        options.put(2, new String[]{"3", "4", "5", "6"});
        answers.put(2, 1);

        questions.put(3, "Who painted the Mona Lisa?");
        options.put(3, new String[]{"Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"});
        answers.put(3, 2);

        questions.put(4, "What is the largest ocean?");
        options.put(4, new String[]{"Atlantic", "Indian", "Arctic", "Pacific"});
        answers.put(4, 3);
    }

    private void setupUI() {
        setTitle("Quiz Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        questionLabel = new JLabel();
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit Quiz");
        scoreLabel = new JLabel("Score: 0");
        feedbackLabel = new JLabel("");
        feedbackLabel.setForeground(Color.BLUE);

        // Style components
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nextButton.addActionListener(e -> nextQuestion());
        submitButton.addActionListener(e -> showResults());

        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initially hide submit button
        submitButton.setVisible(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(questionLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(feedbackLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(nextButton);
        mainPanel.add(submitButton);

        add(mainPanel);
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestion >= totalQuestions) {
            questionLabel.setText("Quiz Complete!");
            nextButton.setEnabled(false);
            return;
        }

        // Clear previous options
        optionsPanel.removeAll();
        radioButtons = new JRadioButton[4];
        ButtonGroup group = new ButtonGroup();

        // Load current question
        questionLabel.setText(questions.get(currentQuestion));

        // Create radio buttons
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton(options.get(currentQuestion)[i]);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            radioButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            group.add(radioButtons[i]);
            optionsPanel.add(radioButtons[i]);
            optionsPanel.add(Box.createVerticalStrut(10));
        }

        // Clear feedback
        feedbackLabel.setText("");

        // Show/hide buttons based on position
        if (currentQuestion == totalQuestions - 1) {
            nextButton.setText("Submit Quiz");
            submitButton.setVisible(true);
            nextButton.addActionListener(e -> submitButton.doClick());
        } else {
            submitButton.setVisible(false);
            nextButton.addActionListener(e -> nextQuestion());
        }
    }

    private void nextQuestion() {
        if (currentQuestion < totalQuestions - 1) {
            currentQuestion++;
            loadQuestion();
        } else {
            submitButton.doClick();
        }
    }

    private void showResults() {
        int totalQuestions = this.totalQuestions;
        this.currentQuestion = 0;

        for (int i = 0; i < totalQuestions; i++) {
            int selected = getSelectedAnswer(i);
            if (selected == answers.get(i)) {
                score++;
            }
        }

        questionLabel.setText("Your Score: " + score + "/" + totalQuestions);
        scoreLabel.setText("Final Score: " + score);
        optionsPanel.removeAll();
        nextButton.setEnabled(false);

        // Show results
        String feedback = "";
        for (int i = 0; i < totalQuestions; i++) {
            int selected = getSelectedAnswer(i);
            if (selected == -1) {
                feedback += "Question " + (i + 1) + ": No answer\n";
            } else if (selected == answers.get(i)) {
                feedback += "Question " + (i + 1) + ": ✓ Correct\n";
            } else {
                feedback += "Question " + (i + 1) + ": ✗ Wrong (Correct: " + 
                           options.get(i)[answers.get(i)] + ")\n";
            }
        }

        feedbackLabel.setText(feedback);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Add a restart button
        JButton restartButton = new JButton("Restart Quiz");
        restartButton.addActionListener(e -> restartQuiz());
        optionsPanel.add(restartButton);
    }

    private int getSelectedAnswer(int questionIndex) {
        if (radioButtons == null) return -1;
        
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private void restartQuiz() {
        currentQuestion = 0;
        score = 0;
        loadQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApplication quiz = new QuizApplication();
            quiz.setVisible(true);
        });
    }
}