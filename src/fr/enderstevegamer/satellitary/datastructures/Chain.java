package fr.enderstevegamer.satellitary.datastructures;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A data structure representing elements with a reference to the next object
 * @param <T> The type of object stored in this chain
 */
public class Chain<T> {
    private T element;
    private Chain<T> nextChain;

    /**
     * Creates a {@link Chain} with no next {@link Chain}
     * @param element The element of the {@link Chain}
     */
    @Contract(pure = true)
    public Chain(T element) {
        this.element = element;
        this.nextChain = null;
    }

    /**
     * Creates a {@link Chain} that has a next {@link Chain}
     * @param element The element of the {@link Chain}
     * @param nextChain The {@link Chain} following this one
     */
    @Contract(pure = true)
    public Chain(T element, Chain<T> nextChain) {
        this(element);
        this.nextChain = nextChain;
    }

    /**
     * @return The element in this {@link Chain}
     */
    public T getElement() {return this.element;}

    /**
     * Sets the element of this {@link Chain}
     * @param element The new element of this {@link Chain}
     */
    public void setElement(T element) {this.element = element;}

    /**
     * @return {@code true} if this {@link Chain} has a next {@link Chain}, {@code false} otherwise
     */
    public boolean hasNext() {return this.nextChain != null;}

    /**
     * <p>
     *     Returns the next {@link Chain} after this one
     * </p>
     * <p>
     *     Returns {@code null} if this {@link Chain} has no next {@link Chain}
     * </p>
     * @return The {@link Chain} following this one
     */
    public @Nullable Chain<T> getNextChain() {return this.nextChain;}

    /**
     * Sets the next {@link Chain} after this one
     * @param chain The new next {@link Chain}
     */
    public void setNextChain(@NotNull Chain<T> chain) {this.nextChain = chain;}

    /**
     * Removes the next {@link Chain} after this one (sets it to {@code null})
     */
    public void removeNextChain() {this.nextChain = null;}
}
