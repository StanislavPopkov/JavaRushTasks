package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final long serialVersionUID = 1L;
    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();

    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<>(Math.max((int) (collection.size()/.75f) + 1, 16));
        for(E element : collection){
            add(element);
        }
    }

     private void writeObject(ObjectOutputStream out) throws IOException {
        int capacityStr = HashMapReflectionHelper.callHiddenMethod(map, "capacity");
        float loadFactorStr = HashMapReflectionHelper.callHiddenMethod(map, "loadFactor");
        out.defaultWriteObject();
        out.writeInt(capacityStr);
        out.writeFloat(loadFactorStr);
        out.writeInt(map.size());
        for(E element : map.keySet()){
            out.writeObject(element);
        }
     }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int capacityStr = in.readInt();
        float loadFactorStr = in.readFloat();
        int sizeMap = in.readInt();
        this.map = new HashMap<>(capacityStr, loadFactorStr);
        for(int i = 0; i < sizeMap; i++){
            E element = (E) in.readObject();
            map.put(element, PRESENT);
        }

    }


    public boolean equals(Set<E> o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof AmigoSet)) return false;
        AmigoSet<E> amigoSet = (AmigoSet) o;
        if(this.map.size() != amigoSet.map.size())return false;
        for(E element : this.map.keySet()){
            if(!amigoSet.map.containsKey(element)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Object clone() throws InternalError {
        try {
            AmigoSet<E> amigoSet = (AmigoSet<E>) super.clone();
            amigoSet.map = (HashMap<E, Object>) map.clone();
            return amigoSet;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(E e) {
        Object obj = map.put(e, PRESENT);
        if(obj == null) return true;
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }



    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public boolean removeIf(Predicate filter) {
        return false;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }
    @Override
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }
}
