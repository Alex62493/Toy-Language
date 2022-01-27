package Model.adt;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

public class ExeStack<T> implements IStack<T> {
    Stack<T> exeStack;

    public ExeStack() {
        exeStack = new Stack<T>();
    }

    @Override
    public T pop() {
        return exeStack.pop();
    }

    @Override
    public void push(T v) {
        exeStack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return exeStack.isEmpty();
    }

    @Override
    public String toString() {
        String[] str = new String[exeStack.size()];
        StringBuilder s = new StringBuilder("[ | ");
        int a = 0;
        for (T i : exeStack)
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
        for (T i : exeStack)
        {
            v.add(i.toString());
        }
        Collections.reverse(v);
        return v;
    }
}
