package pl.edu.agh.macwozni.dmeshparallel.production;

import java.util.Objects;

/**
 * Base implementation for productions that draw their result after execution.
 *
 * @param <P> type of object transformed by the production
 */
public abstract class AbstractProduction<P> implements IProduction<P> {

    private final P object;
    private final PDrawer<? super P> drawer;
    private volatile P result;

    /**
     * Creates a production for a matched object.
     *
     * @param object object to transform
     * @param drawer sink used to display the result
     */
    protected AbstractProduction(P object, PDrawer<? super P> drawer) {
        this.object = Objects.requireNonNull(object, "object");
        this.drawer = Objects.requireNonNull(drawer, "drawer");
    }

    /**
     * Applies the production and draws its result.
     */
    @Override
    public final void run() {
        result = apply(object);
        drawer.draw(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final P getObj() {
        return result;
    }
}
