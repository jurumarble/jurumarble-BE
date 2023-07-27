package co.kr.jurumarble.comment.repository;

import co.kr.jurumarble.comment.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query(value = "SELECT c FROM Comment c WHERE c.voteId = :voteId AND c.parent IS NULL ORDER BY (c.likeCount + c.hateCount) DESC , c.createdDate DESC")
    List<Comment> findHotComments(@Param("voteId") Long voteId, Pageable pageable);                    //인기순 댓글 불러오기

    @Query(value = "SELECT c FROM Comment c WHERE c.voteId = :voteId AND c.parent IS NULL Order By c.createdDate DESC")
    List<Comment> findNewestComments(@Param("voteId") Long voteId, Pageable pageable);                 //최신순 댓글 불러오기

    int countByVoteIdAndParentIsNull(Long voteId);

    List<Comment> findByParent(Comment parent);
}
