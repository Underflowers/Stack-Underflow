package underflowers.stackunderflow.application.identitymgmt;

import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticateCommand;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticationFailedException;
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
                    .clearTextPassword(command.getClearPassword(), command.getRepeatPassword())
                    .build();

            repository.save(user);
        } catch (Exception e) {
            throw new RegistrationFailedException(e.getMessage());
        }
    }

    public AuthenticatedUserDTO authenticate(AuthenticateCommand command) throws AuthenticationFailedException {
        // check that the user exists
        User user = repository.findByEmail(command.getEmail()).orElseThrow(() -> new AuthenticationFailedException("User doesn't exist"));

        // try and authenticate the user
        if (!user.authenticate(command.getClearPassword()))
            throw new AuthenticationFailedException("Bad credentials");

        // all gucci :D we can create a new DTO and return it
        return AuthenticatedUserDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail()).build();
    }
}
