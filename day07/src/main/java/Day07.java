import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day07 extends AbstractMultiStepDay<Long, Long> {

    public Day07(String fileName) {
        super(fileName);
    }

    public Day07() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day07 day07 = new Day07();
        day07.fullRun();
    }

    private final List<Equation> equationList = new ArrayList<>();

    @Override
    public Long resultStep1() {
        return resolveStep(1);
    }

    private Long resolveStep(int step) {
        Long result = 0L;
        for (Equation equation : equationList) {
            if (resolveEquation(equation, step)) {
                result += equation.target;
            }
        }
        return result;
    }

    private static boolean resolveEquation(Equation equation, int step) {
        int operationCount = equation.numbers.size() - 1;
        List<Operation> availableOperations = Stream.of(Operation.values())
                .filter(operation -> operation.isAvailable(step))
                .toList();
        int totalCombination = (int) Math.pow(availableOperations.size(), operationCount);

        for (int i = 0; i < totalCombination; i++) {
            List<Operation> operations = new ArrayList<>(operationCount);
            int idx = i;
            for (int j = 0; j < operationCount; j++) {
                operations.add(availableOperations.get(idx % availableOperations.size()));
                idx /= availableOperations.size();
            }
            Long result = evaluateOperation(equation.numbers, operations);
            if (result.equals(equation.target)) {
                return true;
            }
        }

        return false;
    }

    private static Long evaluateOperation(List<Long> numbers, List<Operation> operations) {
        Long result = 0L;
        for (int i = 0; i < operations.size(); i++) {
            result = operations.get(i).evaluate(i == 0 ? numbers.get(i) : result, numbers.get(i+1));
        }
        return result;
    }

    @Override
    public Long resultStep2() {
        return resolveStep(2);
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] split = line.split(": ");
                equationList.add(
                        new Equation(
                                Long.parseLong(split[0]),
                                Stream.of(split[1].split(" "))
                                        .map(Long::parseLong)
                                        .toList()
                        )
                );
                line = br.readLine();
            }
        }
    }

    private record Equation(Long target, List<Long> numbers) {
    }

    private enum Operation {
        MULTIPLY(1) {
            @Override
            public Long evaluate(Long integer, Long integer1) {
                return integer * integer1;
            }
        },
        ADD(1) {
            @Override
            public Long evaluate(Long integer, Long integer1) {
                return integer + integer1;
            }
        },
        CONCAT(2) {
            @Override
            public Long evaluate(Long integer, Long integer1) {
                return Long.parseLong(integer + "" + integer1);
            }
        };

        private int step;
        private Operation(int step) {
            this.step = step;
        }

        public abstract Long evaluate(Long integer, Long integer1);
        public boolean isAvailable(int step) {
            return this.step <= step;
        }
    }


}
