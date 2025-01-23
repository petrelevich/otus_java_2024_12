package ru.otus.reflection;

import java.lang.reflect.Field;

class NodeBounded<T extends Comparable<T>> {
    private T data;
    private NodeBounded<T> next;

    public NodeBounded(T data, NodeBounded<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }
    // ...
}

public class TypeErasureBounded {
    public static void main(String[] args) throws NoSuchFieldException {
        var node = new NodeBounded<String>("first node", null);

        Field field = node.getClass().getDeclaredField("data");
        System.out.println("'data' field type: " + field.getType().getCanonicalName());

        Field fieldNext = node.getClass().getDeclaredField("next");
        System.out.println("'next' field type: " + fieldNext.getType().getCanonicalName());
    }
}

/*
public class NodeBounded {
   private Comparable data;
   private NodeBounded next;

   public NodeBounded(Comparable data, NodeBounded next) {
       this.data = data;
       this.next = next;
   }

   public Comparable getData() { return data; }
   // ...
}
*/
