package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteGenerator {

    private final VoteRepository voteRepository;
    private final VoteContentRepository voteContentRepository;
    private final VoteDrinkContentRepository voteDrinkContentRepository;

    public Long createNormalVote(Vote vote, VoteContent voteContent) {
        voteRepository.save(vote);
        voteContent.mappingVote(vote.getId());
        VoteContent save = voteContentRepository.save(voteContent);
        return save.getVoteId();
    }


    public Long createDrinkVote(Vote vote, VoteDrinkContent voteDrinkContent) {
        voteRepository.save(vote);
        voteDrinkContent.mappingVote(vote.getId());
        VoteDrinkContent save = voteDrinkContentRepository.save(voteDrinkContent);
        return save.getVoteId();
    }
}
