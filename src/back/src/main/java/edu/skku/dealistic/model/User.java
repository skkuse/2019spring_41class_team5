package edu.skku.dealistic.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(length = 20)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority = Authority.USER;

    @Lob
    @Column
    private Byte[] profileImage;

    @OneToMany(
            targetEntity = Bookmark.class,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn
    private List<Bookmark> bookmarks;

    //@OneToMany(targetEntity = Review.class, fetch = FetchType.LAZY)
    //private List<Review> reviews;
}
