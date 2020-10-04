package underflowers.stackunderflow.infrastructure.persistence.memory;

import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryUserRepository extends InMemoryRepository<User, UserId> implements IUserRepository {
    @Override
    public Optional<User> findByEmail(String email) {

        List<User> matches = findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .collect(Collectors.toList());

        // no matches were found or there is more than one match, something is wrong with the repository
        // TODO split into 2 checks and throw an exception if greater than 1?
        if (matches.size() != 1)
            return Optional.empty();

        return Optional.of(matches.get(0).deepClone());
    }
}
