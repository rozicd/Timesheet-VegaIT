package com.example.vegacalendar.data.jpas;

import com.example.vegacalendar.data.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryJPARepository extends CrudRepository<Category, UUID> {
    Iterable<Category> findCategoryByDeletedFalse();
    Optional<Category> findCategoryByIdAndDeletedFalse(UUID id);
}
