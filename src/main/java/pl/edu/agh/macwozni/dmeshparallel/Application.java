package pl.edu.agh.macwozni.dmeshparallel;

import pl.edu.agh.macwozni.dmeshparallel.parallelism.ConcurrentBlockRunner;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        new Executor(new ConcurrentBlockRunner()).run();
    }
}
