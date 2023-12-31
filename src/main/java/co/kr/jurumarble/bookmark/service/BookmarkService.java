package co.kr.jurumarble.bookmark.service;

import co.kr.jurumarble.bookmark.domain.Bookmark;
import co.kr.jurumarble.bookmark.repository.BookmarkRepository;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        bookmarkRepository.findByUserIdAndVoteId(userId, voteId).ifPresentOrElse(
                bookmarkRepository::delete,
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
