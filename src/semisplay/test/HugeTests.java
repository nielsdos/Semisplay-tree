package semisplay.test;

import org.junit.Assert;
import org.junit.Test;
import semisplay.SemiSplayTree;

import java.util.ArrayList;
import java.util.List;

public class HugeTests
{
    /**
     * Bouwt een lange keten boom op en test op een paar dingen
     *
     * @param k k
     * @param n aantal elementen
     */
    private void helper(int k, int n)
    {
        List<Integer> list = new ArrayList<>();
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(k);
        for (int i = 0; i < n; ++i)
        {
            Assert.assertTrue(tree.add(i));
            list.add(i);
        }

        Assert.assertEquals(n, tree.size());

        for (int i = 0; i < n; ++i)
            Assert.assertTrue(tree.contains(i));

        Assert.assertEquals(n, tree.size());
        Assert.assertEquals(list, Util.iteratorToList(tree.iterator())); // Kan optimaler, maar is slechts een test, geen production code
    }

    @Test
    public void chain3()
    {
        helper(3, 5000000);
    }

    @Test
    public void chain4()
    {
        helper(4, 5000000);
    }

    @Test
    public void chain5()
    {
        helper(5, 5000000);
    }

    @Test
    public void chain6()
    {
        helper(6, 5000000);
    }

    @Test
    public void chain7()
    {
        helper(7, 5000000);
    }

    @Test
    public void chain8()
    {
        helper(8, 5000000);
    }

    @Test
    public void chain9()
    {
        helper(9, 5000000);
    }

    @Test
    public void chain10()
    {
        helper(10, 5000000);
    }

    @Test
    public void chain15()
    {
        helper(15, 5000000);
    }

    @Test
    public void chain20()
    {
        helper(20, 5000000);
    }
}
