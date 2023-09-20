package io.mhan.springjpatest2.series.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesSetPostRequest {
    @Size(min = 1, message = "적어도 하나 이상의 post ID가 필요합니다.")
    private Set<UUID> postIds;
}
