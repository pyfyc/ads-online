package com.skypro.adsonline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_comments_users"))
    private UserEntity author;
    @ManyToOne
    @JoinColumn(name = "ad_id", foreignKey = @ForeignKey(name = "fk_comments_ads"))
    private AdEntity ad;
    private long createdAt;
    private String text;

}
