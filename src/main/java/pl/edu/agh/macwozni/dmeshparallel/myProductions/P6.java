package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class P6 extends AbstractProduction<Vertex> {

    public P6(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    @Override
    public Vertex apply(Vertex t2) {
        System.out.println("p6");
        return Vertex.withGraphLock(() -> {
            t2.setLabel("|e2|");
            return t2;
        });
    }
}
