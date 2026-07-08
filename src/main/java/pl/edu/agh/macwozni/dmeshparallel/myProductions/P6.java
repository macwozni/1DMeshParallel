package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Production that marks a {@code T2} vertex as a finite element labelled
 * {@code |e2|}.
 */
public class P6 extends AbstractProduction<Vertex> {

    /**
     * Creates a production for the supplied {@code T2} vertex.
     *
     * @param object matched {@code T2} vertex
     * @param drawer drawer invoked after the production is applied
     */
    public P6(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    /**
     * Replaces the label of the supplied vertex with {@code |e2|}.
     *
     * @param t2 matched {@code T2} vertex
     * @return relabelled vertex
     */
    @Override
    public Vertex apply(Vertex t2) {
        System.out.println("p6");
        return Vertex.withGraphLock(() -> {
            t2.setLabel("|e2|");
            return t2;
        });
    }
}
