package underflowers.stackunderflow.domain.user;

import underflowers.stackunderflow.application.identitymgmt.authenticate.AuthenticatedUserDTO;
import underflowers.stackunderflow.domain.IRepository;

import java.util.Collection;
import java.util.Optional;

public interface IUserRepository extends IRepository<User, UserId> {
    Optional<User> findByEmail(String email);
    int update(User user);
}
