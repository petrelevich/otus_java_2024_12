package ru.otus.reflection;

import java.lang.reflect.Field;

class NodeUnbounded<T> {
    private T data;
    private NodeUnbounded<T> next;

    public NodeUnbounded(T data, NodeUnbounded<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }
    // ...
}

public class TypeErasureUnbounded {
    public static void main(String[] args) throws NoSuchFieldException {
        var node = new NodeUnbounded<String>("first node", null);

        Field field = node.getClass().getDeclaredField("data");
        System.out.println("'data' field type: " + field.getType().getCanonicalName());

        Field fieldNext = node.getClass().getDeclaredField("next");
        System.out.println("'next' field type: " + fieldNext.getType().getCanonicalName());
    }
}

/*
public class NodeUnbounded {
   private Object data;
   private NodeUnbounded next;

   public NodeUnbounded(Object data, NodeUnbounded next) {
       this.data = data;
       this.next = next;
   }

   public Object getData() { return data; }
   // ...
}
*/
