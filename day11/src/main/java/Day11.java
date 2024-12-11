import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 extends AbstractMultiStepDay<Long, Long> {

    public Day11(String fileName) {
        super(fileName);
    }

    public Day11() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day11 day11 = new Day11();
        day11.fullRun();
    }

    private LinkedList<Long> stones;

    @Override
    public Long resultStep1() {
        return blink(25, stones);
    }

    @Override
    public Long resultStep2() {
        return blink(75, stones);
    }

    private long blink(int times, LinkedList<Long> stoneList) {
        long result = 0;
        List<StoneAndDepth> stones = stoneList.stream()
                .map(stone -> new StoneAndDepth(stone, times))
                .toList();
        Map<StoneAndDepth, Long> cache = new HashMap<>();
        for(StoneAndDepth stone : stones) {
            result += processOneStone(stone, cache);
        }
        return result;
    }

    private long processOneStone(StoneAndDepth stone, Map<StoneAndDepth, Long> cache) {
        long result = 0;
        if (stone.depth == 0) {
            result = 1;
        } else {
            if (cache.containsKey(stone)) {
                result += cache.get(stone);
                return result;
            }
            result = getChilds(stone).stream()
                    .mapToLong(child -> processOneStone(child, cache))
                    .sum();
        }
        cache.put(stone, result);
        return result;
    }

    private static List<StoneAndDepth> getChilds(StoneAndDepth stone) {
        if (stone.stone == 0) {
            return List.of(new StoneAndDepth(1L, stone.depth - 1));
        } else {
            String stringStone = String.valueOf(stone.stone);
            if (stringStone.length() % 2 == 0) {
                return List.of(
                        new StoneAndDepth(
                                Long.parseLong(stringStone.substring(0, stringStone.length() / 2)),
                                stone.depth - 1)
                        ,
                        new StoneAndDepth(
                                Long.parseLong(stringStone.substring(stringStone.length() / 2)),
                                stone.depth - 1)
                );
            } else {
                return List.of(
                        new StoneAndDepth(stone.stone() * 2024L, stone.depth - 1)
                );
            }
        }
    }
    private record StoneAndDepth(Long stone, int depth) {

    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                stones = Stream.of(line.split(" "))
                        .map(Long::parseLong)
                        .collect(Collectors.toCollection(LinkedList::new));
                line = br.readLine();
            }
        }
    }


}
