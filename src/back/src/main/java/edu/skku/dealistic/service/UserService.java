package edu.skku.dealistic.service;

import edu.skku.dealistic.model.User;
import edu.skku.dealistic.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String id, User user) {
        User retrievedUser = userRepository.getOne(id);

        retrievedUser.setName(user.getName());
        retrievedUser.setOrganization(user.getOrganization());
        retrievedUser.setProfileImage(user.getProfileImage());

        userRepository.save(retrievedUser);
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
