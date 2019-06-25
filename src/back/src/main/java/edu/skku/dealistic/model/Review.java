package edu.skku.dealistic.model;

import edu.skku.dealistic.exception.NotFoundException;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

// JPA Annotations
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"author_id", "item_id"}))
// Lombok Annotations
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ManyToOne
    @JoinColumn
    @Setter(AccessLevel.NONE)
    private User author;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private String authorName;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Setter(AccessLevel.NONE)
    private Item item;

    @Column(length = 1000)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer contentLength = 0;

    @Column(nullable = false)
    private Double rating = 2.5;

    @Column(nullable = false)
    private Double originalRating = 2.5;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Setter(AccessLevel.NONE)
    private Reference reference;

    @Column(nullable = false)
    private Double importance = 2.5;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(
            targetEntity = ReviewKeyword.class,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "review_id")
    private List<ReviewKeyword> keywords;

    public ReviewKeyword getKeyword(Keyword keyword) {
        return keywords
                .stream()
                .filter(rkeyword -> rkeyword.getKeyword().equals(keyword))
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    public ReviewKeyword getKeyword(ItemKeyword iKeyword) {
        return getKeyword(iKeyword.getKeyword());
    }

}
