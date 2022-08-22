package behaviour.iterator;

import java.util.Arrays;
import java.util.Iterator;

public class ReverseArrayCollection<T> implements Iterable<T> {
    private T[] array;

    public ReverseArrayCollection(T... objs) {
        this.array = Arrays.copyOfRange(objs, 0, objs.length);
    }

//    public ReverseArrayCollection(T[] arr){
//        this.array = arr;
//    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseIterator();
    }


    class ReverseIterator implements Iterator<T> {
        // 索引位置
        int index;

        public ReverseIterator() {
            // 创建Iterator时,索引在数组末尾:
            this.index = ReverseArrayCollection.this.array.length;
        }

        @Override
        public boolean hasNext() {
            // 如果索引大于0,那么可以移动到下一个元素(倒序往前移动):
            return index > 0;
        }

        @Override
        public T next() {
            if (this.index > 0){
                return ReverseArrayCollection.this.array[--this.index];
            }else {
                throw new ArrayIndexOutOfBoundsException("越界了");
            }
        }
    }
}
