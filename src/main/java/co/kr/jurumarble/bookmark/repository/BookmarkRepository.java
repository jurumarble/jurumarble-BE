package co.kr.jurumarble.bookmark.repository;

import co.kr.jurumarble.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserIdAndVoteId(Long userId, Long voteId);

}
