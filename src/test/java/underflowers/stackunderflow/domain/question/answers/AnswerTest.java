package underflowers.stackunderflow.domain.question.answers;

import org.junit.Test;
import underflowers.stackunderflow.domain.question.answer.Answer;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerTest {

    @org.junit.Test
    public void buildWithNoQuestionThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Answer.builder()
                .authorUUID(new UserId())
                .content("This is the answer.")
                .build()
        );
    }

    @org.junit.Test
    public void buildWithNoAuthorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Answer.builder()
                .questionUUID(new QuestionId())
                .content("This is the answer.")
                .build()
        );
    }

    @org.junit.Test
    public void buildWithNoContentThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Answer.builder()
                .authorUUID(new UserId())
                .questionUUID(new QuestionId())
                .build()
        );
    }

    @Test
    public void deepCloneWorks() {
        Answer answer = Answer.builder()
                .questionUUID(new QuestionId())
                .authorUUID(new UserId())
                .content("This is the answer.")
                .build();

        Answer clonedAnswer = answer.deepClone();

        assertEquals(answer.getId().asString(), clonedAnswer.getId().asString());
        assertEquals(answer.getQuestionUUID(), clonedAnswer.getQuestionUUID());
        assertEquals(answer.getAuthorUUID(), clonedAnswer.getAuthorUUID());
        assertEquals(answer.getContent(), clonedAnswer.getContent());

        // Since it's a clone, the reference to the object mustn't be the same
        assertNotSame(answer, clonedAnswer);
    }
}
