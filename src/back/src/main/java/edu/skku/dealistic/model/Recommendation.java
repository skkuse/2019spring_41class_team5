package edu.skku.dealistic.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

//@Entity
@Data
public class Recommendation {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    private ItemCategory category;

    @ManyToOne
    private User author;

    //@OneToMany
    private List<Keyword> keywords;
}
