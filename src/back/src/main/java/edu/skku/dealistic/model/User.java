package edu.skku.dealistic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"bookmarks", "reviews"})
public class User {

    @Id
    @Column(length = 20)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @JsonIgnore
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String organization;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Lob
    @Column
    private String profileImage; // base64

    @JsonIgnore
    @OneToMany(
            targetEntity = Bookmark.class,
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(
            targetEntity = Review.class,
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private List<Review> reviews;
}
