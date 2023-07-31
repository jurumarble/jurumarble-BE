package co.kr.jurumarble.vote.controller;

import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.VoteListData;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import co.kr.jurumarble.vote.dto.request.DoVoteRequest;
import co.kr.jurumarble.vote.dto.request.UpdateVoteRequest;
import co.kr.jurumarble.vote.dto.response.GetIsUserVotedResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteListResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteRecommendListResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteResponse;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.service.VoteService;
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

@RequestMapping("/api/votes")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "vote", description = "vote api")
public class VoteController {
    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "헤더에 토큰 담고, 바디에 {title, titleA, titleB, imageA, imageB, filteredGender, filteredAge, filteredMbti} json 형식으로 보내주시면 됩니다.")
    @PostMapping("")
    public ResponseEntity createVote(@Valid @RequestBody CreateVoteRequest request, @RequestAttribute Long userId) {

        voteService.createVote(request, userId);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "투표 리스트 조회", description = "파라미터에 sortBy, page, size, category 보내주시면 됩니다.")
    @GetMapping("")
    public ResponseEntity<GetVoteListResponse> getVoteList(@RequestParam SortByType sortBy, @RequestParam int page, @RequestParam int size) {
        Slice<VoteListData> voteListData = voteService.getVoteList(sortBy, page, size);

        return new ResponseEntity(new GetVoteListResponse(voteListData), HttpStatus.OK);
    }

    @Operation(summary = "투표 리스트 검색", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다.")
    @GetMapping("/search")
    public ResponseEntity<GetVoteListResponse> getVoteSearchList(@RequestParam String keyword, @RequestParam SortByType sortBy, @RequestParam int page, @RequestParam int size) {
        Slice<VoteListData> voteListData = voteService.getSearchVoteList(keyword, sortBy, page, size);
        return new ResponseEntity(new GetVoteListResponse(voteListData), HttpStatus.OK);
    }

    @Operation(summary = "투표 단건 조회", description = "파라미터에 voteId 보내주시면 됩니다.")
    @GetMapping("/{voteId}")
    public ResponseEntity<GetVoteResponse> getVote(@PathVariable Long voteId) {

        GetVoteResponse response = voteService.getVote(voteId);

        return new ResponseEntity(response,HttpStatus.OK);
    }

    @Operation(summary = "투표 수정", description = "파라미터에 voteId, 바디에 {title, detail, titleA, titleB} json 형식으로 보내주시면 됩니다.")
    @PatchMapping("/{voteId}")
    public ResponseEntity updateVote(@PathVariable("voteId") Long voteId, @RequestBody UpdateVoteRequest request, @RequestAttribute Long userId) {

        voteService.updateVote(request, userId, voteId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "투표 삭제", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다")
    @DeleteMapping("/{voteId}")
    public ResponseEntity deleteVote(@PathVariable("voteId") Long voteId, @RequestAttribute Long userId) {

        voteService.deleteVote(voteId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "투표 참여", description = "헤더에 토큰담고, 파라미터에 voteId, 바디에 {choice} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/{voteId}/vote")
    public ResponseEntity doVote(@RequestBody DoVoteRequest request, @PathVariable("voteId") Long voteId, @RequestAttribute Long userId) {

        voteService.doVote(request.toService(userId, voteId));

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "투표 검색어 추천", description = "파라미터에 keyword, category 보내주시면 됩니다.")
    @GetMapping("/recommend")
    public ResponseEntity recommendVote(@RequestParam String keyword) {

        List<String> voteRecommendListData = voteService.getRecommendVoteList(keyword);

        return new ResponseEntity(new GetVoteRecommendListResponse(voteRecommendListData), HttpStatus.OK);
    }

    @Operation(summary = "투표 북마크", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다.")
    @PostMapping("/{voteId}/bookmark")
    public ResponseEntity bookmarkVote(@PathVariable Long voteId, @RequestAttribute Long userId) {

        voteService.bookmarkVote(userId, voteId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "투표 참여 여부 조회", description = "파라미터에 voteId, 헤더에 userId 보내주시면 됩니다.")
    @GetMapping("/{voteId}/voted")
    public ResponseEntity<GetIsUserVotedResponse> getIsUserVoted(@PathVariable Long voteId, @RequestAttribute Long userId) {
        GetIsUserVoted userVoted = voteService.isUserVoted(voteId, userId);
        GetIsUserVotedResponse getIsUserVotedResponse = new GetIsUserVotedResponse();
        getIsUserVotedResponse.converter(userVoted);
        return new ResponseEntity(getIsUserVotedResponse,HttpStatus.OK);
    }
//
//    @Operation(summary = "북마크 여부 조회", description = "파라미어테 voteId, 헤더에 userId 보내주시면 됩니다.")
//    @GetMapping("/{voteId}/bookmark")
//    public ResponseEntity checkBookmarked(@PathVariable Long voteId, @RequestAttribute Long userId){
//
//        boolean result = voteService.checkBookmarked(userId, voteId);
//
//        return new ResponseEntity<>(new GetBookmarkedResponse(voteService.checkBookmarked(userId, voteId)), HttpStatus.OK);
//    }
}