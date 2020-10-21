package underflowers.stackunderflow.infrastructure.persistence.memory;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import java.time.LocalDate;

public class InMemoryQuestionRepositoryTest {

    private InMemoryQuestionRepository repo;
    private Question question;
    private QuestionId questionId = new QuestionId();

    @BeforeEach
    public void prepareRepository() {
        this.repo = new InMemoryQuestionRepository();
        this.question = Question.builder()
                .id(questionId)
                .title("test")
                .content("test")
                .authorUUID(new UserId())
                .creationDate(LocalDate.now())
                .build();
        repo.save(question);
        Assertions.assertTrue(repo.findById(questionId).isPresent());
    }

    @Test
    void canFindById() {
        Assertions.assertNotSame(repo.findById(questionId).get(), question);
        Assertions.assertEquals(repo.findById(questionId).get(), question);
    }

    @Test
    void canRemoveFromRepository() {
        repo.remove(questionId);
        Assertions.assertTrue(repo.findById(questionId).isEmpty());
    }

    @Test
    void canFindAll() {
        Question[] c = repo.findAll().toArray(new Question[0]);
        Assertions.assertEquals(c.length, 1);
        Assertions.assertNotSame(c[0], question);
        Assertions.assertEquals(c[0], question);
    }
}

