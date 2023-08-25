package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.domain.CommentEmotion;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.repository.CommentEmotionRepository;
import co.kr.jurumarble.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentEmoteManager {
    private final CommentEmotionRepository commentEmotionRepository;

    public void doEmote(Emotion emotion, User user, Comment comment) {
        Optional<CommentEmotion> byCommentAndUser = commentEmotionRepository.findByCommentAndUser(comment, user);

        byCommentAndUser.ifPresentOrElse(
                commentEmotion -> {
                    if (emotion == commentEmotion.getEmotion()) {
                        //좋아요(싫어요)를 기존에 눌렀는데 또 눌렀을 경우 좋아요(싫어요) 취소
                        cancelEmotion(commentEmotion, comment);
                    } else {
                        //싫어요(좋아요)를 기존에 누른 상태로 좋아요(싫어요)를 누른 경우 싫어요(좋아요) 취소 후 좋아요(싫어요)로 등록
                        changeEmotion(commentEmotion, emotion, user, comment);
                    }
                },
                // 좋아요(싫어요)가 없을 경우 좋아요(싫어요) 추가
                () -> addEmotion(emotion, user, comment));
    }


    private void cancelEmotion(CommentEmotion commentEmotion, Comment comment) {
        commentEmotionRepository.delete(commentEmotion);
        comment.removeEmotion(commentEmotion);
        comment.updateLikeHateCount();
    }

    private void changeEmotion(CommentEmotion existingEmotion, Emotion newEmotion, User user, Comment comment) {
        commentEmotionRepository.delete(existingEmotion);
        comment.removeEmotion(existingEmotion);
        addEmotion(newEmotion, user, comment);
    }

    private void addEmotion(Emotion emotion, User user, Comment comment) {
        CommentEmotion newEmotion = new CommentEmotion();
        newEmotion.setEmote(emotion);
        newEmotion.mappingComment(comment);
        newEmotion.mappingUser(user);
        comment.updateLikeHateCount();
        commentEmotionRepository.save(newEmotion);
    }
}
