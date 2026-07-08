package pl.edu.agh.macwozni.dmeshparallel.mesh;

import java.util.Objects;
import java.util.StringJoiner;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

public final class GraphDrawer implements PDrawer<Vertex> {

    @Override
    public void draw(Vertex vertex) {
        Objects.requireNonNull(vertex, "vertex");
        System.out.println(Vertex.withGraphLock(() -> toLine(vertex)));
    }

    private static String toLine(Vertex vertex) {
        var current = vertex;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        var labels = new StringJoiner("--");
        while (current != null) {
            labels.add(current.getLabel());
            current = current.getRight();
        }
        return labels.toString();
    }
}
