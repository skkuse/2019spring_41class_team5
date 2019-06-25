package edu.skku.dealistic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Lob
    @Column
    private String thumbnail; // base64

    @ManyToOne(targetEntity = ItemCategory.class)
    @JoinColumn
    private ItemCategory category;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn
    private User author;

    @ManyToMany(targetEntity = Keyword.class)
    @JoinTable(
            name = "recommendation_keyword",
            joinColumns = @JoinColumn(name = "recommendation_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords;

}
