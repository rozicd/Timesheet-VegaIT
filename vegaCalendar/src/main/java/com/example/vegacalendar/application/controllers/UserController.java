package com.example.vegacalendar.application.controllers;

import com.example.vegacalendar.application.Constants;
import com.example.vegacalendar.application.dtos.*;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.exceptions.UserWithSameEmailException;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.model.UserModel;
import com.example.vegacalendar.core.services.JwtService;
import com.example.vegacalendar.core.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody CreateUserRequestDTO createUserRequestDTO) throws UserWithSameEmailException {
        UserModel userModel = modelMapper.map(createUserRequestDTO, UserModel.class);
        String hashedPassword = passwordEncoder.encode(createUserRequestDTO.getPassword());
        userModel.setPassword(hashedPassword);
        UserModel savedUser = userService.save(userModel);
        UserResponseDTO userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        UserModel userModel = userService.getById(id);
        UserResponseDTO userResponseDTO = modelMapper.map(userModel, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable UUID id,@RequestBody UpdateUserRequestDTO updateUserRequestDTO) throws ResourceNotFoundException, UserWithSameEmailException {
        UserModel userModel = modelMapper.map(updateUserRequestDTO, UserModel.class);
        UserModel updatedUser = userService.update(userModel, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>("User succesfuly deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = Constants.DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int pageSize){

        Paginated<UserModel> userModelPaginated = userService.getAll(page, pageSize);
        List<UserResponseDTO> userResponseDTOS = userModelPaginated.getItems().stream()
                .map(userModel -> modelMapper.map(userModel, UserResponseDTO.class)).collect(Collectors.toList());
        Paginated<UserResponseDTO> userPaginatedResponse = new Paginated<>(userResponseDTOS, page, userModelPaginated.getTotalItems(), userModelPaginated.getTotalPages());

        return new ResponseEntity<>(userPaginatedResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/list-all")
    public ResponseEntity<?> listAll(){
        List<UserModel> userModels = userService.listAll();
        List<UserResponseDTO> userResponseDTOS = userModels.stream().map(userModel -> modelMapper.map(userModel, UserResponseDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) throws ResourceNotFoundException, BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserModel userModel = userService.findByEmail(loginDTO.getEmail());
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(userModel);
            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(token);
            return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}
