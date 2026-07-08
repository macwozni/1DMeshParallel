package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.parallelism.ConcurrentBlockRunner;

/**
 * Command-line entry point for the one-dimensional mesh production example.
 */
public final class Application {

    private Application() {
    }

    /**
     * Runs the example with the concurrent block runner.
     *
     * @param args command-line arguments; currently ignored
     */
    public static void main(String[] args) {
        new Executor(new ConcurrentBlockRunner()).run();
    }
}
