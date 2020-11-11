package semisplay.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Test utilities
 */
final class Util
{
    /**
     * Iterator naar lijst, gebruikt voor debug/test
     *
     * @param it  De iterator
     * @param <T> Het type van de elementen
     * @return De lijst
     */
    static <T> List<T> iteratorToList(Iterator<T> it)
    {
        List<T> l = new ArrayList<>();
        while (it.hasNext())
            l.add(it.next());
        return l;
    }
}
