package com.javarush.task.task37.task3701;

import java.util.*;
import java.util.function.Consumer;

/* 
Круговой итератор
*/
public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }

        Iterator<Integer> iterator = list.iterator();
        count = 0;
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer == 3){
                iterator.remove();
            }
            count++;
            if (count == 10) {
                break;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public class RoundIterator implements Iterator {
        protected transient int modCount = 0;
        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                Solution.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer action) {
            Objects.requireNonNull(action);
            final int size = Solution.this.size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = Solution.this.toArray();
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                action.accept((T) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        @Override
        public boolean hasNext() {
            return Solution.this.size() > 0;
        }

        @Override
        public Object next() {
            checkForComodification();
            int i = cursor;
            Object[] elementData = Solution.this.toArray();
            if (i == elementData.length) {
                i = 0;
                cursor = 0;
            }
            if (i > elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }


}
