import java.util.List;

public interface IDeepening_Searcher {
    List<Path> searchDeepening(AProblem problem,double minCost , double maxCost) throws InterruptedException;
}
