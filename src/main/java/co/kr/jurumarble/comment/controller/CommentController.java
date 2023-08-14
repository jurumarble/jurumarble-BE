package co.kr.jurumarble.comment.controller;

import co.kr.jurumarble.comment.dto.SearchSnackResponse;
import co.kr.jurumarble.comment.dto.request.GetCommentRequest;
import co.kr.jurumarble.comment.dto.request.CreateCommentRequest;
import co.kr.jurumarble.comment.dto.request.UpdateCommentRequest;
import co.kr.jurumarble.comment.dto.request.UpdateSnackRequest;
import co.kr.jurumarble.comment.dto.response.GetCommentResponse;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "comment", description = "comment api")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "헤더에 토큰, 파라미터에 voteId, 바디에 {parentId, content} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/votes/{voteId}/comments")
    public ResponseEntity createComment(@PathVariable Long voteId, @RequestAttribute Long userId, @RequestBody @Valid CreateCommentRequest createCommentRequest) {

        commentService.createComment(voteId, userId, createCommentRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @Operation(summary = "댓글 조회", description = "파라미터에 voteId, {age, mbti, gender, sortBy, page, size} json 형식으로 보내주시면 됩니다.")
    @GetMapping("/votes/{voteId}/comments")
    public ResponseEntity<Slice<GetCommentResponse>> getComment(@PathVariable Long voteId, @ModelAttribute GetCommentRequest getCommentRequest) {

        Slice<GetCommentResponse> getCommentResponses = commentService.getComments(voteId, getCommentRequest);

        return new ResponseEntity(getCommentResponses, HttpStatus.OK);
    }


    @Operation(summary = "댓글 수정", description = "파라미터에 voteId, commentId {content} json 형식으로 보내주시면 됩니다.")
    @PatchMapping("/votes/{voteId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long voteId, @PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest updateCommentRequest, @RequestAttribute Long userId) {

        commentService.updateComment(voteId, commentId, userId, updateCommentRequest);

        return new ResponseEntity(HttpStatus.OK);
    }


    @Operation(summary = "댓글 삭제", description = "헤더에 토큰 담고, 파라미터에 voteId, commentId 보내주시면 됩니다.")
    @DeleteMapping("/votes/{voteId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId) {

        commentService.deleteComment(voteId, commentId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "댓글 좋아요", description = "헤더에 토큰 담고, 파라미터에 voteId, commentId 보내주시면 됩니다.")
    @PostMapping("/votes/{voteId}/comments/{commentId}/likers")
    public ResponseEntity likeComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId) {

        commentService.emoteComment(voteId, commentId, userId, Emotion.LIKE);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "댓글 싫어요", description = "헤더에 토큰 담고, 파라미터에 voteId, commentId 보내주시면 됩니다")
    @PostMapping("/votes/{voteId}/comments/{commentId}/haters")
    public ResponseEntity hateComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId) {

        commentService.emoteComment(voteId, commentId, userId, Emotion.HATE);


        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "안주 추가", description = "헤더에 토큰 담고, 파라미터에 voteId, commentId 보내주시면 됩니다")
    @PatchMapping("/votes/{voteId}/comments/{commentId}/snack")
    public ResponseEntity addRestaurantToComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId, @RequestBody UpdateSnackRequest updateSnackRequest) {

        commentService.addRestaurantToComment(voteId, commentId, userId, updateSnackRequest);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "안주 검색", description = "헤더에 토큰 담고, 파라미터에 voteId, commentId 보내주시면 됩니다")
    @GetMapping("/votes/{voteId}/comments/{commentId}/snack")
    public ResponseEntity<List<SearchSnackResponse>> searchRestaurant(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam int page) {

        List<SearchSnackResponse> searchRestaurantResponses = commentService.searchSnack(voteId, commentId, userId, keyword, page);

        return new ResponseEntity(searchRestaurantResponses, HttpStatus.OK);
    }

    @Operation(summary = "안주 이미지", description = "헤더에 토큰 담고, 파라미터에 voteId, commentId 보내주시면 됩니다")
    @GetMapping("/votes/{voteId}/comments/{commentId}/snack/{contentId}")
    public ResponseEntity<List<String>> getRestaurantImage(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId, @PathVariable String contentId) {

        List<String> restaurantImage = commentService.getRestaurantImage(voteId, commentId, userId, contentId);

        return new ResponseEntity(restaurantImage, HttpStatus.OK);
    }


}