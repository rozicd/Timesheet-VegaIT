package com.example.vegacalendar.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.ClientModel;
import com.example.vegacalendar.core.model.ClientRequestParamsModel;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.repositories.ClientRepository;
import com.example.vegacalendar.core.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientModel save(ClientModel clientModel) throws ResourceNotFoundException {
        return clientRepository.save(clientModel);
    }

    @Override
    public ClientModel getById(UUID id) throws ResourceNotFoundException {
        return clientRepository.getById(id);
    }

    @Override
    public ClientModel update(ClientModel clientModel, UUID id) throws ResourceNotFoundException {
        return clientRepository.update(clientModel, id);
    }

    @Override
    public Paginated<ClientModel> getAll(ClientRequestParamsModel clientRequestParamsModel) {
        return clientRepository.getAll(clientRequestParamsModel);
    }

    @Override
    public void delete(UUID id) throws ResourceNotFoundException {
        clientRepository.delete(id);
    }

    @Override
    public List<ClientModel> listAll() {
        return clientRepository.listAll();
    }
}
