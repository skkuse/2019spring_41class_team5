package service;

import model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//how to connect to data base? this is just an example
//JPA integration
@Service
public class UserService {

    ArrayList<User> users = new ArrayList<User>();
    //this is only for testing
    public UserService() {
        User p = new User();
        p.setID("Team5");
        p.setName("Dealisitc");
        users.add(p);


    }
    public User getUser(String id) {
        for(User user:users) {
            if(user.getID().equalsIgnoreCase(id)) return user;
        }
        return null;
    }
    public ArrayList<User> getAll() {
        return users;
    }
}
