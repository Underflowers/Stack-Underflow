package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.application.question.QuestionsQuery;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.Question;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(buildQuery("SELECT * FROM questions", query,
                    "ORDER BY created_at DESC"));
            rs = stmt.executeQuery();

            while (rs.next())
                matches.add(buildQuestion(rs));
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
    public int count(QuestionsQuery query) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(buildQuery("SELECT COUNT(*) AS countEntity FROM questions", query, ""));
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
    public int count() {
        return count(QuestionsQuery.builder().build());
    }

    @Override
    public void save(Question question) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO questions(uuid, title, description, created_at, users_uuid)" +
                            "VALUES(?,?,?,?,?)");
            stmt.setString(1, question.getId().asString());
            stmt.setString(2, question.getTitle());
            stmt.setString(3, question.getContent());
            stmt.setString(4, question.getCreationDate().toString());
            stmt.setString(5, question.getAuthorId().asString());

            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }

    @Override
    public void remove(QuestionId id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "DELETE FROM questions WHERE uuid=(?)"
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
    public Optional<Question> findById(QuestionId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM questions WHERE uuid=?");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(buildQuestion(rs));
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
    public Collection<Question> findAll() {
        LinkedList<Question> matches = new LinkedList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM questions");
            rs = stmt.executeQuery();

            while(rs.next()){
                matches.add(buildQuestion(rs));
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

    private Question buildQuestion(ResultSet res) throws SQLException {
        return Question.builder()
                .id(new QuestionId(res.getString("uuid")))
                .authorId(new UserId(res.getString("users_uuid")))
                .title(res.getString("title"))
                .content(res.getString("description"))
                .creationDate(LocalDateTime.parse(res.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    private String buildQuery(String baseQuery, QuestionsQuery query, String commands) {
        StringBuilder q = new StringBuilder(baseQuery);
        q.append(" WHERE 1=1");

        if (query.getSearchTerm() != null) {
            q.append(" AND title LIKE '%");
            q.append(query.getSearchTerm());
            q.append("%'");
        } else if(query.getAuthorId() != null) { // Question from specific user
            q.append(" AND users_uuid='");
            q.append(query.getAuthorId().asString());
            q.append("'");
        }

        q.append(" ");
        q.append(commands);

        if (query.getLimit() != 0) {
            q.append(" LIMIT ");
            q.append(query.getLimit());
            q.append(" OFFSET ");
            q.append(query.getOffset());
        }

        return q.toString();
    }
}
