package edu.skku.dealistic.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Keyword {

    enum KeywordType {
        POSITIVE, NEGATIVE, NEUTRAL, INFO
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private KeywordType type;

    @ManyToMany(targetEntity = Review2.class)
    @JoinColumn
    private List<Review2> reviews;

    @ManyToMany(targetEntity = Item.class)
    @JoinColumn
    private List<Item> items;

    @ManyToMany(targetEntity = Recommendation.class)
    @JoinColumn
    private List<Recommendation> recommendations;
}