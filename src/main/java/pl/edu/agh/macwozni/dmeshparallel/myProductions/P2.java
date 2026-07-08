package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Production that inserts a {@code T2} vertex to the right of a matched
 * {@code T1} vertex.
 */
public class P2 extends AbstractProduction<Vertex> {

    /**
     * Creates a production for the supplied {@code T1} vertex.
     *
     * @param object matched {@code T1} vertex
     * @param drawer drawer invoked after the production is applied
     */
    public P2(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    /**
     * Inserts a {@code T2} vertex between the supplied {@code T1} vertex and
     * its right neighbour.
     *
     * @param t1 matched {@code T1} vertex with an existing right neighbour
     * @return the original {@code T1} vertex
     */
    @Override
    public Vertex apply(Vertex t1) {
        System.out.println("p2");
        return Vertex.withGraphLock(() -> {
            var right = t1.getRight();
            var t2 = new Vertex(t1, right, "T2");
            right.setLeft(t2);
            t1.setRight(t2);
            return t1;
        });
    }
}
