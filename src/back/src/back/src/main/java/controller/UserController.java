package controller;

import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.User;
import service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService ps;

    @RequestMapping(value="/all", produces= MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<User> getAll() {
        return ps.getAll();
    }

    @RequestMapping(value = "{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") String id) {
        return ps.getUser(id);
    }

}
