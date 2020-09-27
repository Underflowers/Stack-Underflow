package underflowers.stackunderflow.domain.user;

import underflowers.stackunderflow.domain.Id;

import java.util.UUID;

public class UserId extends Id {

    public UserId() {
        super();
    }

    public UserId(String id) {
        super(id);
    }

    public UserId(UUID id) {
        super(id);
    }
}
