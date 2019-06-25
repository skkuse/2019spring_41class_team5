package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.skku.dealistic.converter.ListToCsvConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(targetEntity = ItemCategory.class)
    @JoinColumn(nullable = false)
    private ItemCategory category;

    @Convert(converter = ListToCsvConverter.class)
    @Column(length = 1000)
    private List<String> synonyms;

    @Convert(converter = ListToCsvConverter.class)
    @Column(length = 1000)
    private List<String> antonyms;

    public Boolean contains(String word) {
        return name.equalsIgnoreCase(word) || synonyms.stream().anyMatch(w -> w.equalsIgnoreCase(word));
    }

}