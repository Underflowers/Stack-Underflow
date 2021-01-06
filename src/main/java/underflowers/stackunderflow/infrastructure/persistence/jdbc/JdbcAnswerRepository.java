package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.application.question.answer.AnswersQuery;
import underflowers.stackunderflow.domain.question.answer.Answer;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                "INSERT INTO answers(uuid, content, users_uuid, questions_uuid, created_at)" +
                        "VALUES(?,?,?,?,?)");
            stmt.setString(1, answer.getId().asString());
            stmt.setString(2, answer.getContent());
            stmt.setString(3, answer.getAuthorId().asString());
            stmt.setString(4, answer.getQuestionId().asString());
            stmt.setString(5, answer.getCreatedAt().toString());

            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }

    @Override
    public void remove(AnswerId id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "DELETE FROM answers WHERE uuid=(?)"
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
    public Optional<Answer> findById(AnswerId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM answers WHERE uuid=?");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(buildAnswer(rs));
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
    public Collection<Answer> findAll() {
        LinkedList<Answer> matches = new LinkedList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM answers");
            rs = stmt.executeQuery();

            while(rs.next()){
                matches.add(buildAnswer(rs));
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

    @Override
    public int count() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) AS countEntity FROM answers");
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
    public Collection<Answer> find(AnswersQuery query) {
        LinkedList<Answer> matches = new LinkedList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();

            if(query.getId() != null) { // Answers from a specific question
                stmt = conn.prepareStatement(
                        "SELECT * FROM answers WHERE questions_uuid=? ORDER BY created_at DESC"
                );
                stmt.setString(1, query.getId().asString());
            } else if(query.getAuthUserId() != null) { // Answers from a specific user
                stmt = conn.prepareStatement(
                        "SELECT * FROM answers WHERE users_uuid=? ORDER BY created_at DESC"
                );
                stmt.setString(1, query.getAuthUserId().asString());
            } else { // All answers
                stmt = conn.prepareStatement(
                        "SELECT * FROM answers"
                );
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                matches.add(buildAnswer(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }

        return matches;
    }

    private Answer buildAnswer(ResultSet rs) throws SQLException {
        return Answer.builder()
                .id(new AnswerId(rs.getString("uuid")))
                .authorId(new UserId(rs.getString("users_uuid")))
                .questionId(new QuestionId(rs.getString("questions_uuid")))
                .content(rs.getString("content"))
                .createdAt(LocalDateTime.parse(rs.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
