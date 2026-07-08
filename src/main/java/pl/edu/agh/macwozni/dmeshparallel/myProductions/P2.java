package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class P2 extends AbstractProduction<Vertex> {

    public P2(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

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
