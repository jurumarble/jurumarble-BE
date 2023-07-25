package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {


}
