package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class P3 extends AbstractProduction<Vertex> {

    public P3(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

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
