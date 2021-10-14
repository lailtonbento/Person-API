package com.personapi.personapi.services;

import com.personapi.personapi.dto.request.PersonDTO;
import com.personapi.personapi.dto.response.MessageResponseDTO;
import com.personapi.personapi.mapper.PersonMapper;
import com.personapi.personapi.model.Person;
import com.personapi.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Person personSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personSave   );
        return MessageResponseDTO
                .builder()
                .message("Created Person with ID " + savedPerson.getId())
                .build();
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }
}
