package edu.skku.dealistic.model;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

// JPA Annotations
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "user_id"}))
// Lombok Annotations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDate addDate;
}

