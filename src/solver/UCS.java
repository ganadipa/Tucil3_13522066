package solver;

import java.util.*;


/**
 * This class is a solver for word ladder problem using BFS algorithm.
 * where BFS stands for Breadth First Search.
 */
public class UCS implements Solver{
    private boolean solved;
    private String source;
    private String target;
    private List<String> solution;
    private Map<String, Boolean> englishWordsMap;
    private Integer totalNodesVisited;
    private Long solveTime;


    public UCS(Map<String, Boolean> englishWordsMap) {
        solved = false;
        this.englishWordsMap = englishWordsMap;
        solution = new ArrayList<String>();
    }

    public void solve(String source, String target) throws Exception{
        solved = false;
        solution.clear();
        totalNodesVisited = 0;

        // Check if source and target have the same length
        if (source.length() != target.length()) {
            throw new Exception("Source and target must have the same length");
        }

        // Record start time
        long currentTimeMillis = System.currentTimeMillis();

        
        this.source = source;
        this.target = target;
        
        Queue<String> q = new LinkedList<String>();
        q.add(source);

        // This variable will store the parent of each node
        Map<String, String> parent = new HashMap<String, String>();

        // Keep track of the depth
        Integer depth = 0;
        boolean found = false;


        // BFS-ing
        while (!q.isEmpty() && !found) {

            // System.out.println("Searching at depth: " + depth);

            Integer size = q.size();
            for (int i = 0; i < size; i++) {
                String current = q.poll();
                totalNodesVisited++;


                // If current is equals to target then just STOP!
                if (current.equals(target)) {
                    found = true;
                    break;
                }

                for (int j = 0; j < current.length(); j++) {

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (current.charAt(j) == c) continue;
                        
                        String next = current.substring(0, j) + c + current.substring(j + 1);

                        if (
                            englishWordsMap.containsKey(next) && 
                            !parent.containsKey(next)
                        ) {
                            q.add(next);
                            parent.put(next, current);
                        }
                    }
                }
            }

            depth++;
        }

        if (!found) {
            throw new Exception("Solution not found!");
        }

        // Reconstruct the path
        String current = target;
        while (!current.equals(source)) {
            solution.add(current);
            current = parent.get(current);
        }
        solution.add(source);


        solved = true;
        Collections.reverse(solution);
        solveTime = System.currentTimeMillis() - currentTimeMillis;
    }

    public boolean isSolved() {
        return solved;
    }

    public List<String> getSolution() throws Exception {
        if (isSolved()) {
            return solution;
        } else {
            throw new Exception("Solution not found!");
        }
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public Integer getTotalNodesVisited() {
        return totalNodesVisited;
    }

    public Long getSolveTime() {
        return solveTime;
    }

}
