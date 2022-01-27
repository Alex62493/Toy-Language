package Model.adt;

import java.util.Vector;

public interface IStack<T> {

    T pop();
    void push(T v);
    boolean isEmpty();
    String toString();
    Vector<String> forFile();
}

