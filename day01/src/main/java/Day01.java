import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 extends AbstractMultiStepDay<Long, Long> {

    public Day01(String fileName) {
        super(fileName);
    }

    public Day01() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day01 day01 = new Day01();
        day01.fullRun();
    }

    private final List<Long> leftList = new ArrayList<>();
    private final List<Long> rightList = new ArrayList<>();


    public Long resultStep1() {
        List<Long> sortedLeft = new ArrayList<>(leftList);
        List<Long> sortedRight = new ArrayList<>(rightList);
        Collections.sort(sortedLeft);
        Collections.sort(sortedRight);
        return IntStream.range(0, sortedLeft.size())
                .mapToLong(i -> Math.abs(sortedLeft.get(i) - sortedRight.get(i)))
                .sum();
    }

    public Long resultStep2() {
        Map<Long, Long> mapOccurences = rightList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return leftList.stream()
                .mapToLong(i -> i * mapOccurences.getOrDefault(i,0L))
                .sum();
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] splitted = line.split("\\s+");
                leftList.add(Long.parseLong(splitted[0]));
                rightList.add(Long.parseLong(splitted[1]));
                line = br.readLine();
            }
        }
    }


}
