package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

/**
 * Runs a block of productions and waits until the whole block is finished.
 */
public interface BlockRunner {

    /**
     * Executes all productions from one dependency block.
     *
     * @param productions productions that may be scheduled within the same block
     */
    void runBlock(List<? extends IProduction<?>> productions);
}
