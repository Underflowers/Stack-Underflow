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
                    "INSERT INTO questions(uuid, title, description, created_at, users_uuid)" +
                            "VALUES(?,?,?,?,?)");
            statement.setString(1, question.getId().asString());
            statement.setString(2, question.getTitle());
            statement.setString(3, question.getContent());
            statement.setString(4, question.getCreationDate().toString());
            statement.setString(5, question.getAuthorUUID().asString());

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
        // TODO Factorize
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT * FROM questions WHERE uuid=?");
            statement.setString(1, id.asString());
            ResultSet res = statement.executeQuery();

            ArrayList<Question> matches = new ArrayList<>();

            while(res.next()){
                Question question = Question.builder()
                        .id(new QuestionId(res.getString("uuid")))
                        .authorUUID(new UserId(res.getString("users_uuid")))
                        .title(res.getString("title"))
                        .content(res.getString("description"))
                        .creationDate(LocalDate.now()) // TODO fix me
                        .build();
                matches.add(question);
            }

            /// no matches were found or there is more than one match, something is wrong with the repository
            // TODO split into 2 checks and throw an exception if greater than 1?
            if (matches.size() != 1)
                return Optional.empty();

            return Optional.of(matches.get(0));

        } catch (SQLException e) {
            //traitement de l'exception
        }
        return Optional.empty();
    }

    @Override
    public Collection<Question> findAll() {
        return null;
    }
}