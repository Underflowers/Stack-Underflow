package underflowers.stackunderflow.infrastructure.persistence.memory;

import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("InMemoryUserRepository")
public class InMemoryUserRepository extends InMemoryRepository<User, UserId> implements IUserRepository {
    @Override
    public Optional<User> findByEmail(String email) {

        List<User> matches = findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .limit(1)
                .collect(Collectors.toList());

        return matches.size() == 0 ? Optional.empty() : Optional.of(matches.get(0));
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int count() {
        return findAll().size();
    }
}
