package com.example.vegacalendar.data.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.exceptions.UserWithSameEmailException;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.model.UserInfo;
import com.example.vegacalendar.core.model.UserModel;
import com.example.vegacalendar.core.repositories.UserRepository;
import com.example.vegacalendar.data.entities.User;
import com.example.vegacalendar.data.jpas.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserModel save(UserModel userModel) throws UserWithSameEmailException {
        Optional<User> existingUser = userJPARepository.findUsersByEmail(userModel.getEmail());
        if(existingUser.isPresent()){
            throw new UserWithSameEmailException("User with email: " + userModel.getEmail() + " already exists");
        }
        User user = modelMapper.map(userModel, User.class);
        userJPARepository.save(user);
        return modelMapper.map(user, UserModel.class);
    }

    @Override
    public UserModel getById(UUID id) throws ResourceNotFoundException {
        Optional<User> user = userJPARepository.findUserByIdAndDeletedFalse(id);
        if(user.isPresent()){
            return modelMapper.map(user, UserModel.class);
        }
        throw new ResourceNotFoundException("User with that id:" + id + " does not exist");
    }

    @Override
    public UserModel update(UserModel userModel, UUID id) throws ResourceNotFoundException, UserWithSameEmailException {
        Optional<User> optionalUser = userJPARepository.findUserByIdAndDeletedFalse(id);
        Optional<User> optionalUserEmail = userJPARepository.findUsersByEmail(userModel.getEmail());
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User with that id:" + id + " does not exist");
        }
        if(optionalUserEmail.isPresent()){
            throw new UserWithSameEmailException("User with that email: " + userModel.getEmail() + " already existst!");
        }
        User user = optionalUser.get();
        User newUser = modelMapper.map(userModel, User.class);
        newUser.setUserId(id);
        newUser.setPassword(user.getPassword());
        userJPARepository.save(newUser);
        return modelMapper.map(newUser, UserModel.class);

    }

    @Override
    public void delete(UUID id) throws ResourceNotFoundException {
        Optional<User> optionalUser = userJPARepository.findUserByIdAndDeletedFalse(id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User with that id:" + id + " does not exist");
        }
        User user = optionalUser.get();
        user.setDeleted(true);
        userJPARepository.save(user);
    }

    @Override
    public Paginated<UserModel> getAll(int page, int pageSize) {
        Pageable pageable = (Pageable) PageRequest.of(page-1, pageSize);
        Page<User> userPage = userJPARepository.findAll(pageable);
        List<UserModel> userModels = userPage.getContent().stream().map(user -> modelMapper
                .map(user, UserModel.class)).collect(Collectors.toList());

        return new Paginated<UserModel>(userModels, page, (int) userPage.getTotalElements(), userPage.getTotalPages());
    }

    @Override
    public List<UserModel> listAll() {
        List<User> users = userJPARepository.findAllByDeletedFalse();
        List<UserModel> userModels = users.stream().map(user -> modelMapper.map(user, UserModel.class)).collect(Collectors.toList());
        return userModels;
    }

    @Override
    public UserModel findByEmail(String email) throws UsernameNotFoundException {
        User user = userJPARepository.findUsersByEmail(email).orElseThrow(() ->new UsernameNotFoundException("User not found " + email));

        return modelMapper.map(user, UserModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userJPARepository.findUsersByEmail(username).orElseThrow(() ->new UsernameNotFoundException("User not found " + username));
        UserModel userModel = modelMapper.map(user, UserModel.class);

        return new UserInfo(userModel);
    }
}
