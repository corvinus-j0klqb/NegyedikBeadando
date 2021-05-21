package com.NegyedikBeadando.NegyedikBeadando.DAO;

import com.NegyedikBeadando.NegyedikBeadando.DAO.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByUserName(String username);
}
