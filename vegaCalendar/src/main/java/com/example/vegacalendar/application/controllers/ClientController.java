package com.example.vegacalendar.application.controllers;

import com.example.vegacalendar.application.Constants;
import com.example.vegacalendar.application.dtos.ClientRequestParamsDTO;
import com.example.vegacalendar.application.dtos.CreateClientRequestDTO;
import com.example.vegacalendar.application.dtos.ClientResponseDTO;
import com.example.vegacalendar.application.dtos.UpdateClientRequestDTO;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.ClientModel;
import com.example.vegacalendar.core.model.ClientRequestParamsModel;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.services.ClientService;
import com.example.vegacalendar.core.services.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/client")
public class ClientController{

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody CreateClientRequestDTO createClientRequestDTO) throws ResourceNotFoundException {
        ClientModel clientModel = modelMapper.map(createClientRequestDTO, ClientModel.class);
        ClientModel savedClient = clientService.save(clientModel);
        ClientResponseDTO clientResponseDTO = modelMapper.map(savedClient, ClientResponseDTO.class);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws ResourceNotFoundException{

        ClientModel clientModel = clientService.getById(id);
        ClientResponseDTO clientResponseDTO = modelMapper.map(clientModel, ClientResponseDTO.class);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);

    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@RequestBody UpdateClientRequestDTO updateClientRequestDTO, @PathVariable UUID id) throws ResourceNotFoundException{
        ClientModel clientModel = modelMapper.map(updateClientRequestDTO, ClientModel.class);
        ClientModel newClient = clientService.update(clientModel, id);
        ClientResponseDTO clientResponseDTO = modelMapper.map(newClient, ClientResponseDTO.class);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);
    }

    @PutMapping(path="delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        clientService.delete(id);
        return new ResponseEntity<>("client successfully deleted", HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(ClientRequestParamsDTO clientRequestParamsDTO){
        ClientRequestParamsModel clientRequestParamsModel = modelMapper.map(clientRequestParamsDTO, ClientRequestParamsModel.class);
        Paginated<ClientModel> clientModelPaginated = clientService.getAll(clientRequestParamsModel);

        List<ClientResponseDTO> clientResponseDTOS = clientModelPaginated.getItems().stream()
                .map(item-> modelMapper.map(item, ClientResponseDTO.class))
                .collect(Collectors.toList());
        Paginated<ClientResponseDTO> clientPaginatedResponse = new Paginated<>(clientResponseDTOS, clientRequestParamsModel.getPage(), clientModelPaginated.getTotalItems(), clientModelPaginated.getTotalPages());
        return new ResponseEntity<>(clientPaginatedResponse, HttpStatus.OK);
    }
    @GetMapping(path="/list-all")
    public ResponseEntity<?> listAll(){
        List<ClientModel> clientModels = clientService.listAll();
        List<ClientResponseDTO> clientResponseDTOS = clientModels.stream().map(clientModel -> modelMapper.map(clientModel, ClientResponseDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(clientResponseDTOS, HttpStatus.OK);
    }

}
