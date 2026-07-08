package pl.edu.agh.macwozni.dmeshparallel.production;

import java.util.Objects;

public abstract class AbstractProduction<P> implements IProduction<P> {

    private final P object;
    private final PDrawer<? super P> drawer;
    private volatile P result;

    protected AbstractProduction(P object, PDrawer<? super P> drawer) {
        this.object = Objects.requireNonNull(object, "object");
        this.drawer = Objects.requireNonNull(drawer, "drawer");
    }

    @Override
    public final void run() {
        result = apply(object);
        drawer.draw(result);
    }

    @Override
    public final P getObj() {
        return result;
    }
}
