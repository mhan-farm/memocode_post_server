package io.mhan.springjpatest2.users.repository;

import io.mhan.springjpatest2.users.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    List<User> findByIdIn(List<Long> ids);
}
