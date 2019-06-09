package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "keyword_id"})
)
@Builder
public class ItemKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(targetEntity = Item.class)
    @JoinColumn
    private Item item;

    @ManyToOne(targetEntity = Keyword.class)
    @JoinColumn(nullable = false)
    private Keyword keyword;

    @Column
    private Double rating = 2.5;

    private Long count = 0L;

}

