package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
    boolean existsByVoteIdAndVotedUserId(@Param("voteId") Long voteId, @Param("votedUserId") Long votedUserId);

//    Long countByVote(@Param("vote") Vote vote);

    @Query("SELECT vr FROM VoteResult vr WHERE vr.voteId = :voteId and vr.votedUserId = :userId")
    Optional<VoteResult> getVoteResultByVoteIdAndUserId(@Param("voteId") Long voteId, @Param("userId") Long userId);
}
