package com.example.vegacalendar.data.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CategoryModel;
import com.example.vegacalendar.core.repositories.CategoryRepository;
import com.example.vegacalendar.data.entities.Category;
import com.example.vegacalendar.data.jpas.CategoryJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private CategoryJPARepository categoryRepositoryData;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryModel> getAll() {
        Iterable<Category> categories = categoryRepositoryData.findCategoryByDeletedFalse();

        List<CategoryModel> categoryModels = StreamSupport.stream(categories.spliterator(), false)
                .map(country->modelMapper.map(country, CategoryModel.class))
                .collect(Collectors.toList());

        return categoryModels;
    }

    @Override
    public CategoryModel getById(UUID id) throws ResourceNotFoundException {
        Optional<Category> category = categoryRepositoryData.findCategoryByIdAndDeletedFalse(id);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("Category with that id: " + id +" does not exist");
        }
        return this.modelMapper.map(category, CategoryModel.class);


    }

    public CategoryModel save(CategoryModel categoryModel) {
         Category category = modelMapper.map(categoryModel, Category.class);
         categoryRepositoryData.save(category);
         return modelMapper.map(category, CategoryModel.class);
    }
}