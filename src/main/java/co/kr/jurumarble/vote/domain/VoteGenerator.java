package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteGenerator {

    private final VoteRepository voteRepository;
    private final VoteContentRepository voteContentRepository;

    public Long createVote(Vote vote, VoteContent voteContent) {
        voteRepository.save(vote);
        voteContent.mappingVote(vote.getId());
        VoteContent save = voteContentRepository.save(voteContent);
        return save.getVoteId();
    }


}
