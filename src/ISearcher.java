import java.util.List;

public interface ISearcher {
    List<SearchingVertex> search(AProblem problem, double maxCost) throws InterruptedException;
}
