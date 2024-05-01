package solver;

import java.util.*;

/**
 * This class implements the A* algorithm to solve the word ladder problem.
 * where here the heuristic function is the sum of the number of characters that
 * need to be changed to make the current node the target node and the depth of
 * the current node.
 */
public class ASTAR implements Solver {
    private boolean solved;
    private String source;
    private String target;
    private List<String> solution;
    private Map<String, Boolean> englishWordsMap;
    private Integer totalNodesVisited;

    public ASTAR(Map<String, Boolean> englishWordsMap) {
        solved = false;
        this.englishWordsMap = englishWordsMap;
        solution = new ArrayList<String>();
    }
    
    public void solve(String source, String target) {
        // TODO
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
}
