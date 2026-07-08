package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class P1 extends AbstractProduction<Vertex> {

    public P1(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

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
