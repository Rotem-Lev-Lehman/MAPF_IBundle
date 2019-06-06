import java.util.List;

public interface IDeepeningSearcher {
    List<Path> searchDeepening(AProblem problem,double minCost , double maxCost);
}
