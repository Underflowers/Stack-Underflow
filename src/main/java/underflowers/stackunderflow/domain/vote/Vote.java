package underflowers.stackunderflow.domain.vote;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

@Data
@Builder(toBuilder = true)
public class Vote implements IEntity<Vote, VoteId> {
    @Setter(AccessLevel.NONE)
    private VoteId id;

    private UserId author;
    private QuestionId relatedQuestion;
    private AnswerId relatedAnswer;
    private boolean isUpvote;

    @Override
    public Vote deepClone() {
        return this.toBuilder()
                .id(new VoteId(this.id.asString()))
                .author(author)
                .relatedQuestion(relatedQuestion)
                .relatedAnswer(relatedAnswer)
                .isUpvote(isUpvote)
                .build();
    }

    public static class VoteBuilder {
        public Vote build() {
            if (id == null) {
                id = new VoteId();
            }

            if (author == null) {
                throw new IllegalArgumentException("A vote can't be anonymous!");
            }

            if (relatedQuestion == null && relatedAnswer == null) {
                throw new IllegalArgumentException("A vote must be linked to a question OR an answer!");
            }

            if (relatedQuestion != null && relatedAnswer != null) {
                throw new IllegalArgumentException("A vote can't be linked to a question AND an answer!");
            }

            return new Vote(id, author, relatedQuestion, relatedAnswer, isUpvote);

        }

    }
}

