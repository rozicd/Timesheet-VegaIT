package com.example.vegacalendar.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CategoryModel;
import com.example.vegacalendar.core.repositories.CategoryRepository;
import com.example.vegacalendar.core.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryModel> getAll(){
        return categoryRepository.getAll();
    }

    @Override
    public CategoryModel getById(UUID id) throws ResourceNotFoundException {
        return this.modelMapper.map(categoryRepository.getById(id), CategoryModel.class);
    }

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        CategoryModel category = this.modelMapper.map(categoryModel, CategoryModel.class);
        return categoryRepository.save(category);
    }
}

