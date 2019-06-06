import java.util.List;

public interface ISearcher {
    List<Path> searchIteratively(AProblem problem, double minCost, double costJump, double maxCost);
    List<Path> search(AProblem problem, double maxCost);
}
