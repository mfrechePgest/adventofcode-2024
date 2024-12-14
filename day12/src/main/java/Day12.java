import mf.map.Direction;
import mf.map.Point;
import mf.map.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12 extends AbstractMultiStepDay<Long, Long> {

    public Day12(String fileName) {
        super(fileName);
    }

    public Day12() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day12 day12 = new Day12();
        day12.fullRun();
    }

    private final List<List<Character>> grid = new ArrayList<>();
    private int mapWidth, mapHeight;
    private final Map<Character, List<Point>> mapPoints = new HashMap<>();

    @Override
    public Long resultStep1() {
        return computeFencesCosts(false);
    }

    @Override
    public Long resultStep2() {
        return computeFencesCosts(true);
    }

    private long computeFencesCosts(boolean groupBorders) {
        long result = 0;
        Set<Point> visited = new HashSet<>();
        Queue<Point> queue = new LinkedList<>(mapPoints.values().stream().flatMap(Collection::stream).toList());
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            if (visited.contains(point)) {
                continue;
            }
            // visiting new region
            int scoreRegion = getScoreRegion(point, visited, groupBorders);
            result += scoreRegion;
        }
        return result;
    }

    private int getScoreRegion(Point point, Set<Point> visited, boolean groupBorders) {
        Character currentChar = getCharacter(point);
        Set<Point> region = new HashSet<>();
        region.add(point);
        List<Point> perimeter = new ArrayList<>();
        Queue<Point> queueRegion = new LinkedList<>(point.neighbours(1).map(Step::pos).toList());
        visited.add(point);
        while (!queueRegion.isEmpty()) {
            Point neighbour = queueRegion.poll();
            if (neighbour.isValid(0, mapWidth, 0, mapHeight) && getCharacter(neighbour).equals(currentChar)) {
                // same region neighbour
                if (visited.contains(neighbour)) {
                    continue;
                }
                queueRegion.addAll(neighbour.neighbours(1).map(Step::pos).toList());
                region.add(neighbour);
                visited.add(neighbour);
            } else {
                // border
                perimeter.add(neighbour);
            }
        }
        int perimeterCost;
        if (groupBorders) {
            perimeterCost = countBorders(perimeter, region);
        } else {
            perimeterCost = perimeter.size();
        }
        int scoreRegion = region.size() * perimeterCost;
        System.out.printf("Region [%s] is %d, %d = %d%n", currentChar, region.size(), perimeterCost, scoreRegion);
        return scoreRegion;
    }

    protected int countBorders(List<Point> perimeter, Collection<Point> regionChar) {
        Set<Border> visited = new HashSet<>();
        int count = 0;
        for (Point border : perimeter) {
            List<Direction> dirRegion = border.neighbours(1)
                    .filter(step -> step.pos().isValid(0, mapWidth, 0, mapHeight))
                    .filter(step -> regionChar.contains(step.pos()))
                    .map(Step::dir)
                    .toList();
            for (Direction dir : dirRegion) {
                Border pointNeighboured = new Border(border, dir);
                if (visited.contains(pointNeighboured)) {
                    continue;
                }
                inspectBorder(pointNeighboured, visited, perimeter);
                count++;
            }
        }
        return count;
    }

    private void inspectBorder(Border border, Set<Border> visited, List<Point> perimeter) {
        Queue<Border> queue = new LinkedList<>();
        Way way = Way.fromDirection(border.sideToRegion()).perpendicular();
        queue.add(border);
        while (!queue.isEmpty()) {
            Border current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                queue.addAll(
                        current.point().neighbours(1)
                                .filter(step -> way.isInRightWay(step.dir()))
                                .map(Step::pos)
                                .filter(perimeter::contains)
                                .map(p -> new Border(p, current.sideToRegion()))
                                .filter(step -> !visited.contains(step))
                                .toList());
            }
        }
    }

    private Character getCharacter(Point point) {
        return grid.get((int) point.y()).get((int) point.x());
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                grid.add(line.chars().mapToObj(c -> (char) c).toList());
                IntStream.range(0, grid.getLast().size())
                        .mapToObj(index -> new AbstractMap.SimpleEntry<>(grid.getLast().get(index), Point.of(index, grid.size() - 1)))
                        .forEach(entry -> mapPoints.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(entry.getValue()));
                mapWidth = grid.getLast().size();
                mapHeight = grid.size();
                line = br.readLine();
            }
        }
    }


    public void setMapWidth(int i) {
        mapWidth = i;
    }

    public void setMapHeight(int i) {
        mapHeight = i;
    }

    private record Border(Point point, Direction sideToRegion) {
    }

    private enum Way {
        HORIZONTAL(Direction.EAST, Direction.WEST),
        VERTICAL(Direction.NORTH, Direction.SOUTH);

        private final Direction[] directions;

        Way(Direction... directions) {
            this.directions = directions;
        }

        public static Way fromDirection(Direction dir) {
            return Stream.of(Way.values())
                    .filter(way -> way.isInRightWay(dir))
                    .findAny()
                    .orElseThrow();
        }

        public Way perpendicular() {
            if (this == HORIZONTAL) {
                return VERTICAL;
            } else {
                return HORIZONTAL;
            }
        }

        public boolean isInRightWay(Direction dir) {
            return Arrays.stream(this.directions).anyMatch(d -> d == dir);
        }
    }
}
