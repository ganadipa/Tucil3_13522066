package stress;

import java.io.*;
import java.util.*;

import solver.*;

public class Stress {
    private static String currentDirectory = System.getProperty("user.dir") + '/';

    public static void main(String[] args) {
        
        Map<String, Boolean> englishWordMap = setupWordsMap("words.txt");
        List<String> words = setupListOfWords("words.txt");

        System.out.println(words.get(2));

        Integer length = words.size();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) continue;
                
                if (words.get(i).length() != words.get(j).length()) continue;

                String source = words.get(i);
                String target = words.get(j);
                System.out.print("Checking between " + source + " and " + target + ": ");

                Solver s1 = new BFS(englishWordMap), s2 = new GBFS(englishWordMap);
                try {
                    s1.solve(source, target);
                    s2.solve(source, target);

                    if (s1.getSolution().size() == s2.getSolution().size()) {
                        System.out.println("SAME SOLUTION!");
                    } else {
                        System.out.println("DIFFERENT SIZE!");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    private static List<String> setupListOfWords(String filename) {
        List<String> words = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(currentDirectory + "../words/" + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    words.add(line);  
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }
}

