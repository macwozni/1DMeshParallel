package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

public final class SerialBlockRunner extends AbstractBlockRunner {

    @Override
    protected void runNonEmptyBlock(List<IProduction<?>> productions) {
        productions.forEach(IProduction::run);
    }
}
