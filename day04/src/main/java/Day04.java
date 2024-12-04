import mf.map.Direction;
import mf.map.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 extends AbstractMultiStepDay<Long, Long> {

    public Day04(String fileName) {
        super(fileName);
    }

    public Day04() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day04 day04 = new Day04();
        day04.fullRun();
    }

    private final List<String> wordSearch = new ArrayList<>();
    private final List<Point> xCoordinates = new ArrayList<>();
    private final List<Point> aCoordinates = new ArrayList<>();

    @Override
    public Long resultStep1() {
        System.out.println(xCoordinates.size() + " X start positions");
        return xCoordinates.stream()
                .flatMap(this::fourStepsInEveryDirection)
                .filter(listSteps -> listSteps.size() == 4)
                .map(listSteps -> listSteps.stream()
                        .map(this::getCharAt)
                        .map(String::valueOf)
                        .collect(Collectors.joining())
                )
                .filter(word -> word.equals("XMAS"))
                .count();
    }

    private char getCharAt(Point point) {
        return wordSearch.get((int) point.y()).charAt((int) point.x());
    }

    private Stream<List<Point>> fourStepsInEveryDirection(Point x) {
        return
                Stream.concat(
                                Stream.of(Direction.values()) // Basic NORTH, SOUTH, EAST, WEST
                                        .map(List::of),
                                Direction.diagonals()
                        )
                        .map(d -> fourStepsInADirection(x, d));
    }

    private List<Point> fourStepsInADirection(Point x, List<Direction> direction) {
        List<Point> word = new ArrayList<>();
        Point currentStep = x;
        word.add(x);
        for (int i = 0; i < 3; i++) {
            Point nextPoint = oneStep(currentStep, direction);
            if (nextPoint.isValid(0, wordSearch.getFirst().length(), 0, wordSearch.size())) {
                currentStep = nextPoint;
                word.add(currentStep);
            } else {
                break;
            }
        }
        return word;
    }

    private static Point oneStep(Point currentStep, List<Direction> direction) {
        return direction.stream()
                .reduce(currentStep, (s, d) -> d.move(s, 1), (p1, p2) -> p2);
    }

    @Override
    public Long resultStep2() {
        System.out.println(aCoordinates.size() + " A start positions");
        return aCoordinates.stream()
                .map(point -> Direction.diagonals()
                        .map(dir -> oneStep(point, dir))
                        .filter(p -> p.isValid(0, wordSearch.getFirst().length(), 0, wordSearch.size()))
                        .map(this::getCharAt)
                        .sorted()
                        .map(String::valueOf)
                        .collect(Collectors.joining())
                )
                .filter(word -> word.equals("MMSS"))
                .count();
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int yIndex = 0;
            while (line != null) {
                wordSearch.add(line);
                int idx = -1;
                while ((idx = line.indexOf('X', idx + 1)) != -1) {
                    xCoordinates.add(Point.of(idx, yIndex));
                }
                while ((idx = line.indexOf('A', idx + 1)) != -1) {
                    aCoordinates.add(Point.of(idx, yIndex));
                }
                line = br.readLine();
                yIndex++;
            }
        }
    }


}
