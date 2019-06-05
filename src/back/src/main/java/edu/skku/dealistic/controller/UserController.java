package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.ApiResponse;
import edu.skku.dealistic.model.User;
import edu.skku.dealistic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public ApiResponse createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ApiResponse("success!");
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PatchMapping("{id}")
    public ApiResponse updateUser(@PathVariable String id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ApiResponse("success!");
    }

    @DeleteMapping("{id}")
    public ApiResponse deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ApiResponse("success!");
    }
}
