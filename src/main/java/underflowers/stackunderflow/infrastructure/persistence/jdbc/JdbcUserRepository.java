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
import java.util.*;

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
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email=?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            ArrayList<User> matches = new ArrayList<>();

            while(rs.next()){
                User user = User.builder()
                        .id(new UserId(rs.getString("uuid")))
                        .firstname(rs.getString("firstname"))
                        .lastname(rs.getString("lastname"))
                        .email(rs.getString("email"))
                        .hashedPassword(rs.getString("password"))
                        .build();
                matches.add(user);
            }

            /// no matches were found or there is more than one match, something is wrong with the repository
            // TODO split into 2 checks and throw an exception if greater than 1?
            if (matches.size() != 1)
                return Optional.empty();

            return Optional.of(matches.get(0));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
        return Optional.empty();
    }

    @Override
    public int count() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) AS countEntity FROM users");
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("countEntity");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }

        return 0;
    }

    @Override
    public void save(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO users(uuid, firstname, lastname, email, password)" +
                        "VALUES(?,?,?,?,?)");
            stmt.setString(1, user.getId().asString());
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getLastname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());

            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }

    @Override
    public void remove(UserId id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "DELETE FROM users WHERE uuid=(?)"
            );
            stmt.setString(1, id.asString());
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }

    @Override
    public Optional<User> findById(UserId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE uuid=?");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(buildUser(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        LinkedList<User> matches = new LinkedList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users");
            rs = stmt.executeQuery();

            while(rs.next()){
                matches.add(buildUser(rs));
            }

            return matches;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
        return matches;
    }

    private User buildUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(new UserId(rs.getString("uuid")))
                .firstname(rs.getString("firstname"))
                .lastname(rs.getString("lastname"))
                .email(rs.getString("email"))
                .hashedPassword(rs.getString("password"))
                .build();
    }


}
