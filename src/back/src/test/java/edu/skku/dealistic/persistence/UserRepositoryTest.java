package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Authority;
import edu.skku.dealistic.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        User user = User.builder()
                .id("skku_team5")
                .name("Team 5")
                .password("password")
                .organization("SKKU")
                .authority(Authority.USER)
                .build();

        userRepository.save(user);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void createUser() {
        User anotherUser = User.builder()
                .id("skku_team6")
                .name("Team 6")
                .password("password")
                .organization("SKKU")
                .authority(Authority.USER)
                .build();

        userRepository.save(anotherUser);

        List<User> users = userRepository.findAll();

        assertThat(users, CoreMatchers.hasItem(anotherUser));
    }

    @Test
    public void updateUser() {
        User user = userRepository.findById("skku_team5").get();
        user.setName("Dealistic Team");
        userRepository.save(user);

        User retrievedUser = userRepository.findById("skku_team5").get();

        assertThat(retrievedUser.getName(), CoreMatchers.is("Dealistic Team"));
    }

}