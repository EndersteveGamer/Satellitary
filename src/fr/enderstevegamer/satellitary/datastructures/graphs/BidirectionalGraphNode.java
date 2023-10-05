package fr.enderstevegamer.satellitary.datastructures.graphs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * <p>
 *     A class representing a node of a graph
 * </p>
 * <p>
 *     The links between the nodes are bi-directional
 * </p>
 * @param <T> The type of the elements contained in the graph
 */
public class BidirectionalGraphNode<T> extends GraphNode<T> {
    public BidirectionalGraphNode(T value, @NotNull ArrayList<GraphNode<T>> linked) {
        super(value);
        link(linked);
    }

    /**
     * Links a node to this one
     * @param other The node to link to this one
     */
    @Override
    public void link(@NotNull GraphNode<T> other) {
        other.linked.add(this);
        this.linked.add(other);
    }

    /**
     * Unlinks a node from this one
     * @param other The node to unlink from this one
     */
    @Override
    public void unlink(@NotNull GraphNode<T> other) {
        other.linked.remove(this);
        this.linked.remove(other);
    }
}
