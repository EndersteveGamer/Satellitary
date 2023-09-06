package fr.enderstevegamer.satellitary;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *     A class representing a {@link Stack}
 * </p>
 * <p>
 *     When inserting an object, they are put on the top on the {@link Stack}
 * </p>
 * <p>
 *     When unstacking an object, the last inserted object is removed from the {@link Stack} and returned
 * </p>
 * @param <T> The type of the elements contained in the {@link Stack}
 */
public class Stack<T> {
    private final ArrayList<T> objects;

    /**
     * A constructor creating an empty {@link Stack}
     */
    @Contract(pure = true)
    public Stack() {this.objects = new ArrayList<>();}

    /**
     * <p>
     *     A constructor creating a {@link Stack} from a {@link List}
     * </p>
     * <p>
     *     The first element of the list will be on top of the {@link Stack}
     * </p>
     * @param list The {@link List} to create the {@link Stack} from
     */
    public Stack(@NotNull List<T> list) {
        this.objects = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) this.objects.add(list.get(i));
    }

    /**
     * A contructor cloning the given {@link Stack}
     * @param other The {@link Stack} to clone
     */
    @Contract(pure = true)
    public Stack(@NotNull Stack<? extends T> other) {
        this.objects = new ArrayList<>(other.objects);
    }

    /**
     * Adds an object on the top of the {@link Stack}
     * @param object The object to add to the {@link Stack}
     */
    public void stack(T object) {
        objects.add(object);
    }

    /**
     * Removes the last inserted object from the {@link Stack}
     * @return The removed object
     */
    public T unstack() {
        if (objects.isEmpty()) throw new IllegalStateException("Can't unstack from en empty stack!");
        return objects.remove(objects.size() - 1);
    }

    public boolean isEmpty() {return this.objects.isEmpty();}

    public int stackSize() {return this.objects.size();}
}
