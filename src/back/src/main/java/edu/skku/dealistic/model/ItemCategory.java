package edu.skku.dealistic.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ItemCategory {

    @Id
    @GeneratedValue
    @Column
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(length = 100, nullable = false)
    @Setter(AccessLevel.NONE)
    private String name;

    @OneToMany(targetEntity = Item.class)
    @JoinColumn
    private List<Item> items;
}
