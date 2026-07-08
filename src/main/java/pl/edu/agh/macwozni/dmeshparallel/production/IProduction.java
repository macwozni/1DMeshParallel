package pl.edu.agh.macwozni.dmeshparallel.production;

/**
 * A graph production that transforms one object and stores the result.
 *
 * @param <P> type of object transformed by the production
 */
public interface IProduction<P> extends Runnable {

    /**
     * Applies the production rule to the supplied object.
     *
     * @param value object matched by the production
     * @return transformed object or a representative vertex of the transformed graph
     */
    P apply(P value);

    /**
     * Returns the result produced by the most recent run.
     *
     * @return production result, or {@code null} before the production is run
     */
    P getObj();
}
