import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day09 extends AbstractMultiStepDay<Long, Long> {

    public Day09(String fileName) {
        super(fileName);
    }

    public Day09() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day09 day09 = new Day09();
        day09.fullRun();
    }

    private final List<File> managedFiles = new ArrayList<>();
    private int maxId = 0;

    @Override
    public Long resultStep1() {
        List<Integer> file = toFlatList(managedFiles);
        for (int i = 0; i < file.size(); i++) {
            if (file.get(i) == null) {
                file.set(i, file.getLast());
                do {
                    // remove moved block and trailing empty blocks
                    file.removeLast();
                } while (file.getLast() == null);
            }
        }
        return computeHash(file);
    }

    @Override
    public Long resultStep2() {
        for (int currentId = maxId; currentId >= 0; currentId--) {
            int finalCurrentId = currentId;
            int fileIndex = IntStream.range(0, managedFiles.size())
                    .filter(j -> Optional.ofNullable(managedFiles.get(j).id()).filter(id -> id == finalCurrentId).isPresent())
                    .findFirst()
                    .orElseThrow();
            File currentFile = managedFiles.get(fileIndex);
            if (currentFile.id() != null) {
                // Trying to find an empty block fitting
                OptionalInt fittingBlock = IntStream.range(0, fileIndex)
                        .filter(j -> managedFiles.get(j).id == null && managedFiles.get(j).length >= currentFile.length())
                        .findFirst();
                if (fittingBlock.isPresent()) {
                    // found an empty block
                    File emptyBlock = managedFiles.get(fittingBlock.getAsInt());
                    moveToEmptyBlock(fileIndex, currentFile, fittingBlock.getAsInt(), emptyBlock.length);
                }
            }
        }
        return computeHash(toFlatList(managedFiles));
    }

    private void moveToEmptyBlock(int fileIndex, File fileToMove, int emptyBlockIndex, int emptySpace) {
        managedFiles.set(fileIndex, new File(null, fileToMove.length));
        managedFiles.set(emptyBlockIndex, fileToMove);
        if (emptySpace > fileToMove.length()) {
            managedFiles.add(emptyBlockIndex + 1, new File(null, emptySpace - fileToMove.length()));
        }
        trimList();
    }

    private void trimList() {
        // Removing trailing empty blocks
        while (managedFiles.getLast().id() == null) {
            managedFiles.removeLast();
        }
    }

    private long computeHash(List<Integer> file) {
        long result = 0L;
        for (int i = 0; i < file.size(); i++) {
            if (file.get(i) != null) {
                result += (long) i * file.get(i);
            }
        }
        return result;
    }

    private List<Integer> toFlatList(List<File> managedFiles) {
        List<Integer> result = new ArrayList<>();
        for (File currentFile : managedFiles) {
            int fileLength = currentFile.length();
            for (int j = 0; (j < fileLength); j++) {
                result.add(currentFile.id());
            }
        }
        return result;
    }

    @Override
    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    int fileLength = Character.getNumericValue(line.charAt(i));
                    if (i % 2 == 0) {
                        int id = i / 2;
                        if (id > maxId) {
                            maxId = id;
                        }
                        managedFiles.add(new File(id, fileLength));
                    } else {
                        managedFiles.add(new File(null, fileLength));
                    }
                }
                line = br.readLine();
            }
        }
    }

    private record File(Integer id, int length) {
    }


}
