package io.mhan.springjpatest2.series.repository;

import io.mhan.springjpatest2.series.entity.Series;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SeriesRepository extends CrudRepository<Series, UUID> {

    List<Series> findByAuthorId(UUID authorId);
}
