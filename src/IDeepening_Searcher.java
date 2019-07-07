import java.util.List;

public interface IDeepening_Searcher {
    List<SearchingVertex> searchDeepening(AProblem problem,double minCost , double maxCost) throws InterruptedException;
}
