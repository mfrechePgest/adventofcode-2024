import mf.map.Direction;
import mf.map.Point;
import mf.map.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day06 extends AbstractMultiStepDay<Long, Long> {

    public Day06(String fileName) {
        super(fileName);
    }

    public Day06() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day06 day06 = new Day06();
        day06.fullRun();
    }

    private final List<Point> obstacles = new ArrayList<>();
    private int mapWidth;
    private int mapHeight;
    private Point guardPosition;

    @Override
    public Long resultStep1() {
        Point currentPos = Point.of(guardPosition.x(), guardPosition.y());
        Set<Point> explored;
        try {
            explored = getExploredPath(currentPos, obstacles)
                    .stream()
                    .map(Step::pos)
                    .collect(Collectors.toSet());
        } catch (LoopDetectedException e) {
            throw new RuntimeException(e);
        }
        return (long) explored.size();
    }

    private Set<Step> getExploredPath(Point currentPos, Collection<Point> obstacleList) throws LoopDetectedException {
        Set<Step> alreadyExplored = new HashSet<>();
        Direction currentDir = Direction.NORTH;
        while (currentPos.isValid(0, mapWidth, 0, mapHeight)) {
            Step newStep = new Step(currentPos, currentDir);
            if (alreadyExplored.contains(newStep)) {
                throw new LoopDetectedException();
            } else {
                alreadyExplored.add(newStep);
            }
            Point newPos = currentDir.move(currentPos, 1);
            while (obstacleList.contains(newPos)) {
                currentDir = currentDir.right();
                newPos = currentDir.move(currentPos, 1);
            }
            currentPos = newPos;
        }
        return alreadyExplored;
    }

    @Override
    public Long resultStep2() {
        Point currentPos = Point.of(guardPosition.x(), guardPosition.y());
        Set<Step> previousPath;
        try {
            previousPath = getExploredPath(currentPos, obstacles);
        } catch (LoopDetectedException e) {
            throw new RuntimeException(e);
        }
        return previousPath.stream()
                .map(Step::pos)
                .distinct()
                .filter(pos -> !pos.equals(currentPos))
                .filter(pos -> findLoopWithObstacleInPath(currentPos, pos))
                .count();
    }

    private boolean findLoopWithObstacleInPath(Point start, Point posObstacle) {
        Set<Point> newObstacle = new HashSet<>(obstacles);
        newObstacle.add(posObstacle);
        try {
            getExploredPath(start, newObstacle);
            return false;
        } catch (LoopDetectedException e) {
            return true;
        }
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int yIdx = 0;
            while (line != null) {
                mapWidth = line.length();
                int obstacleX = -1;
                while ((obstacleX = line.indexOf('#', obstacleX + 1)) != -1) {
                    obstacles.add(Point.of(obstacleX, yIdx));
                }
                if (line.indexOf('^') != -1) {
                    guardPosition = Point.of(line.indexOf('^'), yIdx);
                }
                line = br.readLine();
                yIdx++;
            }
            mapHeight = yIdx;
        }
    }


    private static class LoopDetectedException extends Throwable {
    }
}
