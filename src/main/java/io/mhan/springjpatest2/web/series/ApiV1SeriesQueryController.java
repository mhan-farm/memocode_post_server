package io.mhan.springjpatest2.web.series;

import io.mhan.springjpatest2.series.dto.SeriesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "ApiV1SeriesQueryController", description = "SERIES 조회")
public interface ApiV1SeriesQueryController {
    @Operation(summary = "시리즈 조회")
    ResponseEntity<SeriesDto> getSeries(@PathVariable UUID seriesId);

    @Operation(summary = "나의 모드 시리즈 조회")
    ResponseEntity<List<SeriesDto>> getAllMySeries();
}
