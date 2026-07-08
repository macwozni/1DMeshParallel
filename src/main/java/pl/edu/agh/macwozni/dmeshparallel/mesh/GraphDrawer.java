package pl.edu.agh.macwozni.dmeshparallel.mesh;

import java.util.Objects;
import java.util.StringJoiner;
import pl.edu.agh.macwozni.dmeshparallel.production.PDrawer;

/**
 * Prints a mesh as a single line of labels joined by {@code --}.
 */
public final class GraphDrawer implements PDrawer<Vertex> {

    /**
     * Creates a console graph drawer.
     */
    public GraphDrawer() {
    }

    /**
     * Prints the complete connected component containing the given vertex.
     *
     * @param vertex any vertex from the mesh to print
     */
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
