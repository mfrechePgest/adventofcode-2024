import java.util.Optional;

public class Day11Test extends AbstractMultipleSampleDayTest<Day11, Long, Long>  {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day11Test() {
        super(Day11::new,
                new DayTestParam<>(SAMPLE_FILE, Optional.of(55312L), Optional.empty())
        );
    }

}
