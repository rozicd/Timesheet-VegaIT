package com.example.vegacalendar.core.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.ClientModel;
import com.example.vegacalendar.core.model.ClientRequestParamsModel;
import com.example.vegacalendar.core.model.Paginated;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {
    ClientModel getById(UUID id) throws ResourceNotFoundException;
    ClientModel save(ClientModel clientModel) throws ResourceNotFoundException;
    ClientModel update(ClientModel clientModel, UUID id) throws ResourceNotFoundException;
    void delete(UUID Id) throws ResourceNotFoundException;

    Paginated<ClientModel> getAll(ClientRequestParamsModel clientRequestParamsModel);
    List<ClientModel> listAll();
}
