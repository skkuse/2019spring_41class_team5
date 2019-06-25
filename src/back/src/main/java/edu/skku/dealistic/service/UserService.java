package edu.skku.dealistic.service;

import edu.skku.dealistic.model.User;
import edu.skku.dealistic.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * User Service.
 * Main Features:
 * - CRUD User Object
 *
 * @author Junhyun Kim
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String id) {
        return userRepository.getOne(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(String id, User user) {
        User retrievedUser = userRepository.getOne(id);

        retrievedUser.setName(user.getName());
        retrievedUser.setOrganization(user.getOrganization());
        retrievedUser.setProfileImage(user.getProfileImage());

        return userRepository.save(retrievedUser);
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
