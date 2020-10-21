package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.application.question.vote.VotesQuery;
import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;
import underflowers.stackunderflow.domain.question.vote.IVoteRepository;
import underflowers.stackunderflow.domain.question.vote.Vote;
import underflowers.stackunderflow.domain.question.vote.VoteId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@ApplicationScoped
@Named("JdbcVoteRepository")
public class JdbcVoteRepository implements IVoteRepository {
    @Resource(lookup = "jdbc/StackUnderflow")
    DataSource dataSource;

    public JdbcVoteRepository(){}

    public JdbcVoteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Collection<Vote> find(VotesQuery query) {
        LinkedList<Vote> matches = new LinkedList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(buildQuery(
                "SELECT * FROM votes WHERE 1=1", query
            ));
            rs = stmt.executeQuery();

            while (rs.next()) {
                matches.add(buildVote(rs));
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

    @Override
    public Optional<Vote> findFirst(VotesQuery query) {
        Collection<Vote> matches = find(query);

        if (matches.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(matches.iterator().next());
    }

    @Override
    public int count(VotesQuery query) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(buildQuery(
                    "SELECT SUM(isUpvote * 2 - 1) AS countEntity FROM votes WHERE 1=1", query
            ));
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
    public void switchVote(VoteId id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "UPDATE votes SET isUpvote = NOT isUpvote WHERE uuid=(?)"
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
    public void save(Vote entity) {
        String field;
        String fieldContent;

        if (entity.getRelatedAnswer() != null) {
            field = "answers_uuid";
            fieldContent = entity.getRelatedAnswer().asString();
        } else {
            field = "questions_uuid";
            fieldContent = entity.getRelatedQuestion().asString();
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "INSERT INTO votes(uuid, users_uuid, isUpvote, " + field + ")" +
                            "VALUES(?,?,?,?)");
            stmt.setString(1, entity.getId().asString());
            stmt.setString(2, entity.getAuthor().asString());
            stmt.setBoolean(3, entity.isUpvote());
            stmt.setString(4, fieldContent);

            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }

    @Override
    public void remove(VoteId id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(
                    "DELETE FROM votes WHERE uuid=(?)"
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
    public Optional<Vote> findById(VoteId id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM votes WHERE uuid=?");
            stmt.setString(1, id.asString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(buildVote(rs));
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
    public Collection<Vote> findAll() {
        return find(VotesQuery.builder().build());
    }

    @Override
    public int count() {
        return count(VotesQuery.builder().build());
    }

    private Vote buildVote(ResultSet res) throws SQLException {
        return Vote.builder()
                .id(new VoteId(res.getString("uuid")))
                .author(new UserId(res.getString("users_uuid")))
                .relatedQuestion(res.getString("questions_uuid") == null ? null
                        : new QuestionId(res.getString("questions_uuid")))
                .relatedAnswer(res.getString("answers_uuid") == null ? null
                        : new AnswerId(res.getString("answers_uuid")))
                .isUpvote(res.getBoolean("isUpvote"))
                .build();
    }

    private String buildQuery(String baseQuery, VotesQuery query) {
        StringBuilder request = new StringBuilder(baseQuery);

        if (query.getUser() != null) {
            request.append(" AND users_uuid='");
            request.append(query.getUser().asString());
            request.append("'");
        }

        if (query.getRelatedQuestion() != null) {
            request.append(" AND questions_uuid='");
            request.append(query.getRelatedQuestion().asString());
            request.append("'");
        }

        if (query.getRelatedAnswer() != null) {
            request.append(" AND answers_uuid='");
            request.append(query.getRelatedAnswer().asString());
            request.append("'");
        }

        return request.toString();
    }
}
