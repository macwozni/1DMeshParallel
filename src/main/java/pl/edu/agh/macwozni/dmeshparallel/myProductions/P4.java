package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Production that appends another {@code T2} vertex after a matched
 * {@code T2} vertex.
 */
public class P4 extends AbstractProduction<Vertex> {

    /**
     * Creates a production for the supplied {@code T2} vertex.
     *
     * @param object matched {@code T2} vertex
     * @param drawer drawer invoked after the production is applied
     */
    public P4(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    /**
     * Inserts a new {@code T2} vertex immediately to the right of the supplied
     * {@code T2} vertex.
     *
     * @param t2 matched {@code T2} vertex
     * @return the original {@code T2} vertex
     */
    @Override
    public Vertex apply(Vertex t2) {
        System.out.println("p4");
        return Vertex.withGraphLock(() -> {
            var right = t2.getRight();
            var t2Prim = new Vertex(t2, right, "T2");
            if (right != null) {
                right.setLeft(t2Prim);
            }
            t2.setRight(t2Prim);
            return t2;
        });
    }
}
