package semisplay;

import java.util.*;

/**
 * Een semisplay boom
 *
 * @param <E> Het type van de data
 */
public class SemiSplayTree<E extends Comparable<E>> implements SearchTree<E>
{
    private final int k;
    private TreeNode<E> root;
    private int size;

    /*
     * Deze ArrayList zal hergebruikt worden doorheen method calls.
     * Het is iets efficiënter om de lijst te clearen dan een nieuwe aan te maken.
     * De keuze voor een ArrayList is om twee redenen:
     *   1) Bijna net zo snel als een gewone array om te accessen.
     *   2) Je kan geen array van generics maken zonder dat Java klaagt.
     *
     * Ik weet op voorhand hoe lang de ArrayList zal zijn, dus ik kan de capaciteit pre-allocaten.
     * Hierdoor gebeurt de add bewerking in constante tijd, want er zal nooit uitgebreid moeten worden.
     * Na enkele tests merk ik dat het performance verschil tussen arrays en pre-allocated ArrayList bijna niet merkbaar is.
     * Vandaar deze keuze.
     */
    private final ArrayList<TreeNode<E>> outerTrees;
    private final ArrayList<TreeNode<E>> nodesOnPath;

    /**
     * Maakt een lege semisplay boom met splaypad lengte k aan
     *
     * @param k Lengte van het splaypad
     */
    public SemiSplayTree(int k)
    {
        // Opgave: "minstens 3"
        if (k < 3)
            throw new IllegalArgumentException("k must be at least 3, k = " + k);

        this.k = k;
        outerTrees = new ArrayList<>(k + 1);
        nodesOnPath = new ArrayList<>(k);
    }

    @Override
    public boolean add(E e)
    {
        if (root == null)
        {
            root = new TreeNode<>(e);
        }
        else
        {
            TreeNode<E> current = root;
            TreeNode<E> parent = null;
            int comp = 0;

            while (current != null)
            {
                parent = current;

                comp = e.compareTo(current.getData());
                if (comp == 0)
                {
                    semiSplay(current);
                    return false;
                }
                else if (comp < 0)
                    current = current.getLeft();
                else
                    current = current.getRight();
            }

            current = new TreeNode<>(e);
            if (comp < 0)
                parent.setLeft(current);
            else
                parent.setRight(current);

            semiSplay(current);
        }

        ++size;
        return true;
    }

    /**
     * Voer semisplay uit met semisplaypad eindpunt "current".
     * Vanaf daar wordt er k omhoog geteld, en dan heb je een pad van lengte k dat gesplayed wordt.
     * Dit wordt herhaald tot je niet meer verder omhoog kan.
     *
     * @param current Het eindpunt voor het semisplaypad.
     */
    private void semiSplay(TreeNode<E> current)
    {
        TreeNode<E> pathEnd = current;

        while (current != null)
        {
            for (int count = 1; count < k && current != null; ++count)
                current = current.getParent();

            if (current != null)
            {
                E end = pathEnd.getData();
                TreeNode<E> grandparent = current.getParent();
                TreeNode<E> result = semiSplaySingleStep(current, end);

                // Link herstellen in de boom. Indien null, dan was het de wortel.
                if (grandparent != null)
                {
                    replaceParentLink(grandparent, current, result);

                    // Blijven doorgaan met splayen
                    pathEnd = current = result;
                }
                else
                {
                    root = result;
                    root.setParent(null);
                    current = null;
                }
            }
        }
    }

    /**
     * Een enkele semisplay bewerking.
     *
     * @param node Startpunt van het splaypad
     * @param e    Eindpunt data, nodig voor de richting te weten
     * @return Wortel van de geconstrueerde vervangboom
     */
    private TreeNode<E> semiSplaySingleStep(TreeNode<E> node, E e)
    {
        // Al deze stappen zijn theta(k)
        fillPathAndOuterTrees(node, e);
        TreeNode<E> result = constructBinTree(0, k - 1);
        // Buitenbomen herkoppelen aan de nodes
        for (int i = 0, j = 0; i < k; ++i)
        {
            TreeNode<E> n = nodesOnPath.get(i);

            if (!n.hasLeft())
                n.setLeft(outerTrees.get(j++));
            if (!n.hasRight())
                n.setRight(outerTrees.get(j++));
        }

        outerTrees.clear();
        nodesOnPath.clear();

        return result;
    }

    /**
     * Construeert een zo goed mogelijk gebalanceerde binaire boom
     *
     * @param start       Startindex van de deellijst
     * @param end         Eindindex van de deellijst
     * @return Wortel van de (deel)boom
     */
    private TreeNode<E> constructBinTree(int start, int end)
    {
        if (start > end)
            return null;

        int mid = (start + end) / 2;

        TreeNode<E> node = nodesOnPath.get(mid);
        node.setLeft(constructBinTree(start, mid - 1));
        node.setRight(constructBinTree(mid + 1, end));

        return node;
    }

    /**
     * Vult het pad gesorteerd op, en voegt de buitenbomen toe aan een lijst
     *
     * @param root        Wortel van de deelboom om te vertrekken
     * @param data        Data om te vinden
     */
    private void fillPathAndOuterTrees(TreeNode<E> root, E data)
    {
        int comp = data.compareTo(root.getData());

        if (comp < 0)
            fillPathAndOuterTrees(root.getLeft(), data);
        else
            outerTrees.add(root.getLeft());

        nodesOnPath.add(root);

        if (comp > 0)
            fillPathAndOuterTrees(root.getRight(), data);
        else
            outerTrees.add(root.getRight());
    }

    @Override
    public boolean contains(E e)
    {
        TreeNode<E> parent = null;
        TreeNode<E> current = root;

        while (current != null)
        {
            parent = current;

            int comp = e.compareTo(current.getData());

            if (comp == 0)
            {
                semiSplay(current);
                return true;
            }
            else if (comp < 0)
                current = current.getLeft();
            else // comp > 0
                current = current.getRight();
        }

        semiSplay(parent);

        return false;
    }

    @Override
    public boolean remove(E e)
    {
        TreeNode<E> parent = root;
        TreeNode<E> current = root;
        int comp;

        while (current != null && (comp = e.compareTo(current.getData())) != 0)
        {
            parent = current;

            if (comp < 0)
                current = current.getLeft();
            else // comp > 0
                current = current.getRight();
        }

        // Zat er niet in
        if (current == null)
        {
            semiSplay(parent);
            return false;
        }

        semiSplay(removeNode(current));
        --size;

        return true;
    }

    /**
     * Remove node
     *
     * @param current De node die verwijderd moet worden
     * @return De node die gesemisplayed moet worden
     */
    private TreeNode<E> removeNode(TreeNode<E> current)
    {
        // Geval: blad
        if (current.isLeaf())
        {
            // Geval: wortel
            if (current == root)
            {
                root = null;
                return null;
            }
            // Geval: heeft een ouder, dus we moeten kijken welke link we moeten verbreken
            else
            {
                TreeNode<E> parent = current.getParent();
                replaceParentLink(parent, current, null);
                return parent;
            }
        }
        // Geval: enkel rechterkind => parent linken met rechterkind
        else if (!current.hasLeft())
        {
            TreeNode<E> replacement = current.getRight();
            current.setData(replacement.getData());
            current.setLeft(replacement.getLeft());
            current.setRight(replacement.getRight());
            return current;
        }
        // Geval: enkel linkerkind => parent linken met linkerkind
        else if (!current.hasRight())
        {
            TreeNode<E> replacement = current.getLeft();
            current.setData(replacement.getData());
            current.setLeft(replacement.getLeft());
            current.setRight(replacement.getRight());
            return current;
        }
        // Geval: twee kinderen
        else
        {
            TreeNode<E> replacement = minimum(current.getRight());
            TreeNode<E> parent = replacement.getParent();
            current.setData(replacement.getData());
            removeNode(replacement);
            return parent;
        }
    }

    /**
     * Vervang een link in de parent naar het kind door een andere node
     *
     * @param parent      De parent node
     * @param child       De kind node
     * @param replacement De vervangende node
     */
    private void replaceParentLink(TreeNode<E> parent, TreeNode<E> child, TreeNode<E> replacement)
    {
        if (parent.getLeft() == child)
            parent.setLeft(replacement);
        else
            parent.setRight(replacement);
    }

    /**
     * Zoekt de kleinste node in deze deelboom
     *
     * @param node De wortel van de deelboom
     * @return De kleinste node
     */
    private TreeNode<E> minimum(TreeNode<E> node)
    {
        while (node.hasLeft())
            node = node.getLeft();

        return node;
    }

    @Override
    public int size()
    {
        return size;
    }

    /**
     * Diepte van de deelboom berekenen
     *
     * @param node De wortel van de deelboom
     * @return Diepte
     */
    private int depth(TreeNode<E> node)
    {
        if (node == null) return -1; // Want gedefinieerd als aantal bogen, niet aantal toppen

        int l = depth(node.getLeft());
        int r = depth(node.getRight());

        return Math.max(l, r) + 1;
    }

    @Override
    public int depth()
    {
        return depth(root);
    }

    @Override
    public Iterator<E> iterator()
    {
        return new TreeIterator<>(root);
    }

    /**
     * Voor tests & debug: breedte eerst output in een lijst
     */
    public List<E> bfs()
    {
        List<E> l = new ArrayList<>(size());

        if (root == null)
            return l;

        LinkedList<TreeNode<E>> queue = new LinkedList<>();

        queue.add(root);
        while (!queue.isEmpty())
        {
            TreeNode<E> n = queue.removeFirst();

            l.add(n.getData());

            if (n.hasLeft())
                queue.add(n.getLeft());
            if (n.hasRight())
                queue.add(n.getRight());
        }

        return l;
    }
}
