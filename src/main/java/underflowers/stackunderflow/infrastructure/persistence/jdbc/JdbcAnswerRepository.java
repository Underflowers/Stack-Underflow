package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.domain.answer.Answer;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@ApplicationScoped
@Named("JdbcAnswerRepository")
public class JdbcAnswerRepository implements IAnswerRepository {

    @Resource(lookup = "jdbc/StackUnderflow")
    DataSource dataSource;

    public JdbcAnswerRepository() {}

    public JdbcAnswerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Answer answer) {

        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(
                    "INSERT INTO answers(uuid, content, users_uuid, questions_uuid)" +
                            "VALUES(?,?,?,?)");
            statement.setString(1, answer.getId().asString());
            statement.setString(2, answer.getContent());
            statement.setString(3, answer.getAuthorUUID().asString());
            statement.setString(4, answer.getQuestionUUID().asString());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(AnswerId id) {

    }

    @Override
    public Optional<Answer> findById(AnswerId id) {
        return Optional.empty();
    }

    @Override
    public Collection<Answer> findAll() {
        return null;
    }

    @Override
    public Collection<Answer> find(QuestionId questionId) {
        LinkedList<Answer> matches = new LinkedList<>();

        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(
                    "SELECT * FROM answers WHERE questions_uuid=?"
            );
            statement.setString(1, questionId.asString());
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Answer answer = Answer.builder()
                        .id(new AnswerId(res.getString("uuid")))
                        .authorUUID(new UserId(res.getString("users_uuid")))
                        .questionUUID(questionId)
                        .content(res.getString("content"))
                        .build();
                matches.add(answer);
            }
        } catch (SQLException e) {
            //traitement de l'exception
        }
        return matches;
    }
}
