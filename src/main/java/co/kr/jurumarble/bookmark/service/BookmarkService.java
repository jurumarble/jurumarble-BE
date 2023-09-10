package co.kr.jurumarble.bookmark.service;

import co.kr.jurumarble.bookmark.domain.Bookmark;
import co.kr.jurumarble.bookmark.repository.BookmarkRepository;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Transactional
    public void bookmarkVote(Long userId, Long voteId) {

        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Optional<Bookmark> byVoteAndUser = bookmarkRepository.findByUserIdAndVoteId(userId, voteId);

        byVoteAndUser.ifPresentOrElse(
                bookmark -> {
                    //북마크를 눌렀는데 또 눌렀을 경우 북마크 취소
                    bookmarkRepository.delete(bookmark);
                },
                // 북마크가 없을 경우 북마크 추가
                () -> {
                    Bookmark bookmark = new Bookmark(userId, voteId);
                    bookmarkRepository.save(bookmark);
                }
        );

    }

    public boolean checkBookmarked(Long userId, Long voteId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        return bookmarkRepository.findByUserIdAndVoteId(userId, voteId).isPresent();
    }

}
