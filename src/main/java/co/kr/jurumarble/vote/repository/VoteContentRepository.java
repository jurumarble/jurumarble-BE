package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.VoteContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteContentRepository extends JpaRepository<VoteContent, Long> {

    Optional<VoteContent> findByVoteId(Long voteId);


}
