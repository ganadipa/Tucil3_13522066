package stress;

import java.io.*;
import java.util.*;

import solver.*;

public class Stress {
    private static String currentDirectory = System.getProperty("user.dir") + '/';
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        
        Map<String, Boolean> englishWordMap = setupWordsMap("words.txt");
        List<String> words = setupListOfWords("words.txt");

        System.out.println(words.get(2));

        Integer length = words.size();
        for (int i = 0; i < length; i++) {

            if (words.get(i).length() != 4) continue;


            for (int j = length - i - 1; j >= 0; j--) {
                if (i == j) continue;
                
                if (words.get(i).length() != words.get(j).length()) continue;

                String source = words.get(i);
                String target = words.get(j);
                System.out.print("Checking between " + source + " and " + target + ": ");

                Solver s1 = new AStar(englishWordMap), s2 = new BFS(englishWordMap);
                try {
                    s1.solve(source, target);
                } catch (Exception e) {

                }

                try {
                    s2.solve(source, target);
                } catch (Exception e) {
                    
                }

                if (s1.isSolved() == s2.isSolved() && !s2.isSolved()) {
                    System.out.println(ANSI_GREEN + "PASSED! Both doesnt have solution" + ANSI_RESET);
                    break;
                } 

                if (s1.isSolved() != s2.isSolved()) {
                    System.out.println(ANSI_RED + "FAILED!" + ANSI_RESET);
                    return;
                }




                try {
                    Integer size1 = s1.getSolution().size();
                    Integer size2 = s2.getSolution().size();

                    if (size1 == size2) {
                        System.out.println(ANSI_GREEN + "PASSED! " + size1 + " " + size2 + ANSI_RESET);
                        
                    } else {
                        System.out.println(ANSI_RED + "FAILED!" + ANSI_RESET);
                        return;
                    }
                } catch (Exception e) {

                }
                
                break;
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

