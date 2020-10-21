package underflowers.stackunderflow.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Null;
import java.util.UUID;

@EqualsAndHashCode
public abstract class Id {
    @Getter
    private UUID id;

    public Id() {
        id = UUID.randomUUID();
    }

    public Id(String id) {
        this.id = UUID.fromString(id);
    }

    public Id(UUID id) {
        if (id == null) {
            throw new NullPointerException();
        }

        this.id = id;
    }

    public String asString() {
        return id.toString();
    }
}
