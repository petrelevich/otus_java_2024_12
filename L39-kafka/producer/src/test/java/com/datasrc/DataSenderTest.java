package com.datasrc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import com.datasrc.model.StringValue;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.LongStream;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataSenderTest {
    @BeforeAll
    static void init() throws ExecutionException, InterruptedException, TimeoutException {
        KafkaBase.start(List.of(new NewTopic(StringValueKafkaProducer.TOPIC_NAME, 1, (short) 1)));
    }

    @Test
    void dataHandlerTest() {
        // given
        List<StringValue> stringValues = LongStream.range(0, 9)
                .boxed()
                .map(idx -> new StringValue(idx, "test:" + idx))
                .toList();

        var myProducer = new StringValueKafkaProducer(KafkaBase.getBootstrapServers());

        List<StringValue> factStringValues = new CopyOnWriteArrayList<>();
        var dataProducer = new DataSender(myProducer, factStringValues::add);
        var valueSource = new ValueSource() {
            @Override
            public void generate() {
                for (var value : stringValues) {
                    dataProducer.dataHandler(value);
                }
            }
        };

        // when
        valueSource.generate();

        // then
        await().atMost(60, TimeUnit.SECONDS).until(() -> factStringValues.size() == stringValues.size());
        assertThat(factStringValues).hasSameElementsAs(stringValues);
    }
}
