package gabia.library.domain;

import gabia.library.config.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class BookRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Destination destination;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted;

    public void update() { this.status = Status.COMPLETED; }

    public void remove() {
        this.status = Status.CANCELED;
        this.isDeleted = true;
    }

    @Builder
    public BookRequest(Long userId, String bookName, String author, Destination destination, String url, Status status) {
        this.userId = userId;
        this.bookName = bookName;
        this.author = author;
        this.destination = destination;
        this.url = url;
        this.status = status;
    }
}
