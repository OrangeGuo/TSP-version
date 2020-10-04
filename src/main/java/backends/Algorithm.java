package backends;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Algorithm {
    Greedy("贪心算法"),
    Genetic("遗传算法"),
    Ant("蚁群算法")
    ;
    @Getter
    private final String name;
}
