package gabia.library.listener;

import gabia.library.kafka.BookRentMessage;
import gabia.library.kafka.channel.BookRentInputChannel;
import gabia.library.kafka.consumer.BookRentConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding({BookRentInputChannel.class})
public class BookRentStreamListener {

    private final BookRentConsumer bookRentConsumer;

    /**
     * book rent stream listener
     */
    @StreamListener(BookRentInputChannel.BOOK_RENT_CONSUMER)
    public void bookRentListener(BookRentMessage message) {
        bookRentConsumer.addRentAndUpdateBook(message);
    }

}
