package fr.enderstevegamer.satellitary.datatrees;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BinaryTree<T> extends Tree<T> {
    public BinaryTree(T root, Tree<T> leftTree, Tree<T> rightTree) {
        super(root, new ArrayList<>(List.of(leftTree, rightTree)));
    }

    public BinaryTree(T root) {
        super(root);
    }

    public Optional<Tree<T>> getLeft() {
        return this.hasSubTrees() ? Optional.of(this.subTrees.get(0)) : Optional.empty();
    }

    public void setLeft(Tree<T> subTree) {this.subTrees.set(0, subTree);}

    public Optional<Tree<T>> getRight() {
        return this.hasSubTrees() ? Optional.of(this.subTrees.get(1)) : Optional.empty();
    }

    public void setRight(Tree<T> subTree) {this.subTrees.set(1, subTree);}
}
