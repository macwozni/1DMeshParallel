package pl.edu.agh.macwozni.dmeshparallel.production;

/**
 * Draws or logs the state produced by a production.
 *
 * @param <P> type of object to draw
 */
@FunctionalInterface
public interface PDrawer<P> {

    /**
     * Draws the supplied value.
     *
     * @param value value to draw
     */
    void draw(P value);
}
