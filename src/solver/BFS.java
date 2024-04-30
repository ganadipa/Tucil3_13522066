package solver;

import java.util.*;

public class BFS implements Solver{
    private boolean solved;
    private String source;
    private String target;
    private List<String> solution;
    private Map<String, Boolean> englishWordsMap;

    private Map<String, Integer> distance;

    public BFS(Map<String, Boolean> englishWordsMap) {
        solved = false;
        this.englishWordsMap = englishWordsMap;
        solution = new ArrayList<String>();
    }

    public void solve(String source, String target) throws Exception{
        solved = false;
        solution.clear();

        // Check if source and target have the same length
        if (source.length() != target.length()) {
            throw new Exception("Source and target must have the same length");
        }

        this.source = source;
        this.target = target;

        distance = new HashMap<String, Integer>();
        
        Queue<String> q = new LinkedList<String>();
        q.add(source);
        distance.put(source, 0);

        // This variable will store the parent of each node
        Map<String, String> parent = new HashMap<String, String>();

        // Keep track of the depth
        Integer depth = 0;
        boolean found = false;


        // BFS-ing
        while (!q.isEmpty() && !found) {
            System.out.println("Searching at depth: " + ++depth);

            Integer size = q.size();
            for (int i = 0; i < size && !found; i++) {
                String current = q.poll();

                for (int j = 0; j < current.length() && !found; j++) {
                    if (current.charAt(j) == target.charAt(j)) {
                        continue;
                    }

                    for (char c = 'a'; c <= 'z' && !found; c++) {
                        String next = current.substring(0, j) + c + current.substring(j + 1);

                        if (englishWordsMap.containsKey(next) && !distance.containsKey(next)) {
                            q.add(next);
                            parent.put(next, current);
                            distance.put(next, depth + 1);
                            if (target.equals(next)) {
                                found = true;
                            }
                        }
                    }
                }
            }
        }

        if (!found) {
            throw new Exception("Solution not found!");
        }

        // Reconstruct the path
        String current = target;
        while (current != null) {
            solution.add(current);
            current = parent.get(current);
        }

        solved = true;
        Collections.reverse(solution);
    }

    public boolean isSolved() {
        return solved;
    }

    public List<String> getSolution() {
        if (isSolved()) {
            return solution;
        } else {
            return null;
        }
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

}
