package underflowers.stackunderflow.domain.question.answers;

import org.junit.jupiter.api.Test;
import underflowers.stackunderflow.domain.question.answer.Answer;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerTest {

    @Test
    public void buildWithNoQuestionThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Answer.builder()
                .authorId(new UserId())
                .content("This is the answer.")
                .build()
        );
    }

    @Test
    public void buildWithNoAuthorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Answer.builder()
                .questionId(new QuestionId())
                .content("This is the answer.")
                .build()
        );
    }

    @Test
    public void buildWithNoContentThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Answer.builder()
                .authorId(new UserId())
                .questionId(new QuestionId())
                .build()
        );
    }

    @Test
    public void deepCloneWorks() {
        Answer answer = Answer.builder()
                .questionId(new QuestionId())
                .authorId(new UserId())
                .content("This is the answer.")
                .build();

        Answer clonedAnswer = answer.deepClone();

        assertEquals(answer.getId().asString(), clonedAnswer.getId().asString());
        assertEquals(answer.getQuestionId(), clonedAnswer.getQuestionId());
        assertEquals(answer.getAuthorId(), clonedAnswer.getAuthorId());
        assertEquals(answer.getContent(), clonedAnswer.getContent());

        // Since it's a clone, the reference to the object mustn't be the same
        assertNotSame(answer, clonedAnswer);
    }
}
