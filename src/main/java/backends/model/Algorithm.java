package backends.model;

import backends.service.AntSolver;
import backends.service.GeneticSolver;
import backends.service.GreedySolver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Algorithm {
    Greedy("贪心算法", new GreedySolver()),
    Genetic("遗传算法", new GeneticSolver()),
    Ant("蚁群算法", new AntSolver());
    @Getter
    private final String name;
    @Getter
    private final AbstractSolver abstractSolver;

    public static AbstractSolver getSolverByName(String name) {
        for (Algorithm algorithm : values()) {
            if (algorithm.getName().equals(name)) {
                return algorithm.getAbstractSolver();
            }
        }
        return Algorithm.Greedy.getAbstractSolver();
    }
}
