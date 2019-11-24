package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class OurHashMapStorageStrategy implements StorageStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;

    public OurHashMapStorageStrategy() {
    }

    int hash(Long k){
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }
    int indexFor(int hash, int length){
        return hash & (length - 1);
    }
    Entry getEntry(Long key){
        int hash = (key == null) ? 0 : hash( (long) key.hashCode() );
        for (Entry e = table[indexFor( hash, table.length )];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals( k ))))
                return e;
        }
        return null;
    }
    void resize(int newCapacity){
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        this.table=newTable;
        threshold = (int)(newCapacity * loadFactor);
    }
    void transfer(Entry[] newTable){
        for(int i=0;i<table.length;i++){
            newTable[i] = table[i];
        }
    }
    void addEntry(int hash, Long key, String value, int bucketIndex){
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry( hash, key, value, e );
        if (size++ >= threshold)
            resize( 2 * table.length );
    }
    void createEntry(int hash, Long key, String value, int bucketIndex){
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry( hash, key, value, e );
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        int hash = (key == null) ? 0 : hash((long) key.hashCode());
        int index = indexFor(hash, table.length);
        for (Entry e = table[index]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        if(value != null) {
            for (Entry entry : table) {
                if (entry != null && Objects.equals(value, entry.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        if(key != null) {
            if (key == null) {
                put(Long.valueOf(0), value);
                return;
            }
            int hash = hash(key);
            int i = indexFor(hash, table.length);
            for (Entry e = table[i]; e != null; e = e.next) {
                Object k;
                if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                    String oldValue = e.value;
                    e.value = value;
                }
            }
            size++;
            addEntry(hash, key, value, i);
        }
    }

    @Override
    public Long getKey(String value) {
        if(value != null) {
            for (Entry entry : table) {
                for (Entry e = entry; e != null; e = e.next) {
                    if (Objects.equals(value, entry.getValue())) {
                        return entry.getKey();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        if(this.containsKey(key)) {
            return getEntry(key).getValue();
        }
        return null;
    }
}
