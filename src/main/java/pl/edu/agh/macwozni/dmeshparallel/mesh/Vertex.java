package pl.edu.agh.macwozni.dmeshparallel.mesh;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A vertex in a mutable, doubly linked one-dimensional mesh.
 *
 * <p>Each vertex stores a label and links to the adjacent vertices on the left
 * and right. The shared graph lock is intentionally coarse-grained because this
 * project demonstrates production scheduling rather than fine-grained graph
 * concurrency.</p>
 */
public final class Vertex {

    private static final Object GRAPH_LOCK = new Object();

    private String label;
    private Vertex left;
    private Vertex right;

    /**
     * Creates a vertex with the given neighbours and label.
     *
     * @param left vertex on the left side, or {@code null} for the left boundary
     * @param right vertex on the right side, or {@code null} for the right boundary
     * @param label label printed for this vertex
     */
    public Vertex(Vertex left, Vertex right, String label) {
        this.left = left;
        this.right = right;
        this.label = Objects.requireNonNull(label, "label");
    }

    /**
     * Creates an unlabeled vertex with no neighbours.
     */
    public Vertex() {
    }

    /**
     * Runs an operation while holding the graph-wide lock.
     *
     * @param operation operation to execute
     * @param <T> result type
     * @return result returned by the operation
     */
    public static <T> T withGraphLock(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation");
        synchronized (GRAPH_LOCK) {
            return operation.get();
        }
    }

    /**
     * Sets the left neighbour.
     *
     * @param left vertex on the left side, or {@code null}
     */
    public void setLeft(Vertex left) {
        this.left = left;
    }

    /**
     * Sets the right neighbour.
     *
     * @param right vertex on the right side, or {@code null}
     */
    public void setRight(Vertex right) {
        this.right = right;
    }

    /**
     * Sets the printable label.
     *
     * @param label non-null label
     */
    public void setLabel(String label) {
        this.label = Objects.requireNonNull(label, "label");
    }

    /**
     * Returns the left neighbour.
     *
     * @return vertex on the left side, or {@code null}
     */
    public Vertex getLeft() {
        return left;
    }

    /**
     * Returns the right neighbour.
     *
     * @return vertex on the right side, or {@code null}
     */
    public Vertex getRight() {
        return right;
    }

    /**
     * Returns the printable label.
     *
     * @return current label
     */
    public String getLabel() {
        return label;
    }
}
