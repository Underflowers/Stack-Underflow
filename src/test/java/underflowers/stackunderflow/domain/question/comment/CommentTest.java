package underflowers.stackunderflow.domain.question.comment;

import org.junit.Before;
import org.junit.Test;
import underflowers.stackunderflow.domain.question.answer.Answer;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    private Answer answer;
    private Question question;

    @Before
    public void init() {
        question = Question.builder()
                .authorUUID(new UserId())
                .creationDate(LocalDate.now())
                .title("Question title")
                .content("Question 4 tests")
                .build();

        answer = Answer.builder()
                .authorUUID(new UserId())
                .createdAt(LocalDateTime.now())
                .content("Answer 4 tests")
                .questionUUID(question.getId())
                .build();
    }

    @Test
    public void buildWithNoQuestionOrAnswerThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Comment.builder()
                .authorId(new UserId())
                .content("Unit testing")
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    @Test
    public void buildWithQuestionAndAnswerThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Comment.builder()
                .authorId(new UserId())
                .content("Unit testing")
                .createdAt(LocalDateTime.now())
                .answerId(answer.getId())
                .questionId(question.getId())
                .build()
        );
    }

    @Test
    public void buildWithoutContentThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Comment.builder()
                .authorId(new UserId())
                .createdAt(LocalDateTime.now())
                .questionId(question.getId())
                .build());
    }

    @Test
    public void buildWithOnlyQuestionWorks() {
        assertDoesNotThrow(() -> Comment.builder()
                .authorId(new UserId())
                .content("Unit testing")
                .createdAt(LocalDateTime.now())
                .questionId(question.getId())
                .build());
    }

    @Test
    public void buildWithOnlyAnswerWorks() {
        assertDoesNotThrow(() -> Comment.builder()
                .authorId(new UserId())
                .content("Unit testing")
                .createdAt(LocalDateTime.now())
                .answerId(answer.getId())
                .build());
    }

    @Test
    public void deepCloneWorks() {
        Comment comment = Comment.builder()
                .authorId(new UserId())
                .content("Unit testing")
                .createdAt(LocalDateTime.now())
                .answerId(answer.getId())
                .build();

        Comment clone = comment.deepClone();

        assertEquals(comment.getId().asString(), clone.getId().asString());
        assertEquals(comment.getAnswerId(), clone.getAnswerId());
        assertEquals(comment.getAuthorId(), clone.getAuthorId());
        assertEquals(comment.getCreatedAt(), clone.getCreatedAt());
        assertEquals(comment.getContent(), clone.getContent());

        // since it's a clone, the reference to the object mustn't be the same
        assertNotSame(comment, clone);
    }
}
