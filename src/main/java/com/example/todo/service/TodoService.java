package com.example.todo.service;

import com.example.todo.model.TodoEntity;
import com.example.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	/**
	 * description : create new todo element
	 *
	 * @param entity
	 * @return list
	 * @author : yeachan
	 */
	public List<TodoEntity> create(final TodoEntity entity) {
		validate(entity);
		repository.save(entity);
		return repository.findByUserId(entity.getUserId());
	}
	
	/**
	 * description : get all todo element by checking user id
	 *
	 * @param user id
	 * @return list
	 * @author : yeachan
	 */
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);
		if (repository.existsById(entity.getId())) {
			repository.save(entity);
		}
		else
			throw new RuntimeException("Unknown id");
		
		return repository.findByUserId(entity.getUserId());		
	}
	
	/**
	 * description : delete todo element by todo id
	 *
	 * @param entity
	 * @return list
	 * @author : yeachan
	 */
	public List<TodoEntity> delete(final TodoEntity entity) {
		if(repository.existsById(entity.getId()))
			repository.deleteById(entity.getId());
		else
			throw new RuntimeException("id does not exist");
		
		return repository.findByUserId(entity.getUserId());
	}
	
	/**
	 * description : validate entity
	 *
	 * @param entity
	 * @author : yeachan
	 */
	public void validate(final TodoEntity entity) {
		if(entity == null ) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}
