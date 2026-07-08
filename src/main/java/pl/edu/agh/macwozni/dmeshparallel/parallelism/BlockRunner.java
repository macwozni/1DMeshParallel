package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

public interface BlockRunner {

    void runBlock(List<? extends IProduction<?>> productions);
}
