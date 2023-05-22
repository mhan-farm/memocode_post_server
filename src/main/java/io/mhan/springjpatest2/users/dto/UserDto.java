package io.mhan.springjpatest2.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public static UserDto fromUser(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .build();

        return userDto;
    }
}
