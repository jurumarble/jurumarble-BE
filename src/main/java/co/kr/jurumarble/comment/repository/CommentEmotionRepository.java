package co.kr.jurumarble.comment.repository;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.domain.CommentEmotion;
import co.kr.jurumarble.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentEmotionRepository extends JpaRepository<CommentEmotion, Long> {
    Optional<CommentEmotion> findByCommentAndUser(Comment comment, User user);


}