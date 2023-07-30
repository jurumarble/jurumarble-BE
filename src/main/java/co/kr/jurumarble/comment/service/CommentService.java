package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.domain.CommentEmotion;
import co.kr.jurumarble.comment.dto.request.CommentCreateRequest;
import co.kr.jurumarble.comment.dto.request.CommentGetRequest;
import co.kr.jurumarble.comment.dto.request.CommentUpdateRequest;
import co.kr.jurumarble.comment.dto.response.CommentGetResponse;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.repository.CommentEmotionRepository;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.exception.comment.CommentEmotionNotFoundException;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;

    private final CommentEmotionRepository commentEmotionRepository;

    public void createComment(Long voteId, Long userId, CommentCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment parentComment = checkParentComment(request);

        Comment comment = new Comment(request, parentComment, user, voteId);

        commentRepository.save(comment);

    }

    public Slice<CommentGetResponse> getComments(Long voteId, CommentGetRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        List<Comment> comments = findCommentsBySortType(voteId, request, pageable); //정렬방식에 따라 Pageable 적용하여 부모댓글 가져오기

        getChildCommentByParentComment(comments);  //부모댓글에 속한 대댓글들 다가져오기

        List<CommentGetResponse> commentGetResponse = convertToCommentGetResponseList(comments); // 댓글 목록을 매개 변수로 받아, CommentGetResponse 목록을 반환

        Slice<CommentGetResponse> slice = convertToSlice(voteId, request, pageable, commentGetResponse); // Response 리스트를 Slice 객체로 만들어주기

        return slice;

    }



    public void updateComment(Long voteId, Long commentId, Long userId, CommentUpdateRequest request) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.updateContent(request);
    }

    public void deleteComment(Long voteId, Long commentId, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        commentRepository.delete(comment);
    }


    private void getChildCommentByParentComment(List<Comment> comments) {
        List<Comment> childComments = new ArrayList<>(); //대댓글
        //대댓글 가져오기
        for (Comment parent : comments) {
            childComments.addAll(commentRepository.findByParent(parent));
        }
        comments.addAll(childComments);
    }

    private List<Comment> findCommentsBySortType(Long voteId, CommentGetRequest request, Pageable pageable) {
        List<Comment> comments;
        switch (request.getSortBy()) {
            case ByTime:
                comments = commentRepository.findNewestComments(voteId, pageable);
                break;
            case ByPopularity:
                comments = commentRepository.findHotComments(voteId, pageable);
                break;
            default:
                throw new IllegalArgumentException("적절한 정렬 방식이 아닙니다.");   //예외처리 분리해야 함
        }
        return comments;
    }

    private Comment checkParentComment(CommentCreateRequest request) {
        if (request.getParentId() == null) {
            return null;
        }
        return commentRepository.findById(request.getParentId())
                .orElseThrow(() -> new CommentNotFoundException());
    }

    private List<CommentGetResponse> convertToCommentGetResponseList(List<Comment> parentComments) {
        List<CommentGetResponse> commentGetResponse = new ArrayList<>();
        Map<Long, CommentGetResponse> map = new HashMap<>();

        for (Comment comment : parentComments) {
            CommentGetResponse response = new CommentGetResponse(comment);
            map.put(response.getId(), response);

            if (response.getParentId() != null) {
                map.get(response.getParentId()).getChildren().add(response);
            } else {
                commentGetResponse.add(response);
            }
        }
        return commentGetResponse;
    }

    private Slice<CommentGetResponse> convertToSlice(Long voteId, CommentGetRequest request, Pageable pageable, List<CommentGetResponse> commentGetResponse) {
        int countComments = commentRepository.countByVoteIdAndParentIsNull(voteId);   //투표에 속한 부모 댓글수 전부 가져오기
        int lastPageNumber = (int) Math.ceil((double) countComments / request.getSize());
        boolean hasNext = request.getPage() < lastPageNumber - 1;

        Slice<CommentGetResponse> slice = new SliceImpl<>(commentGetResponse, pageable, hasNext);
        return slice;
    }

    public void emoteComment(Long voteId, Long commentId, Long userId, Emotion emotion) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        doEmote(emotion, user, comment);
    }

    private void doEmote(Emotion emotion, User user, Comment comment) {  //책임에 맞게 리팩토링 해야함
        Optional<CommentEmotion> byCommentAndUser = commentEmotionRepository.findByCommentAndUser(comment, user);

        byCommentAndUser.ifPresentOrElse(commentEmotion -> {

                    //좋아요(싫어요)를 기존에 눌렀는데 또 눌렀을 경우 좋아요(싫어요) 취소
                    if (emotion == commentEmotion.getEmotion()) {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);
                        comment.updateLikeHateCount();
                    }
                    //싫어요(좋아요)를 기존에 누른 상태로 좋아요(싫어요)를 누른 경우 싫어요(좋아요) 취소 후 좋아요(싫어요)로 등록
                    else {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);

                        CommentEmotion changeEmotion = new CommentEmotion();

                        changeEmotion.setEmote(emotion);
                        changeEmotion.mappingComment(comment);
                        changeEmotion.mappingUser(user);
                        comment.updateLikeHateCount();

                        commentEmotionRepository.save(changeEmotion);
                    }

                },
                // 좋아요(싫어요)가 없을 경우 좋아요(싫어요) 추가
                () -> {
                    CommentEmotion commentEmotion = new CommentEmotion();

                    commentEmotion.setEmote(emotion);
                    commentEmotion.mappingComment(comment);
                    commentEmotion.mappingUser(user);
                    comment.updateLikeHateCount();

                    commentEmotionRepository.save(commentEmotion);
                });
    }

}

