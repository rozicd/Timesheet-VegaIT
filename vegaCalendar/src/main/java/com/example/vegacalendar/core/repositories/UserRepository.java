package com.example.vegacalendar.core.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.exceptions.UserWithSameEmailException;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    UserModel save(UserModel userModel) throws UserWithSameEmailException;
    UserModel getById(UUID uuid) throws ResourceNotFoundException;
    UserModel update(UserModel newUserModel, UUID id) throws ResourceNotFoundException, UserWithSameEmailException;
    void delete(UUID id) throws ResourceNotFoundException;
    Paginated<UserModel> getAll(int page, int pageSize);
    List<UserModel> listAll();
    UserModel findByEmail(String email) throws ResourceNotFoundException;
    UserDetails loadUserByUsername(String username);
}
