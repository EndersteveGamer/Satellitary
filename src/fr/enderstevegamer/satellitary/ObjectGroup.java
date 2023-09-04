package fr.enderstevegamer.satellitary;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("unused")
public class ObjectGroup<T> {
    private final ArrayList<T> objects;



    @Contract(pure = true)
    public ObjectGroup() {
        this.objects = new ArrayList<>();
    }

    @SafeVarargs
    public ObjectGroup(T... elements) {
        this(); Collections.addAll(this.objects, elements);
    }

    public ObjectGroup(Collection<T> elements) {
        this(); this.objects.addAll(elements);
    }

    public int size() {return this.objects.size();}


    public boolean add(@NotNull T element) {return this.objects.add(element);}

    public boolean add(@NotNull ObjectGroup<? extends T> otherGroup) {return this.addAll(otherGroup.getObjects());}

    public boolean addAll(@NotNull Collection<? extends T> collection) {
        boolean wasChanged = false;
        for (T t : collection) wasChanged = this.add(t) || wasChanged;
        return wasChanged;
    }

    public boolean remove(T element) {return this.objects.remove(element);}

    public T remove(int index) {return this.objects.remove(index);}

    public boolean remove(@NotNull ObjectGroup<? extends T> otherGroup) {return this.removeAll(otherGroup.getObjects());}

    public boolean removeAll(@NotNull Collection<? extends T> collection) {
        boolean wasChanged = false;
        for (T t : collection) wasChanged = this.remove(t) || wasChanged;
        return wasChanged;
    }

    public void clear() {this.objects.clear();}





    public ArrayList<T> getObjects() {return this.objects;}
}
