package underflowers.stackunderflow.application.question.vote;

import underflowers.stackunderflow.domain.question.vote.IVoteRepository;
import underflowers.stackunderflow.domain.question.vote.Vote;

public class VoteFacade {

    private IVoteRepository voteRepository;

    public VoteFacade(IVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
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
