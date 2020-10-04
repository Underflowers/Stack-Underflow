package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.IEntity;
import underflowers.stackunderflow.domain.IRepository;
import underflowers.stackunderflow.domain.Id;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
@Named("JdbcUserRepository")
public class JdbcUserRepository implements IUserRepository {

    @Resource(lookup = "jdbc/StackUnderflow")
    DataSource dataSource;

    public JdbcUserRepository(){}

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Statement stmt = dataSource.getConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users");
        } catch (SQLException e) {
            //traitement de l'exception
        }
        return Optional.empty();
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void remove(UserId id) {

    }

    @Override
    public Optional<User> findById(UserId id) {
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }
}
