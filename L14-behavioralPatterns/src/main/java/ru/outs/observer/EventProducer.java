package ru.outs.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class EventProducer {

    private String data = "";

    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    void addListener(Listener listener) {
        listeners.add(listener);
    }

    void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    void event(String d) {
        data = d;
        // listeners.forEach(c -> c.onUpdate(d));
        for (int i = 0; i < listeners.size(); ++i) {
            try {
                listeners.get(i).onUpdate(data);
            } catch (Exception ex) {
                // логирование исключения
            }
        }
    }
}
