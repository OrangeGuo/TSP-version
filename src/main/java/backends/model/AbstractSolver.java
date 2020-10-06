package backends.model;

import backends.util.Config;

public abstract class AbstractSolver {
    public void solve() {
        long startTime = System.currentTimeMillis();
        process();
        Config.runTime = System.currentTimeMillis() - startTime;
    }

    public abstract void process();
}
