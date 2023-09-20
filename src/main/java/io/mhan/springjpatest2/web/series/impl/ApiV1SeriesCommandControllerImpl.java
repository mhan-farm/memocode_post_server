package io.mhan.springjpatest2.web.series.impl;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.series.dto.SeriesDto;
import io.mhan.springjpatest2.series.request.SeriesCreateRequest;
import io.mhan.springjpatest2.series.request.SeriesSetPostRequest;
import io.mhan.springjpatest2.series.service.SeriesCommandService;
import io.mhan.springjpatest2.series.service.SeriesQueryService;
import io.mhan.springjpatest2.web.series.ApiV1SeriesCommandController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/series")
@RequiredArgsConstructor
public class ApiV1SeriesCommandControllerImpl implements ApiV1SeriesCommandController {

    private final SeriesCommandService seriesCommandService;
    private final SeriesQueryService seriesQueryService;
    private final SecurityUser securityUser;

    // 시리즈 생성
    @IsUserOrAdmin
    @PostMapping
    public ResponseEntity<SeriesDto> newSeries(@RequestBody SeriesCreateRequest request) {

        UUID seriesId = seriesCommandService.createAndSave(request.getTitle(), securityUser.getId());

        SeriesDto dto = seriesQueryService.getSeriesDtoById(seriesId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(seriesId)
                .toUri();
        return ResponseEntity.created(location).body(dto);
    }

    // 시리즈에 post 추가
    @IsUserOrAdmin
    @PutMapping("/{seriesId}/posts")
    public ResponseEntity<Void> setPosts(@PathVariable UUID seriesId, @RequestBody SeriesSetPostRequest request) {
        seriesCommandService.setPosts(seriesId, request);

        return ResponseEntity.noContent().build();
    }

    @IsUserOrAdmin
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<Void> deleteSeries(@PathVariable UUID seriesId) {
        seriesCommandService.deleteById(seriesId, securityUser.getId());

        return ResponseEntity.noContent().build();
    }
}
