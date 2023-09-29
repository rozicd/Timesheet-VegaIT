package com.example.vegacalendar.data.jpas;

import com.example.vegacalendar.data.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJPARepository extends CrudRepository<User, UUID> {
    List<User> findAllByDeletedFalse();
    Optional<User> findUserByIdAndDeletedFalse(UUID id);
    Page<User> findAll(Pageable pageable);
    Optional<User> findUsersByEmail(String email);
}
