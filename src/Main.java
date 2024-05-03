import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import solver.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import GUI.panel.MainPanel;
import GUI.theme.Colors;

class Main {

    private static String currentDirectory = System.getProperty("user.dir") + '/';
    private static Map<String, Boolean> englishWordMap;
    private static JPanel mainPanel = new JPanel();

    public static void main(String[] args) {
        System.out.println("Word ladder solver!");
        englishWordMap = setupWordsMap("words.txt");

        System.out.println("Do you want to use CLI or GUI ?");
        String choice = Input.getString("Input must be either CLI or GUI!", input -> {
            return (input.equals("CLI") || input.equals("GUI"));
        });

        if (choice.equals("CLI")) runCLI();
        else runGUI();
    }


    private static Map<String, Boolean> setupWordsMap(String filename) {
        
        Map<String, Boolean> englishWordMap = new HashMap<String, Boolean>();

        try (BufferedReader reader = new BufferedReader(new FileReader(currentDirectory + "../words/" + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    englishWordMap.put(line, true);  
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return englishWordMap;
    }

    private static void runGUI() {
        // Make frame
        JFrame f = new JFrame("Word Ladder Solver");

        mainPanel.removeAll();
        mainPanel.add(new MainPanel(englishWordMap));
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.setBackground(Colors.green50);

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


        // Add scroll when overflow
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        f.getContentPane().add(scrollPane, BorderLayout.CENTER);


        f.setSize(960, 540);

        f.setVisible(true);

    }

    private static void runCLI() {
        System.out.print("Source: ");
        String source = Input.getString("No english word matches your input!", input -> {
            return !(englishWordMap.get(input) == null);
        });

        System.out.print("Target: ");
        String target = Input.getString("No english word matches your input!", input -> {
            return !(englishWordMap.get(input) == null);
        });

        System.out.println("1. BFS\n2. GBFS\n3. A*");
        System.out.println("Algorithm (1/2/3) : ");
        Integer algorithm = Input.getInt();
        
        Solver s;
        switch (algorithm) {
            case 1:
                s = new BFS(englishWordMap);
                break;
            case 2:
                s = new GBFS(englishWordMap);
                break;
            default:
                s = new AStar(englishWordMap);
                break;
        }

        try {
            s.solve(source, target);
            s.getSolution().forEach(System.out::println);
            System.out.println("Total nodes visited: " + s.getTotalNodesVisited());
            System.out.println("Solve time: " + s.getSolveTime() + " miliseconds.");
            System.out.println("SOlution length: " + s.getSolution().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}