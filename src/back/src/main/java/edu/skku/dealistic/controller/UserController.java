package edu.skku.dealistic.controller;

import edu.skku.dealistic.payload.ApiResponse;
import edu.skku.dealistic.model.User;
import edu.skku.dealistic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * User Controller.
 * Main Features:
 * - CRUD users
 *
 * @author Junhyun Kim
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PreAuthorize("#id == principal.username or hasAuthority('MANAGER')")
    @PatchMapping("/{id}")
    public ApiResponse updateUser(@PathVariable String id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ApiResponse.SUCCESS;
    }

    @PreAuthorize("#id == principal.username or hasAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.SUCCESS;
    }
}
