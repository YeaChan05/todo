package com.example.todo.controller;

import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoContoller {
    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try {
            log.info("Log:createTodo entrance");
            TodoEntity entity=TodoDTO.toEntity(dto);
            log.info("Log:dto => entity ok!");
            entity.setUserId("temporary-userid");
            Optional<TodoEntity> entities=service.create(entity);
            log.info("Log:service.create ok!");

            List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            log.info("Log:entites => dtos ok!");

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            log.info("Log:responsedto ok!");

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId="temporary-user";
        List<TodoEntity> entities=service.retrieve(temporaryUserId);
        List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/update")
    public ResponseEntity<?>update(@RequestBody TodoDTO dto){
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId("temporary-user");

            Optional<TodoEntity> entites = service.update(entity);
            List<TodoDTO> dtos = entites.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);

            Optional<TodoEntity> entites = service.updateTodo(entity);
            List<TodoDTO> dtos = entites.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
