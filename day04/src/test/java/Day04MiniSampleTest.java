public class Day04MiniSampleTest extends AbstractMultiStepDayTest<Day04, Long, Long> {

    public static final String SAMPLE_FILE = "minisample.txt";

    public Day04MiniSampleTest() {
        super(() -> new Day04(SAMPLE_FILE), 4L, 0L);
    }

}
