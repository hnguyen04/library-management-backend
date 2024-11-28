package com.example.library_management_backend.entity;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "book_copies")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(cascade = CascadeType.ALL)
    Book book;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BookCopyStatusEnum status;

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
