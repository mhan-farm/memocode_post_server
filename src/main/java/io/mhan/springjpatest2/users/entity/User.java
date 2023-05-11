package io.mhan.springjpatest2.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String username;

    private String password;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static User create(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return user;
    }
}
