import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import solver.*;

class Main {

    private static String currentDirectory = System.getProperty("user.dir") + '/';

    public static void main(String[] args) {
        System.out.println("Word ladder solver!");

        Map<String, Boolean> englishWordMap = setupWordsMap("words.txt");


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
}