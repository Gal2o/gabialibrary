package gabia.library.kafka.consumer;

import gabia.library.domain.book.Book;
import gabia.library.domain.book.BookRepository;
import gabia.library.domain.rent.Rent;
import gabia.library.domain.rent.RentRepository;
import gabia.library.exception.EntityNotFoundException;
import gabia.library.kafka.message.BookReturnMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gabia.library.exception.message.CommonExceptionMessage.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BookReturnConsumer {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;

    @Transactional
    public void bookReturn(BookReturnMessage message) {
        Book book = bookRepository.findByIdAndIsDeleted(message.getBookId(), false).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        book.returnBook();
    }

    @Transactional
    public void rentReturn(BookReturnMessage message) {
        Rent rent = rentRepository.findById(message.getRentId()).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        rent.returnBook();
    }

}
