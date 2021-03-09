package gabia.library.kafka.publisher;

import gabia.library.kafka.BookRentMessage;
import gabia.library.kafka.BookReturnMessage;
import gabia.library.kafka.channel.BookRentOutputChannel;
import gabia.library.kafka.channel.BookReturnOutputChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessagePublisher {

    private final BookRentOutputChannel bookRentOutputChannel;
    private final BookReturnOutputChannel bookReturnOutputChannel;

    public void publishBookRentMessage(BookRentMessage message) {
        bookRentOutputChannel.outputChannel().send(MessageBuilder.withPayload(message).build());
    }

    public void publishBookReturnMessage(BookReturnMessage message) {
        bookReturnOutputChannel.outputChannel().send(MessageBuilder.withPayload(message).build());
    }

}
