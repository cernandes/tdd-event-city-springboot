package com.devsuperior.bds02.servicies;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.servicies.exceptions.DatabaseException;
import com.devsuperior.bds02.servicies.exceptions.ObjectNotFoundException;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> city = repository.findAll(Sort.by("name"));
        return city.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {
        City obj = new City();
        obj.setName(dto.getName());
        obj = repository.save(obj);
        return new CityDTO(obj);
    }


    public void delete(Long id)  {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new ObjectNotFoundException("Não foi possivel apagar o recurso!");
        } catch (DataIntegrityViolationException err) {
            throw new DatabaseException("Não foi possivel apagar o recurso!");
        }
    }
}
