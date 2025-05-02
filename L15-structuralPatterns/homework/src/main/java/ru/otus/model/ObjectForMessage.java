package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage copy() {
        ObjectForMessage copy = new ObjectForMessage();
        copy.setData(List.copyOf(this.getData()));
        return copy;
    }
}
