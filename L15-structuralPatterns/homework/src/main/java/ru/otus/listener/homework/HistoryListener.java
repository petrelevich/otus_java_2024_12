package ru.otus.listener.homework;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final ConcurrentMap<Long, Message> messageHistory = new ConcurrentHashMap<>();

    @Override
    public void onUpdated(final Message msg) {
        final Message copy = copy(msg);
        messageHistory.put(copy.getId(), copy);
    }

    @Override
    public Optional<Message> findMessageById(final long id) {
        return Optional.ofNullable(messageHistory.get(id)).map(this::copy);
    }

    private Message copy(final Message original) {
        return original.toBuilder().field13(original.getField13()).build();
    }
}
