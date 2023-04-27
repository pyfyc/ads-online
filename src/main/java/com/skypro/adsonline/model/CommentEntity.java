package com.skypro.adsonline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Comments")
public class CommentEntity {
    private Integer author; // id автора комментария
    private String authorImage; // ссылка на аватар автора комментария
    private String authorFirstName; // имя создателя комментария
    private long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk; // id комментария
    private String text; // текст комментария
}
