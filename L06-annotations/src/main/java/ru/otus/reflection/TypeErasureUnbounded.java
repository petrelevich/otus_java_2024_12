package ru.otus.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;

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

        var clazz = node.getClass();
        System.out.println("Class generic parameters: " + Arrays.toString(clazz.getTypeParameters()));

        Field field = clazz.getDeclaredField("data");
        System.out.println("'data' field type: " + field.getType().getCanonicalName());

        Field fieldNext = clazz.getDeclaredField("next");
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
