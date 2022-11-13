package deque;

//怎么实现双向数组呢？
/*
1。通读资料，y
1、5。看课本资料 y
2。抄写框架，实现框架
3。
 */

/*难点：
1。理解这个数据结构
2。理解这个数据结构增删改查时调用的方法关系（索引的计算、每一次对数据结构的操作对索引对计算、扩容、重置大小的指针变化、）

 */

public class ArrayDeque<Item> {
    private Item[] items;
    private final int INIT_CAPACITY = 8;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //用取模的方法，来获取最后一个数字的索引
    private int minusOne(int index) {
        return Math.floorMod(index - 1, items.length);
    }

    //get the next index
    private int plueOne(int index) {
        return Math.floorMod(index + 1, items.length);//向负无穷取整，和%向0取整有差别
    }

    private int plueOne(int index, int length) {
        return Math.floorMod(index + 1, length);
    }

    //小工具
    private void resize() {
        if (size == items.length) {
            expand();
        }
        if (size < (items.length) * 0.25 && items.length > 8) {
            reduce();
        }
    }

    private void expand() {
        resizeHelper(items.length * 1);
    }

    private void reduce() {
        resizeHelper(items.length / 2);
    }

    private void resizeHelper(int capacity) {
        Item[] tempArr = items;
        int begin = plueOne(nextFirst);
        int end = minusOne(nextLast);
        items = (Item[]) new Object[capacity];
        nextFirst = 0;
        nextLast = 1;
        for (int i = begin; i != end; i = plueOne(i, tempArr.length)) {
            items[nextLast] = tempArr[i];
            nextLast = plueOne(nextLast);
        }
        items[nextLast] = tempArr[end];
        nextLast = plueOne(nextLast);

    }

    //增删改查

    public void addFirst(Item item) {
        resize();
        items[nextFirst] = item;
        size ++;
        nextFirst = minusOne(nextFirst);
    }

    private Item getFirst() {
        return items[plueOne(nextFirst)];
    }

    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        resize();
        Item removedItem = getFirst();
        nextFirst = plueOne(nextFirst);
        items[nextFirst] = null;
        size --;
        return removedItem;
    }

    public void addLast(Item item) {
        resize();
        items[nextLast] = item;
        size ++;
        nextLast = plueOne(nextLast);
    }

    private Item getLast() {
        return items[minusOne(nextLast)];
    }

    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        resize();
        Item removedItem = getLast();
        items[nextLast] = null;
        size --;
        return removedItem;
    }

    public void printDeque() {
        for (int index = plueOne(nextFirst); index != nextLast; index = plueOne(index)) {
            System.out.println(items[index] + " ");
        }
        System.out.println();
    }

    public Item get(int index) {
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        }
        index = Math.floorMod(plueOne(nextFirst) + index, items.length);
        return items[index];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            arrayDeque.addFirst(i);
        }
        arrayDeque.printDeque();

        for (int i = 0; i < 99; i++) {
            arrayDeque.removeFirst();
        }

        System.out.println(arrayDeque.size());
    }



}
