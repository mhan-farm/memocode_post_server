package io.mhan.springjpatest2.series.repository;

import io.mhan.springjpatest2.series.entity.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<Series, Long> {
}
