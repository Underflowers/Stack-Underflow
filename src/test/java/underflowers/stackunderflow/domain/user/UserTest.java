package underflowers.stackunderflow.domain.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void userCanAuthenticate(){
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

}
