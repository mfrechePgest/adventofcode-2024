public class Day08SmallTest2 extends AbstractMultiStepDayTest<Day08, Long, Long> {

    public static final String SAMPLE_FILE = "smalltest2.txt";

    public Day08SmallTest2() {
        super(() -> new Day08(SAMPLE_FILE), 3L, 9L);
    }

}
