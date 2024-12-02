import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 extends AbstractMultiStepDay<Long, Long> {

    public Day02(String fileName) {
        super(fileName);
    }

    public Day02() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day02 day02 = new Day02();
        day02.fullRun();
    }

    public Long resultStep1() {
        return data.stream()
                .filter(level -> safeLevels(level, false))
                .count();
    }

    public Long resultStep2() {
        return data.stream()
                .filter(level -> safeLevels(level, true))
                .count();
    }

    private final List<List<Integer>> data = new ArrayList<>();

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                data.add(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList());
                line = br.readLine();
            }
        }
    }

    private boolean safeLevels(List<Integer> level, boolean ignoreOneUnsafeLevel) {
        List<Integer> levelCopy = new ArrayList<>(level);
        boolean increase = levelCopy.getLast() - levelCopy.getFirst() >= 0;
        for (int i = 1; i < levelCopy.size(); i++) {
            int diff = levelCopy.get(i) - levelCopy.get(i - 1);
            boolean newIncrease = diff >= 0;
            int absDiff = Math.abs(diff);
            if (absDiff < 1 || absDiff > 3 || newIncrease != increase) {
                if (ignoreOneUnsafeLevel) {
                    if (i == 1) {
                        // if we were at list beginning, we can try removing the first element
                        if(safeLevels(level.subList(1, level.size()),false)) {
                            return true;
                        }
                    }
                    ignoreOneUnsafeLevel = false;
                    levelCopy.remove(i);
                    i--;
                } else {
                    return false;
                }
            }
        }
        return true;
    }


}
