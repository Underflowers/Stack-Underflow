package underflowers.stackunderflow.infrastucture.persistence.memory;

import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.IRepository;
import underflowers.stackunderflow.domain.Id;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class InMemoryRepository<ENTITY extends IEntity<ENTITY, ID>, ID extends Id> implements IRepository<ENTITY, ID>  {
    private final Map<ID, ENTITY> store = new ConcurrentHashMap<>();

    @Override
    public void save(ENTITY entity) {
        store.put(entity.getId(), entity);
    }

    @Override
    public void remove(ID id) {
        store.remove(id);
    }

    @Override
    public Optional<ENTITY> findById(ID id) {
        ENTITY entity = store.get(id);

        if (entity == null)
            return Optional.empty();

        return Optional.of(entity.deepClone());
    }

    @Override
    public Collection<ENTITY> findAll() {
        return store.values().stream().map(IEntity::deepClone).collect(Collectors.toList());
    }
}
