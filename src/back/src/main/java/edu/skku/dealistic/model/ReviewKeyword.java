package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"review_id", "keyword_id"})
)
public class ReviewKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(targetEntity = Review.class)
    @JoinColumn(nullable = false)
    private Review review;

    @ManyToOne(targetEntity = Keyword.class)
    @JoinColumn(nullable = false)
    private Keyword keyword;

    @Column
    private Double rating = 2.5;

    @Column
    private Long count = 0L;

    public void addRating(Double newRating) {
        count++;
        this.rating = this.rating * (count - 1) / count + newRating / count;
    }
}

