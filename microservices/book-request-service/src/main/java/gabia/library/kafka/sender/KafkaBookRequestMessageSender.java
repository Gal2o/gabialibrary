package gabia.library.kafka.sender;

import gabia.library.kafka.BookRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaBookRequestMessageSender {

    @Qualifier("bookRequestKafkaTemplate")
    private final KafkaTemplate<String, BookRequestMessage> kafkaTemplate;

    @Value("${kafka.topic.request.name}")
    private String topicName;

    public void send(BookRequestMessage bookRequestMessage) {

        Message<BookRequestMessage> message = MessageBuilder
                .withPayload(bookRequestMessage)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        ListenableFuture<SendResult<String, BookRequestMessage>> future = kafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, BookRequestMessage>>() {
            @Override
            public void onSuccess(SendResult<String, BookRequestMessage> result) {
                log.info("Sent message={} to topic{} with offset={}",
                        result.getProducerRecord().value().toString(), result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[] due to : " + ex.getMessage());
            }
        });
    }

}
