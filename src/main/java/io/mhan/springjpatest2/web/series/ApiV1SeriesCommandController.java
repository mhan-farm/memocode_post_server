package io.mhan.springjpatest2.web.series;

import io.mhan.springjpatest2.series.dto.SeriesDto;
import io.mhan.springjpatest2.series.request.SeriesCreateRequest;
import io.mhan.springjpatest2.series.request.SeriesSetPostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "ApiV1SeriesCommandController", description = "SERIES 생성, 변경, 삭제")
public interface ApiV1SeriesCommandController {
    @Operation(summary = "생성")
    ResponseEntity<SeriesDto> newSeries(SeriesCreateRequest request);

    @Operation(summary = "시리즈에 게시글들 세팅", description = "postids 에서 작성한 순서대로 시리즈의 게시글 순서가 정해짐")
    ResponseEntity<Void> setPosts(UUID seriesId, SeriesSetPostRequest request);

    @Operation(summary = "삭제")
    ResponseEntity<Void> deleteSeries(UUID seriesId);
}
