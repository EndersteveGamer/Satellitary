package fr.enderstevegamer.satellitary.datatrees;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class Tree<T> {
    private T root;
    protected final ArrayList<Tree<T>> subTrees;

    @Contract(pure = true)
    public Tree(T root, ArrayList<Tree<T>> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
    }

    public Tree(T root) {
        this(root, new ArrayList<>());
    }

    public T getRoot() {return this.root;}

    public void setRoot(T root) {this.root = root;}

    public List<Tree<T>> getSubTrees() {return List.copyOf(this.subTrees);}

    public int getSubTreeNum() {return this.subTrees.size();}

    public Tree<T> getSubTree(int subTreeIndex) {return this.subTrees.get(subTreeIndex);}

    public Tree<T> addSubTree(Tree<T> subTree) {this.subTrees.add(subTree); return this;}

    public Tree<T> addSubTree(T object) {this.addSubTree(new Tree<>(object)); return this;}

    public Tree<T> removeSubTree(int subTreeIndex) {return this.subTrees.remove(subTreeIndex);}

    public boolean hasSubTrees() {return !this.subTrees.isEmpty();}

    public T getElement(@NotNull TreeIndex index) throws OperationNotSupportedException {
        if (index.isEmpty()) return root;
        TreeIndex nextIndex;
        int nextTree;
        if (index.isUnique()) {
            nextIndex = new TreeIndex();
            nextTree = index.getUnique();
        }
        else {
            nextIndex = index.subIndex();
            nextTree = index.getFirstIndex();
        }
        nextTree--;
        if (nextTree < 0 || nextTree >= this.subTrees.size()) throw new IndexOutOfBoundsException(
                "subtree index " + nextTree + " out of bounds for " + this.subTrees.size() + " trees!\n"
                        + "Current index: " + index
        );
        return this.subTrees.get(nextTree).getElement(nextIndex);
    }

    public Tree<T> getSubTree(@NotNull TreeIndex index) {
        if (index.isEmpty()) return this;
        return this.subTrees.get(index.getFirstIndex() - 1).getSubTree(index.subIndex());
    }

    public Tree<T> removeSubTree(@NotNull TreeIndex index) throws OperationNotSupportedException {
        if (index.isEmpty()) throw new UnsupportedOperationException("This tree can't remove itself!");
        if (index.isUnique()) {
            return this.subTrees.remove(index.getUnique() - 1);
        }
        return this.subTrees.get(index.getFirstIndex() - 1).removeSubTree(index.subIndex());
    }

    public ArrayList<T> getLeaves() {
        if (!this.hasSubTrees()) return new ArrayList<>(List.of(this.getRoot()));
        ArrayList<T> list = new ArrayList<>();
        for (Tree<T> tree : this.subTrees) list.addAll(tree.getLeaves());
        return list;
    }

    public ArrayList<T> getAllElements() {
        ArrayList<T> list = new ArrayList<>(List.of(this.getRoot()));
        if (!this.hasSubTrees()) return list;
        for (Tree<T> tree : this.subTrees) list.addAll(tree.getAllElements());
        return list;
    }

    public void forEach(Consumer<Tree<T>> consumer) {
        this.subTrees.forEach(consumer);
    }

    public ArrayList<TreeElementSearchResult<T>> findElement(@NotNull Predicate<T> predicate, @NotNull TreeIndex currentIndex) {
        ArrayList<TreeElementSearchResult<T>> list = new ArrayList<>(predicate.test(this.getRoot())
                ? List.of(new TreeElementSearchResult<>(this.getRoot(), currentIndex))
                : List.of());
        for (int i = 0; i < this.subTrees.size(); i++) {
            list.addAll(this.subTrees.get(i).findElement(predicate, new TreeIndex(currentIndex, i + 1)));
        }
        return list;
    }

    public ArrayList<TreeElementSearchResult<T>> findElement(Predicate<T> predicate) {
        return findElement(predicate, new TreeIndex());
    }

    public ArrayList<TreeElementSearchResult<T>> findElement(T object) {return findElement(o -> o.equals(object));}

    public Optional<TreeSearchResult<T>> findFirstCommonTree(TreeIndex index, T otherObject) {
        if (this.getSubTree(index).getAllElements().contains(otherObject)) return Optional.of(
                new TreeSearchResult<>(this.getSubTree(index),
                        index
                )
        );
        if (index.isEmpty()) return Optional.empty();
        return findFirstCommonTree(index.parentIndex(), otherObject);
    }

    private ArrayList<TreeSearchResult<T>> findSmallestCommonTree(List<T> objects, TreeIndex currentIndex) {
        ArrayList<TreeSearchResult<T>> validTrees = this.getSubTreesContaining(objects, currentIndex);
        ArrayList<TreeSearchResult<T>> selected = new ArrayList<>();
        int maxSize = Integer.MIN_VALUE;
        for (TreeSearchResult<T> tree : validTrees) {
            if (tree.foundIndex.indexes.size() > maxSize) {
                selected = new ArrayList<>(List.of(tree));
                maxSize = tree.foundIndex.indexes.size();
            }
            else if (tree.foundIndex.indexes.size() == maxSize) selected.add(tree);
        }
        return selected;
    }

    public ArrayList<TreeSearchResult<T>> findSmallestCommonTree(List<T> objects) {
        return findSmallestCommonTree(objects, new TreeIndex());
    }

    private @NotNull ArrayList<TreeSearchResult<T>> getSubTreesContaining(List<T> objects, TreeIndex currentIndex) {
        if (!this.containsAllElements(objects)) return new ArrayList<>();
        ArrayList<TreeSearchResult<T>> results = new ArrayList<>(
                List.of(new TreeSearchResult<>(this, currentIndex))
        );
        for (int i = 0; i < this.subTrees.size(); i++) {
            results.addAll(
                    this.subTrees.get(i).getSubTreesContaining(objects, new TreeIndex(currentIndex, i + 1))
            );
        }
        return results;
    }

    public boolean containsAllElements(List<T> objects) {
        objects: for (T obj : objects) {
            for (T element : this.getAllElements()) {
                if (element.equals(obj)) continue objects;
            }
            return false;
        }
        return true;
    }

    public ArrayList<T> getChildElements(TreeIndex index) {
        ArrayList<Tree<T>> subTrees = this.getSubTree(index).subTrees;
        ArrayList<T> list = new ArrayList<>();
        for (Tree<T> tree : subTrees) list.add(tree.getRoot());
        return list;
    }

    public record TreeElementSearchResult<T>(T object, TreeIndex foundIndex) {
        @Contract(pure = true)
        public T getObject() {return this.object;}
        @Contract(pure = true)
        public TreeIndex getFoundIndex() {return this.foundIndex;}

        @Override
        public @NotNull String toString() {
            return object.toString() + " | " + foundIndex;
        }
    }

    public record TreeSearchResult<T>(Tree<T> tree, TreeIndex foundIndex) {
        @Contract(pure = true)
        public Tree<T> getTree() {return this.tree;}
        @Contract(pure = true)
        public TreeIndex getFoundIndex() {return this.foundIndex;}

        @Override
        public String toString() {
            return foundIndex.toString();
        }
    }

    public static class TreeIndex {
        ArrayList<Integer> indexes;

        @Contract(pure = true)
        public TreeIndex() {
            indexes = new ArrayList<>();
        }

        @Contract(pure = true)
        public TreeIndex(@NotNull TreeIndex other) {
            indexes = new ArrayList<>(other.indexes);
        }

        public TreeIndex(@NotNull TreeIndex other, Integer indexes) {
            ArrayList<Integer> list = new ArrayList<>(other.indexes);
            list.add(indexes);
            this.indexes = list;
        }

        public TreeIndex(Integer... treeIndex) {
            indexes = new ArrayList<>(Arrays.asList(treeIndex));
        }

        @Contract(pure = true)
        protected boolean isUnique() {return this.indexes.size() == 1;}

        protected Integer getUnique() throws OperationNotSupportedException {
            if (!isUnique()) throw new OperationNotSupportedException(
                    """
                    Can't get unique from a non-unique TreeIndex!
                    Check if the TreeIndex is unique with TreeIndex#isUnique before calling this method!
                    """
            );
            return this.indexes.get(0);
        }

        protected boolean isEmpty() {return this.indexes.isEmpty();}

        protected Integer getFirstIndex() {
            if (this.isEmpty()) throw new IllegalStateException("Can't get the first index of an empty TreeIndex!");
            return this.indexes.get(0);
        }

        protected TreeIndex subIndex() {
            if (this.isEmpty()) throw new IllegalStateException("Can't get a subindex of an empty TreeIndex!");
            TreeIndex newIndex = new TreeIndex(this);
            newIndex.indexes.remove(0);
            return newIndex;
        }

        protected TreeIndex parentIndex() {
            TreeIndex parentIndex = new TreeIndex(this);
            if (this.isEmpty()) return parentIndex;
            parentIndex.indexes.remove(parentIndex.indexes.size() - 1);
            return parentIndex;
        }

        @Override
        public String toString() {
            if (this.isEmpty()) return "0";
            StringBuilder string = new StringBuilder();
            for (int i = 0; i < indexes.size(); i++) {
                string.append(indexes.get(i));
                if (i < indexes.size() - 1) string.append('.');
            }
            return string.toString();
        }
    }
}
