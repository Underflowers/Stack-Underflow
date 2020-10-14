package underflowers.stackunderflow.domain.user;

import lombok.*;
import org.mindrot.jbcrypt.*;

import underflowers.stackunderflow.domain.IEntity;

@Data
@Builder (toBuilder = true)
public class User implements IEntity<User, UserId> {

    @Setter(AccessLevel.NONE)
    private UserId id;

    private String firstname;
    private String lastname;
    private String email;

    @EqualsAndHashCode.Exclude
    private String password;

    public boolean authenticate(String clearPassword) {
        return BCrypt.checkpw(clearPassword, password);
    }

    @Override
    public User deepClone() {
        return this.toBuilder().id(new UserId(id.asString())).build();
    }

    public static class UserBuilder {

        // here we check password policy
        public UserBuilder clearTextPassword(String clearTextPassword) {

            // Password must exists
            if (clearTextPassword == null || clearTextPassword.isEmpty())
                throw new java.lang.IllegalArgumentException("Password must not be empty");
            // Password must have a minimum length of 4
            if (clearTextPassword.length() < 4)
                throw new java.lang.IllegalArgumentException("Password must have a minimum length of 4");

            password = BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
            return this;
        }

        public UserBuilder hashedPassword(String hashedPassword) {
            this.password = hashedPassword;
            return this;
        }

        public User build() {
            if (id == null)
                id = new UserId();

            if(password == null || password.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Password is mandatory !");
            }

            if (firstname == null || firstname.isEmpty())
                throw new java.lang.IllegalArgumentException("First name is mandatory!");

            if (lastname == null || lastname.isEmpty())
                throw new java.lang.IllegalArgumentException("Last name is mandatory!");

            if (email == null || email.isEmpty())
                throw new java.lang.IllegalArgumentException("Email is mandatory!");


            return new User(id, firstname, lastname, email, password);
        }
    }
}
