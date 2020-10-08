package backends.util;

import backends.model.City;
import backends.model.Solution;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public final class SolutionUtil {
    private static final String path = "src/main/resources/data/solutions.dat";

    public static ArrayList<Solution> updateBestSolutions(Caches caches, Solution currentSolution) {
        Optional<ArrayList<Solution>> bestSolutions = loadFromFile();
        if (!bestSolutions.isPresent()) {
            ArrayList<Solution> solutions = Lists.newArrayList(currentSolution);
            saveToFile(solutions);
            return solutions;
        }
        ArrayList<Solution> solutions = bestSolutions.get();
        Solution relatedSolution = solutions.stream().filter(solution -> isRelatedSolution(caches, solution, currentSolution)).findFirst().orElse(null);
        if (relatedSolution == null) {
            solutions.add(0, currentSolution);
        } else {
            if (relatedSolution.getDistance() > currentSolution.getDistance() || relatedSolution.getDistance() == currentSolution.getDistance() && relatedSolution.getRunTime() > currentSolution.getRunTime()) {
                solutions.remove(relatedSolution);
                solutions.add(0, currentSolution);
            }
        }
        saveToFile(solutions);
        return solutions;
    }

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

    private static boolean isRelatedSolution(Caches caches, Solution solutionA, Solution solutionB) {
        if (solutionA.getBestPath().size() != solutionB.getBestPath().size()) {
            return false;
        }
        for (City city : solutionA.getBestPath()) {
            City currentCity = caches.getCityByNo(city.getNo());
            if (currentCity == null || city.getX() != currentCity.getX() || city.getY() != currentCity.getY()) {
                return false;
            }
        }
        return true;
    }
}
