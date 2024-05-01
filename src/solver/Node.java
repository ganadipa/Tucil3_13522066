package solver;

public class Node implements Comparable<Node>{
    private String word;
    private Integer evaluation;

    public Node(String word, Integer evaluation) {
        this.word = word;
        this.evaluation = evaluation;
    }

    public String getWord() {
        return word;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    @Override
    public int compareTo(Node o) {
        if (this.evaluation < o.evaluation) {
            return -1;
        } else if (this.evaluation > o.evaluation) {
            return 1;
        } else {
            return this.word.compareTo(o.getWord());
        }
    }
}
