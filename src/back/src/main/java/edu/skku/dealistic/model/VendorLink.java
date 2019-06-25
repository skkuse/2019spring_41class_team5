package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Item reference
 *
 * @author Junhyun Kim
 */
// JPA Annotations
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "vendor_id"}))
// Lombok Annotations
@Data
@Builder
@ToString(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
// Jackson Annotations
public class VendorLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String vendorItemId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Vendor vendor;

    @JsonIgnore
    @Column
    private LocalDateTime lastCrawledDateTime;

    @JsonIgnore
    public String getName() {
        if (vendor == null) return null;
        return vendor.getName();
    }

    @JsonIgnore
    public String getItemDetailUrl() {
        return vendor.getItemDetailUrl(this);
    }

    @JsonIgnore
    public String getReviewDetailUrl() {
        return vendor.getReviewDetailUrl(this);
    }
}
