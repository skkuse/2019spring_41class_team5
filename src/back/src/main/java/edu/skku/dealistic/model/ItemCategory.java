package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"items", "keywords"})
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(length = 100, nullable = false)
    @Setter(AccessLevel.NONE)
    private String name;

    @JsonIgnore
    @OneToMany(targetEntity = Item.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<Item> items;

    @JsonIgnore
    @OneToMany(targetEntity = Keyword.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<Keyword> keywords;
}
