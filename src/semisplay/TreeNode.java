package semisplay;

public final class TreeNode<E extends Comparable<E>>
{
    private E data;
    private TreeNode<E> left;
    private TreeNode<E> right;
    private TreeNode<E> parent;

    TreeNode(E data)
    {
        this.data = data;
    }

    public void setData(E e)
    {
        data = e;
    }

    public E getData()
    {
        return data;
    }

    public TreeNode<E> getLeft()
    {
        return left;
    }

    public boolean hasLeft()
    {
        return getLeft() != null;
    }

    public void setLeft(TreeNode<E> left)
    {
        this.left = left;
        if (left != null) left.parent = this;
    }

    public TreeNode<E> getRight()
    {
        return right;
    }

    public boolean hasRight()
    {
        return getRight() != null;
    }

    public void setRight(TreeNode<E> right)
    {
        this.right = right;
        if (right != null) right.parent = this;
    }

    public TreeNode<E> getParent()
    {
        return parent;
    }

    public void setParent(TreeNode<E> parent)
    {
        this.parent = parent;
    }

    public boolean isLeaf()
    {
        return !hasLeft() && !hasRight();
    }
}
