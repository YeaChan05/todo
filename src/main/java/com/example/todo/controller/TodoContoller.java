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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("todo")
public class TodoContoller {
    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto){
        try {
            TodoEntity entity=TodoDTO.toEntity(dto);

            entity.setId(null);
            entity.setUserId(userId);
            List<TodoEntity> entities=service.create(entity);
            log.info(entities.toString());
            List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            log.info("Log:entitys => dtos ok!");
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping
    public ResponseEntity<?> retrieveTodo( @AuthenticationPrincipal String userId){
        List<TodoEntity> entities=service.retrieve(userId);
        List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
//    @GetMapping("/update")
//    public ResponseEntity<?>update(@RequestBody TodoDTO dto){
//        try {
//            TodoEntity entity = TodoDTO.toEntity(dto);
//            entity.setUserId("temporary-user");
//
//            Optional<TodoEntity> entites = service.update(entity);
//            List<TodoDTO> dtos = entites.stream().map(TodoDTO::new).collect(Collectors.toList());
//            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
//            return ResponseEntity.ok().body(response);
//        }catch (Exception e){
//            String error=e.getMessage();
//            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    @PutMapping
    public ResponseEntity<?> updateTodo( @AuthenticationPrincipal String userId,
                                         @RequestBody TodoDTO dto){
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);
            List<TodoEntity> entites = service.update(entity);
            List<TodoDTO> dtos = entites.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto){
        try {
            TodoEntity entity=TodoDTO.toEntity(dto);
            List<TodoEntity> entities=service.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO >builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
