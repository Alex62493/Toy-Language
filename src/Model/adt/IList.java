package Model.adt;

import java.util.Vector;

public interface IList<T> {
    void add(T v);
    T pop();
    String toString();
    Vector<String> forFile();
}
