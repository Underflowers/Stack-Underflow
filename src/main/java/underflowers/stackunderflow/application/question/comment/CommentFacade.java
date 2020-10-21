package underflowers.stackunderflow.application.question.comment;

import underflowers.stackunderflow.domain.question.answer.AnswerId;
import underflowers.stackunderflow.domain.question.comment.Comment;
import underflowers.stackunderflow.domain.question.comment.ICommentRepository;
import underflowers.stackunderflow.domain.question.QuestionId;
import underflowers.stackunderflow.domain.user.IUserRepository;
import underflowers.stackunderflow.domain.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CommentFacade {
    private ICommentRepository repository;
    private IUserRepository userRepository;

    public CommentFacade(ICommentRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public CommentsDTO getQuestionComments(QuestionId id) {
        return CommentsDTO.builder()
                .comments(buildCommentDTOList(repository.findQuestionComments(id)))
                .build();
    }

    public CommentsDTO getAnswerComments(AnswerId id) {
        return CommentsDTO.builder()
                .comments(buildCommentDTOList(repository.findAnswerComments(id)))
                .build();
    }

    public void comment(CommentCommand command) throws InvalidCommentException {

        try {
            Comment comment = Comment.builder()
                    .authorId(command.getAuthorId())
                    .answerId(command.getAnswerId())
                    .questionId(command.getQuestionId())
                    .content(command.getContent())
                    .createdAt(LocalDateTime.now())
                    .build();
            repository.save(comment);
        } catch (Exception e) {
            throw new InvalidCommentException(e.getMessage());
        }
    }

    private List<CommentsDTO.CommentDTO> buildCommentDTOList(Collection<Comment> comments) {

        return comments.stream().map(comment -> {
                    User author = userRepository.findById(comment.getAuthorId()).get();

                    return CommentsDTO.CommentDTO.builder()
                            .uuid(comment.getId().getId())
                            .author(author.getFirstname() + " " + author.getLastname())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build();
                }
        ).collect(Collectors.toList());
    }
}
