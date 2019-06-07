package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.skku.dealistic.converter.ListToJsonConverter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue
    @Column
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Setter(AccessLevel.NONE)
    private ItemCategory category;

    @Column(length = 100, nullable = false)
    @Setter(AccessLevel.NONE)
    private String company;

    @Lob
    @Column
    private Byte[] image;

    @Column(nullable = false)
    private Double rating = 0.0;

    @JsonIgnore
    @Column(nullable = false)
    private Double originalRating = 0.0;

    @Convert(converter = ListToJsonConverter.class)
    @Column(length = 4000)
    private List<String> specs;

    //@OneToMany
    //private List<VendorLink> vendorLinks;

    //@OneToMany
    //private List<Review> reviews;


}
