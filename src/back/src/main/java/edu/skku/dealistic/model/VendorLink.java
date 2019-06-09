package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.*;
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
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"name", "itemDetailUrl", "reviewDetailUrl"})
@ToString(of = {"id"})
public class VendorLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String vendorItemId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Item item;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Vendor vendor;

    @JsonIgnore
    @Column
    private LocalDateTime lastCrawledDateTime;

    public String getName() {
        if (vendor == null) return null;
        return vendor.getName();
    }

    public String getItemDetailUrl() {
        return vendor.getItemDetailUrl(this);
    }

    public String getReviewDetailUrl() {
        return vendor.getReviewDetailUrl(this);
    }
}
