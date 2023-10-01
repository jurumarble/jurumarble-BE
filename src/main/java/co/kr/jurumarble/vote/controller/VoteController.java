package co.kr.jurumarble.vote.controller;

import co.kr.jurumarble.comment.enums.Region;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.dto.request.*;
import co.kr.jurumarble.vote.dto.response.GetIsUserVotedResponse;
import co.kr.jurumarble.vote.dto.response.GetMyVotesResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteListResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteRecommendListResponse;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import co.kr.jurumarble.vote.repository.dto.MyVotesCntData;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
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

    @Operation(summary = "일반 투표 생성", description = "헤더에 토큰 담고, 바디에 {title, titleA, titleB, imageA, imageB} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/normal")
    public ResponseEntity<HttpStatus> createNormalVote(@Valid @RequestBody CreateNormalVoteRequest request, @RequestAttribute Long userId) {
        voteService.createNormalVote(request.toServiceRequest(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "전통주 투표 생성", description = "헤더에 토큰 담고, 바디에 {title, drinkAId, drinkBId} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/drink")
    public ResponseEntity<HttpStatus> createDrinkVote(@Valid @RequestBody CreateDrinkVoteRequest request, @RequestAttribute Long userId) {
        voteService.createDrinkVote(request.toServiceRequest(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "일반/전통주 투표 리스트 검색, 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 기획상 최신순, 인기순 만")
    @GetMapping("")
    public ResponseEntity<Slice<VoteData>> getVotes(@RequestParam(required = false) String keyword, @RequestParam SortByType sortBy, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.sortFindVotes(keyword, sortBy, page, size));
    }

    @Operation(summary = "마이페이지- 내가 참여한 투표 리스트 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 ")
    @GetMapping("/participated")
    public ResponseEntity<Slice<VoteData>> getParticipatedVotes(@RequestAttribute Long userId,@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.getParticipatedVotes(userId, page, size));
    }

    @Operation(summary = "마이페이지- 내가 작성한 투표 리스트 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 ")
    @GetMapping("/my-vote")
    public ResponseEntity<Slice<VoteData>> getMyVotes(@RequestAttribute Long userId,@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.getMyVote(userId, page, size));
    }

    @Operation(summary = "마이페이지- 내가 북마크한 투표 리스트 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 ")
    @GetMapping("/bookmarked")
    public ResponseEntity<Slice<VoteData>> getBookmarkedVotes(@RequestAttribute Long userId,@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.getBookmarkedVotes(userId, page, size));
    }

    @Operation(summary = "전통주 투표 리스트 검색, 조회", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다. 검색이 아니면 keyword = 에 값 없이 ")
    @GetMapping("/drinks")
    public ResponseEntity<GetVoteListResponse> getDrinkVotes(@RequestParam(required = false) String keyword, @RequestParam(required = false) Region region, @RequestParam SortByType sortBy, @RequestParam int page, @RequestParam int size) {
        String regionName = (region != null) ? region.getName() : null;
        Slice<VoteData> voteListData = voteService.findDrinkVotes(keyword, regionName, sortBy, page, size);
        return new ResponseEntity<>(new GetVoteListResponse(voteListData), HttpStatus.OK);
    }

    @Operation(summary = "일반/전통주 투표 단건 조회", description = "파라미터에 voteId 보내주시면 됩니다.")
    @GetMapping("/{voteId}")
    public ResponseEntity<VoteWithPostedUserData> getVote(@PathVariable Long voteId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voteService.getVoteWithPostedUserData(voteId));
    }

    @Operation(summary = "일반 투표 수정", description = "파라미터에 voteId, 바디에 {title, detail, titleA, titleB} json 형식으로 보내주시면 됩니다.")
    @PutMapping("/{voteId}/normal")
    public ResponseEntity<HttpStatus> updateNormalVote(@PathVariable("voteId") Long voteId, @RequestBody UpdateNormalVoteRequest request, @RequestAttribute Long userId) {
        voteService.updateNormalVote(request.toServiceRequest(voteId, userId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "전통주 투표 수정", description = "파라미터에 voteId, 바디에 {title, detail, titleA, titleB} json 형식으로 보내주시면 됩니다.")
    @PutMapping("/{voteId}/drink")
    public ResponseEntity<HttpStatus> updateDrinkVote(@PathVariable("voteId") Long voteId, @RequestBody UpdateDrinkVoteRequest request, @RequestAttribute Long userId) {
        voteService.updateDrinkVote(request.toServiceRequest(voteId, userId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "투표 삭제", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다")
    @DeleteMapping("/{voteId}/")
    public ResponseEntity<HttpStatus> deleteVote(@PathVariable("voteId") Long voteId, @RequestAttribute Long userId) {
        voteService.deleteVote(voteId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "투표 참여", description = "헤더에 토큰담고, 파라미터에 voteId, 바디에 {choice} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/{voteId}/vote")
    public ResponseEntity<HttpStatus> doVote(@RequestBody DoVoteRequest request, @PathVariable("voteId") Long voteId, @RequestAttribute Long userId) {
        voteService.doVote(request.toService(userId, voteId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "투표 검색어 추천", description = "파라미터에 keyword, category 보내주시면 됩니다.")
    @GetMapping("/recommend")
    public ResponseEntity<GetVoteRecommendListResponse> recommendVote(@RequestParam String keyword, @RequestParam int recommendCount) {
        List<String> voteRecommendListData = voteService.getRecommendVoteList(keyword, recommendCount);
        return new ResponseEntity(new GetVoteRecommendListResponse(voteRecommendListData), HttpStatus.OK);
    }

    @Operation(summary = "투표 참여 여부 조회", description = "파라미터에 voteId, 헤더에 userId 보내주시면 됩니다.")
    @GetMapping("/{voteId}/voted")
    public ResponseEntity<GetIsUserVotedResponse> getIsUserVoted(@PathVariable Long voteId, @RequestAttribute Long userId) {
        GetIsUserVoted userVoted = voteService.isUserVoted(voteId, userId);
        return new ResponseEntity(new GetIsUserVotedResponse(userVoted), HttpStatus.OK);
    }

    @Operation(summary = "핫 전통주 투표", description = "현재 시간 기준 일주일간 핫한 전통주 투표 조회")
    @GetMapping("/drinks/hot")
    public ResponseEntity<HotDrinkVoteData> getHotDrinkVote() {
        HotDrinkVoteData hotDrinkVote = voteService.getHotDrinkVote();
        return ResponseEntity.ok().body(hotDrinkVote);
    }

    @Operation(summary = "마이페이지 각 투표 갯수 조회", description = "작성한 투표, 참여한 투표, 북마크 투표 갯수 조회 기능")
    @GetMapping("/myActivities")
    public ResponseEntity<GetMyVotesResponse> getMyVotes(@RequestAttribute Long userId) {
        MyVotesCntData myVotes = voteService.getMyVotes(userId);
        return ResponseEntity.ok().body(GetMyVotesResponse.fromVotesCntData(myVotes));
    }
}