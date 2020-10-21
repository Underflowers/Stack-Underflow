package underflowers.stackunderflow.domain.question.vote;

import underflowers.stackunderflow.application.question.vote.VotesQuery;
import underflowers.stackunderflow.domain.IRepository;

import java.util.Collection;
import java.util.Optional;

public interface IVoteRepository extends IRepository<Vote, VoteId> {
    Collection<Vote> find(VotesQuery query);
    Optional<Vote> findFirst(VotesQuery query);
    int count(VotesQuery query);
    void switchVote(VoteId id);
}
