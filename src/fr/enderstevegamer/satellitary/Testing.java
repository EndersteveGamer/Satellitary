package fr.enderstevegamer.satellitary;

import fr.enderstevegamer.satellitary.datatrees.Tree;
import org.jetbrains.annotations.Contract;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public class Testing {
    @Contract(pure = true)
    public static void main(String[] args) throws OperationNotSupportedException {
        Tree<Integer> tree = new Tree<>(0)
                .addSubTree(
                        new Tree<>(1)
                                .addSubTree(
                                        new Tree<>(2)
                                                .addSubTree(4)
                                                .addSubTree(5)

                                )
                                .addSubTree(
                                        new Tree<>(3)
                                                .addSubTree(6)
                                                .addSubTree(7)
                        )
                )
                .addSubTree(
                        new Tree<>(8)
                                .addSubTree(
                                        new Tree<>(9)
                                                .addSubTree(11)
                                                .addSubTree(12)
                                )
                                .addSubTree(
                                        new Tree<>(10)
                                                .addSubTree(13)
                                                .addSubTree(14)
                                )
                );
        Tree<Integer> otherTree = new Tree<>(0)
                .addSubTree(
                        new Tree<>(
                                1
                        )
                                .addSubTree(
                                        new Tree<>(2)
                                )
                                .addSubTree(
                                        new Tree<>(3)
                                                .addSubTree(5)
                                                .addSubTree(
                                                        new Tree<>(6)
                                                                .addSubTree(
                                                                        new Tree<>(14)
                                                                )
                                                )
                                )
                                .addSubTree(4)
                )
                .addSubTree(
                        new Tree<>(7)
                                .addSubTree(8)
                                .addSubTree(9)
                                .addSubTree(10)
                                .addSubTree(11)
                )
                .addSubTree(
                        new Tree<>(12)
                                .addSubTree(13)
                );
    }
}
