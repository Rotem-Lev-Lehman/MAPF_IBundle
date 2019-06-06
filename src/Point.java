import java.util.Objects;

public class Point extends Location_Indicator {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public double getHeuristic(Location_Indicator goal) {
        if(goal instanceof Point == false)
            throw new UnsupportedOperationException();
        Point goalPoint = (Point)goal;
        return manhattanDistance(goalPoint);
    }

    private double manhattanDistance(Point goalPoint) {
        int xDistance = Math.abs(this.x - goalPoint.x);
        int yDistance = Math.abs(this.y - goalPoint.y);
        return xDistance+yDistance;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
