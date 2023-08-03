package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.domain.Bookmark;
import co.kr.jurumarble.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByVoteAndUser(Vote vote, User user);

    Long countByUser(User user);

}
