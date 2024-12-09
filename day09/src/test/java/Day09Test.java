public class Day09Test extends AbstractMultiStepDayTest<Day09, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day09Test() {
        super(() -> new Day09(SAMPLE_FILE), 1928L, 2858L);
    }

}
