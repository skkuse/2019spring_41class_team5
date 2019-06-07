package edu.skku.dealistic.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Recommendation {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne(targetEntity = ItemCategory.class)
    @JoinColumn
    private ItemCategory category;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn
    private User author;

    @ManyToMany(targetEntity = Keyword.class)
    private List<Keyword> keywords;
}
