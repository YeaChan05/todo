package com.example.todo.controller;

import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	
	/**
	 * description : create new todo element
	 *
	 * @param user id
	 * @param dto
	 * @return response entity
	 * @author : yeachan
	 */
	@PostMapping
	public ResponseEntity<?> createTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto) {
		try {
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setId(null);
		entity.setUserId(userId);
		
		List<TodoEntity> entities = service.create(entity);
		
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * description : get everything of todo element
	 *
	 * @param user id
	 * @return response entity
	 * @author : yeachan
	 */
	@GetMapping
	public ResponseEntity<?> retrieveTodo(@AuthenticationPrincipal String userId) {
		
		List<TodoEntity> entities = service.retrieve(userId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
		
	}
	
	
	/**
	 * description : update todo element
	 *
	 * @param user id
	 * @param dto
	 * @return response entity
	 * @author : yeachan
	 */
	@PutMapping
	public ResponseEntity<?> updateTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto) {
		try {
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		entity.setUserId(userId);
		
		List<TodoEntity> entities = service.update(entity);
		
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	
	/**
	 * description : delete todo element
	 *
	 * @param user id
	 * @param dto
	 * @return response entity
	 * @author : yeachan
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(
			@AuthenticationPrincipal String userId,
			@RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			entity.setUserId(userId);

			List<TodoEntity> entities = service.delete(entity);
			
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
			} catch (Exception e) {
				String error = e.getMessage();
				ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
				return ResponseEntity.badRequest().body(response);
			}			
	
	}
}
