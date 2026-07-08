package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class P4 extends AbstractProduction<Vertex> {

    public P4(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

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
