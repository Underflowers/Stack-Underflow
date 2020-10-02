package underflowers.stackunderflow.domain.user;

import underflowers.stackunderflow.domain.IRepository;

import java.util.Collection;
import java.util.Optional;

public interface IUserRepository extends IRepository<User, UserId> {
    public Optional<User> findByEmail(String email);
}
