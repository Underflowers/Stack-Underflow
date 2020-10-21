package underflowers.stackunderflow.domain.vote;

import underflowers.stackunderflow.domain.Id;

import java.util.UUID;

public class VoteId extends Id {
    public VoteId() {
        super();
    }

    public VoteId(String id) {
        super(id);
    }

    public VoteId(UUID id) {
        super(id);
    }
}
