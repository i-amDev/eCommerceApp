package com.project.user_service.controller;

import com.project.user_service.dto.UserRequest;
import com.project.user_service.dto.UserResponse;
import com.project.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        log.info("Request received for user : {}", id);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.ok("User added successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        boolean updated = userService.updateUser(id, userRequest);
        if (updated) return ResponseEntity.ok("User updated successfully.");
        return ResponseEntity.notFound().build();
    }
}
