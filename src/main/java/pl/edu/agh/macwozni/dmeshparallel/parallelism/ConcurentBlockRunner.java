package pl.edu.agh.macwozni.dmeshparallel.parallelism;

/**
 * Backward-compatible alias for the misspelled historical class name.
 *
 * @deprecated use {@link ConcurrentBlockRunner}
 */
@Deprecated(since = "1.0", forRemoval = false)
public final class ConcurentBlockRunner extends ConcurrentBlockRunner {

    /**
     * Creates a runner using the historical misspelled class name.
     *
     * @deprecated use {@link ConcurrentBlockRunner#ConcurrentBlockRunner()}
     */
    @Deprecated(since = "1.0", forRemoval = false)
    public ConcurentBlockRunner() {
    }
}
