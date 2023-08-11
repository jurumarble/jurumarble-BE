package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {
    boolean existsByVoteAndVotedUser(@Param("vote") Vote vote, @Param("votedUser") User user);

//    Long countByVote(@Param("vote") Vote vote);

//    @Query("SELECT vr FROM VoteResult vr WHERE vr.vote.id = :voteId and vr.votedUser.id = :userId")
//    Optional<VoteResult> getVoteResultByVoteIdAndUserId(@Param("voteId") Long voteId, @Param("userId") Long userId);
}
