package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.domain.VoteGenerator;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.VoteListData;
import co.kr.jurumarble.vote.dto.request.UpdateVoteRequest;
import co.kr.jurumarble.vote.dto.response.GetVoteResponse;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.repository.BookmarkRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import co.kr.jurumarble.vote.service.request.CreateDrinkVoteServiceRequest;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VoteService {

    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;
    private final BookmarkRepository bookmarkRepository;
    private final VoteGenerator voteGenerator;


    @Transactional
    public Long createNormalVote(CreateNormalVoteServiceRequest request, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteContent voteContent = request.toVoteContent();
        Vote vote = request.toVote(userId);
        return voteGenerator.createNormalVote(vote, voteContent);
    }

    @Transactional
    public Long createDrinkVote(CreateDrinkVoteServiceRequest request, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteDrinkContent voteDrinkContent = request.toVoteDrinkContent();
        Vote vote = request.toVote(userId);
        return voteGenerator.createDrinkVote(vote,voteDrinkContent);
    }

    public GetVoteResponse getVote(Long voteId) {

//        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

//        User user = userRepository.findById(vote.getPostedUserId()).orElseThrow(UserNotFoundException::new);

//        return vote.toDto(user);
        return null;
    }

    public void updateVote(UpdateVoteRequest request, Long userId, Long voteId) {

//        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

//        isVoteOfUser(userId, vote);

//        vote.update(request);

    }

    public void isVoteOfUser(Long userId, Vote vote) {

//        if(!vote.isVoteOfUser(userId)) throw new UserNotAccessRightException();

    }

    public void deleteVote(Long voteId, Long userId) {

//        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
//
//        isVoteOfUser(userId, vote);
//
//        voteRepository.delete(vote);
    }

    public void doVote(DoVoteInfo info) {

//        Vote vote = voteRepository.findById(info.getVoteId()).orElseThrow(VoteNotFoundException::new);
//        User user = userRepository.findById(info.getUserId()).orElseThrow(UserNotFoundException::new);

//        if(vote.isVoteOfUser(user.getId())) throw new UserNotAccessRightException();

//        if(voteResultRepository.existsByVoteAndVotedUser(vote, user)) throw new AlreadyUserDoVoteException();

//        VoteResult voteResult = new VoteResult(vote, user, info.getChoice());

//        voteResultRepository.save(voteResult);

    }

    public Slice<VoteListData> getVoteList(SortByType sortBy, Integer page, Integer size) {

        Slice<VoteListData> voteListData = getVoteListData(sortBy, page, size);

        return voteListData;
    }

    private Slice<VoteListData> getVoteListData(SortByType sortBy, Integer page, Integer size) {
        Slice<VoteListData> voteListData;
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

    private Slice<VoteListData> getVoteSortByTime(PageRequest pageRequest) {
        Slice<VoteListData> voteListData;

//        Slice<FindVoteListData> sliceBy = voteRepository.findSliceBy(pageRequest);
//        voteListData = sliceBy.map(findVoteListData -> new VoteListData(findVoteListData.getVote(), findVoteListData.getCnt()));

//        return voteListData;
        return null;
    }

    private Slice<VoteListData> getVoteByPopularity(PageRequest pageRequest) {

//        Slice<Vote> voteSlice = voteRepository.findWithVoteResult(pageRequest);

//        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
//            Long countVoted = voteResultRepository.countByVote(vote);
//            return new VoteListData(vote, countVoted);
//        });
//        return voteListData;
        return null;
    }

    public Slice<VoteListData> getSearchVoteList(String keyword, SortByType sortBy, int page, int size) {

        Slice<Vote> searchedVoteSlice = null;

        if(sortBy.equals(SortByType.ByTime)) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
//            searchedVoteSlice = voteRepository.findAllByTitleAfter(keyword, pageRequest);
        } else if(sortBy.equals(SortByType.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
//            searchedVoteSlice = voteRepository.findSliceByTitleContainsPopularity(keyword, pageRequest);
        }

//        Slice<VoteListData> voteListData = searchedVoteSlice.map(vote -> {
//            Long countVoted = voteResultRepository.countByVote(vote);
//            return new VoteListData(vote, countVoted);
//        });
//        return voteListData;
        return null;
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

// voteResult 상위 투표 결과 5개 가져왔음 voteId 5개만 잇음
// voteId 5개를 쿼리로 데이터를 뽑아오는거 페이지네이션으로 프론트에 반환

// Slice<VoteResult> voteResults (5개) -> voteId 5개 있으면
// voteResults.stream().map(voteResult -> voteRepository.findById(voteResult.voteId)).collects.toList;

// Slice<Vote> voteRepository.findById(voteId 5개)

// page = 3, size = 10 -> 30번째부터 40번쨰까지 받아오는거

// jpa 에서 제공하는 voteResult말고 우리가 정의한 객체 끍어오는

// 목표-> 투표 인기순 조회 투표 결과를 통해서 투표를 가지고 올 때
// 투표안에 작성자가 있다,