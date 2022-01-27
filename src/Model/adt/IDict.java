package Model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public interface IDict<T1,T2>{

    void add(T1 v1, T2 v2);
    void remove(T1 id) throws Exception;
    void update(T1 v1, T2 v2);
    T2 lookup(T1 id) throws Exception;
    boolean isDefined(String id);
    String toString();
    Vector<String> forFile();
    HashMap<String, String> forGUI();
    Map<T1, T2> getContent();
    void setContent(Map<T1, T2> newDict);
}
