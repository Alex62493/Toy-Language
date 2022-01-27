package Model.adt;

import Exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Heap<T1,T2> implements IDict<T1,T2> {
    HashMap<T1, T2> dictionary;

    public Heap() {
        dictionary = new HashMap<T1,T2>();
    }


    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public void remove(T1 id) throws Exception {
        if (dictionary.get(id) == null)
            throw new ADTException("Variable doesn't exist!");
        dictionary.remove(id);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) throws Exception {
        T2 a = dictionary.get(id);
        if (a == null)
            throw new ADTException("Variable doesn't exist!");
        return a;
    }

    @Override
    public boolean isDefined(String id) {
        return dictionary.containsKey(id);
    }

    @Override
    public Vector<String> forFile() {
        Vector<String> v = new Vector<>();
        for (T1 i : dictionary.keySet()) {
            v.add(i.toString() + "->" + dictionary.get(i).toString());
        }
        return v;
    }

    @Override
    public HashMap<String, String> forGUI() {
        HashMap<String, String> v = new HashMap<>();
        for (T1 i : dictionary.keySet()) {
            v.put(i.toString(), dictionary.get(i).toString());
        }
        return v;
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public void setContent(Map<T1, T2> newDict) {
        dictionary.clear();
        dictionary.putAll(newDict);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{ ");
        for (T1 i : dictionary.keySet()) {
            s.append(i.toString()).append("->").append(dictionary.get(i).toString()).append(" ");
        }
        s.append("}");
        return s.toString();
    }
}
