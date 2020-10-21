package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.comment.Comment;
import underflowers.stackunderflow.domain.question.comment.CommentId;
import underflowers.stackunderflow.domain.question.comment.ICommentRepository;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@ApplicationScoped
@Named("JdbcCommentRepository")
public class JdbcCommentRepository implements ICommentRepository {

    @Resource(lookup = "jdbc/StackUnderflow")
    DataSource dataSource;

    public JdbcCommentRepository() {}

    public JdbcCommentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Comment> findQuestionComments(QuestionId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        LinkedList<Comment> matches = new LinkedList<>();

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "SELECT * FROM comments WHERE questions_uuid = ?");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            while (rs.next())
                // since we are getting comments related to a question, we know that the `answers_uuid` field is empty!
                matches.add(buildComment(rs, new QuestionId(rs.getString("questions_uuid")), null));
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
    public Collection<Comment> findAnswerComments(AnswerId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        LinkedList<Comment> matches = new LinkedList<>();

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "SELECT * FROM comments WHERE answers_uuid = ? ORDER BY created_at ASC");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            while (rs.next())
                // since we are getting comments related to a question, we know that the `answers_uuid` field is empty!
                matches.add(buildComment(rs, null, new AnswerId(rs.getString("answers_uuid"))));
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
    public void save(Comment comment) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO comments(uuid, content, users_uuid, questions_uuid, answers_uuid, created_at)" +
                            "VALUES(?,?,?,?,?,?)");

            String questionId = comment.getQuestionId() != null ? comment.getQuestionId().asString() : null;
            String answerId = comment.getAnswerId() != null ? comment.getAnswerId().asString() : null;

            stmt.setString(1, comment.getId().asString());
            stmt.setString(2, comment.getContent());
            stmt.setString(3, comment.getAuthorId().asString());
            stmt.setString(4, questionId);
            stmt.setString(5, answerId);
            stmt.setString(6, comment.getCreatedAt().toString());

            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }

    @Override
    public void remove(CommentId id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "DELETE FROM comments WHERE uuid=(?)"
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
    public Optional<Comment> findById(CommentId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM comments WHERE uuid=?");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("questions_uuid").isEmpty()) {
                    return Optional.of(buildComment(rs, null, new AnswerId(rs.getString("answers_uuid"))));
                } else {
                    return Optional.of(buildComment(rs, new QuestionId(rs.getString("questions_uuid")), null));
                }
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
    public Collection<Comment> findAll() {
        LinkedList<Comment> matches = new LinkedList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM comments");
            rs = stmt.executeQuery();

            while(rs.next()){
                if (rs.getString("questions_uuid").isEmpty()) {
                    matches.add(buildComment(rs, null, new AnswerId(rs.getString("answers_uuid"))));
                } else {
                    matches.add(buildComment(rs, new QuestionId(rs.getString("questions_uuid")), null));
                }
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

    private Comment buildComment(ResultSet res, QuestionId qid, AnswerId aid) throws SQLException {
        return Comment.builder()
                .id(new CommentId(res.getString("uuid")))
                .authorId(new UserId(res.getString("users_uuid")))
                .content(res.getString("content"))
                .questionId(qid)
                .answerId(aid)
                .createdAt(LocalDateTime.parse(res.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @Override
    public int count() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) AS countEntity FROM comments");
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
}
