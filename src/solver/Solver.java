package solver;

import java.util.List;

public interface Solver {
    public void solve(String source, String target) throws Exception;
    public boolean isSolved();
    public List<String> getSolution();
}
