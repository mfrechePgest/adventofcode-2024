import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day05 extends AbstractMultiStepDay<Long, Long> {

    public Day05(String fileName) {
        super(fileName);
    }

    public Day05() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day05 day05 = new Day05();
        day05.fullRun();
    }

    Map<Integer, List<Integer>> mapRules = new HashMap<>();
    List<List<Integer>> pages = new ArrayList<>();

    @Override
    public Long resultStep1() {
        return pages.stream()
                .filter(this::isCorrectlyOrdered)
                .mapToLong(this::middlePage)
                .sum();
    }

    @Override
    public Long resultStep2() {
        return pages.stream()
                .filter(page -> !isCorrectlyOrdered(page))
                .map(this::fixOrder)
                .mapToLong(this::middlePage)
                .sum();
    }

    private boolean isCorrectlyOrdered(List<Integer> page) {
        return IntStream.range(0, page.size())
                .allMatch(idx -> isCorrectlyOrdered(page.get(idx), idx, page));
    }

    private boolean isCorrectlyOrdered(int page, int idx, List<Integer> pages) {
        return mapRules.getOrDefault(page, new ArrayList<>())
                .stream()
                .map(pages::indexOf)
                .allMatch(idxPageAfter -> idxPageAfter == -1 || idxPageAfter > idx);
    }

    private List<Integer> fixOrder(List<Integer> page) {
        return page.stream()
                .sorted(this::comparator)
                .toList();
    }

    private int comparator(int a, int b) {
        if ( a == b ) {
            return 0;
        }
        if (mapRules.containsKey(a) && mapRules.get(a).contains(b)) {
            return -1;
        }
        if(mapRules.containsKey(b) && mapRules.get(b).contains(a)) {
            return 1;
        }
        return 0;
    }

    private int middlePage(List<Integer> page) {
        return page.get(page.size() / 2);
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            boolean rulesOrPages = true;
            while (line != null) {
                if (line.isBlank()) {
                    rulesOrPages = false;
                } else {
                    if (rulesOrPages) {
                        String[] split = line.split("\\|");
                        mapRules.computeIfAbsent(Integer.parseInt(split[0]), ArrayList::new)
                                .add(Integer.parseInt(split[1]));
                    } else {
                        pages.add(Stream.of(line.split(",")).map(Integer::parseInt).toList());
                    }
                }
                line = br.readLine();
            }
        }
    }


}
