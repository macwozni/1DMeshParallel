package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import java.util.Objects;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

/**
 * Common validation and defensive copying for block runner implementations.
 */
public abstract class AbstractBlockRunner implements BlockRunner {

    /**
     * Creates a block runner with shared validation logic.
     */
    protected AbstractBlockRunner() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void runBlock(List<? extends IProduction<?>> productions) {
        Objects.requireNonNull(productions, "productions");

        if (productions.isEmpty()) {
            return;
        }

        runNonEmptyBlock(List.copyOf(productions));
    }

    /**
     * Executes a non-empty, immutable production block.
     *
     * @param productions productions to execute
     */
    protected abstract void runNonEmptyBlock(List<IProduction<?>> productions);
}
