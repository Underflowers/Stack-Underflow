package underflowers.stackunderflow.infrastructure.persistence.memory;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import java.util.Optional;

public class InMemoryUserRepositoryTest {

    private static String USER_EMAIL = "test@test.com";

    private InMemoryUserRepository repo;
    private User user;
    private UserId userId = new UserId();

    // TODO: discuss if save should clone or not
    // TODO: discuss about integrity constraints (can we add the same user twice, two users with same email)

    @BeforeEach
    public void prepareRepository() {
        this.repo = new InMemoryUserRepository();
        this.user = User.builder()
                .id(userId)
                .firstname("test")
                .lastname("test")
                .email(USER_EMAIL)
                .hashedPassword("haaaaash")
                .build();
        repo.save(user);
        Assertions.assertTrue(repo.findById(userId).isPresent());
    }

    @Test
    void canFindById() {
        Assertions.assertNotSame(repo.findById(userId).get(), user);
        Assertions.assertEquals(repo.findById(userId).get(), user);
    }

    @Test
    void canRemoveFromRepository() {
        repo.remove(userId);
        Assertions.assertTrue(repo.findById(userId).isEmpty());
    }

    @Test
    void canFindAll() {
        User[] c = repo.findAll().toArray(new User[0]);
        Assertions.assertEquals(c.length, 1);
        Assertions.assertNotSame(c[0], user);
        Assertions.assertEquals(c[0], user);
    }

    @Test
    void canFindByEmail() {
        Optional<User> u = repo.findByEmail(USER_EMAIL);
        Assertions.assertTrue(u.isPresent());
        Assertions.assertNotSame(u.get(), user);
        Assertions.assertEquals(u.get(), user);
        repo.remove(userId);
        Assertions.assertTrue(repo.findByEmail(USER_EMAIL).isEmpty());
    }
}

