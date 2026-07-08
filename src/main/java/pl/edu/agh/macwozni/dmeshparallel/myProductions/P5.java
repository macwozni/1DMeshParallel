package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Production that marks a {@code T1} vertex as a finite element labelled
 * {@code |e1|}.
 */
public class P5 extends AbstractProduction<Vertex> {

    /**
     * Creates a production for the supplied {@code T1} vertex.
     *
     * @param object matched {@code T1} vertex
     * @param drawer drawer invoked after the production is applied
     */
    public P5(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    /**
     * Replaces the label of the supplied vertex with {@code |e1|}.
     *
     * @param t1 matched {@code T1} vertex
     * @return relabelled vertex
     */
    @Override
    public Vertex apply(Vertex t1) {
        System.out.println("p5");
        return Vertex.withGraphLock(() -> {
            t1.setLabel("|e1|");
            return t1;
        });
    }
}
