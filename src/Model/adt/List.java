package Model.adt;
import java.util.*;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class List<T> implements IList<T> {
    Stack<T> list;

    public List() {
        list = new Stack<T>();
    }

    @Override
    public void add(T v) {list.add(v);}

    @Override
    public T pop() {return list.pop();}

    @Override
    public String toString() {
        String[] str = new String[list.size()];
        StringBuilder s = new StringBuilder("[ | ");
        int a = 0;
        for (T i : list)
        {
            str[a] = i.toString();
            a++;
        }
        for (int j = a-1; j >= 0; j--)
        {
            s.append(str[j]).append(" | ");
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public Vector<String> forFile() {
        Vector<String> v = new Vector<>();
        for (T i : list)
        {
            v.add(i.toString());
        }
        Collections.reverse(v);
        return v;
    }
}
