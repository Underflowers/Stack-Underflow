package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.application.answer.AnswersQuery;
import underflowers.stackunderflow.domain.answer.Answer;
import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
                    "INSERT INTO answers(uuid, content, users_uuid, questions_uuid, created_at)" +
                            "VALUES(?,?,?,?,?)");
            statement.setString(1, answer.getId().asString());
            statement.setString(2, answer.getContent());
            statement.setString(3, answer.getAuthorUUID().asString());
            statement.setString(4, answer.getQuestionUUID().asString());
            statement.setString(5, answer.getCreatedAt().toString());

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
    public int count() {
        try {
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS countEntity FROM answers");
            rs.next();
            return rs.getInt("countEntity");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    @Override
    public Collection<Answer> find(AnswersQuery query) {
        LinkedList<Answer> matches = new LinkedList<>();

        try {
            PreparedStatement statement;

            if(query.getQuestionId() != null) { // Answers from a specific question
                statement = dataSource.getConnection().prepareStatement(
                        "SELECT * FROM answers WHERE questions_uuid=? ORDER BY created_at DESC"
                );
                statement.setString(1, query.getQuestionId().asString());
            } else if(query.getAuthorId() != null) { // Answers from a specific user
                statement = dataSource.getConnection().prepareStatement(
                        "SELECT * FROM answers WHERE users_uuid=? ORDER BY created_at DESC"
                );
                statement.setString(1, query.getAuthorId().asString());
            } else { // All answers
                statement = dataSource.getConnection().prepareStatement(
                        "SELECT * FROM answers"
                );
            }

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Answer answer = Answer.builder()
                        .id(new AnswerId(res.getString("uuid")))
                        .authorUUID(new UserId(res.getString("users_uuid")))
                        .content(res.getString("content"))
                        .createdAt(LocalDateTime.parse(res.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build();
                matches.add(answer);
            }
        } catch (SQLException e) {
            //traitement de l'exception
        }

        return matches;
    }

}
