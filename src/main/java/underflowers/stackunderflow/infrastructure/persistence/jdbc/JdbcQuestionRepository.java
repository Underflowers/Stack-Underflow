package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@ApplicationScoped
@Named("JdbcQuestionRepository")
public class JdbcQuestionRepository implements IQuestionRepository {

    @Resource(lookup = "jdbc/StackUnderflow")
    DataSource dataSource;

    public JdbcQuestionRepository(){}

    public JdbcQuestionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Question> find(QuestionsQuery query) {
        LinkedList<Question> matches = new LinkedList<>();

        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT * FROM questions");
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Question question = Question.builder()
                        .id(new QuestionId(res.getString("uuid")))
                        .authorUUID(new UserId(res.getString("users_uuid")))
                        .title(res.getString("title"))
                        .content(res.getString("description"))
                        .creationDate(LocalDate.now()) // TODO fix me
                        .build();
                matches.add(question);
            }
        } catch (SQLException e) {
            //traitement de l'exception
        }
        return matches;
    }

    @Override
    public void save(Question question) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(
                    "INSERT INTO questions(uuid, title, description, votes, created_at, users_uuid)" +
                            "VALUES(?,?,?,?,?,?)");
            statement.setString(1, question.getId().asString());
            statement.setString(2, question.getTitle());
            statement.setString(3, question.getContent());
            statement.setInt(4, 0); // Todo : Refactor when votes implemented
            statement.setString(5, question.getCreationDate().toString());
            statement.setString(6, question.getAuthorUUID().asString());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(QuestionId id) {

    }

    @Override
    public Optional<Question> findById(QuestionId id) {
        return Optional.empty();
    }

    @Override
    public Collection<Question> findAll() {
        return null;
    }
}
