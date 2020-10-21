package underflowers.stackunderflow.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void cannotCreateUserWithoutFirstname() {
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .lastname("doe")
                .email("john@doe.com")
                .password("pass")
                .build());
    }

    @Test
    void cannotCreateUserWithoutLastname() {
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .firstname("john")
                .email("john@doe.com")
                .password("pass")
                .build());
    }

    @Test
    void cannotCreateUserWithoutEmail() {
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .firstname("john")
                .lastname("doe")
                .password("pass")
                .build());
    }

    @Test
    void cannotCreateUserWithoutPassword() {
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .firstname("john")
                .lastname("john")
                .email("john@doe.com")
                .build());
    }

    @Test
    void canCreateUser() {
        assertDoesNotThrow(() -> User.builder()
                .firstname("john")
                .lastname("doe")
                .email("john@doe.com")
                .clearTextPassword("john")
                .build());
    }

    @Test
    void cannotCreateUserWhithPasswordLengthMinusThan4() {
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .firstname("john")
                .lastname("doe")
                .email("john@doe.com")
                .clearTextPassword("joe")
                .build());
    }

    @Test
    void userCanAuthenticate() {
        String clearTextPassword = "john";
        User user = User.builder()
                .id(new UserId())
                .firstname("john")
                .lastname("doe")
                .email("john@doe.com")
                .clearTextPassword(clearTextPassword)
                .build();

        assertTrue(user.authenticate(clearTextPassword));
    }

}
