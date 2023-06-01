package io.mhan.springjpatest2.series.service;

import io.mhan.springjpatest2.series.entity.Series;
import io.mhan.springjpatest2.series.repository.SeriesRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    private final UserService userService;

    public Series createAndSave(String name, Long authorId) {
        Series series = create(name, authorId);

        Series savedSeries = seriesRepository.save(series);

        return savedSeries;
    }

    private Series create(String name, Long authorId) {
        User author = userService.findActiveByIdElseThrow(authorId);

        Series series = Series.builder()
                .name(name)
                .author(author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return series;
    }

    public Optional<Series> findById(Long seriesId) {
        return seriesRepository.findById(seriesId);
    }

    public Series findByIdElseThrow(Long seriesId) {
        return findById(seriesId)
                .orElseThrow();
    }
}
