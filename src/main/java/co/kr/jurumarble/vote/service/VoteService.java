package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.AlreadyUserDoVoteException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteGenerator;
import co.kr.jurumarble.vote.domain.VoteResult;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.NormalVoteData;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VoteService {

    private final UserRepository userRepository;
    private final VoteGenerator voteGenerator;
    private final VoteRepository voteRepository;
    private final VoteContentRepository voteContentRepository;
    private final VoteResultRepository voteResultRepository;

    @Transactional
    public void createVote(CreateVoteServiceRequest request, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteContent voteContent = request.toVoteContent();
        Vote vote = request.toVote(userId);
        voteGenerator.createVote(vote, voteContent);
    }

    public GetVoteData getVote(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        User user = userRepository.findById(vote.getPostedUserId()).orElseThrow(UserNotFoundException::new);

        VoteContent voteContent = voteContentRepository.findByVoteId(voteId).orElseThrow(VoteNotFoundException::new);
        return new GetVoteData(vote, user, voteContent);
    }

    public void updateVote(UpdateVoteServiceRequest request) {

        Vote vote = voteRepository.findById(request.getVoteId()).orElseThrow(VoteNotFoundException::new);

        isVoteOfUser(request.getUserId(), vote);

        VoteContent voteContent = voteContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteNotFoundException::new);

        vote.update(request);

        voteContent.update(request);

    }

    public void isVoteOfUser(Long userId, Vote vote) {

        if(!vote.isVoteOfUser(userId)) throw new UserNotAccessRightException();

    }

    public void deleteVote(Long voteId, Long userId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        isVoteOfUser(userId, vote);

        voteRepository.delete(vote);
    }

    public void doVote(DoVoteInfo info) {

        Vote vote = voteRepository.findById(info.getVoteId()).orElseThrow(VoteNotFoundException::new);
        User user = userRepository.findById(info.getUserId()).orElseThrow(UserNotFoundException::new);

        if(vote.isVoteOfUser(user.getId())) throw new UserNotAccessRightException();

        if(voteResultRepository.existsByVoteAndVotedUser(vote, user)) throw new AlreadyUserDoVoteException();

        VoteResult voteResult = new VoteResult(vote.getId(), user.getId(), info.getChoice());

        voteResultRepository.save(voteResult);

    }

    public Slice<NormalVoteData> getVoteList(SortByType sortBy, Integer page, Integer size) {

        Slice<NormalVoteData> voteListData = getVoteListData(sortBy, page, size);

        return voteListData;
    }

    private Slice<NormalVoteData> getVoteListData(SortByType sortBy, Integer page, Integer size) {
        Slice<NormalVoteData> voteListData;
        if (sortBy.equals(SortByType.ByTime)) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            voteListData = getVoteSortByTime(pageRequest);
        } else if (sortBy.equals(SortByType.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            voteListData = getVoteByPopularity(pageRequest);
        } else {
            throw new RuntimeException("잘못된 요청입니다.");
        }
        return voteListData;
    }

    private Slice<NormalVoteData> getVoteSortByTime(PageRequest pageRequest) {
        Slice<NormalVoteData> voteListData = voteRepository.findVoteDataWithTime(pageRequest);
        return voteListData;
    }

    private Slice<NormalVoteData> getVoteByPopularity(PageRequest pageRequest) {

        Slice<NormalVoteData> voteSlice = voteRepository.findVoteDataWithPopularity(pageRequest);
        return voteSlice;
    }

    public Slice<NormalVoteData> getSearchVoteList(String keyword, SortByType sortBy, int page, int size) {

        Slice<NormalVoteData> searchedVoteSlice = null;

        if(sortBy.equals(SortByType.ByTime)) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            searchedVoteSlice = voteRepository.findVoteDataByTitleContainsWithTime(keyword, pageRequest);
        } else if(sortBy.equals(SortByType.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            searchedVoteSlice = voteRepository.findVoteDataByTitleContainsPopularity(keyword, pageRequest);
        }

        return searchedVoteSlice;
    }

    public List<String> getRecommendVoteList(String keyword) {
//        return voteRepository.findByTitleContains(keyword).stream()
//                .limit(5)
//                .map(Vote::getTitle)
//                .collect(Collectors.toList());
        return null;
    }

    public GetIsUserVoted isUserVoted(Long voteId, Long userId) {
        GetIsUserVoted getIsUserVoted = new GetIsUserVoted(false, null);
//        voteResultRepository.getVoteResultByVoteIdAndUserId(voteId, userId).ifPresent(voteResult -> {
//            getIsUserVoted.setVoted(true);
//            getIsUserVoted.setUserChoice(voteResult.getChoice());
//        });
//        return getIsUserVoted;
        return null;
    }

    public void bookmarkVote(Long userId, Long voteId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
//        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

//        Optional<Bookmark> byVoteAndUser = bookmarkRepository.findByVoteAndUser(vote, user);

//        byVoteAndUser.ifPresentOrElse(
//                bookmark -> {
//                    //북마크를 눌렀는데 또 눌렀을 경우 북마크 취소
//                    bookmarkRepository.delete(bookmark);
//                    vote.removeBookmark(bookmark);
//                },
//                // 북마크가 없을 경우 북마크 추가
//                () -> {
//                    Bookmark bookmark = new Bookmark();
//
//                    bookmark.mappingVote(vote);
//                    bookmark.mappingUser(user);
//
//                    bookmarkRepository.save(bookmark);
//                }
//        );

    }
}