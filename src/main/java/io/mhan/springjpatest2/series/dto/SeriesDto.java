package io.mhan.springjpatest2.series.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.series.entity.PostSeries;
import io.mhan.springjpatest2.series.entity.Series;
import io.mhan.springjpatest2.users.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeriesDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("postIds")
    private List<UUID> postIds;

    @JsonProperty("posts")
    private List<PostDto> postDtos;

    @JsonProperty("author")
    private AuthorDto authorDto;

    public static SeriesDto fromSeries(Series series) {
        AuthorDto authorDto = AuthorDto.fromAuthor(series.getAuthor());

        List<UUID> postIds = series.getPostSeries().stream()
                .map(postSeries -> postSeries.getPost().getId()).toList();

        List<Post> posts = series.getPostSeries().stream()
                .map(PostSeries::getPost).toList();

        List<PostDto> postDtos = PostDto.fromPosts(posts);

        SeriesDto seriesDto = SeriesDto.builder()
                .id(series.getId())
                .title(series.getTitle())
                .authorDto(authorDto)
                .postIds(postIds)
                .postDtos(postDtos)
                .build();

        return seriesDto;
    }

    public static List<SeriesDto> fromSeriesList(List<Series> seriesList) {
        List<SeriesDto> seriesDtoList = seriesList.stream()
                .map(SeriesDto::fromSeries)
                .toList();

        return seriesDtoList;
    }
}
