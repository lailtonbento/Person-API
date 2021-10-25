package com.personapi.personapi.services;

import com.personapi.personapi.dto.request.PersonDTO;
import com.personapi.personapi.dto.response.MessageResponseDTO;
import com.personapi.personapi.exception.PersonNotFoundException;
import com.personapi.personapi.mapper.PersonMapper;
import com.personapi.personapi.model.Person;
import com.personapi.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()){
            throw new PersonNotFoundException(id);
        }
        return personMapper.toDTO(optionalPerson.get());
    }
    public MessageResponseDTO update (Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person updatedPerson = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(updatedPerson);
        MessageResponseDTO messageResponse = createMessageResponse("Person successfully updated with ID", savedPerson.getId());
        return messageResponse;

    }

    public void delete(Long id) throws PersonNotFoundException{
        verifyIfExists(id);
        personRepository.deleteById(id);

    }

    public Person verifyIfExists (Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(()-> new PersonNotFoundException(id));
    }

    public MessageResponseDTO createMessageResponse (String s, Long id){
        return MessageResponseDTO.builder()
                .message(s + id)
                .build();
    }
}
