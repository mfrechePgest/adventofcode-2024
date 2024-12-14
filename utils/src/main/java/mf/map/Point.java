package mf.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public final class Point {
    private final double x;
    private final double y;

    private static final Map<Double, Map<Double,Point>> pointCache = new HashMap<>();

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(double x, double y) {
        if (!pointCache.containsKey(x)) {
            Point p = new Point(x,y);
            Map<Double, Point> col = new HashMap<>();
            col.put(y, p);
            pointCache.put(x, col);
            return p;
        } else {
            synchronized (pointCache) {
                return pointCache.get(x).computeIfAbsent(y, col -> new Point(x, col));
            }
        }
    }

    /**
     * @param mapXLowerBound x lower bound inclusive
     * @param mapXUpperBound x upper bound exclusive
     * @param mapYLowerBound y lower bound inclusive
     * @param mapYUpperBound y upper bound exclusive
     * @return true if point is in map boundaries
     */
    public boolean isValid(long mapXLowerBound, long mapXUpperBound, long mapYLowerBound, long mapYUpperBound) {
        return x >= mapXLowerBound && y >= mapYLowerBound && x < mapXUpperBound && y < mapYUpperBound;
    }

    public Stream<Step> neighbours(double step) {
        return
                Stream.of(
                                new Step(Point.of(x - step, y), Direction.WEST),
                                new Step(Point.of(x + step, y), Direction.EAST),
                                new Step(Point.of(x, y - step), Direction.NORTH),
                                new Step(Point.of(x, y + step), Direction.SOUTH)
                        );
    }

    public double manhattanDist(Point p1) {
        return Math.abs(p1.x() - this.x) + Math.abs(p1.y() - this.y);
    }


    public Point floor() {
        return new Point(Math.floor(x), Math.floor(y));
    }
    public Point ceil() { return new Point(Math.ceil(x), Math.ceil(y));}

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Point) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

    public Point lower(Point to) {
        if (to.x() < this.x() || to.y() < this.y()) {
            return to;
        } else {
            return this;
        }
    }

    public Point higher(Point to) {
        if (to.x() > this.x() || to.y() > this.y()) {
            return to;
        } else {
            return this;
        }
    }
}
