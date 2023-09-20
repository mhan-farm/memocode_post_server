package io.mhan.springjpatest2.series.service;

import io.mhan.springjpatest2.series.dto.SeriesDto;
import io.mhan.springjpatest2.series.entity.Series;
import io.mhan.springjpatest2.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeriesQueryService {

    private final SeriesRepository seriesRepository;

    public Optional<Series> findById(UUID seriesId) {
        return seriesRepository.findById(seriesId);
    }

    public Series findByIdElseThrow(UUID seriesId) {
        return findById(seriesId)
                .orElseThrow();
    }

    public SeriesDto getSeriesDtoById(UUID seriesId) {
        Series series = findByIdElseThrow(seriesId);

        return SeriesDto.fromSeries(series);
    }

    public List<SeriesDto> getAllMySeriesByAuthorId(UUID authorId) {
        return SeriesDto.fromSeriesList(seriesRepository.findByAuthorId(authorId));
    }
}
