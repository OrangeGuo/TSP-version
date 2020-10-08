package backends.model;

import backends.util.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public abstract class AbstractSolver {
    final String name;
    public void solve() {
        long startTime = System.currentTimeMillis();
        process();
        Config.runTime = System.currentTimeMillis() - startTime;
    }

    public abstract void process();
}
