package underflowers.stackunderflow.application.question.vote;

import io.underflowers.underification.api.EventApiControllerApi;
import io.underflowers.underification.api.dto.Event;
import underflowers.stackunderflow.domain.question.IQuestionRepository;
import underflowers.stackunderflow.domain.question.answer.IAnswerRepository;
import underflowers.stackunderflow.domain.question.vote.IVoteRepository;
import underflowers.stackunderflow.domain.question.vote.Vote;
import underflowers.stackunderflow.domain.user.UserId;

import java.util.NoSuchElementException;

public class VoteFacade {

    private final static int GREAT_QUESTION_THRESHOLD = 10;
    private final static int BAD_QUESTION_THRESHOLD = -5;

    private IVoteRepository voteRepository;
    private IQuestionRepository questionRepository;
    private IAnswerRepository answerRepository;

    private EventApiControllerApi underificationApiController = new EventApiControllerApi();

    public VoteFacade(IVoteRepository voteRepository,
                      IQuestionRepository questionRepository,
                      IAnswerRepository answerRepository) {
        this.voteRepository = voteRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void vote(VoteCommand command) throws InvalidVoteException {
        Vote currentState = voteRepository.findFirst(VotesQuery.builder()
            .user(command.getAuthor())
            .relatedQuestion(command.getRelatedQuestion())
            .relatedAnswer(command.getRelatedAnswer())
            .build()).orElse(null);

        if (currentState != null) {
            if (currentState.isUpvote() != command.isUpvote()) {
                voteRepository.switchVote(currentState.getId());
            } else {
                voteRepository.remove(currentState.getId());
            }
        } else {
            try {
                Vote submittedVote = Vote.builder()
                        .author(command.getAuthor())
                        .relatedAnswer(command.getRelatedAnswer())
                        .relatedQuestion(command.getRelatedQuestion())
                        .isUpvote(command.isUpvote())
                        .build();
                voteRepository.save(submittedVote);


                // Trigger gamification API
                UserId evaluatedUser = command.getRelatedAnswer() == null ?
                        questionRepository.findById(command.getRelatedQuestion()).orElseThrow().getAuthorId() :
                        answerRepository.findById(command.getRelatedAnswer()).orElseThrow().getAuthorId();
                String eventType = command.isUpvote() ? "upVoted" : "downVoted";
                underificationApiController.triggerEvent(new Event().appUserId(command.getAuthor().asString())
                        .eventType("voted"));
                underificationApiController.triggerEvent(new Event().appUserId(evaluatedUser.asString())
                        .eventType(eventType));

                if (command.getRelatedQuestion() != null) {
                    int voteCount = voteRepository.count(VotesQuery.builder()
                            .relatedQuestion(command.getRelatedQuestion())
                            .build());

                    if (voteCount <= BAD_QUESTION_THRESHOLD) {
                        underificationApiController.triggerEvent(new Event().appUserId(evaluatedUser.asString())
                                .eventType("badQuestion"));
                    } else if (voteCount >= GREAT_QUESTION_THRESHOLD) {
                        underificationApiController.triggerEvent(new Event().appUserId(evaluatedUser.asString())
                                .eventType("greatQuestion"));
                    }
                }
            } catch (Exception e) {
                throw new InvalidVoteException(e.getMessage());
            }
        }
    }

    public VotesDTO getVotes(VotesQuery query) {
        return VotesDTO.builder()
                .count(voteRepository.count(VotesQuery.builder()
                        .relatedAnswer(query.getRelatedAnswer())
                        .relatedQuestion(query.getRelatedQuestion())
                        .build()))
                .isAuthUserUpvote(voteRepository.findFirst(query)
                        .map(Vote::isUpvote))
                .build();
    }
}
