package com.NegyedikBeadando.NegyedikBeadando;

import com.NegyedikBeadando.NegyedikBeadando.DAO.UserRepository;
import com.NegyedikBeadando.NegyedikBeadando.DAO.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUserName("Dori14");
        user.setPassword("pass1234");
        user.setName("Laczko Dora");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getUserName()).isEqualTo(existUser.getUserName());

    }
}
