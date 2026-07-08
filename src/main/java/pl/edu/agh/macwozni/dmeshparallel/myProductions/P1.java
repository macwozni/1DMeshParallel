package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Production that replaces the axiom with the initial two-vertex {@code T1}
 * mesh.
 */
public class P1 extends AbstractProduction<Vertex> {

    /**
     * Creates a production for the supplied axiom vertex.
     *
     * @param object axiom vertex used as the production input
     * @param drawer drawer invoked after the production is applied
     */
    public P1(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    /**
     * Rewrites the axiom into two connected {@code T1} vertices.
     *
     * @param s axiom vertex; the current implementation creates a fresh mesh
     * @return left boundary of the created mesh
     */
    @Override
    public Vertex apply(Vertex s) {
        System.out.println("p1");
        return Vertex.withGraphLock(() -> {
            var t1 = new Vertex(null, null, "T1");
            var t2 = new Vertex(t1, null, "T1");
            t1.setRight(t2);
            return t1;
        });
    }
}
