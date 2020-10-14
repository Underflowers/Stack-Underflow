package underflowers.stackunderflow.domain.user;

import org.junit.jupiter.api.Test;
import underflowers.stackunderflow.domain.question.Question;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    /*
    @Test
    void userCanAuthenticate() {
        String clearTextPassword = "john";
        User user = User.builder()
                    .id(new UserId())
                    .firstname("john")
                    .lastname("doe")
                    .email("john@doe.com")
                    .clearTextPassword(clearTextPassword, clearTextPassword)
                    .build();

        assertTrue(user.authenticate(clearTextPassword));
    }

    @Test
    void cannotCreateUserWithoutFirstname() {
        String clearTextPassword = "john";
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .id(new UserId())
                .lastname("doe")
                .email("john@doe.com")
                .clearTextPassword(clearTextPassword, clearTextPassword)
                .build());
    }

    @Test
    void cannotCreateUserWithoutLastname() {
        String clearTextPassword = "john";
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .id(new UserId())
                .firstname("John")
                .email("john@doe.com")
                .clearTextPassword(clearTextPassword, clearTextPassword)
                .build());
    }

    @Test
    void cannotCreateUserWithoutEmail() {
        String clearTextPassword = "john";
        assertThrows(IllegalArgumentException.class, () -> User.builder()
                .id(new UserId())
                .firstname("John")
                .lastname("Doe")
                .clearTextPassword(clearTextPassword, clearTextPassword)
                .build());
    }
*/
}
