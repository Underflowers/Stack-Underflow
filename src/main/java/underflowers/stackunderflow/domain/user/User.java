package underflowers.stackunderflow.domain.user;

import lombok.*;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.question.QuestionId;

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

    @Override
    public User deepClone() {
        return this.toBuilder().id(new UserId(id.asString())).build();
    }

    public static class UserBuilder {
        public UserBuilder clearTextPassword(String clearTextPassword) {

            if (clearTextPassword == null || clearTextPassword.isEmpty())
                throw new java.lang.IllegalArgumentException("Password is mandatory!");

            // TODO hash password
            password = clearTextPassword;
            return this;
        }

        public User build() {
            if (id == null)
                id = new UserId();

            if (firstname == null || firstname.isEmpty())
                throw new java.lang.IllegalArgumentException("First name is mandatory!");

            if (lastname == null || lastname.isEmpty())
                throw new java.lang.IllegalArgumentException("Last name is mandatory!");

            if (email == null || email.isEmpty())
                throw new java.lang.IllegalArgumentException("Email is mandatory!");


            return new User(id, email, firstname, lastname, password);
        }
    }
}
