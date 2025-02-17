package ru.otus.proxy.lazy;

public class LazyProxy implements HeavyObject {
    private HeavyObject heavyObject = null;

    private String value = null;

    public LazyProxy() {
        //this.heavyObject = heavyObject;
    }

    @Override
    public String getValue() {
        if (heavyObject == null) {
            heavyObject = new HeavyObjectImpl();
            heavyObject.init(value);
        }
        return heavyObject.getValue();
    }

    @Override
    public void init(String value) {
        this.value = value;
    }

    @Override
    public boolean isInit() {
        return heavyObject != null;
    }
}
