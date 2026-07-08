package pl.edu.agh.macwozni.dmeshparallel.production;

public interface IProduction<P> extends Runnable {

    P apply(P value);

    P getObj();
}
