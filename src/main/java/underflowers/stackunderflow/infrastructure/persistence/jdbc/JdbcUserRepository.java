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
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

            PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT * FROM users WHERE email=?");
            statement.setString(1, email);
            ResultSet res = statement.executeQuery();

            ArrayList<User> matches = new ArrayList<>();
            while(res.next()){
                User user = User.builder()
                        .firstname(res.getString("firstname"))
                        .lastname(res.getString("lastname"))
                        .email(res.getString("email"))
                        .hashedPassword(res.getString("password"))
                        .build();
                matches.add(user);
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
    public void save(User user) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(
                    "INSERT INTO users(uuid, firstname, lastname, email, password)" +
                        "VALUES(?,?,?,?,?)");
            statement.setString(1, user.getId().asString());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
