package solver;

import java.util.*;

/**
 * This class implements the A* algorithm to solve the word ladder problem.
 * where here the heuristic function is the sum of the number of characters that
 * need to be changed to make the current node the target node and the depth of
 * the current node.
 */
public class AStar implements Solver {
    private boolean solved;
    private String source;
    private String target;
    private List<String> solution;
    private Map<String, Boolean> englishWordsMap;
    private Integer totalNodesVisited;
    private Long solveTime;

    public AStar(Map<String, Boolean> englishWordsMap) {
        solved = false;
        this.englishWordsMap = englishWordsMap;
        solution = new ArrayList<String>();
    }
    
    public void solve(String source, String target) throws Exception {
        solved = false;
        solution.clear();
        totalNodesVisited = 0;

        // Check if source and target have the same length
        if (source.length() != target.length()) {
            throw new Exception("Source and target must have the same length");
        }

        long currentTimeMillis = System.currentTimeMillis();

        this.source = source;
        this.target = target;
        
        Queue<Node> pq = new PriorityQueue<Node>();
        pq.add(new Node(source, 0 + estimatedCostToGoal(source)));

        // This variable will store the parent of each node
        Map<String, String> parent = new HashMap<String, String>();

        // This variable will store the distance from source to the element
        Map<String, Integer> distance = new HashMap<String, Integer>();
        distance.put(source, 0);

        boolean found = false;
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            totalNodesVisited++;

            if (current.getWord().equals(target)) {
                found = true;
                break;
            }

            String currentWord = current.getWord();

            for (int i = 0; i < currentWord.length(); i++) {

                for (char c = 'a'; c <= 'z'; c++) {
                    if (currentWord.charAt(i) == c) continue;
                    
                    String next = currentWord.substring(0, i) + c + currentWord.substring(i + 1);

                    if (englishWordsMap.containsKey(next)) {
                        Integer estimate = estimatedCostToGoal(next);

                        Integer oldEvaluation = ((distance.get(next) == null ? 0 : distance.get(next))) + estimate;
                        Integer currentEvaluation = distance.get(currentWord) + 1 + estimate;

                        if (parent.containsKey(next) && oldEvaluation <= currentEvaluation) {
                            continue;
                        }

                        distance.put(next, distance.get(currentWord) + 1);
                        pq.add(new Node(next, distance.get(next) + estimate));
                        parent.put(next, currentWord);
                    }
                }
            }
        }

        if (!found) 
            throw new Exception("Solution not found!");

        // Reconstruct the path
        String current = target;
        while (current != source) {
            solution.add(current);
            current = parent.get(current);
        }
        solution.add(source);

        solved = true;
        Collections.reverse(solution);
        solveTime = System.currentTimeMillis() - currentTimeMillis;
    }

    private Integer estimatedCostToGoal(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                count++;
            }
        }
        return count;
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
