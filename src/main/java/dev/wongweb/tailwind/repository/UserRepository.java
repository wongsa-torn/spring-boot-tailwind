package dev.wongweb.tailwind.repository;

import dev.wongweb.tailwind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}