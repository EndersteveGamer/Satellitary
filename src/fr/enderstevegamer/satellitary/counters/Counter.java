package fr.enderstevegamer.satellitary.counters;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * A class used to link objects to a counter
 * @param <T> The type of object to link the counter to
 */
public class Counter <T> {
    private final HashMap<T, Double> counts;
    private final double startValue;
    private final double increment;

    /**
     * This constructor takes the start value of the counter and its default increment
     * @param startValue The start value of the counter. All the counters that are incremented or their values read
     *                   are defaulted to this value
     * @param increment The default increment of the counter. Each time a counter is incremented without specifying the
     *                  increment, this value is used instead
     * @see Counter#Counter(double)
     * @see Counter#Counter()
     */
    public Counter(double startValue, double increment) {
        counts = new HashMap<>();
        this.startValue = startValue;
        this.increment = increment;
    }

    /**
     * This constructor takes the start value of the counter
     * <p> The default increment is set to 1 </p>
     * @param startValue The start value of the counter. All the counters that are incremented or their values read
     *                   are defaulted to this value
     * @see Counter#Counter(double, double)
     * @see Counter#Counter()
     */
    public Counter(double startValue) {this(startValue, 1);}

    /**
     * This constructor takes no argument
     * <p> The start value is set to 0 </p>
     * <p> The default increment is set to 1 </p>
     * @see Counter#Counter(double, double)
     * @see Counter#Counter(double)
     */
    public Counter() {this(0);}

    /**
     * Gets the current count of the object
     * <p> If the object does not have a count, the start value will be returned </p>
     * @param key The object to get the count from
     * @return The count associated with the object
     */
    public double getCount(T key) {
        if (!counts.containsKey(key)) return startValue;
        return counts.get(key);
    }

    /**
     * Increments the count of the object by the increment
     * @param key The object which count to increment
     * @param increment The amount to increment
     * @see Counter#increment(Object) 
     */
    public void increment(T key, double increment) {
        if (!counts.containsKey(key)) {counts.put(key, startValue + increment); return;}
        counts.put(key, counts.get(key) + increment);
    }

    /**
     * Increments the count of the object by the default increment value
     * @param key The object which count to increment
     * @see Counter#increment(Object, double) 
     */
    public void increment(T key) {increment(key, this.increment);}

    /**
     * Increments all the values by the specified increment
     * @param increment The increment to add to all values
     * @see Counter#incrementAll() 
     */
    public void incrementAll(double increment) {
        counts.forEach((k, d) -> increment(k, increment));
    }

    /**
     * Increment all the value by the default increment
     * @see Counter#incrementAll(double) 
     */
    public void incrementAll() {
        counts.forEach((k, d) -> increment(k));
    }

    /**
     * Resets all the values of the counter to the start value
     */
    public void reset() {counts.clear();}

    /**
     * Reset the specified object counter to the start value
     * @param key The object which counter to reset
     */
    @Contract(pure = true)
    public void resetValue(T key) {counts.remove(key);}

    /**
     * Sets the values of this counter to the specified value
     * @param key The object which counter to change the value
     * @param count The value to set the counter to
     */
    @Contract(pure = true)
    public void setValue(T key, double count) {counts.put(key, count);}

    /**
     * Set the value of this counter for this object to the start value
     * @param key The object to initialize the value
     */
    @Contract(pure = true)
    public void setValue(T key) {setValue(key, startValue);}

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (T element : counts.keySet()) {
            string.append(element.toString()).append(": ").append(counts.get(element)).append("\n");
        }
        return string.toString();
    }

    /**
     * Applies the provided consumer to all the values of the counter
     * <p> The consumer takes each values of type T as a first argument, and the count
     * associated the the value as a second argument</p>
     * @param consumer The {@link BiConsumer} of type T and Double
     */
    public void forEach(BiConsumer<? super T, ? super Double> consumer) {
        for (T element : counts.keySet()) {
            consumer.accept(element, counts.get(element));
        }
    }
}
