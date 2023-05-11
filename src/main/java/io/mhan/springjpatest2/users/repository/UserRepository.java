package io.mhan.springjpatest2.users.repository;

import io.mhan.springjpatest2.users.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
