package deque;
/*我的思路：如何构建一个双端链表（基于SLList）
1。先构建Sllist（列出框架，然后实现）🉐
2。列出deque的框架（从题目之中寻找，需要什么）
3。解题（参照2.3）
* */

public class LinkedListDeque<T> {
    //他的裸链表
    private static class Node<B> {

        public Node prev;
        public B item;
        public Node next;

        public Node(Node p, B i, Node n){
            prev = p;
            item = i;
            next = n;
        }
    }

    //他的属性

    private Node sentFront;
    private int size;
    private Node sentBack;


    //无参的构造函数
    public LinkedListDeque(){
        sentFront = new Node(null, 65, null);
        sentBack = new Node(null, 66, null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
        size = 0;
    }

//    含参的构造函数,提示说让最后做
    public LinkedListDeque(T x){
        sentFront = new Node(null, 65, null);
        sentBack = new Node(null, 66, null);
        Node thisOne = new Node<>(sentFront, x, sentBack);
        sentFront.next = thisOne;
        sentBack.prev = thisOne;
        size = 1;
    }



    public T getRecursive(int index) {
        Node p = sentFront;
        int i = 0;
        while (i < index) {
            p = p.next;
            i += 1;
        }
        return (T) p.item;
    }

//    public T getIterable(int index){
//
//    }

    //在哨兵后面插入一个元素
    public void addFirst(T x){
        if(size == 0) {
            Node first = new Node(sentFront, x, sentBack);
            sentFront.next = first;
            sentBack.prev = first;
        }else {
            Node first = new Node(sentFront, x, sentFront.next);
            sentFront.next.prev = first;
            sentFront.next = first;
        }
        size += 1;

    }

    //在队末尾插入一个元素
    public void addLast(T x){
        //这个也要看有多少个元素呀！woc
        //难道是用循环？
        if(size == 0) {
            Node last = new Node(sentFront, x, sentBack);
            sentFront.next = last;
            sentBack.prev = last;
        }else{
            Node last = new Node(sentBack.prev, x, sentBack);
            sentBack.prev.next = last;
            sentBack.prev = last;
//            last.prev =sentBack.prev;
//            last.next = sentBack;
        }

        size += 1;

    }

    public T getLast() {
        return (T) sentBack.prev.item;
    }



    //判断是否为空
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else {
            return false;
        }
    }

    //判断大小
    public int Size(){
        return size;
    }

    public void printDeque(){

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        if (size == 1){
            sentFront.next = sentBack;
            sentBack.prev = sentFront;
        }else {
            sentFront.next = sentFront.next.next;
            sentFront.next.prev = sentFront;
        }
        return (T)this;

    }

    public T removeLast() {
        //分两种情况，一种是只有一个元素，另一种是两个元素及以上
        if (size == 0) {
            return null;
        }
        if (size == 1){
            sentFront.next = sentBack;
            sentBack.prev = sentFront;
        }else {
         sentBack.prev = sentBack.prev.prev;
         sentBack.prev.next = sentBack;
        }
        return (T) this;
    }
//
//    public T get(int index) {
//
//    }

    public static void main(String[] args){
        LinkedListDeque d = new LinkedListDeque();
//        d.addLast(3);
//        d.addLast(6);
//        d.addLast(9);
//        d.removeLast();
//        System.out.println(d.getLast());
//        d.addFirst(6);
//        d.addFirst(3);
//        d.removeFirst();
    }
}
