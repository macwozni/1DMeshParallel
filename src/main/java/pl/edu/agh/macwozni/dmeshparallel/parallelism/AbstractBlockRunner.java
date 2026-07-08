package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import java.util.Objects;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

public abstract class AbstractBlockRunner implements BlockRunner {

    @Override
    public final void runBlock(List<? extends IProduction<?>> productions) {
        Objects.requireNonNull(productions, "productions");

        if (productions.isEmpty()) {
            return;
        }

        runNonEmptyBlock(List.copyOf(productions));
    }

    protected abstract void runNonEmptyBlock(List<IProduction<?>> productions);
}
