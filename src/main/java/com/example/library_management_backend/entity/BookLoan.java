package com.example.library_management_backend.entity;

import com.example.library_management_backend.constants.BookLoanStatusEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "book_loans")
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(cascade = CascadeType.ALL)
    BookCopy bookCopy;

    @ManyToOne(cascade = CascadeType.ALL)
    User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date loanDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date returnDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    Date actualReturnDate;

    @Enumerated(EnumType.STRING)
    BookLoanStatusEnum status;

    @Column(nullable = true)
    String currentBookRequestId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date CreatedAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date UpdatedAt;

    @PrePersist
    protected void onCreate() {
        CreatedAt = new Date();
        UpdatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        UpdatedAt = new Date();
    }
}

