import mf.map.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day08 extends AbstractMultiStepDay<Long, Long> {

    public Day08(String fileName) {
        super(fileName);
    }

    public Day08() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day08 day08 = new Day08();
        day08.fullRun();
    }

    private int mapWidth;
    private int mapHeight;
    private final Map<Character, List<Point>> antennas = new HashMap<>();

    @Override
    public Long resultStep1() {
        return countAntiNodes(1, 1);
    }

    @Override
    public Long resultStep2() {
        return countAntiNodes(0, Integer.MAX_VALUE);
    }

    private long countAntiNodes(int resonnanceLowerBound, int resonnanceHigherBound) {
        Set<Point> antiNodes = new HashSet<>();
        for(Character antennaType : antennas.keySet()) {
            for (int i = 0 ; i < antennas.get(antennaType).size() ; i++) {
                for (int j = i + 1 ; j < antennas.get(antennaType).size() ; j++) {
                    Point firstAntenna = antennas.get(antennaType).get(i);
                    Point secondAntenna = antennas.get(antennaType).get(j);
                    double diffX = firstAntenna.x() - secondAntenna.x();
                    double diffY = firstAntenna.y() - secondAntenna.y();
                    Point firstAntiNode = null;
                    int idx = resonnanceLowerBound;
                    while(idx <= resonnanceHigherBound && (firstAntiNode == null || firstAntiNode.isValid(0, mapWidth, 0, mapHeight))) {
                        firstAntiNode = Point.of(
                                firstAntenna.x() + (idx * diffX),
                                firstAntenna.y() + (idx * diffY)
                        );
                        idx++;
                        antiNodes.add(firstAntiNode);
                    }

                    Point secondAntiNode = null;
                    idx = resonnanceLowerBound;
                    while(idx <= resonnanceHigherBound && (secondAntiNode == null || secondAntiNode.isValid(0, mapWidth, 0, mapHeight))) {
                        secondAntiNode = Point.of(
                                secondAntenna.x() - (idx * diffX),
                                secondAntenna.y() - (idx * diffY)
                        );
                        idx++;
                        antiNodes.add(secondAntiNode);
                    }
                }
            }
        }
        return antiNodes.stream()
                .filter(antiNode -> antiNode.isValid(0, mapWidth, 0, mapHeight))
                .count();
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int yIdx = 0;
            while (line != null) {
                if (yIdx == 0) {
                    mapWidth = line.length();
                }
                for (int x = 0; x < mapWidth; x++) {
                    if (line.charAt(x) != '.') {
                        Point p = Point.of(x, yIdx);
                        antennas.computeIfAbsent(line.charAt(x), ArrayList::new).add(p);
                    }
                }
                line = br.readLine();
                yIdx++;
            }
            mapHeight = yIdx;
        }
    }


}
