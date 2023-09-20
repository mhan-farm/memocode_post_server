package io.mhan.springjpatest2.web.series.impl;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.series.dto.SeriesDto;
import io.mhan.springjpatest2.series.service.SeriesQueryService;
import io.mhan.springjpatest2.web.series.ApiV1SeriesQueryController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/series")
@RequiredArgsConstructor
public class ApiV1SeriesQueryControllerImpl implements ApiV1SeriesQueryController {

    private final SeriesQueryService seriesQueryService;
    private final SecurityUser securityUser;

    @GetMapping("/{seriesId}")
    public ResponseEntity<SeriesDto> getSeries(@PathVariable UUID seriesId) {

        SeriesDto dto = seriesQueryService.getSeriesDtoById(seriesId);

        return ResponseEntity.ok(dto);
    }

    // 나의 모든 시리즈 조회
    @IsUserOrAdmin
    @GetMapping("/my")
    public ResponseEntity<List<SeriesDto>> getAllMySeries() {
        List<SeriesDto> seriesDtos = seriesQueryService.getAllMySeriesByAuthorId(securityUser.getId());

        return ResponseEntity.ok(seriesDtos);
    }
}
