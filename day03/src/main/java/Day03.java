import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends AbstractMultiStepDay<Long, Long> {

    public Day03(String fileName) {
        super(fileName);
    }

    public Day03() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day03 day03 = new Day03();
        day03.fullRun();
    }

    private final static Pattern MUL_PATTERN = Pattern.compile("mul\\((-?\\d+),(-?\\d+)\\)");

    private final List<String> formulas = new ArrayList<>();

    public Long resultStep1() {
        long result = 0L;
        for (String formula : formulas) {
            result += compileFormula(formula);
        }
        return result;
    }

    private static long compileFormula(String formula) {
        long result = 0L;
        Matcher matcher = Day03.MUL_PATTERN.matcher(formula);
        while (matcher.find()) {
            result += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }
        return result;
    }

    public Long resultStep2() {
        long result = 0L;
        String fullFormula = String.join("", formulas);
        Pattern patternDoDont = Pattern.compile("(^|do\\(\\))(.*?)(don't\\(\\)|$)");
        Matcher matcher = patternDoDont.matcher(fullFormula);
        while (matcher.find()) {
            String substring = matcher.group(2);
            result += compileFormula(substring);
        }
        return result;
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                formulas.add(line);
                line = br.readLine();
            }
        }
    }


}
