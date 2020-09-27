package underflowers.stackunderflow.infrastucture.persistence.memory;

import underflowers.stackunderflow.application.user.UsersQuery;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements IUserRepository {
    private final Map<UserId, User> store = new ConcurrentHashMap<>();


    @Override
    public Collection<User> find(UsersQuery query) {
        // TODO when UsersQuery is better defined
        return null;
    }

    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public void remove(UserId userId) {
        store.remove(userId);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        User existingUser = store.get(userId);
        if (existingUser == null) {
            return Optional.empty();
        }

        User clonedUser = existingUser.toBuilder().build();
        return Optional.of(clonedUser);

    }

    @Override
    public Collection<User> findAll() {
        return store.values().stream()
                .map(user -> user.toBuilder().build())
                .collect(Collectors.toList());
    }
}
