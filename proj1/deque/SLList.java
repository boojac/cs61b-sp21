package deque;

//the resources
//添加题目版本，当作自己的笔记

public class SLList {

    //裸列表
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n){
            item = i;
            next = n;
//            System.out.println(size);
        }
    }

    //sllist的属性
    private IntNode sential;
    private int size;
    private IntNode last;

    //构造方法

    //无参的
    public SLList(){
        sential = new IntNode(66, null);
        size = 0;
    }

    //有参的
    public SLList(int x){
        sential = new IntNode(66, null);
        sential.next = new IntNode(x, null);
        size = 1;
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x){
        sential.next = new IntNode(x, sential.next);
        size = size + 1;
    }

    //return the first item in the list
    public int getFirst(){
        return sential.next.item;
    }

//    add x to the end of the list
    public void addLast(int x) {
        IntNode p = sential;
        size += 1;

        while (p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }


    //return the size of the list
    public int Size(){
        return size;
    }

    public static void main(String[] args) {
        SLList L = new SLList();
        L.addLast(20);
    }



}
