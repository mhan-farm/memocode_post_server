package io.mhan.springjpatest2.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "authors", uniqueConstraints = {
        @UniqueConstraint(name = "UK_users_username", columnNames = {"username"})
})
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Author {
    @Id
    private UUID id;

    @Column(name = "username", unique = true, nullable = false, length = 25, updatable = false)
    private String username;

    @Column(name = "nickname", unique = true)
    private String nickname;

    private String name;

    @Builder.Default
    private UUID imageId = UUID.randomUUID();

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updated = LocalDateTime.now();

    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    @Column(nullable = true)
    private LocalDateTime deleted;

    public void delete() {
        this.isDeleted = true;
        this.deleted = LocalDateTime.now();
    }
}
