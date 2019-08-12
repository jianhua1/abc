package test.hashmap;

public class MyHashMap2<K,V> implements MapInter2<K,V>{

    public static final int INIT_SIZE=16;
    public static final float DEFAULT_FACTOR=0.75f;
    public int initSize;
    public float defaultFactor;
    Entry2<K,V>[] table=null;

    MyHashMap2(int initSize,float defaultFactor){
        this.initSize=initSize;
        this.defaultFactor=defaultFactor;
        table=new Entry2[initSize];
    }

    MyHashMap2(){
        this(INIT_SIZE,DEFAULT_FACTOR);
    }

    @Override
    public V put(K k, V v) {
        int hashCode = k.hashCode();
        int index=hashCode&2;
        if(table[index]==null){
            table[index]=new Entry2<>(k,v,null);
        }else{
            Entry2<K, V> entry = table[index];
            Entry2<K,V> old=entry;
            while (entry!=null){
                if(entry.k==k||entry.k.equals(k)){
                    entry.v=v;
                    return v;
                }
                entry = entry.entry;
            }
            table[index]=new Entry2<>(k,v,old);
        }
        return null;
    }

    @Override
    public V get(K k) {
        int hashCode = k.hashCode();
        int index=hashCode&2;
        Entry2<K, V> kvEntry2 = table[index];
        while(kvEntry2!=null){
            if (kvEntry2.getKey()==k || k.equals(kvEntry2.getKey())){
                return kvEntry2.getValue();
            }
            kvEntry2 = kvEntry2.entry;
        }
        return null;
    }

    class Entry2<K,V> implements EntryInter2<K,V>{

        K k;
        V v;
        Entry2<K,V> entry;

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        public Entry2(K k,V v,Entry2<K,V> next){
            this.k=k;
            this.v=v;
            this.entry=next;
        }
    }

    public static void main(String[] args) {
        MyHashMap2<Object, Object> map2 = new MyHashMap2<>();
        for(int i=0;i<730;i++){
            map2.put("key:"+i,"value:"+i);
        }
        for(int j=0;j<730;j++){
            System.out.println(map2.get("key:"+j));
        }
    }
}
