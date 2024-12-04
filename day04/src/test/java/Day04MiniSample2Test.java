public class Day04MiniSample2Test extends AbstractMultiStepDayTest<Day04, Long, Long> {

    public static final String SAMPLE_FILE = "minisample2.txt";

    public Day04MiniSample2Test() {
        super(() -> new Day04(SAMPLE_FILE), 0L, 1L);
    }

}
