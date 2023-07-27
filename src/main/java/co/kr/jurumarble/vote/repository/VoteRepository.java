package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.dto.FindVoteListData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {


    @Query("SELECT v FROM Vote v " +
            "left join FETCH v.voteResultList vr " +
            "join FETCH v.postedUser pu " +
            "GROUP BY v.id, vr.id " +
            "order by count(vr.vote.id) DESC")
    Slice<Vote> findWithVoteResult(PageRequest pageRequest);

    @Query("SELECT new co.kr.jurumarble.vote.dto.FindVoteListData(v,(SELECT count(vr.vote) FROM VoteResult vr WHERE vr.vote = v))" +
            "FROM Vote v")
    Slice<FindVoteListData> findSliceBy(Pageable pageable);
}
