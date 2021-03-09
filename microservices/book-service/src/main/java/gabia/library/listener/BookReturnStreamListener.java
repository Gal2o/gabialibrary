package gabia.library.listener;

import gabia.library.kafka.BookReturnMessage;
import gabia.library.kafka.channel.BookReturnInputChannel;
import gabia.library.kafka.consumer.BookReturnConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding({BookReturnInputChannel.class})
public class BookReturnStreamListener {

    private final BookReturnConsumer bookReturnConsumer;

    @StreamListener(BookReturnInputChannel.BOOK_RETURN_CONSUMER)
    public void bookReturnListener(BookReturnMessage message) {
        bookReturnConsumer.bookReturn(message);
    }

    @StreamListener(BookReturnInputChannel.RENT_RETURN_CONSUMER)
    public void rentReturnListener(BookReturnMessage message) {
        bookReturnConsumer.rentReturn(message);
    }

}
