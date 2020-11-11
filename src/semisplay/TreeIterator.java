package semisplay;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TreeIterator<E extends Comparable<E>> implements Iterator<E>
{
    private final Stack<TreeNode<E>> todo;

    TreeIterator(TreeNode<E> root)
    {
        todo = new Stack<>();

        while (root != null)
        {
            todo.add(root);
            root = root.getLeft();
        }
    }

    @Override
    public boolean hasNext()
    {
        return !todo.isEmpty();
    }

    @Override
    public E next()
    {
        // Moet volgens contract
        if (!hasNext())
            throw new NoSuchElementException();

        /*
         * De nodes die nog geoutput moeten worden komen op de "todo" stack terecht.
         * Het hele linkerpad wordt op de todo stack gezet.
         * Per node die je tegenkomt doe je hetzelfde op het rechterkind.
         * Wanneer je niet meer verder kan, output je 1 voor 1 de elementen.
         */

        TreeNode<E> node = todo.pop();
        E result = node.getData();
        node = node.getRight();
        while (node != null)
        {
            todo.add(node);
            node = node.getLeft();
        }

        return result;
    }
}
