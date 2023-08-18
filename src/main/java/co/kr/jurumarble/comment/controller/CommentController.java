package co.kr.jurumarble.comment.controller;

import co.kr.jurumarble.comment.dto.SearchRestaurantResponse;
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

    @Operation(summary = "댓글 생성", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'를, 요청 바디에 'parentId'(댓글의 부모 아이디. 대댓글일 경우 부모 댓글 아이디, 없으면 빈 문자열)와 'content'(댓글 내용)을 JSON 형식으로 보내주세요.")
    @PostMapping("/votes/{voteId}/comments")
    public ResponseEntity createComment(@PathVariable Long voteId, @RequestAttribute Long userId, @RequestBody @Valid CreateCommentRequest createCommentRequest) {

        commentService.createComment(voteId, userId, createCommentRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @Operation(summary = "댓글 조회", description = "헤더에 토큰(`Authorization`)을 포함하고, URL 파라미터에 'voteId'를, 요청 쿼리에 'age'(연령 필터 - 선택), 'mbti'(MBTI 필터 - 선택), 'gender'(성별 필터 - 선택), 'sortBy'(정렬 기준 - ByTime, ByPopularity), 'page'(페이지 번호)와 'size'(페이지 내의 데이터 수)를 JSON 형식으로 보내 주십시오.")
    @GetMapping("/votes/{voteId}/comments")
    public ResponseEntity<Slice<GetCommentResponse>> getComment(@PathVariable Long voteId, @ModelAttribute GetCommentRequest getCommentRequest) {

        Slice<GetCommentResponse> getCommentResponses = commentService.getComments(voteId, getCommentRequest);

        return new ResponseEntity(getCommentResponses, HttpStatus.OK);
    }


    @Operation(summary = "댓글 수정", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'와 'commentId'를, 요청 바디에 'content'(수정할 댓글 내용)을 JSON 형식으로 보내주세요.")
    @PatchMapping("/votes/{voteId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long voteId, @PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest updateCommentRequest, @RequestAttribute Long userId) {

        commentService.updateComment(voteId, commentId, userId, updateCommentRequest);

        return new ResponseEntity(HttpStatus.OK);
    }


    @Operation(summary = "댓글 삭제", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'와 'commentId'를 전달하여 댓글을 삭제하는 기능.")
    @DeleteMapping("/votes/{voteId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId) {

        commentService.deleteComment(voteId, commentId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "댓글 좋아요", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'와 'commentId'를 전달하여 댓글을 좋아요하는 기능입니다.")
    @PostMapping("/votes/{voteId}/comments/{commentId}/likers")
    public ResponseEntity likeComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId) {

        commentService.emoteComment(voteId, commentId, userId, Emotion.LIKE);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "댓글 싫어요", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'와 'commentId'를 전달하여 댓글을 싫어요하는 기능입니다.")
    @PostMapping("/votes/{voteId}/comments/{commentId}/haters")
    public ResponseEntity hateComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId) {

        commentService.emoteComment(voteId, commentId, userId, Emotion.HATE);


        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "음식점 추가", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'와 'commentId'를 전달하며, 요청 바디에 업데이트할 음식점 정보를 JSON 형식으로 전달하여 댓글에 추가하는 기능입니다.")
    @PatchMapping("/votes/{voteId}/comments/{commentId}/snack")
    public ResponseEntity addRestaurantToComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId, @RequestBody UpdateSnackRequest updateSnackRequest) {

        commentService.addRestaurantToComment(voteId, commentId, userId, updateSnackRequest);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "음식점 검색", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'와 'commentId'를 전달하며, 요청 쿼리에 'keyword'(검색 키워드 - 선택)과 'page'(요청 페이지 인덱스)를 전달하여 음식점을 검색하는 기능입니다.")
    @GetMapping("/votes/{voteId}/comments/{commentId}/snack")
    public ResponseEntity<List<SearchRestaurantResponse>> searchRestaurant(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam int page) {

        List<SearchRestaurantResponse> searchRestaurantResponses = commentService.searchRestaurant(voteId, commentId, userId, keyword, page);

        return new ResponseEntity(searchRestaurantResponses, HttpStatus.OK);
    }

    @Operation(summary = "음식점 이미지 조회", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId', 'commentId'와 'contentId'를 전달하여 특정 음식점의 이미지를 가져오는 기능입니다.")
    @GetMapping("/votes/{voteId}/comments/{commentId}/snack/{contentId}")
    public ResponseEntity<List<String>> getRestaurantImage(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Long userId, @PathVariable String contentId) {

        List<String> restaurantImage = commentService.getRestaurantImage(voteId, commentId, userId, contentId);

        return new ResponseEntity(restaurantImage, HttpStatus.OK);
    }


}