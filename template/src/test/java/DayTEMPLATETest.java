import java.util.Optional;

public class DayTEMPLATETest extends AbstractMultipleSampleDayTest<DayTEMPLATE, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public DayTEMPLATETest() {
        super(DayTEMPLATE::new,
                new DayTestParam<>("sample.txt", Optional.of(0L), Optional.empty())
        );
    }

}
