package io.mhan.springjpatest2.series.controller;

import io.mhan.springjpatest2.series.entity.Series;
import io.mhan.springjpatest2.series.request.SeriesCreateRequest;
import io.mhan.springjpatest2.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.mhan.springjpatest2.base.init.InitData.USER_ID;

@RestController
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    @PostMapping("/series")
    public void newSeries(@RequestBody SeriesCreateRequest request) {

        Series series = seriesService.createAndSave(request.getName(), USER_ID);
    }
}
