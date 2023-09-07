package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteDrinkContentRepository extends JpaRepository<VoteDrinkContent, Long> {

    Optional<VoteDrinkContent> findByVoteId(Long voteId);
}
