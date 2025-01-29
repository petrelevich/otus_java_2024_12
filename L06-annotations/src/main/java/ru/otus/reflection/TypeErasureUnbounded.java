package ru.otus.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;

@SuppressWarnings({"java:S1068", "java:S125"})
class NodeUnbounded<T> {
    private final T data;
    private final NodeUnbounded<T> next;

    public NodeUnbounded(T data, NodeUnbounded<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }
    // ...

    /*
    public class NodeUnbounded {
       private final Object data;
       private final NodeUnbounded next;

       public NodeUnbounded(Object data, NodeUnbounded next) {
           this.data = data;
           this.next = next;
       }

       public Object getData() { return data; }
       // ...
    }
    */
}

@SuppressWarnings({"java:S106", "java:S2133"})
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
