package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.StructuredTaskScope;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

/**
 * Runs each production in a block as a structured subtask on a virtual thread.
 *
 * <p>A latch is used so all tasks wait until the whole block has been submitted
 * before any production starts executing. {@link StructuredTaskScope} then
 * keeps all subtasks bound to this method call and handles failure
 * propagation.</p>
 */
public class ConcurrentBlockRunner extends AbstractBlockRunner {

    /**
     * Creates a runner that executes productions on virtual threads.
     */
    public ConcurrentBlockRunner() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runNonEmptyBlock(List<IProduction<?>> productions) {
        var startGate = new CountDownLatch(1);

        try (var scope = StructuredTaskScope.open()) {
            for (var production : productions) {
                scope.fork(() -> {
                    startGate.await();
                    production.run();
                    return null;
                });
            }

            startGate.countDown();
            scope.join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Production block was interrupted", exception);
        } catch (StructuredTaskScope.FailedException exception) {
            throw new IllegalStateException("Production failed", exception.getCause());
        }
    }
}
