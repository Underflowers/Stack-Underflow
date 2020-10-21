package underflowers.stackunderflow.domain.question;

import org.junit.jupiter.api.Test;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    void buildWithNoAuthorMustThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Question.builder()
                .title("title")
                .content("Content")
                .build());
    }

    @Test
    void buildWithNoTitleMustThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Question.builder()
                .authorUUID(new UserId())
                .content("Content")
                .build());
    }

    @Test
    void buildWithNoContentMustThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Question.builder()
                .authorUUID(new UserId())
                .title("title")
                .build());
    }

    @Test
    void buildWithNoIdAndCreationDateMustWorks() {
        assertDoesNotThrow(() -> Question.builder()
                .authorUUID(new UserId())
                .title("title")
                .content("content")
                .build());
    }


    @Test
    void deepCloneMustWorks() {
        Question q1 = Question.builder()
                        .id(new QuestionId())
                        .title("Question title")
                        .content("Question content")
                        .creationDate(LocalDate.now())
                        .authorUUID(new UserId())
                        .build();
        Question q2 = q1.deepClone();

        // All contents must be same
        assertEquals(q1.getId().asString(), q2.getId().asString());
        assertEquals(q1.getTitle(), q2.getTitle());
        assertEquals(q1.getContent(), q2.getContent());
        assertEquals(q1.getCreationDate(), q2.getCreationDate());
        assertEquals(q1.getAuthorUUID().asString(), q2.getAuthorUUID().asString());
        // Object reference must be different
        assertNotSame(q1, q2);
    }

}
