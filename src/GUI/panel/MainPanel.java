package GUI.panel;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import GUI.component.InputField;
import GUI.component.NormalButton;
import GUI.component.RadioButton;
import GUI.component.WordComparisonPanel;
import GUI.theme.Colors;

public class MainPanel extends JPanel {
    private Map<String, Boolean> englishWordMap;
    private JLabel messageLabel; // Label for displaying messages
    private InputField sourceInput;
    private InputField targetInput;
    private WordComparisonPanel comparisonPanel;

    public MainPanel(Map<String, Boolean> dictionary) {
        this.englishWordMap = dictionary;
        initUI();
    }

    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.background);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Word Ladder Solver!");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);

        // Input fields setup
        JPanel inputPanel = setupInputPanel();

        // Algorithm selection
        JPanel radioPanel = setupRadioPanel();

        // Submit button
        NormalButton submitButton = new NormalButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> processWords());

        // Message label for feedback
        messageLabel = new JLabel();
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Comparison panel initially empty
        comparisonPanel = new WordComparisonPanel("", ""); // Initialize with empty strings

        // Adding components
        addComponents(titleLabel, inputPanel, radioPanel, submitButton);
    }

    private JPanel setupInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.setMaximumSize(new Dimension(600, 50));

        // Source and target inputs
        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setForeground(Color.BLACK);
        sourceInput = new InputField(10);

        JLabel targetLabel = new JLabel("Target:");
        targetLabel.setForeground(Color.BLACK);
        targetInput = new InputField(10);

        inputPanel.add(sourceLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPanel.add(sourceInput);
        inputPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        inputPanel.add(targetLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPanel.add(targetInput);

        return inputPanel;
    }

    private JPanel setupRadioPanel() {
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        RadioButton ucsButton = new RadioButton("UCS");
        RadioButton gbfsButton = new RadioButton("GBFS");
        RadioButton aStarButton = new RadioButton("A*");

        ButtonGroup algorithmGroup = new ButtonGroup();
        algorithmGroup.add(ucsButton);
        algorithmGroup.add(gbfsButton);
        algorithmGroup.add(aStarButton);

        radioPanel.add(ucsButton);
        radioPanel.add(gbfsButton);
        radioPanel.add(aStarButton);

        return radioPanel;
    }

    private void addComponents(JLabel titleLabel, JPanel inputPanel, JPanel radioPanel, NormalButton submitButton) {
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(inputPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(radioPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(submitButton);
        add(messageLabel);
        add(comparisonPanel);
        add(Box.createVerticalGlue());
    }

    private void processWords() {
        String source = sourceInput.getText().trim();
        String target = targetInput.getText().trim();
        
        if (source.isEmpty() || target.isEmpty() || !englishWordMap.containsKey(source) || !englishWordMap.containsKey(target)) {
            messageLabel.setText("Both words must be in the dictionary.");
            messageLabel.setForeground(Color.RED);
        } else {
            messageLabel.setText("Success! Words are valid.");
            messageLabel.setForeground(Color.GREEN);
            comparisonPanel.setWords(source, target); // Update comparison panel
        }
    }
}
