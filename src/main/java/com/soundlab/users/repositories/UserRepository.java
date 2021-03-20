package com.soundlab.users.repositories;

import com.soundlab.users.core.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    <T> List<T> findBy(Class<T> type);
    <T> Optional<T> findById(Long id, Class<T> type);
}
