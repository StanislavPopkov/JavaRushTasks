package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        Collection<List<V>> lists = map.values();
        int count = 0;
        for(List<V> list: lists){
            count += list.size();
        }
        return count;
    }

    @Override
    public V put(K key, V value) {
        if(map.containsKey(key)) {
            List<V> list = map.get(key);
            if (list.size() < repeatCount) {
                list.add(value);
                return list.get(list.size() - 2);
            } else if (list.size() == repeatCount) {
                list.remove(0);
                list.add(value);
                return list.get(list.size() - 2);
            }
            return null;
        }
        List<V> vList = map.get(key);
        if(vList == null) {
            map.put(key, new ArrayList<>());
            vList = map.get(key);
            vList.add(value);
        } else {
            vList.add(value);
        }
        map.put(key, vList);
        if(vList.size() == 1) {
            return null;
        }
        return vList.get(vList.size() - 2);
    }

    @Override
    public V remove(Object key) {
        if(map.containsKey(key)) {
            V value = null;
            List<V> list = map.get(key);
            if (list.size() >= 1) {
                value = list.remove(0);
            } if (list.size() == 0) {
                map.remove(key);
            }
            return value;
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<List<V>> lists = map.values();
        List<V> vList = new ArrayList<>();
        for(List<V> list: lists){
            vList.addAll(list);
        }
        return vList;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        Collection<List<V>> lists = map.values();
        for(List<V> vList : lists){
            if(vList.contains(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}