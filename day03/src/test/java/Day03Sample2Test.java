public class Day03Sample2Test extends AbstractMultiStepDayTest<Day03, Long, Long> {

    public static final String SAMPLE_FILE = "sample2.txt";

    public Day03Sample2Test() {
        super(() -> new Day03(SAMPLE_FILE), 161L, 48L);
    }

}
