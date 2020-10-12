package underflowers.stackunderflow.domain.answer;

import underflowers.stackunderflow.domain.Id;

import java.util.UUID;

public class AnswerId extends Id {

    public AnswerId () {
        super();
    }

    public AnswerId(String id) {
        super(id);
    }

    public AnswerId(UUID id) {
        super(id);
    }
}
