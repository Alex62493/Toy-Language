package Model.CyclicBarrier;

import java.util.HashMap;
import java.util.Map;

public interface ICyclicBarrier<K, V>
{
    void add(K key, V value);

    V get(K key);

    void update(K key, V value);

    boolean contains(K key);

    Iterable<K> getAll();

    HashMap<String, String> forGUI();
}
