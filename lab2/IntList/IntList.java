package IntList;

public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /** Method to return a string representation of an IntList */
    public String toString() {
        if (rest == null) {
            // Converts an Integer to a String!
            return String.valueOf(first);
        } else {
            return first + " -> " + rest.toString();
        }
    }

    /* 一个对原列表内的数据做平方的方法 */
    public static void dSquareList(IntList L) {
        while(L != null) {
            L.first = L.first * L.first;
            L = L.rest;
        }
    }

    /* 采用递归，一个对原列表内的数据做平方的方法 */
    public static void rSquareList(IntList L) {
        if (L != null) {
            L.first = L.first * L.first;
            rSquareList(L.rest);
        }
    }

    /* 无损方法，返回平方列表，用迭代方法*/
    public static IntList squareListIterative(IntList L) {
//        通过一个固定指针、一个游动指针来构造 无破坏的平方
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.first * L.first, null);
        IntList ptr = res;
        L = L.rest;
        while (L != null) {
            ptr.rest = new IntList(L.first * L.first, null);
            L = L.rest;
            ptr = ptr.rest;
        }
        return res;
    }

    /* 无损方法，返回平方列表，用递归方法*/

    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.first * L.first, squareListRecursive(L.rest));
    }


    /* 传入两个链表，把两个链表链接起来，可以破坏原列表，通过移动指针 */
    public static IntList dcatenate(IntList A, IntList B) {
        IntList temp = A;
        while(temp.rest != null) {
            temp = temp.rest;
        }
        temp.rest = B;
        return A;
    }

    /* 传入两个链表，把两个链表链接起来，不可以破坏原列表 */
    public static IntList catenate(IntList A, IntList B) {
        IntList newList = new IntList(A.first, null);
        IntList temp1 = A;
        IntList temp2 = newList;
        while(temp1.rest != null){
            temp1 = temp1.rest;
            temp2.rest = new IntList(temp1.first, null);
            temp2 = temp2.rest;
        }
        temp2.rest = B;
        return newList;


    }




    /**
     * Method to create an IntList from an argument list.
     * You don't have to understand this code. We have it here
     * because it's convenient with testing. It's used like this:
     *
     * IntList myList = IntList.of(1, 2, 3, 4, 5);
     * will create an IntList 1 -> 2 -> 3 -> 4 -> 5 -> null.
     *
     * You can pass in any number of arguments to IntList.of and it will work:
     * IntList mySmallerList = IntList.of(1, 4, 9);
     */
    public static IntList of(int ...argList) {
        if (argList.length == 0)
            return null;
        int[] restList = new int[argList.length - 1];
        System.arraycopy(argList, 1, restList, 0, argList.length - 1);
        return new IntList(argList[0], IntList.of(restList));
    }

    public static void main(String[] args) {
        IntList origL = IntList.of(1, 2, 3);
//        dSquareList(origL);
        rSquareList(origL);
        System.out.println(origL.toString());

    }
}
