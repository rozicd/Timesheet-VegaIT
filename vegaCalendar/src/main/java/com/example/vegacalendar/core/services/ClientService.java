package com.example.vegacalendar.core.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.ClientModel;
import com.example.vegacalendar.core.model.ClientRequestParamsModel;
import com.example.vegacalendar.core.model.Paginated;

import java.util.List;
import java.util.UUID;


public interface ClientService {
    ClientModel save(ClientModel clientModel) throws ResourceNotFoundException;
    ClientModel getById(UUID id) throws ResourceNotFoundException;

    ClientModel update(ClientModel clientModel, UUID id) throws ResourceNotFoundException;
    Paginated<ClientModel> getAll(ClientRequestParamsModel clientRequestParamsModel);
    void delete(UUID id) throws ResourceNotFoundException;
    List<ClientModel> listAll();
}
