package ru.otus.processor.homework;

import java.time.LocalDateTime;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSecond implements Processor {

    private final TimeProvider timeProvider;

    public ProcessorEvenSecond(final TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        int currentSecond = timeProvider.getCurrentSecond();
        if (currentSecond % 2 == 0) {
            throw new EvenSecondException("Current second is even: " + currentSecond);
        }
        return message;
    }

    public interface TimeProvider {
        int getCurrentSecond();
    }

    public static class SystemTimeProvider implements TimeProvider {
        @Override
        public int getCurrentSecond() {
            return LocalDateTime.now().getSecond();
        }
    }

    public static class EvenSecondException extends RuntimeException {
        public EvenSecondException(final String message) {
            super(message);
        }
    }
}
