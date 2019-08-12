package test;

public class MyHashMap<K,V> implements MapInter<K,V>{

    private static final int DEFAULT_INITIAL_CAPACITY=1<<2;
    private static final float DEFAULT_LOAD_FACTOR=0.75f;
    private int defaultInitSize;
    private float defaultLoadFactor;
    private Entry<K,V>[] table=null;
    private int entryUserSize;

    public MyHashMap(){
        this(DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int defaultInitCapacity,float defaultLoadFactor){
        if(defaultInitCapacity<0){
            throw new IllegalArgumentException("defaultInitCapacity:"+defaultInitCapacity);
        }
        if(defaultLoadFactor<=0 || Float.isNaN(defaultLoadFactor)){
            throw new IllegalArgumentException("defaultLoadFactor:"+defaultLoadFactor);
        }
        this.defaultInitSize=defaultInitCapacity;
        this.defaultLoadFactor=defaultLoadFactor;
        table=new Entry[defaultInitSize];
    }

    class Entry<K,V> implements MapInter.Entry<K,V>{

        K key;
        V value;
        Entry<K,V> next;
        public Entry(){

        }
        public Entry(K key,V value,Entry<K,V> next){
            this.key=key;
            this.value=value;
            this.next=next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }

    @Override
    public V put(K key, V value) {
        V oldValue=null;
        if(entryUserSize>=defaultInitSize*defaultLoadFactor){
            resize(2*defaultInitSize);
        }
        int index=hash(key)&(defaultInitSize-1);
        if(table[index]==null){
            table[index]=new Entry<K,V>(key,value,null);
            ++entryUserSize;
        }else{
            Entry<K,V> entry=table[index];
            Entry<K,V> e=entry;
            while(e!=null){
                if (key==e.getKey()|| key.equals(e.getKey())){
                    oldValue=e.value;
                    e.value=value;
                    return oldValue;
                }
                e=e.next;
            }
            table[index]=new Entry<K,V>(key,value,entry);
            ++entryUserSize;
        }
        return oldValue;
    }

    @Override
    public V get(K key) {
        int index=hash(key)&(defaultInitSize-1);
        if(table[index]==null){
            return null;
        }else{
            Entry<K,V> entry=table[index];
            do{
                if(key==entry.getKey()|| key.equals(entry.getKey())){
                    return entry.value;
                }
                entry=entry.next;
            }while (entry!=null);
        }
        return null;
    }

    private int hash(K k){
        int hashCode = k.hashCode();
        return hashCode;
    }

    private void resize(int i){

    }
    private void rehash(Entry<K,V>[] newTable){

    }

    public static void main(String[] args) {
        MyHashMap<String,String> map = new MyHashMap<>();
        for(int i=0;i<500;i++){
            map.put("key:"+i,"value:"+i);
        }
        for(int i=0;i<500;i++){
            System.out.println(map.get("key:"+i));
        }
    }
}
