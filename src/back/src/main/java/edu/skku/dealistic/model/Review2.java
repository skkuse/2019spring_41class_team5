package edu.skku.dealistic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review2 {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @Column(length = 200)
    private String title;

    @Column(length = 5000)
    private String content;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Double originalRating;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Reference reference;

    @Column(nullable = false)
    private Double importance;

    @ManyToMany(targetEntity = Keyword.class, fetch = FetchType.EAGER)
    @JoinColumn
    private List<Keyword> keywords;
}
