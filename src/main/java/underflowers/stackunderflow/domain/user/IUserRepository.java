package underflowers.stackunderflow.domain.user;

import underflowers.stackunderflow.domain.IRepository;

import java.util.Collection;

public interface IUserRepository extends IRepository<User, UserId> {
    public Collection<User> findByUsername(String username);
}
