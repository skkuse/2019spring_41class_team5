package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class Vendor {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String url; // Example: https://amazon.com

    @JsonIgnore
    @Column(length = 500, nullable = false)
    private String itemDetailPrefix;

    @JsonIgnore
    @Column(length = 500, nullable = false)
    private String itemDetailPostfix;

    @JsonIgnore
    @Column(length = 500, nullable = false)
    private String reviewDetailPrefix; // Example: /product-reviews/

    @JsonIgnore
    @Column(length = 500, nullable = false)
    private String reviewDetailPostfix; // Example: reviewerType=all_reviews&sortBy=recent

    @JsonIgnore
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
}
