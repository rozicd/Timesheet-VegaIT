package com.example.vegacalendar.application.controllers;

import com.example.vegacalendar.application.dtos.CreateCategoryRequestDTO;
import com.example.vegacalendar.application.dtos.CategoryResponseDTO;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CategoryModel;
import com.example.vegacalendar.core.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="api/category")
public class CategoryController {

    @Autowired
    private CategoryService categorySerivce;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<?> saveOne(@RequestBody CreateCategoryRequestDTO createCategoryRequestDTO){
        CategoryModel categoryModel = modelMapper.map(createCategoryRequestDTO, CategoryModel.class);
        CategoryModel savedCategory = categorySerivce.save(categoryModel);
        CategoryResponseDTO categoryResponse = modelMapper.map(savedCategory, CategoryResponseDTO.class);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public ResponseEntity<?> getAll(){
        List<CategoryModel> categorymModels = categorySerivce.getAll();
        List<CategoryResponseDTO> categoryResponseDTOS = categorymModels.stream()
                .map(category->modelMapper.map(category,CategoryResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoryResponseDTOS, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws ResourceNotFoundException {
    CategoryModel categoryModel = categorySerivce.getById(id);
    CategoryResponseDTO categoryResponseDTO = modelMapper.map(categoryModel, CategoryResponseDTO.class);
    return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

}
