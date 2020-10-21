package underflowers.stackunderflow.application.identitymgmt;

import org.mindrot.jbcrypt.BCrypt;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
import underflowers.stackunderflow.application.identitymgmt.profile.EditUserCommand;
import underflowers.stackunderflow.application.identitymgmt.profile.EditUserFailedException;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationCommand;
import underflowers.stackunderflow.application.identitymgmt.registration.RegistrationFailedException;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;

public class IdentityManagementFacade {
    IUserRepository repository;

    public IdentityManagementFacade(IUserRepository repository) {
        this.repository = repository;
    }

    public void register(RegistrationCommand command) throws RegistrationFailedException {
        // check if the user already exists
        if (repository.findByEmail(command.getEmail()).orElse(null) != null)
            throw new RegistrationFailedException("Email address already in use!");

        // all gucci :D we can try and create a new user and save it
        // Note: we need to do a try catch since the UserBuilder can throw an exception!
        try {
            User user = User.builder()
                    .firstname(command.getFirstname())
                    .lastname(command.getLastname())
                    .email(command.getEmail())
                    .clearTextPassword(command.getClearPassword())
                    .build();

            repository.save(user);
        } catch (Exception e) {
            throw new RegistrationFailedException(e.getMessage());
        }
    }

    public AuthenticatedUserDTO authenticate(AuthenticateCommand command) throws AuthenticationFailedException {
        // get the user
        User user = repository.findByEmail(command.getEmail()).orElse(null);//.orElseThrow(() -> new AuthenticationFailedException("User doesn't exist"));

        // check if the user was found and then try and authenticate her/him
        if (user == null || !user.authenticate(command.getClearPassword()))
            throw new AuthenticationFailedException("Bad credentials");

        // all gucci :D we can create a new DTO and return it
        return AuthenticatedUserDTO.builder()
                .uuid(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail()).build();
    }

    public int countUsers() {
        return this.repository.count();
    }

    public AuthenticatedUserDTO editUser(EditUserCommand editUserCommand) throws EditUserFailedException {
        // User want to change his email => check email Unique
        if(!editUserCommand.getOldEmail().equals(editUserCommand.getNewEmail())){
            // check if the user already exists
            if (repository.findByEmail(editUserCommand.getNewEmail()).orElse(null) != null)
                throw new EditUserFailedException("Email address already in use!");
        }

        // Fetch the old user (must exists)
        User user = repository.findByEmail(editUserCommand.getOldEmail()).orElse(null);

        boolean changePassword = false;
        // Password change process only if oldPassword is not empty (user typed something)
        if(!editUserCommand.getClearOldPassword().isEmpty()){
            // Check if the old password typed are the real old password
            if(!BCrypt.checkpw(editUserCommand.getClearOldPassword(), user.getPassword())){
                throw new EditUserFailedException("Old password wrong");
            }

            changePassword = true;
        }

        // all gucci we can try to update the user
        User updatedUser = null;
        // Note: we need to do a try catch since the UserBuilder can throw an exception!
        try {
            if(changePassword){
                // Build user with new clear password
                updatedUser = User.builder()
                        .id(user.getId())
                        .firstname(editUserCommand.getNewFirstname())
                        .lastname(editUserCommand.getNewLastname())
                        .email(editUserCommand.getNewEmail())
                        .clearTextPassword(editUserCommand.getClearNewPassword())
                        .build();
            }else{
                // Build user with old hashed password (no password change)
                updatedUser = User.builder()
                        .id(user.getId())
                        .firstname(editUserCommand.getNewFirstname())
                        .lastname(editUserCommand.getNewLastname())
                        .email(editUserCommand.getNewEmail())
                        .hashedPassword(user.getPassword())
                        .build();
            }
        } catch (Exception e) {
            throw new EditUserFailedException(e.getMessage());
        }

        // 1 affected row -> successfully updated
        if(repository.update(updatedUser) == 1) {
             return AuthenticatedUserDTO.builder()
                    .uuid(user.getId())
                    .firstname(editUserCommand.getNewFirstname())
                    .lastname(editUserCommand.getNewLastname())
                    .email(editUserCommand.getNewEmail())
                    .build();
        }
        // no affected row, return old User values
        else {
            return AuthenticatedUserDTO.builder()
                    .uuid(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .build();
        }
    }
}
