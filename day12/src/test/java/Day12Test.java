import mf.map.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Day12Test extends AbstractMultipleSampleDayTest<Day12, Long, Long> {

    public Day12Test() {
        super(Day12::new,
                new DayTestParam<>("first-sample.txt", Optional.of(140L), Optional.of(80L)),
                new DayTestParam<>("second-sample.txt", Optional.of(772L), Optional.of(436L)),
                new DayTestParam<>("large-sample.txt", Optional.of(1930L), Optional.of(1206L)),
                new DayTestParam<>("e-shape.txt", Optional.empty(), Optional.of(236L)),
                new DayTestParam<>("abba-sample.txt", Optional.empty(), Optional.of(368L)),
                new DayTestParam<>("single-dot.txt", Optional.empty(), Optional.of(44L))
        );
    }

    @Test
    @DisplayName("On compte les bordures pour un seul carré au centre")
    void testCountBorder() {
        // GIVEN
        Day12 day12 = new Day12();
        List<Point> perimeter = List.of(
                Point.of(1, 1)
        );
        Collection<Point> region = List.of(
                Point.of(0, 0),
                Point.of(0, 1),
                Point.of(0, 2),
                Point.of(1, 0),
                Point.of(1, 2),
                Point.of(2, 0),
                Point.of(2, 1),
                Point.of(2, 2)
        );
        day12.setMapWidth(3);
        day12.setMapHeight(3);


        // WHEN
        int result = day12.countBorders(perimeter, region);

        // THEN
        Assertions.assertEquals(4, result);
    }

    @Test
    @DisplayName("On compte les bordures pour carré 2x2 au centre")
    void testCountBorder2() {
        // GIVEN
        Day12 day12 = new Day12();
        List<Point> perimeter = List.of(
                Point.of(1, 1),
                Point.of(1, 2),
                Point.of(2, 1),
                Point.of(2, 2)
        );
        Collection<Point> region = List.of(
                Point.of(0, 0),
                Point.of(0, 1),
                Point.of(0, 2),
                Point.of(0, 3),
                Point.of(1, 0),
                Point.of(1, 3),
                Point.of(2, 0),
                Point.of(2, 3),
                Point.of(3, 0),
                Point.of(3, 1),
                Point.of(3, 2),
                Point.of(3, 3)
        );
        day12.setMapWidth(4);
        day12.setMapHeight(4);


        // WHEN
        int result = day12.countBorders(perimeter, region);

        // THEN
        Assertions.assertEquals(4, result);
    }

}
