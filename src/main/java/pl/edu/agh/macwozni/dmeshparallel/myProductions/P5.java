package pl.edu.agh.macwozni.dmeshparallel.myProductions;

import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.production.AbstractProduction;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public class P5 extends AbstractProduction<Vertex> {

    public P5(Vertex object, PDrawer<? super Vertex> drawer) {
        super(object, drawer);
    }

    @Override
    public Vertex apply(Vertex t1) {
        System.out.println("p5");
        return Vertex.withGraphLock(() -> {
            t1.setLabel("|e1|");
            return t1;
        });
    }
}
