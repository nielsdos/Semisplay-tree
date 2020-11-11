package semisplay.test;

import org.junit.Assert;
import org.junit.Test;
import semisplay.SemiSplayTree;

import java.util.*;

public class RemoveTests
{
    @Test
    public void removeNonExistentFromEmptyTreeCustom6()
    {
        SemiSplayTree<Custom> tree = new SemiSplayTree<>(6);
        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.depth());
        Assert.assertFalse(tree.iterator().hasNext());
        Assert.assertEquals(0, tree.bfs().size());
        Assert.assertFalse(tree.contains(new Custom("appel", -100)));
        Assert.assertFalse(tree.remove(new Custom("peer", 3)));
        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.depth());
        Assert.assertFalse(tree.iterator().hasNext());
        Assert.assertEquals(0, tree.bfs().size());
    }

    @Test
    public void removeNonExistentFromNonEmptyTreeInteger3()
    {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Assert.assertTrue(tree.add(10));
        Assert.assertTrue(tree.add(8));
        Assert.assertTrue(tree.add(12));
        Assert.assertTrue(tree.add(20));
        Assert.assertTrue(tree.add(40));
        Assert.assertTrue(tree.add(30));
        Assert.assertTrue(tree.add(0));
        Assert.assertTrue(tree.add(3));
        Assert.assertTrue(tree.add(4));

        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(3, tree.depth());
        Assert.assertEquals(Arrays.asList(12, 4, 30, 3, 8, 20, 40, 0, 10), tree.bfs());
        Assert.assertEquals(Arrays.asList(0, 3, 4, 8, 10, 12, 20, 30, 40), Util.iteratorToList(tree.iterator()));

        Assert.assertFalse(tree.remove(90));

        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(30, 12, 40, 4, 20, 3, 8, 0, 10), tree.bfs());
        Assert.assertEquals(Arrays.asList(0, 3, 4, 8, 10, 12, 20, 30, 40), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void removeAllInteger5()
    {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
        final int n = 10000;

        for(int i = 0; i < n; ++i)
            Assert.assertTrue(tree.add(i));

        Assert.assertEquals(n, tree.size());
        Assert.assertEquals(4999, tree.depth());

        for(int i = 0; i < n; ++i)
            Assert.assertTrue(tree.contains(i));

        Assert.assertEquals(n, tree.size());
        Assert.assertEquals(2306, tree.depth());

        for(int i = 0; i < n; ++i)
            Assert.assertTrue(tree.remove(i));

        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.depth());

        for(int i = 0; i < n; ++i)
            Assert.assertFalse(tree.contains(i));
    }

    @Test
    public void removeRootCustom6()
    {
        SemiSplayTree<Custom> tree = new SemiSplayTree<>(6);
        Assert.assertTrue(tree.add(new Custom("appel", 600)));
        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(0, tree.depth());
        Assert.assertEquals(Collections.singletonList(new Custom("appel", 600)), tree.bfs());
        Assert.assertEquals(Collections.singletonList(new Custom("appel", 600)), Util.iteratorToList(tree.iterator()));
        Assert.assertTrue(tree.remove(new Custom("appel", 600)));
        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.depth());
        Assert.assertFalse(tree.iterator().hasNext());
        Assert.assertEquals(0, tree.bfs().size());
    }

    @Test
    public void removeLeafCustom6()
    {
        SemiSplayTree<Custom> tree = new SemiSplayTree<>(6);
        Assert.assertTrue(tree.add(new Custom("appel", 500)));
        Assert.assertTrue(tree.add(new Custom("peer", 400)));
        Assert.assertEquals(2, tree.size());
        Assert.assertEquals(1, tree.depth());
        Assert.assertEquals(Arrays.asList(new Custom("appel", 500), new Custom("peer", 400)), tree.bfs());
        Assert.assertEquals(Arrays.asList(new Custom("peer", 400), new Custom("appel", 500)), Util.iteratorToList(tree.iterator()));
        Assert.assertFalse(tree.remove(new Custom("peer", 500)));
        Assert.assertTrue(tree.remove(new Custom("peer", 400)));
        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(0, tree.depth());
        Assert.assertEquals(Collections.singletonList(new Custom("appel", 500)), tree.bfs());
        Assert.assertEquals(Collections.singletonList(new Custom("appel", 500)), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void removeLeafCharacter4()
    {
        SemiSplayTree<Character> tree = new SemiSplayTree<>(4);
        Assert.assertTrue(tree.add('B'));
        Assert.assertTrue(tree.add('A'));
        Assert.assertTrue(tree.add('C'));
        Assert.assertEquals(3, tree.size());
        Assert.assertEquals(1, tree.depth());
        Assert.assertTrue(tree.remove('C'));
        Assert.assertFalse(tree.remove('C'));
        Assert.assertEquals(2, tree.size());
        Assert.assertEquals(1, tree.depth());
        Assert.assertEquals(Arrays.asList('B', 'A'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B'), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void removeMultipleWithSplayCharacter4()
    {
        SemiSplayTree<Character> tree = new SemiSplayTree<>(6);
        for(int i = 0; i < 20; ++i)
            Assert.assertTrue(tree.add((char)('A' + i)));

        Assert.assertEquals(20, tree.size());
        Assert.assertEquals(6, tree.depth());
        Assert.assertEquals(Arrays.asList('O', 'L', 'Q', 'I', 'N', 'P', 'R', 'F', 'K', 'M', 'S', 'C', 'H', 'J', 'T', 'A', 'E', 'G', 'B', 'D'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.remove('E'));

        Assert.assertEquals(19, tree.size());
        Assert.assertEquals(6, tree.depth());
        Assert.assertEquals(Arrays.asList('F', 'C', 'L', 'A', 'D', 'I', 'O', 'B', 'H', 'K', 'N', 'Q', 'G', 'J', 'M', 'P', 'R', 'S', 'T'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.remove('S'));

        Assert.assertEquals(18, tree.size());
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(Arrays.asList('O', 'F', 'R', 'C', 'L', 'Q', 'T', 'A', 'D', 'I', 'N', 'P', 'B', 'H', 'K', 'M', 'G', 'J'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'T'), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void removeShiftCharacter4()
    {
        SemiSplayTree<Character> tree = new SemiSplayTree<>(4);

        Assert.assertTrue(tree.add('F'));
        Assert.assertTrue(tree.add('B'));
        Assert.assertTrue(tree.add('Z'));
        Assert.assertTrue(tree.add('H'));
        Assert.assertTrue(tree.add('A'));
        Assert.assertTrue(tree.add('G'));

        Assert.assertEquals(6, tree.size());
        Assert.assertEquals(3, tree.depth());
        Assert.assertEquals(Arrays.asList('G', 'F', 'H', 'B', 'Z', 'A'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'F', 'G', 'H', 'Z'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.remove('H'));

        Assert.assertEquals(5, tree.size());
        Assert.assertEquals(3, tree.depth());
        Assert.assertEquals(Arrays.asList('G', 'F', 'Z', 'B', 'A'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'F', 'G', 'Z'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.remove('Z'));

        Assert.assertEquals(4, tree.size());
        Assert.assertEquals(3, tree.depth());
        Assert.assertEquals(Arrays.asList('G', 'F', 'B', 'A'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'F', 'G'), Util.iteratorToList(tree.iterator()));
    }

    private void randomRemove(int k)
    {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(k);
        final int n = 50; // Total amount of nodes

        List<Integer> l = new ArrayList<>();
        for(int i = 0; i < n; ++i)
            l.add(i);

        Collections.shuffle(l);

        for(int i : l)
            Assert.assertTrue(tree.add(i));

        Collections.sort(l);
        Assert.assertEquals(n, tree.size());
        Assert.assertEquals(l, Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.remove(10));
        Assert.assertTrue(tree.remove(20));
        Assert.assertTrue(tree.remove(30));
        Assert.assertTrue(tree.remove(25));
        l.remove(l.indexOf(10));
        l.remove(l.indexOf(20));
        l.remove(l.indexOf(30));
        l.remove(l.indexOf(25));

        Assert.assertEquals(n - 4, tree.size());
        Assert.assertEquals(l, Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void randomRemove3()
    {
        randomRemove(3);
    }

    @Test
    public void randomRemove4()
    {
        randomRemove(4);
    }

    @Test
    public void randomRemove5()
    {
        randomRemove(5);
    }

    @Test
    public void randomRemove8()
    {
        randomRemove(8);
    }

    @Test
    public void randomRemove9()
    {
        randomRemove(9);
    }
}
