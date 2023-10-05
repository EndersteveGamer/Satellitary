package fr.enderstevegamer.satellitary.datastructures.graphs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *     An abstract class representing a node of a graph
 * </p>
 * @param <T> The type of the elements contained in the graph
 */
public abstract class GraphNode<T> {
    protected T value;
    protected final ArrayList<GraphNode<T>> linked;

    @Contract(pure = true)
    protected GraphNode(T value) {
        this.value = value;
        this.linked = new ArrayList<>();
    }

    /**
     * Returns the value of the node
     * @return the value of the node
     */
    public T getValue() {return this.value;}

    /**
     * Sets the value of the node
     * @param value The new value of the node
     */
    public void setValue(T value) {this.value = value;}

    /**
     * Returns the list of the nodes that this node is connected to
     * @return An {@link ArrayList} of the nodes that this node is connected to
     */
    public ArrayList<GraphNode<T>> getLinked() {return this.linked;}

    /**
     * Links a node to this one
     * @param other The node to link to this one
     */
    public abstract void link(@NotNull GraphNode<T> other);

    /**
     * Links a group of nodes to this one
     * @param others The nodes to link to this one
     */
    public void link(@NotNull Collection<GraphNode<T>> others) {
        for (GraphNode<T> node : others) link(node);
    }

    /**
     * Unlinks a node from this one
     * @param other The node to unlink from this one
     */
    public abstract void unlink(@NotNull GraphNode<T> other);

    /**
     * Unlinks a group of nodes from this one
     * @param others The group of nodes to unlink
     */
    public void unlink(@NotNull Collection<GraphNode<T>> others) {
        for (GraphNode<T> node : others) unlink(node);
    }
}
