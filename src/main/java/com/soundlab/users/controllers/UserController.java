package com.soundlab.users.controllers;

import com.soundlab.users.core.User;
import com.soundlab.users.core.dto.UserRequestDTO;
import com.soundlab.users.core.dto.UserResponseDTO;
import com.soundlab.users.exceptions.UserNotFoundException;
import com.soundlab.users.services.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Tag(
    name = "Domain: User",
    description = "Gerencia o domínio 'User'."
)
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(OK)
    List<UserResponseDTO> findAll() {
        return userService.findAll(UserResponseDTO.class);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(OK)
    UserResponseDTO getById(@PathVariable long id) {
        return userService
            .findById(id, UserResponseDTO.class)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @RequestMapping(method = RequestMethod.POST, produces = { "application/json"})
    ResponseEntity<String> save(@RequestBody UserRequestDTO data) {
        User createdUser = userService.save(data);
        String location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdUser.getId())
            .toUriString();
        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, location).build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(NO_CONTENT)
    void update(@PathVariable Long id, @RequestBody UserRequestDTO data) {
        userService.update(id, data);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable long id) {
        userService.delete(id);
    }
}
