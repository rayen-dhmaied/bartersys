package com.horizon.bartersys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.horizon.bartersys.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    List<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String username, String fullName);
}
