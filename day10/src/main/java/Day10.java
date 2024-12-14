import mf.map.Point;
import mf.map.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day10 extends AbstractMultiStepDay<Long, Long> {

    public Day10(String fileName) {
        super(fileName);
    }

    public Day10() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day10 day10 = new Day10();
        day10.fullRun();
    }

    List<List<Integer>> grid = new ArrayList<>();
    List<Point> startingPositions = new ArrayList<>();

    @Override
    public Long resultStep1() {
        return findPaths(false);
    }

    private Long findPaths(boolean reusePath) {
        Long result = 0L;
        for (Point startingPosition : startingPositions) {
            Set<Point> visited = new HashSet<>();
            LinkedList<Point> queue = new LinkedList<>();
            queue.add(startingPosition);
            while(!queue.isEmpty()) {
                Point current = queue.removeFirst();
                if(!reusePath && visited.contains(current)) {continue;}
                visited.add(current);
                current.neighbours(1)
                        .map(Step::pos)
                        .filter(p -> reusePath || !visited.contains(p))
                        .filter(p -> getAltitude(p) == (getAltitude(current) + 1))
                        .forEach(queue::add);
                if (getAltitude(current) == 9) {
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public Long resultStep2() {
        return findPaths(true);
    }

    private Integer getAltitude(Point pos) {
        return grid.get((int) pos.y()).get((int) pos.x());
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int yIdx = 0;
            while (line != null) {
                grid.add(line.chars().map(Character::getNumericValue).boxed().toList());
                String finalLine = line;
                int finalYIdx = yIdx;
                IntStream.range(0, line.length())
                        .filter(x -> finalLine.charAt(x) == '0')
                        .forEach(x -> startingPositions.add(Point.of(x, finalYIdx)));
                line = br.readLine();
                yIdx++;
            }
        }
    }


}
