package semisplay.test;

import java.util.Objects;

/**
 * Custom data type om mee te testen
 */
public class Custom implements Comparable<Custom>
{
    private String str;
    private int num;

    public Custom(String str, int num)
    {
        this.str = str;
        this.num = num;
    }

    @Override
    public int compareTo(Custom custom)
    {
        int c = Integer.compare(num, custom.num);
        if (c == 0)
            return str.compareTo(custom.str);
        else
            return c;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(str, num);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Custom custom = (Custom) o;
        return num == custom.num &&
                str.equals(custom.str);
    }

    @Override
    public String toString()
    {
        return "Custom{" +
                "str='" + str + '\'' +
                ", num=" + num +
                '}';
    }
}
