package GUI.panel;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import GUI.component.InputField;
import GUI.component.NormalButton;
import GUI.component.RadioButton;
import GUI.component.WordComparisonPanel;
import GUI.theme.Colors;
import solver.UCS;
import solver.AStar;
import solver.GBFS;
import solver.Solver;

public class MainPanel extends JPanel {
    private Map<String, Boolean> englishWordMap;
    private InputField sourceInput;
    private InputField targetInput;
    private JScrollPane scrollPane;
    private JPanel comparisonContainer;
    private JLabel executionTimeLabel;
    private JLabel solutionLengthLabel;

    private JLabel nodesVisitedLabel;
    ButtonGroup algorithmGroup; 
    Solver s;


    public MainPanel(Map<String, Boolean> dictionary) {
        this.englishWordMap = dictionary;
        initUI();
    }

    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.background);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addTitle();
        addInputFields();
        addAlgorithmSelection();
        setupComparisonArea();
        setupMetricsLabels();  // Setup labels for metrics
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("Word Ladder Solver!");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel);
    }

    private void addInputFields() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.setMaximumSize(new Dimension(600, 50));

        sourceInput = new InputField(10);
        targetInput = new InputField(10);

        inputPanel.add(new JLabel("Source:"));
        inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPanel.add(sourceInput);
        inputPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        inputPanel.add(new JLabel("Target:"));
        inputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        inputPanel.add(targetInput);

        add(inputPanel);
    }

    private void addAlgorithmSelection() {
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Create radio buttons
        RadioButton ucsButton = new RadioButton("UCS");
        RadioButton gbfsButton = new RadioButton("GBFS");
        RadioButton aStarButton = new RadioButton("A*");
    
        // Add radio buttons to a button group and set 'UCS' as default
        algorithmGroup = new ButtonGroup();
        algorithmGroup.add(ucsButton);
        algorithmGroup.add(gbfsButton);
        algorithmGroup.add(aStarButton);
        ucsButton.setSelected(true);  // Set UCS as the default selected radio button
    
        // Add buttons to the panel
        radioPanel.add(ucsButton);
        radioPanel.add(gbfsButton);
        radioPanel.add(aStarButton);
    
        // Submit button to process the words
        NormalButton submitButton = new NormalButton("Submit");
        submitButton.addActionListener(e -> processWords());
        add(radioPanel);
        add(submitButton);
    }
    

    private void setupComparisonArea() {
        comparisonContainer = new JPanel();
        comparisonContainer.setLayout(new BoxLayout(comparisonContainer, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(comparisonContainer);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
    }

    private void setupMetricsLabels() {
        executionTimeLabel = new JLabel("");
        nodesVisitedLabel = new JLabel("");
        solutionLengthLabel = new JLabel("");

        executionTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nodesVisitedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        solutionLengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        add(executionTimeLabel);
        add(nodesVisitedLabel);
        add(solutionLengthLabel);
    }

    private void processWords() {
        String source = sourceInput.getText().trim().toLowerCase();
        String target = targetInput.getText().trim().toLowerCase();
        
        if (!source.isEmpty() && !target.isEmpty() && englishWordMap.containsKey(source) && englishWordMap.containsKey(target)) {
            String selectedAlgorithm = getSelectedAlgorithm();
    
            switch (selectedAlgorithm) {
                case "UCS":
                    s = new UCS(englishWordMap);
                    break;
                case "GBFS":
                    s = new GBFS(englishWordMap);
                    break;
                default:
                    assert selectedAlgorithm.equals("A*");

                    s = new AStar(englishWordMap);
                    break;
            }

            try {
                s.solve(source, target);
                updateResult(source, target);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No solution found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Both words must be in the dictionary and not empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getSelectedAlgorithm() {
        for (Enumeration<AbstractButton> buttons = algorithmGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
    
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null; // default or error handling
    }
    

    private void updateResult(String source, String target) {
        comparisonContainer.removeAll();


        try {
            for (String word : s.getSolution()) {
                WordComparisonPanel panel = new WordComparisonPanel(word, target);
                panel.setPreferredSize(new Dimension(680, 30)); // Set a fixed height for each comparison panel
                comparisonContainer.add(panel);
            }

            executionTimeLabel.setText("Execution Time: " + s.getSolveTime() + " ms");
            nodesVisitedLabel.setText("Total Nodes Visited: " + s.getTotalNodesVisited());
            solutionLengthLabel.setText("Solution length: " + s.getSolution().size());
        } catch (Exception e) {
            e.printStackTrace();
        }


        comparisonContainer.revalidate();
        comparisonContainer.repaint();



    }
}
