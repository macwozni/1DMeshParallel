package pl.edu.agh.macwozni.dmeshparallel.parallelism;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import pl.edu.agh.macwozni.dmeshparallel.production.IProduction;

/**
 * Runs each production in a block on its own virtual thread.
 *
 * <p>A latch is used so all tasks wait until the whole block has been submitted
 * before any production starts executing.</p>
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
        var futures = new ArrayList<Future<?>>(productions.size());

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var production : productions) {
                futures.add(executor.submit(() -> {
                    startGate.await();
                    production.run();
                    return null;
                }));
            }

            startGate.countDown();

            for (var future : futures) {
                future.get();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Production block was interrupted", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Production failed", exception.getCause());
        }
    }
}
