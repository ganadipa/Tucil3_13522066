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
            if (englishWordMap.get(input) == null) return false;
            return true;
        });

        System.out.print("Target: ");
        String target = Input.getString("No english word matches your input!", input -> {
            if (englishWordMap.get(input) == null) return false;
            return true;
        });

        System.out.println("Algo (1/2/3): ");
        Integer algorithm = Input.getInt();
        
        Solver s;
        switch (algorithm) {
            case 1:
                s = new BFS(englishWordMap);
                break;
            default:
                s = new GBFS(englishWordMap);
                break;
        }

        try {
            s.solve(source, target);
            s.getSolution().forEach(System.out::println);
            System.out.println("Total nodes visited: " + s.getTotalNodesVisited());
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