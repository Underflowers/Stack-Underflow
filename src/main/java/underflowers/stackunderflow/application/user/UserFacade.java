package underflowers.stackunderflow.application.user;

import underflowers.stackunderflow.domain.user.IUserRepository;

public class UserFacade {

    private IUserRepository userRepository;

    public UserFacade(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // TODO (video 31:00)
}
