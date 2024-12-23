package mf.map;

import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    NORTH('^') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(point.x(), Math.floor(point.y() - step));
        }

        @Override
        public Direction reversed() {
            return SOUTH;
        }
        @Override
        public Direction left() {
            return WEST;
        }

        @Override
        public Direction right() {
            return EAST;
        }

        @Override
        public boolean isInDirection(Point from, Point to) {
            return from.x() == to.x() && from.y() > to.y();
        }
    },
    SOUTH('v') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(point.x(), Math.ceil(point.y() + step));
        }

        @Override
        public Direction reversed() {
            return NORTH;
        }

        @Override
        public Direction left() {
            return EAST;
        }

        @Override
        public Direction right() {
            return WEST;
        }

        @Override
        public boolean isInDirection(Point from, Point to) {
            return from.x() == to.x() && from.y() < to.y();
        }
    },
    EAST('>') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(Math.ceil(point.x() + step), point.y());
        }

        @Override
        public Direction reversed() {
            return WEST;
        }

        @Override
        public Direction left() {
            return NORTH;
        }

        @Override
        public Direction right() {
            return SOUTH;
        }

        @Override
        public boolean isInDirection(Point from, Point to) {
            return from.x() < to.x() && from.y() == to.y();
        }
    },
    WEST('<') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(Math.floor(point.x() - step), point.y());
        }

        @Override
        public Direction reversed() {
            return EAST;
        }

        @Override
        public Direction left() {
            return SOUTH;
        }

        @Override
        public Direction right() {
            return NORTH;
        }

        @Override
        public boolean isInDirection(Point from, Point to) {
            return from.x() > to.x() && from.y() == to.y();
        }
    };

    private final char c;

    Direction(char c) {
        this.c = c;
    }

    public abstract Point move(Point point, double step);

    public char toChar() {
        return c;
    }

    public abstract Direction reversed();

    public abstract Direction left();
    public abstract Direction right();
    public abstract boolean isInDirection(Point from, Point to);

    public static Stream<List<Direction>> diagonals() {
        return Stream.of(NORTH, SOUTH)
                .flatMap(d -> Stream.of(Direction.values())
                        .filter(d2 -> d2 != d.reversed())
                        .filter(d2 -> d2 != d)
                        .map(d2 -> List.of(d, d2))
                );
    }
}
