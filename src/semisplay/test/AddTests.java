package semisplay.test;

import org.junit.Assert;
import org.junit.Test;
import semisplay.SemiSplayTree;

import java.util.Arrays;
import java.util.Collections;

public class AddTests
{
    @Test(expected = IllegalArgumentException.class)
    public void constructorArgument()
    {
        new SemiSplayTree<>(2);
    }

    @Test
    public void emptyInteger3()
    {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(3);
        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.depth());
        Assert.assertFalse(tree.contains(0));
        Assert.assertFalse(tree.iterator().hasNext());
        Assert.assertEquals(0, tree.bfs().size());
    }

    @Test
    public void addOnEmptyCharacter3()
    {
        SemiSplayTree<Character> tree = new SemiSplayTree<>(3);
        Assert.assertTrue(tree.add('A'));
        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(0, tree.depth());
        Assert.assertTrue(tree.contains('A'));
        Assert.assertFalse(tree.contains('B'));
        Assert.assertTrue(tree.iterator().hasNext());
        Assert.assertEquals(Collections.singletonList('A'), tree.bfs());
    }

    @Test
    public void doubleAddOnEmptyTreeCharacter3()
    {
        SemiSplayTree<Character> tree = new SemiSplayTree<>(3);
        Assert.assertTrue(tree.add('C'));
        Assert.assertFalse(tree.add('C'));
        Assert.assertEquals(tree.size(), 1);
        Assert.assertEquals(tree.depth(), 0);
        Assert.assertTrue(tree.contains('C'));
        Assert.assertFalse(tree.contains('D'));
        Assert.assertTrue(tree.iterator().hasNext());
        Assert.assertEquals(Collections.singletonList('C'), tree.bfs());
    }

    @Test
    public void addThreeStrings3()
    {
        SemiSplayTree<String> tree = new SemiSplayTree<>(3);
        Assert.assertTrue(tree.add("peer"));
        Assert.assertTrue(tree.add("banaan"));
        Assert.assertTrue(tree.add("druif"));
        Assert.assertEquals(3, tree.size());
        Assert.assertEquals(1, tree.depth());
        Assert.assertEquals(Arrays.asList("druif", "banaan", "peer"), tree.bfs());
        Assert.assertTrue(tree.contains("druif"));
        Assert.assertTrue(tree.contains("banaan"));
        Assert.assertTrue(tree.contains("peer"));
        Assert.assertFalse(tree.contains("appel"));
        Assert.assertEquals(Arrays.asList("banaan", "druif", "peer"), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void addADuplicateCustom3()
    {
        SemiSplayTree<Custom> tree = new SemiSplayTree<>(3);
        Assert.assertTrue(tree.add(new Custom("schaken", 3)));
        Assert.assertTrue(tree.add(new Custom("dammen", 100)));
        Assert.assertTrue(tree.add(new Custom("vier op een rij", 60)));
        Assert.assertTrue(tree.add(new Custom("presidenten", 1)));
        Assert.assertEquals(4, tree.size());
        Assert.assertEquals(2, tree.depth());

        Custom schaken = new Custom("schaken", 3);
        Custom presidenten = new Custom("presidenten", 1);
        Custom dammen = new Custom("dammen", 100);
        Custom vieropeenrij = new Custom("vier op een rij", 60);

        Assert.assertEquals(Arrays.asList(schaken, presidenten, vieropeenrij, dammen), tree.bfs());
        Assert.assertTrue(tree.contains(schaken));
        Assert.assertTrue(tree.contains(presidenten));
        Assert.assertTrue(tree.contains(dammen));
        Assert.assertTrue(tree.contains(vieropeenrij));
        Assert.assertFalse(tree.contains(new Custom("zzz", 9999)));
        Assert.assertEquals(4, tree.size());
        Assert.assertEquals(2, tree.depth());

        Assert.assertFalse(tree.add(schaken));
        Assert.assertEquals(4, tree.size());
        Assert.assertEquals(2, tree.depth());
        Assert.assertEquals(Arrays.asList(vieropeenrij, schaken, dammen, presidenten), tree.bfs());
        Assert.assertEquals(Arrays.asList(presidenten, schaken, vieropeenrij, dammen), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void containsTrueSplayInteger5()
    {
        // Test "contains" voor een element dat WEL in de boom zit
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(5);
        Assert.assertTrue(tree.add(5));
        Assert.assertTrue(tree.add(4));
        Assert.assertTrue(tree.add(7));
        Assert.assertTrue(tree.add(9));
        Assert.assertTrue(tree.add(10));
        Assert.assertTrue(tree.add(11));
        Assert.assertTrue(tree.add(2));
        Assert.assertTrue(tree.add(1));
        Assert.assertTrue(tree.add(3));
        Assert.assertTrue(tree.contains(11));
        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(9, 4, 10, 1, 5, 11, 2, 7, 3), tree.bfs());
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 7, 9, 10, 11), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void containsFalseSplayFloat5()
    {
        // Test "contains" voor een element dat NIET in de boom zit
        SemiSplayTree<Float> tree = new SemiSplayTree<>(5);
        Assert.assertTrue(tree.add(3.0f));
        Assert.assertTrue(tree.add(2.5f));
        Assert.assertTrue(tree.add(3.5f));
        Assert.assertTrue(tree.add(4.0f));
        Assert.assertTrue(tree.add(4.5f));
        Assert.assertTrue(tree.add(5.0f));
        Assert.assertTrue(tree.add(1.5f));
        Assert.assertTrue(tree.add(1.0f));
        Assert.assertTrue(tree.add(2.0f));
        Assert.assertFalse(tree.contains(6.0f));
        Assert.assertEquals(9, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(4.0f, 2.5f, 4.5f, 1.0f, 3.0f, 5.0f, 1.5f, 3.5f, 2.0f), tree.bfs());
        Assert.assertEquals(Arrays.asList(1.0f, 1.5f, 2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void addShouldBeACompleteBinaryTreeCustom7()
    {
        // Zou moeten resulteren in een complete binaire boom
        // Opmerking: instance niet hergebruikt om te valideren dat er effectief compareTo gebruikt wordt

        SemiSplayTree<Custom> tree = new SemiSplayTree<>(7);
        Assert.assertTrue(tree.add(new Custom("a", 100)));
        Assert.assertTrue(tree.add(new Custom("b", 0)));
        Assert.assertTrue(tree.add(new Custom("c", 90)));
        Assert.assertTrue(tree.add(new Custom("d", 10)));
        Assert.assertTrue(tree.add(new Custom("e", 80)));
        Assert.assertTrue(tree.add(new Custom("f", 20)));
        Assert.assertTrue(tree.add(new Custom("g", 30)));
        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.depth());
        Assert.assertEquals(Arrays.asList(
                new Custom("g", 30),
                new Custom("d", 10),
                new Custom("c", 90),
                new Custom("b", 0),
                new Custom("f", 20),
                new Custom("e", 80),
                new Custom("a", 100)
        ), tree.bfs());
        Assert.assertEquals(Arrays.asList(
                new Custom("b", 0),
                new Custom("d", 10),
                new Custom("f", 20),
                new Custom("g", 30),
                new Custom("e", 80),
                new Custom("c", 90),
                new Custom("a", 100)
        ), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void randomTree1Integer4()
    {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(4);
        Assert.assertTrue(tree.add(20));
        Assert.assertTrue(tree.add(16));
        Assert.assertTrue(tree.add(12));
        Assert.assertTrue(tree.add(8));
        Assert.assertTrue(tree.add(6));
        Assert.assertTrue(tree.add(4));
        Assert.assertTrue(tree.add(18));
        Assert.assertTrue(tree.add(0));
        Assert.assertTrue(tree.add(30));
        Assert.assertTrue(tree.add(5));
        Assert.assertTrue(tree.add(7));
        Assert.assertTrue(tree.add(25));

        Assert.assertEquals(12, tree.size());
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(Arrays.asList(6, 4, 8, 0, 5, 7, 20, 18, 25, 16, 30, 12), tree.bfs());
        Assert.assertEquals(Arrays.asList(0, 4, 5, 6, 7, 8, 12, 16, 18, 20, 25, 30), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add(11));
        Assert.assertEquals(13, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(8, 6, 12, 4, 7, 11, 20, 0, 5, 16, 25, 18, 30), tree.bfs());
        Assert.assertEquals(Arrays.asList(0, 4, 5, 6, 7, 8, 11, 12, 16, 18, 20, 25, 30), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void randomTree2Custom4()
    {
        // Opmerking: instance niet hergebruikt om te valideren dat er effectief compareTo gebruikt wordt

        SemiSplayTree<Custom> tree = new SemiSplayTree<>(4);
        Assert.assertTrue(tree.add(new Custom("appel", 100)));
        Assert.assertTrue(tree.add(new Custom("banaan", 200)));
        Assert.assertTrue(tree.add(new Custom("citroen", 300)));
        Assert.assertTrue(tree.add(new Custom("druif", 400)));
        Assert.assertTrue(tree.add(new Custom("eirebezen", 50)));
        Assert.assertTrue(tree.add(new Custom("framboos", 250)));
        Assert.assertTrue(tree.add(new Custom("granaatappel", 150)));
        Assert.assertTrue(tree.add(new Custom("honingmeloen", 175)));
        Assert.assertTrue(tree.add(new Custom("ikakopruim", 125)));
        Assert.assertTrue(tree.add(new Custom("japanse wijnbes", 0)));
        Assert.assertTrue(tree.add(new Custom("kers", 800)));

        Assert.assertEquals(11, tree.size());
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(Arrays.asList(
                new Custom("eirebezen", 50),
                new Custom("japanse wijnbes", 0),
                new Custom("granaatappel", 150),
                new Custom("appel", 100),
                new Custom("honingmeloen", 175),
                new Custom("ikakopruim", 125),
                new Custom("citroen", 300),
                new Custom("banaan", 200),
                new Custom("druif", 400),
                new Custom("framboos", 250),
                new Custom("kers", 800)
        ), tree.bfs());
        Assert.assertEquals(Arrays.asList(
                new Custom("japanse wijnbes", 0),
                new Custom("eirebezen", 50),
                new Custom("appel", 100),
                new Custom("ikakopruim", 125),
                new Custom("granaatappel", 150),
                new Custom("honingmeloen", 175),
                new Custom("banaan", 200),
                new Custom("framboos", 250),
                new Custom("citroen", 300),
                new Custom("druif", 400),
                new Custom("kers", 800)
        ), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add(new Custom("lijsterbes", 750)));

        Assert.assertEquals(12, tree.size());
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(Arrays.asList(
                new Custom("granaatappel", 150),
                new Custom("eirebezen", 50),
                new Custom("honingmeloen", 175),
                new Custom("japanse wijnbes", 0),
                new Custom("appel", 100),
                new Custom("druif", 400),
                new Custom("ikakopruim", 125),
                new Custom("citroen", 300),
                new Custom("lijsterbes", 750),
                new Custom("banaan", 200),
                new Custom("kers", 800),
                new Custom("framboos", 250)
        ), tree.bfs());
        Assert.assertEquals(Arrays.asList(
                new Custom("japanse wijnbes", 0),
                new Custom("eirebezen", 50),
                new Custom("appel", 100),
                new Custom("ikakopruim", 125),
                new Custom("granaatappel", 150),
                new Custom("honingmeloen", 175),
                new Custom("banaan", 200),
                new Custom("framboos", 250),
                new Custom("citroen", 300),
                new Custom("druif", 400),
                new Custom("lijsterbes", 750),
                new Custom("kers", 800)
        ), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.contains(new Custom("citroen", 300)));

        Assert.assertEquals(12, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(
                new Custom("honingmeloen", 175),
                new Custom("granaatappel", 150),
                new Custom("citroen", 300),
                new Custom("eirebezen", 50),
                new Custom("banaan", 200),
                new Custom("druif", 400),
                new Custom("japanse wijnbes", 0),
                new Custom("appel", 100),
                new Custom("framboos", 250),
                new Custom("lijsterbes", 750),
                new Custom("ikakopruim", 125),
                new Custom("kers", 800)
        ), tree.bfs());
        Assert.assertEquals(Arrays.asList(
                new Custom("japanse wijnbes", 0),
                new Custom("eirebezen", 50),
                new Custom("appel", 100),
                new Custom("ikakopruim", 125),
                new Custom("granaatappel", 150),
                new Custom("honingmeloen", 175),
                new Custom("banaan", 200),
                new Custom("framboos", 250),
                new Custom("citroen", 300),
                new Custom("druif", 400),
                new Custom("lijsterbes", 750),
                new Custom("kers", 800)
        ), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void randomTree3Integer6()
    {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>(6);

        Assert.assertTrue(tree.add(10));
        Assert.assertTrue(tree.add(5));
        Assert.assertTrue(tree.add(20));
        Assert.assertTrue(tree.add(15));
        Assert.assertTrue(tree.add(25));
        Assert.assertTrue(tree.add(2));
        Assert.assertTrue(tree.add(9));
        Assert.assertTrue(tree.add(1));
        Assert.assertTrue(tree.add(6));
        Assert.assertTrue(tree.add(7));

        Assert.assertEquals(10, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(10, 5, 20, 2, 9, 15, 25, 1, 6, 7), tree.bfs());
        Assert.assertEquals(Arrays.asList(1, 2, 5, 6, 7, 9, 10, 15, 20, 25), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add(8));

        Assert.assertEquals(11, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(7, 5, 9, 2, 6, 8, 10, 1, 20, 15, 25), tree.bfs());
        Assert.assertEquals(Arrays.asList(1, 2, 5, 6, 7, 8, 9, 10, 15, 20, 25), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add(30));

        Assert.assertEquals(12, tree.size());
        Assert.assertEquals(4, tree.depth());
        Assert.assertEquals(Arrays.asList(10, 7, 25, 5, 9, 20, 30, 2, 6, 8, 15, 1), tree.bfs());
        Assert.assertEquals(Arrays.asList(1, 2, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30), Util.iteratorToList(tree.iterator()));
    }

    @Test
    public void randomTree4Character8()
    {
        SemiSplayTree<Character> tree = new SemiSplayTree<>(8);

        Assert.assertTrue(tree.add('L'));
        Assert.assertTrue(tree.add('I'));
        Assert.assertTrue(tree.add('A'));
        Assert.assertTrue(tree.add('B'));
        Assert.assertTrue(tree.add('J'));
        Assert.assertTrue(tree.add('K'));
        Assert.assertTrue(tree.add('M'));
        Assert.assertTrue(tree.add('D'));
        Assert.assertTrue(tree.add('C'));
        Assert.assertTrue(tree.add('E'));
        Assert.assertTrue(tree.add('F'));

        Assert.assertEquals(11, tree.size());
        Assert.assertEquals(6, tree.depth());
        Assert.assertEquals(Arrays.asList('L', 'I', 'M', 'A', 'J', 'B', 'K', 'D', 'C', 'E', 'F'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'I', 'J', 'K', 'L', 'M'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add('G'));

        Assert.assertEquals(12, tree.size());
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(Arrays.asList('E', 'B', 'G', 'A', 'D', 'F', 'I', 'C', 'L', 'J', 'M', 'K'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'J', 'K', 'L', 'M'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add('N'));
        Assert.assertTrue(tree.add('O'));

        Assert.assertEquals(14, tree.size());
        Assert.assertEquals(6, tree.depth());
        Assert.assertEquals(Arrays.asList('E', 'B', 'G', 'A', 'D', 'F', 'I', 'C', 'L', 'J', 'M', 'K', 'N', 'O'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'J', 'K', 'L', 'M', 'N', 'O'), Util.iteratorToList(tree.iterator()));

        Assert.assertTrue(tree.add('P'));

        Assert.assertEquals(15, tree.size());
        Assert.assertEquals(5, tree.depth());
        Assert.assertEquals(Arrays.asList('L', 'G', 'N', 'E', 'I', 'M', 'O', 'B', 'F', 'J', 'P', 'A', 'D', 'K', 'C'), tree.bfs());
        Assert.assertEquals(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'), Util.iteratorToList(tree.iterator()));
    }
}
