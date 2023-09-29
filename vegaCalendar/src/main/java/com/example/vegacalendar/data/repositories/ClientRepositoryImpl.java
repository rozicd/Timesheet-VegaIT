package com.example.vegacalendar.data.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.ClientModel;
import com.example.vegacalendar.core.model.ClientRequestParamsModel;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.repositories.ClientRepository;
import com.example.vegacalendar.core.repositories.CountryRepository;
import com.example.vegacalendar.data.entities.Client;
import com.example.vegacalendar.data.jpas.ClientJPARepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    @Autowired
    private ClientJPARepository clientJPARepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClientModel getById(UUID id) throws ResourceNotFoundException {
        Optional<Client> client = clientJPARepository.findClientByIdAndDeletedFalse(id);
        if(client.isEmpty()){
            throw new ResourceNotFoundException("Client with that id: "+ id + " does not exist");
        }
        return modelMapper.map(client, ClientModel.class);
    }

    @Override
    public ClientModel save(ClientModel clientModel) throws ResourceNotFoundException {
        clientModel.setClientId(null);
        Client client = modelMapper.map(clientModel, Client.class);
        clientJPARepository.save(client);
        ClientModel savedClient = modelMapper.map(client, ClientModel.class);

        return savedClient;
    }

    @Override
    public ClientModel update(ClientModel clientModel, UUID id) throws ResourceNotFoundException {
        Optional<Client> optionalClient = clientJPARepository.findClientByIdAndDeletedFalse(id);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("Client with that id: "+ id + " does not exist");
        }
        Client client =  modelMapper.map(clientModel, Client.class);
        client.setClientId(id);
        clientJPARepository.save(client);
        return modelMapper.map(client, ClientModel.class);

    }

    @Override
    public void delete(UUID id) throws ResourceNotFoundException {
        Optional<Client> optionalClient = clientJPARepository.findClientByIdAndDeletedFalse(id);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("Client with that id: "+ id + " does not exist");
        }
        Client client = optionalClient.get();
        client.setDeleted(true);
        clientJPARepository.save(client);
    }

    @Override
    public Paginated<ClientModel> getAll(ClientRequestParamsModel clientRequestParamsModel) {
        Pageable pageable = (Pageable) PageRequest.of(clientRequestParamsModel.getPage()-1, clientRequestParamsModel.getPageSize());
        Page<Client> clientsPage = clientJPARepository.findByNameStartingWithAndNameStartingWithAndDeletedFalse(clientRequestParamsModel.getLetter(), clientRequestParamsModel.getSearch(), pageable);
        List<ClientModel> clientModels = clientsPage.getContent().stream()
                .map(client -> modelMapper.map(client, ClientModel.class))
                .collect(Collectors.toList());

        return new Paginated<>(clientModels, clientRequestParamsModel.getPage(), (int) clientsPage.getTotalElements(), clientsPage.getTotalPages());
    }

    @Override
    public List<ClientModel> listAll() {
        List<Client> clients = clientJPARepository.findAllByDeletedFalse();
        List<ClientModel> clientModels = clients.stream().map(client -> modelMapper.map(client, ClientModel.class)).collect(Collectors.toList());
        return clientModels;
    }

}
