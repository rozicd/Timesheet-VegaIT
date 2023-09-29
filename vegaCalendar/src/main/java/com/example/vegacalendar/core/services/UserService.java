package com.example.vegacalendar.core.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.exceptions.UserWithSameEmailException;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.model.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserModel save(UserModel userModel) throws UserWithSameEmailException;
    UserModel getById(UUID uuid) throws ResourceNotFoundException;
    UserModel update(UserModel newUserModel, UUID id) throws ResourceNotFoundException, UserWithSameEmailException;
    void delete(UUID id)throws ResourceNotFoundException;
    Paginated<UserModel> getAll(int page, int pageSize);

    UserModel findByEmail(String email) throws ResourceNotFoundException;
    List<UserModel> listAll();

}
