package fr.enderstevegamer.satellitary;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * <p>
 *     A class representing a {@link Queue}
 * </p>
 * <p>
 *     When inserting an object, they are put at the end of the {@link Queue}
 * </p>
 * <p>
 *     When unqueuing an object, the first inserted object is removed from the {@link Queue} and returned
 * </p>
 * @param <T> The type of the elements contained in the {@link Queue}
 */
public class Queue<T> {
    private final ArrayList<T> objects;

    /**
     * A constructor creating an empty {@link Queue}
     */
    @Contract(pure = true)
    public Queue() {objects = new ArrayList<>();}

    /**
     * A contructor cloning the given {@link Queue}
     * @param other The {@link Queue} to clone
     */
    @Contract(pure = true)
    public Queue(@NotNull Queue<T> other) {this.objects = new ArrayList<>(other.objects);}

    /**
     * Adds an object at the end of the {@link Queue}
     * @param object The object to add to the {@link Queue}
     */
    public void queue(T object) {objects.add(object);}

    /**
     * Removes the first inserted object from the {@link Queue}
     * @return The removed object
     */
    public T unqueue() {
        if (objects.isEmpty()) throw new IllegalStateException("Can't unqueue from an empty queue!");
        return objects.remove(0);
    }
}
