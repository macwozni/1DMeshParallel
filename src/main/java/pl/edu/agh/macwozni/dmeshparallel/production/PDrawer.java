package pl.edu.agh.macwozni.dmeshparallel.production;

@FunctionalInterface
public interface PDrawer<P> {

    void draw(P value);
}
