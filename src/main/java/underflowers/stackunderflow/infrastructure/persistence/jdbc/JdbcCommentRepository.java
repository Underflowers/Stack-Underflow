package underflowers.stackunderflow.infrastructure.persistence.jdbc;

import underflowers.stackunderflow.domain.answer.AnswerId;
import underflowers.stackunderflow.domain.comment.Comment;
import underflowers.stackunderflow.domain.comment.CommentId;
import underflowers.stackunderflow.domain.comment.ICommentRepository;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.UserId;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
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
        LinkedList<Comment> matches = new LinkedList<>();

        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT * FROM comments WHERE questions_uuid = ?");
            statement.setString(1, id.asString());
            ResultSet res = statement.executeQuery();

            while (res.next())
                // since we are getting comments related to a question, we know that the `answers_uuid` field is empty!
                matches.add(buildComment(res, new QuestionId(res.getString("questions_uuid")), null));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return matches;
    }

    @Override
    public Collection<Comment> findAnswerComments(AnswerId id) {

        LinkedList<Comment> matches = new LinkedList<>();

        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement("SELECT * FROM comments WHERE answers_uuid = ?");
            statement.setString(1, id.asString());
            ResultSet res = statement.executeQuery();

            while (res.next())
                // since we are getting comments related to an answer, we know that the `questions_uuid` field is empty!
                matches.add(buildComment(res, null, new AnswerId(res.getString("answers_uuid"))));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return matches;
    }

    @Override
    public void save(Comment comment) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(
                    "INSERT INTO comments(uuid, content, users_uuid, questions_uuid, answers_uuid, created_at)" +
                            "VALUES(?,?,?,?,?,?)");

            String questionId = comment.getQuestionId() != null ? comment.getQuestionId().asString() : null;
            String answerId = comment.getAnswerId() != null ? comment.getAnswerId().asString() : null;

            statement.setString(1, comment.getId().asString());
            statement.setString(2, comment.getContent());
            statement.setString(3, comment.getAuthorId().asString());
            statement.setString(4, questionId);
            statement.setString(5, answerId);
            statement.setString(6, comment.getCreatedAt().toString());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(CommentId id) {

    }

    @Override
    public Optional<Comment> findById(CommentId id) {
        return Optional.empty();
    }

    @Override
    public Collection<Comment> findAll() {
        return null;
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
}
