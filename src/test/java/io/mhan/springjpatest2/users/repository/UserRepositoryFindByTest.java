package io.mhan.springjpatest2.users.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.users.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@SpringBootTest
@TestInstance(PER_CLASS)
public class UserRepositoryFindByTest {

    @Autowired
    private TestService testService;

    @Autowired
    private UserRepository userRepository;

    User user1;

    @BeforeAll
    void beforeAll() {
        List<User> authors = testService.createUsers(10);
        user1 = authors.get(0);
    }

    @AfterAll
    void afterAll() {
        testService.deleteAll();
    }

    @Test
    @DisplayName("user id를 통해 한번 조회하기")
    void t1() {
        User user = userRepository.findById(user1.getId()).get();
    }

    @Test
    @DisplayName("user id를 통해 두번 조회하기")
    void t2() {
        User user = userRepository.findById(user1.getId()).get();
        User user2 = userRepository.findById(user1.getId()).get();
    }
    // @Id가 달린 user id는 변경에 여지가 없기 때문에 캐싱 지원

    @Test
    @DisplayName("user username을 통해 한번 조회하기")
    void t3() {
        User user = userRepository.findByUsername(user1.getUsername()).get();
    }

    @Test
    @DisplayName("user username을 통해 두번 조회하기")
    void t4() {
        User user = userRepository.findByUsername(user1.getUsername()).get();
        User user2 = userRepository.findByUsername(user1.getUsername()).get();
    }
    // username이 변경에 여지가 있으므로 두번 select 함
    // 그래서 왠만하면 id를 통해 검색해오는 것이 성능상 유리하다.

}
