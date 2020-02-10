package me.sadbuttrue.async.producer;

import me.sadbuttrue.configuration.TimeStorerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(TimeStorerConfiguration.class)
@TestPropertySource(properties = {"me.sadbuttrue.async.producer.TimeProducer.schedulerRate=100"})
public class TimeProducerIntegrationTest {
    @MockBean
    private BlockingQueue<LocalDateTime> timeQueue;

    @SpyBean
    private TimeProducer producer;

    @BeforeEach
    public void setup() {
        producer.setEnabled(true);
    }

    @Test
    public void producerShouldBeCalledAtLeast10TimesPer10Seconds() {
        await()
                .atMost(Duration.of(1, ChronoUnit.SECONDS))
                .untilAsserted(() -> verify(producer, atLeast(10)).produce());
    }

    @Test
    public void producerShouldSubmitTimeToQueue() {
        when(timeQueue.add(any())).thenReturn(true);

        await().pollDelay(1010, TimeUnit.MILLISECONDS).until(() -> true);

        verify(timeQueue, atLeast(10)).add(any());
        verify(timeQueue, atMost(11)).add(any());
    }
}
