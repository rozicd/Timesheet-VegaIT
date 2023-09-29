package com.example.vegacalendar.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.exceptions.UserWithSameEmailException;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.model.UserModel;
import com.example.vegacalendar.core.repositories.UserRepository;
import com.example.vegacalendar.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel save(UserModel userModel) throws UserWithSameEmailException {

        return userRepository.save(userModel);
    }

    @Override
    public UserModel getById(UUID uuid) throws ResourceNotFoundException {
        return userRepository.getById(uuid);
    }

    @Override
    public UserModel update(UserModel newUserModel, UUID id) throws ResourceNotFoundException, UserWithSameEmailException {
        return userRepository.update(newUserModel, id);
    }

    @Override
    public void delete(UUID id) throws ResourceNotFoundException {
        userRepository.delete(id);
    }

    @Override
    public Paginated<UserModel> getAll(int page, int pageSize) {

        return userRepository.getAll(page, pageSize);
    }

    @Override
    public UserModel findByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserModel> listAll() {
        return userRepository.listAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username);
    }
}
