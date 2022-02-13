package com.devsuperior.bds02.servicies;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.servicies.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        try {
            City obj = new City();
            Event entity = repository.getOne(id);
            entity.setDate(dto.getDate());
            entity.setName(dto.getName());
            entity.setUrl(dto.getUrl());
            entity.setCity(new City(dto.getCityId(), dto.getName()));
            entity = repository.save(entity);
            return new EventDTO(entity);
        } catch (EntityNotFoundException err) {
            throw new ObjectNotFoundException("Não foi possivel atualizar o recurso!");
        }
    }
}
