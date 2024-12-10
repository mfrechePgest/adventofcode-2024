import java.util.Optional;

public class Day10Test extends AbstractMultipleSampleDayTest<Day10, Long, Long> {

    public Day10Test() {
        super(Day10::new,
                new DayTestParam<>("sample.txt", Optional.of(1L), Optional.empty()),
                new DayTestParam<>("sample-2-branches.txt", Optional.of(2L), Optional.empty()),
                new DayTestParam<>("four-outputs.txt", Optional.of(4L), Optional.empty()),
                new DayTestParam<>("two-trails.txt", Optional.of(3L), Optional.empty()),
                new DayTestParam<>("sample2.txt", Optional.of(36L), Optional.empty()),
                new DayTestParam<>("3-distinct-trails.txt", Optional.empty(), Optional.of(3L)),
                new DayTestParam<>("single-trailhead-13-paths.txt", Optional.empty(), Optional.of(13L)),
                new DayTestParam<>("singe-trail-227-paths.txt", Optional.empty(), Optional.of(227L))

        );
    }

}
