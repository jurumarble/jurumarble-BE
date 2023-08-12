package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
