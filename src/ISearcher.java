import java.util.List;

public interface ISearcher {
    List<Path> search(AProblem problem, double maxCost) throws InterruptedException;
}
