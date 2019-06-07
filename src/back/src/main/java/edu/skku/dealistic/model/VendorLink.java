package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Item reference
 *
 * @author Junhyun Kim
 */
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "vendor_id"})
)
@Data
public class VendorLink {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(nullable = false)
    private String vendorItemId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Vendor vendor;

    public String getItemDetailUrl() {
        return vendor.getItemDetailUrl(this);
    }

    public String getReviewDetailUrl() {
        return vendor.getReviewDetailUrl(this);
    }
}
