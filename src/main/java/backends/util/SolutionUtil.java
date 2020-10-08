package backends.util;

import backends.model.Solution;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public final class SolutionUtil {
    private static final String path = "src/main/resources/data/solutions.dat";

    public static Optional<Solution> loadFromFile() throws IOException, ClassNotFoundException {
        checkAndCreateDir();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Solution solution = (Solution) ois.readObject();
            return Optional.of(solution);
        } catch (FileNotFoundException fileNotFoundException) {
            return Optional.empty();
        }
    }

    public static void saveToFile(Solution solution) throws FileNotFoundException, IOException {
        checkAndCreateDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(solution);
            oos.flush();
        }
    }

    private static void checkAndCreateDir() {
        File parentDir = new File(path).getParentFile();
        if (!parentDir.exists())
            parentDir.mkdirs();
    }
}
