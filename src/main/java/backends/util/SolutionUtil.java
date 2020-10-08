package backends.util;

import backends.model.Solution;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public final class SolutionUtil {
    private static final String path = "src/main/resources/data/solutions.dat";

    public static Optional<ArrayList<Solution>> loadFromFile() {
        checkAndCreateDir();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            ArrayList<Solution> solutions = (ArrayList<Solution>) ois.readObject();
            return Optional.of(solutions);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public static void saveToFile(ArrayList<Solution> solutions) {
        checkAndCreateDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(solutions);
            oos.flush();
        } catch (Exception exception) {
            log.error(exception);
        }
    }

    private static void checkAndCreateDir() {
        File parentDir = new File(path).getParentFile();
        if (!parentDir.exists())
            parentDir.mkdirs();
    }
}
