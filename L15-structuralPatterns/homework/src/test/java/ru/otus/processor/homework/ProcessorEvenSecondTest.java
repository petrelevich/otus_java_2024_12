package ru.otus.processor.homework;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Message;

@ExtendWith(MockitoExtension.class)
public class ProcessorEvenSecondTest {

    @Mock
    private ProcessorEvenSecond.TimeProvider timeProvider;

    @InjectMocks
    private ProcessorEvenSecond processor;

    @Test
    void shouldThrowExceptionWhenEvenSecond() {
        final Message testMessage = new Message.Builder(1).build();

        when(timeProvider.getCurrentSecond()).thenReturn(2);

        assertThrows(ProcessorEvenSecond.EvenSecondException.class, () -> processor.process(testMessage));
    }

    @Test
    void shouldProcessNormallyWhenOddSecond() {
        final Message testMessage = new Message.Builder(1).build();

        when(timeProvider.getCurrentSecond()).thenReturn(3);

        assertDoesNotThrow(() -> processor.process(testMessage));
        assertEquals(testMessage, processor.process(testMessage));
    }
}
