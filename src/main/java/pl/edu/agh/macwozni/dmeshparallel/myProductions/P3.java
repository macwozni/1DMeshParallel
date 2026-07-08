package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Production that inserts a {@code T2} vertex to the left of a matched
 * {@code T1} vertex.
 */
public class P3 extends AbstractProduction<Vertex> {

    /**
     * Creates a production for the supplied {@code T1} vertex.
     *
     * @param object matched {@code T1} vertex
     * @param drawer drawer invoked after the production is applied
     */
    public P3(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    /**
     * Inserts a {@code T2} vertex between the supplied {@code T1} vertex and
     * its left neighbour.
     *
     * @param t1 matched {@code T1} vertex with an existing left neighbour
     * @return inserted {@code T2} vertex
     */
    @Override
    public Vertex apply(Vertex t1) {
        System.out.println("p3");
        return Vertex.withGraphLock(() -> {
            var left = t1.getLeft();
            var t2 = new Vertex(left, t1, "T2");
            left.setRight(t2);
            t1.setLeft(t2);
            return t2;
        });
    }
}
