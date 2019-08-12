package test;

public interface MapInter<K, V> {
    V put(K key, V value);
    V get(K key);
    interface Entry<K, V> {
        K getKey();
        V getValue();
    }
}
