package underflowers.stackunderflow.domain.vote;

import org.junit.jupiter.api.Test;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import static org.junit.jupiter.api.Assertions.*;

public class VoteTest {
    @Test
    void buildWithNoAuthorMustThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Vote.builder()
                .relatedQuestion(new QuestionId())
                .build());
    }

    @Test
    void buildWithNoRelatedQuestionOrAnswerMustThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Vote.builder()
                .author(new UserId())
                .build());
    }

    @Test
    void buildWithBothRelaredQuestionAndAnswertMustThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Vote.builder()
                .author(new UserId())
                .relatedAnswer(new AnswerId())
                .relatedQuestion(new QuestionId())
                .build());
    }

    @Test
    void buildWithOnlyRelatedQuestionOrAnswerMustWork() {
        assertDoesNotThrow(() -> Vote.builder()
                .author(new UserId())
                .relatedQuestion(new QuestionId())
                .build());

        assertDoesNotThrow(() -> Vote.builder()
                .author(new UserId())
                .relatedAnswer(new AnswerId())
                .build());
    }

    @Test
    void buildWithUpvoteIndicationMustWork() {
        assertDoesNotThrow(() -> Vote.builder()
                .author(new UserId())
                .relatedAnswer(new AnswerId())
                .isUpvote(true)
                .build());
    }


    @Test
    void deepCloneMustWorks() {
        Vote v1 = Vote.builder()
                .id(new VoteId())
                .author(new UserId())
                .relatedQuestion(new QuestionId())
                .isUpvote(true)
                .build();
        Vote v2 = v1.deepClone();

        // All contents must be same
        assertEquals(v1.getId().asString(), v2.getId().asString());
        assertEquals(v1.getAuthor(), v2.getAuthor());
        assertEquals(v1.getRelatedAnswer(), v2.getRelatedAnswer());
        assertEquals(v1.isUpvote(), v2.isUpvote());
        // Object reference must be different
        assertNotSame(v1, v2);
    }
}
