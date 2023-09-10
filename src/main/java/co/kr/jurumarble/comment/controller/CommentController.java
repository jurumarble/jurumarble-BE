package co.kr.jurumarble.comment.controller;

import co.kr.jurumarble.comment.dto.request.CreateCommentRequest;
import co.kr.jurumarble.comment.dto.request.GetCommentRequest;
import co.kr.jurumarble.comment.dto.request.UpdateCommentRequest;
import co.kr.jurumarble.comment.dto.request.UpdateRestaurantRequest;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.enums.Region;
import co.kr.jurumarble.comment.service.CommentService;
import co.kr.jurumarble.comment.service.GetCommentData;
import co.kr.jurumarble.comment.service.SearchRestaurantData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static co.kr.jurumarble.utils.ResponseUtils.wrapWithContent;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "comment", description = "comment api")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'를, 요청 바디에 'parentId'(댓글의 부모 아이디. 대댓글일 경우 부모 댓글 아이디, 없으면 빈 문자열)와 'content'(댓글 내용)을 JSON 형식으로 보내주세요.")
    @PostMapping("/{commentType}/{typeId}/comments/create")
    public ResponseEntity createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest, @PathVariable CommentType commentType, @PathVariable Long typeId, @RequestAttribute Long userId) {
        commentService.createComment(commentType, typeId, userId, createCommentRequest.toServiceRequest());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 조회", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'를, 'sortBy'(정렬 기준 - ByTime, ByPopularity), 'page'(페이지 번호)와 'size'(페이지 내의 데이터 수)를 JSON 형식으로 보내 주십시오.")
    @GetMapping("/{commentType}/{typeId}/comments")
    public ResponseEntity<Page<GetCommentData>> getComment(@ModelAttribute @Valid GetCommentRequest getCommentRequest, @PathVariable CommentType commentType, @PathVariable Long typeId) {
        Page<GetCommentData> getCommentResponses = commentService.getComments(commentType, typeId, getCommentRequest.toServiceRequest());
        return new ResponseEntity(getCommentResponses, HttpStatus.OK);
    }

    @Operation(summary = "맛보기 댓글 조회", description = "헤더에 토큰을 포함하고, URL 파라미터에 'voteId'를, 'sortBy'(정렬 기준 - ByTime, ByPopularity), 'page'(페이지 번호)와 'size'(페이지 내의 데이터 수)를 JSON 형식으로 보내 주십시오.")
    @GetMapping("/{commentType}/{typeId}/comments/sample")
    public ResponseEntity<Map<String, List<GetCommentData>>> getSampleComment(@PathVariable CommentType commentType, @PathVariable Long typeId) {
        List<GetCommentData> getCommentResponses = commentService.getSampleComments(commentType, typeId);
        return wrapWithContent(getCommentResponses);
    }


    @Operation(summary = "댓글 수정", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'와 commentId 를, 요청 바디에 'content'(수정할 댓글 내용)을 JSON 형식으로 보내주세요.")
    @PutMapping("/{commentType}/{typeId}/comments/{commentId}")
    public ResponseEntity updateComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId) {
        commentService.updateComment(commentType, typeId, commentId, userId, updateCommentRequest.toServiceRequest());
        return new ResponseEntity(HttpStatus.OK);
    }


    @Operation(summary = "댓글 삭제", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'를 전달하여 댓글을 삭제하는 기능.")
    @DeleteMapping("/{commentType}/{typeId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId) {
        commentService.deleteComment(commentType, typeId, commentId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "댓글 좋아요", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'와 commentId 를 전달하여 댓글을 좋아요하는 기능입니다.")
    @PostMapping("/{commentType}/{typeId}/comments/{commentId}/likers")
    public ResponseEntity likeComment(@PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId) {
        commentService.emoteComment(commentType, typeId, commentId, userId, Emotion.LIKE);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "댓글 싫어요", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'와 commentId 를 전달하여 댓글을 싫어요하는 기능입니다.")
    @PostMapping("/{commentType}/{typeId}/comments/{commentId}/haters")
    public ResponseEntity hateComment(@PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId) {
        commentService.emoteComment(commentType, typeId, commentId, userId, Emotion.HATE);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "음식점 추가", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'와 commentId 를, 요청 바디에 업데이트할 음식점 정보를 JSON 형식으로 전달하여 댓글에 추가하는 기능입니다.")
    @PostMapping("/{commentType}/{typeId}/comments/{commentId}/restaurant")
    public ResponseEntity addRestaurantToComment(@Valid @RequestBody UpdateRestaurantRequest updateRestaurantRequest, @PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId) {
        commentService.addRestaurantToComment(commentType, typeId, commentId, userId, updateRestaurantRequest.toServiceRequest());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "음식점 검색", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'와 commentId 를, 요청 쿼리에 'keyword'(검색 키워드 - 선택)과 'page'(요청 페이지 인덱스, 1부터 시작)를 전달하여 음식점을 검색하는 기능입니다.")
    @GetMapping("/{commentType}/{typeId}/comments/{commentId}/restaurant")
    public ResponseEntity<List<SearchRestaurantData>> searchRestaurant(@PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(required = false) Region region, @RequestParam int page) {
        List<SearchRestaurantData> searchRestaurantResponse = commentService.searchRestaurant(commentType, typeId, commentId, userId, keyword, region, page);
        return new ResponseEntity(searchRestaurantResponse, HttpStatus.OK);
    }

    @Operation(summary = "음식점 이미지 조회", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id'와 commentId 를 전달하여 특정 음식점의 이미지를 가져오는 기능입니다.")
    @GetMapping("/{commentType}/{typeId}/comments/{commentId}/restaurant/{contentId}")
    public ResponseEntity<List<String>> getRestaurantImage(@PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId, @PathVariable String contentId) {
        List<String> restaurantImage = commentService.getRestaurantImage(commentType, typeId, commentId, userId, contentId);
        return new ResponseEntity(restaurantImage, HttpStatus.OK);
    }

    @Operation(summary = "음식점 삭제", description = "헤더에 토큰을 포함하고, URL 파라미터에 'type'(votes 또는 drinks)와 해당 타입'id 전달하여 음식점을 삭제하는 기능입니다.")
    @DeleteMapping("/{commentType}/{typeId}/comments/{commentId}/restaurant")
    public ResponseEntity<List<String>> deleteRestaurant(@PathVariable CommentType commentType, @PathVariable Long typeId, @PathVariable Long commentId, @RequestAttribute Long userId) {
        commentService.deleteRestaurant(commentType, typeId, commentId, userId);
        return new ResponseEntity(HttpStatus.OK);
    }


}