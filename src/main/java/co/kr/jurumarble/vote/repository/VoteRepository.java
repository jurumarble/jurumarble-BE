package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {


}
