package gabia.library.domain;

import com.fasterxml.jackson.annotation.JsonView;
import gabia.library.config.NoticeJsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_important", columnDefinition = "boolean default false")
    private boolean isImportant;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Builder
    public Notice(String title, String content, boolean isImportant){
        this.title = title;
        this.content = content;
        this.isImportant = isImportant;
    }

    public void updateNotice(String title, String content, boolean isImportant){
        this.title = title;
        this.content = content;
        this.isImportant = isImportant;
    }
}
