package pl.edu.agh.macwozni.dmeshparallel;

import java.util.List;
import java.util.Objects;
import pl.edu.agh.macwozni.dmeshparallel.mesh.GraphDrawer;
import pl.edu.agh.macwozni.dmeshparallel.mesh.Vertex;
import pl.edu.agh.macwozni.dmeshparallel.myProductions.P1;
import pl.edu.agh.macwozni.dmeshparallel.myProductions.P2;
import pl.edu.agh.macwozni.dmeshparallel.myProductions.P3;
import pl.edu.agh.macwozni.dmeshparallel.myProductions.P5;
import pl.edu.agh.macwozni.dmeshparallel.myProductions.P6;
import pl.edu.agh.macwozni.dmeshparallel.parallelism.BlockRunner;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public final class Executor implements Runnable {

    private final BlockRunner runner;

    public Executor(BlockRunner runner) {
        this.runner = Objects.requireNonNull(runner, "runner");
    }

    @Override
    public void run() {
        PDrawer<Vertex> drawer = new GraphDrawer();
        var axiom = new Vertex(null, null, "S");

        var p1 = new P1(axiom, drawer);
        runner.runBlock(List.of(p1));

        var p2 = new P2(p1.getObj(), drawer);
        var p3 = new P3(p1.getObj().getRight(), drawer);
        runner.runBlock(List.of(p2, p3));

        var p5A = new P5(p2.getObj(), drawer);
        var p5B = new P5(p3.getObj().getRight(), drawer);
        var p6A = new P6(p2.getObj().getRight(), drawer);
        var p6B = new P6(p3.getObj(), drawer);
        runner.runBlock(List.of(p5A, p5B, p6A, p6B));

        System.out.println("done");
        drawer.draw(p6B.getObj());
    }
}
