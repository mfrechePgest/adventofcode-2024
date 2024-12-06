public class Day06Test extends AbstractMultiStepDayTest<Day06, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day06Test() {
        super(() -> new Day06(SAMPLE_FILE), 41L, 6L);
    }

}
