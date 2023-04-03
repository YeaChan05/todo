package com.example.todo.service;

import com.example.todo.model.TodoEntity;
import com.example.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public Optional<TodoEntity>create(final TodoEntity entity){
        validate(entity);
        repository.save(entity);
        return repository.findById(entity.getId());
    }

    private void validate(final TodoEntity entity) {
        if(entity==null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId()==null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

}
