package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

/**
 * Runs all productions in a block sequentially on the current thread.
 */
public final class SerialBlockRunner extends AbstractBlockRunner {

    /**
     * Creates a runner that executes productions one after another.
     */
    public SerialBlockRunner() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runNonEmptyBlock(List<IProduction<?>> productions) {
        productions.forEach(IProduction::run);
    }
}
