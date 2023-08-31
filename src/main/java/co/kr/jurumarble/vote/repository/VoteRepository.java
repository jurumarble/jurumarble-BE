package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>, VoteEntityRepository {
    Slice<Vote> findAllByTitleAfter(@Param("keyword") String keyword, Pageable pageable);
}
