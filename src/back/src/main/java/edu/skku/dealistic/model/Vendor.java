package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String url; // Example: https://amazon.com

    @Column(length = 500, nullable = false)
    private String itemDetailPrefix;

    @Column(length = 500, nullable = false)
    private String itemDetailPostfix;

    @Column(length = 500, nullable = false)
    private String reviewDetailPrefix; // Example: /product-reviews/

    @Column(length = 500, nullable = false)
    private String reviewDetailPostfix; // Example: reviewerType=all_reviews&sortBy=recent

    @Column(length = 100, nullable = false)
    private String pageParam; // Example: pageNumber

    public String getItemDetailUrl(VendorLink link) {
        return url + itemDetailPrefix + link.getVendorItemId() + itemDetailPostfix;
    }

    public String getReviewDetailUrl(VendorLink link) {
        return url + reviewDetailPrefix + link.getVendorItemId() + "?" + reviewDetailPostfix;
    }

    public String getReviewDetailUrl(VendorLink link, Integer page) {
        return url + reviewDetailPrefix + link.getVendorItemId() + "?" + pageParam(page) + "&" + itemDetailPostfix;
    }

    private String pageParam(Integer page) {
        return pageParam + "=" + page;
    }

    public Reference referenece() {
        return Reference.builder().id(this.id).name(this.name).build();
    }
}
