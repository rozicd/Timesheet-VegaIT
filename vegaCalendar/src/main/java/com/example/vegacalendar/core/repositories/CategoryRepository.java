package com.example.vegacalendar.core.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CategoryModel;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository {
    List<CategoryModel> getAll();
    CategoryModel getById(UUID id) throws ResourceNotFoundException;
    CategoryModel save(CategoryModel categoryModel);
}
