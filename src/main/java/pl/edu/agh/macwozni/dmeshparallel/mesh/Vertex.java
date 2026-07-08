package pl.edu.agh.macwozni.dmeshparallel.mesh;

import java.util.Objects;
import java.util.function.Supplier;

public final class Vertex {

    private static final Object GRAPH_LOCK = new Object();

    private String label;
    private Vertex left;
    private Vertex right;

    public Vertex(Vertex left, Vertex right, String label) {
        this.left = left;
        this.right = right;
        this.label = Objects.requireNonNull(label, "label");
    }

    public Vertex() {
    }

    public static <T> T withGraphLock(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation");
        synchronized (GRAPH_LOCK) {
            return operation.get();
        }
    }

    public void setLeft(Vertex left) {
        this.left = left;
    }

    public void setRight(Vertex right) {
        this.right = right;
    }

    public void setLabel(String label) {
        this.label = Objects.requireNonNull(label, "label");
    }

    public Vertex getLeft() {
        return left;
    }

    public Vertex getRight() {
        return right;
    }

    public String getLabel() {
        return label;
    }
}
