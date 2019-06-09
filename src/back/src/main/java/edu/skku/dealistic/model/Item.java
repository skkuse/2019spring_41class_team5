package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.skku.dealistic.converter.ListToJsonConverter;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(name = "Item.detail",
        attributeNodes = @NamedAttributeNode(value = "vendorLinks", subgraph = "vendorLinksGraph"),
        subgraphs = {
                @NamedSubgraph(
                        name = "vendorLinksGraph",
                        attributeNodes = {
                                @NamedAttributeNode("vendor")
                        }
                )
        }
)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@ToString(exclude = {"vendorLinks", "reviews", "keywords"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @Setter(AccessLevel.NONE)
    private ItemCategory category;

    @Column(length = 100, nullable = false)
    @Setter(AccessLevel.NONE)
    private String company;

    @Lob
    @Column
    private byte[] image;

    @Column(nullable = false)
    private Double rating = 0.0;

    @JsonIgnore
    @Column(nullable = false)
    private Double originalRating = 0.0;

    @Convert(converter = ListToJsonConverter.class)
    @Column(length = 4000)
    private List<String> specs;

    @Fetch(FetchMode.SELECT)
    @OneToMany(targetEntity = VendorLink.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<VendorLink> vendorLinks;

    @OneToMany(targetEntity = ItemKeyword.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private List<ItemKeyword> keywords;

    @JsonIgnore
    @OneToMany(targetEntity = Review.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<Review> reviews;


}
