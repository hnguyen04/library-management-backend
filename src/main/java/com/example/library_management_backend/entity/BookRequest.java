package com.example.library_management_backend.entity;

import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
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
@Table(name = "book_requests")
public class BookRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(cascade = CascadeType.ALL)
    BookLoan bookLoan;

    @Enumerated(EnumType.STRING)
    BookRequestStatusEnum status;

    @Enumerated(EnumType.STRING)
    BookRequestTypeEnum type;

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
