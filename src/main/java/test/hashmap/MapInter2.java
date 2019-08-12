package test.hashmap;

public interface MapInter2<K,V> {
     V put(K k,V v);
     V get(K k);
     interface EntryInter2<K,V>{
         K getKey();
         V getValue();
     }

}
