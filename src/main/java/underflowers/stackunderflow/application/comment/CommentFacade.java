package underflowers.stackunderflow.application.comment;

import underflowers.stackunderflow.domain.comment.Comment;
import underflowers.stackunderflow.domain.comment.ICommentRepository;

public class CommentFacade {
    ICommentRepository repository;

    public CommentFacade(ICommentRepository repository) {
        this.repository = repository;
    }

    public void comment(CommentCommand command) throws InvalidCommentException {

        try {
            Comment comment = Comment.builder()
                    .authorId(command.getAuthorId())
                    .answerId(command.getAnswerId())
                    .questionId(command.getQuestionId())
                    .content(command.getContent())
                    .build();
            repository.save(comment);
        } catch (Exception e) {
            throw new InvalidCommentException(e.getMessage());
        }
    }
}
