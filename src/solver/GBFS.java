package solver;

import java.util.*;

/**
 * This class is a solver for word ladder problem using GBFS algorithm.
 * Where GBFS stands for Greedy Best First Search.
 * 
 * Here we will use the heuristic function to determine the next node to visit.
 * and my heuristic function is calculated based on how many minimum characters 
 * are needed to change the current node to the target node without the constraint
 * the process must be a valid english word.
 */
public class GBFS implements Solver {
    private boolean solved;
    private String source;
    private String target;
    private List<String> solution;
    private Map<String, Boolean> englishWordsMap;
    private Integer totalNodesVisited;
    private Long solveTime;

    /**
     * Constructor
     * 
     * @param englishWordsMap is a map contains all the english words.
     */
    public GBFS(Map<String, Boolean> englishWordsMap) {
        solved = false;
        this.englishWordsMap = englishWordsMap;
        solution = new ArrayList<String>();
    }


    /**
     * Setup the solution.
     * 
     * @param source
     * @param target
     * @throws Exception
     */
    public void solve(String source, String target) throws Exception{
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

        // A priority queue to store child nodes to be visited
        Queue<Node> pq = new PriorityQueue<Node>();
        pq.add(new Node(source, heuristic(source)));

        Map<String, String> parent = new HashMap<String, String>();


        boolean found = false;

        // Doing Greedy Best First Search
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
                    String next = currentWord.substring(0, i) + c + currentWord.substring(i + 1);

                    if (englishWordsMap.containsKey(next) && !parent.containsKey(next)) {
                        pq.add(new Node(next, heuristic(next)));
                        parent.put(next, currentWord);
                    }
                }
            }
        }

        if (!found) {
            solveTime = System.currentTimeMillis() - currentTimeMillis;
            throw new Exception("Solution not found!");
        }

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


    private Integer heuristic(String current){
        int count = 0;
        for (int i = 0; i < current.length(); i++) {
            if (current.charAt(i) != target.charAt(i)) {
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
