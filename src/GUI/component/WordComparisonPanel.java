package GUI.component;

import javax.swing.*;
import java.awt.*;

public class WordComparisonPanel extends JPanel {
    private String sourceWord;
    private String targetWord;

    public WordComparisonPanel(String sourceWord, String targetWord) {
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
        setLayout(new GridLayout(1, sourceWord.length())); // Set layout based on the word length
        initializeUI();
    }

    private void initializeUI() {
        for (int i = 0; i < sourceWord.length(); i++) {
            JLabel letterLabel = new JLabel(String.valueOf(sourceWord.charAt(i)), SwingConstants.CENTER);
            letterLabel.setOpaque(true);
            letterLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            if (sourceWord.charAt(i) == targetWord.charAt(i)) {
                letterLabel.setBackground(Color.GREEN);
            } else {
                letterLabel.setBackground(Color.GRAY);
            }
            letterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(letterLabel);
        }
    }

    public void setWords(String sourceWord, String targetWord) {
        this.sourceWord = sourceWord;
        this.targetWord = targetWord;
        removeAll();
        initializeUI();
        validate();
        repaint();
    }
}
