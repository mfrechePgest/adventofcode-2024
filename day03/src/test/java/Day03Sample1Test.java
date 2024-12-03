public class Day03Sample1Test extends AbstractMultiStepDayTest<Day03, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day03Sample1Test() {
        super(() -> new Day03(SAMPLE_FILE), 161L, 161L);
    }

}
