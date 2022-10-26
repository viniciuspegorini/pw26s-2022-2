package br.edu.utfpr.pb.pw26s.server.controller;

import br.edu.utfpr.pb.pw26s.server.model.User;
import br.edu.utfpr.pb.pw26s.server.service.UserService;
import br.edu.utfpr.pb.pw26s.server.shared.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@CrossOrigin("http://localhost:3001")
@RestController
@RequestMapping("users")
@Slf4j()
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    GenericResponse createUser(@Valid @RequestBody User user) {
        log.info("Creating new user with username: {}", user.getUsername());
        userService.save(user);
        log.info("New user created with id: {}", user.getId());
        return new GenericResponse("Registro salvo.");
    }

}
