package co.kr.jurumarble.bookmark.controller;

import co.kr.jurumarble.bookmark.dto.response.GetBookmarkedResponse;
import co.kr.jurumarble.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/votes")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "bookmark", description = "bookmark api")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "투표 북마크", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다.")
    @PostMapping("/{voteId}/bookmark")
    public ResponseEntity bookmarkVote(@PathVariable Long voteId, @RequestAttribute Long userId) {

        bookmarkService.bookmarkVote(userId, voteId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "북마크 여부 조회", description = "파라미어테 voteId, 헤더에 userId 보내주시면 됩니다.")
    @GetMapping("/{voteId}/bookmark")
    public ResponseEntity checkBookmarked(@PathVariable Long voteId, @RequestAttribute Long userId){

        boolean result = bookmarkService.checkBookmarked(userId, voteId);

        return new ResponseEntity<>(new GetBookmarkedResponse(result), HttpStatus.OK);
    }

}
