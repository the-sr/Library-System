package library.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_book")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="borrowed_date")
    @Temporal(TemporalType.DATE)
    private LocalDate borrowedDate;

    @Column(name="returned_date")
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    @Column(name = "expected_return_date")
    @Temporal(TemporalType.DATE)
    private LocalDate expectedReturnDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_id")
    private Long bookId;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = false, updatable = false)
    private User user;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id",insertable = false, updatable = false)
    private Book book;

    @PrePersist
    protected void isActive() {
        isActive = true;
    }
}