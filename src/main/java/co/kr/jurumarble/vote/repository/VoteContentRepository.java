package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.VoteContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteContentRepository extends JpaRepository<VoteContent, Long> {
}
