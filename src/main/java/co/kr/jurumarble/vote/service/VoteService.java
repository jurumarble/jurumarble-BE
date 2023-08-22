package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.AlreadyUserDoVoteException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.exception.vote.VoteSortByNotFountException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.*;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.NormalVoteData;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
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
        return voteGenerator.createDrinkVote(vote, voteDrinkContent);
    }

    public GetVoteData getVote(Long voteId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        User user = userRepository.findById(vote.getPostedUserId()).orElseThrow(UserNotFoundException::new);
        VoteContent voteContent = voteContentRepository.findByVoteId(voteId).orElseThrow(VoteNotFoundException::new);
        return new GetVoteData(vote, user, voteContent);
    }

    @Transactional
    public void updateVote(UpdateVoteServiceRequest request) {
        Vote vote = voteRepository.findById(request.getVoteId()).orElseThrow(VoteNotFoundException::new);
        isVoteOfUser(request.getUserId(), vote);
        VoteContent voteContent = voteContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteNotFoundException::new);
        vote.update(request);
        voteContent.update(request);
    }

    public void isVoteOfUser(Long userId, Vote vote) {
        if (!vote.isVoteOfUser(userId)) throw new UserNotAccessRightException();
    }

    @Transactional
    public void deleteVote(Long voteId, Long userId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        isVoteOfUser(userId, vote);
        voteRepository.delete(vote);
    }

    @Transactional
    public void doVote(DoVoteInfo info) {

        Vote vote = voteRepository.findById(info.getVoteId()).orElseThrow(VoteNotFoundException::new);
        User user = userRepository.findById(info.getUserId()).orElseThrow(UserNotFoundException::new);

        if (vote.isVoteOfUser(user.getId())) throw new UserNotAccessRightException();

        if (voteResultRepository.existsByVoteIdAndVotedUserId(vote.getId(), user.getId()))
            throw new AlreadyUserDoVoteException();

        VoteResult voteResult = new VoteResult(vote.getId(), user.getId(), info.getChoice());

        voteResultRepository.save(voteResult);

    }

    public Slice<NormalVoteData> getVoteList(String keyword, SortByType sortBy, Integer page, Integer size) {

        return getVoteListData(keyword, sortBy, page, size);
    }

    private Slice<NormalVoteData> getVoteListData(String keyword, SortByType sortBy, Integer page, Integer size) {
        Slice<NormalVoteData> voteListData;
        if (sortBy.equals(SortByType.ByTime)) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            voteListData = getVoteSortByTime(keyword, pageRequest);
        } else if (sortBy.equals(SortByType.ByPopularity)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            voteListData = getVoteByPopularity(keyword, pageRequest);
        } else {
            throw new VoteSortByNotFountException();
        }
        return voteListData;
    }

    private Slice<NormalVoteData> getVoteSortByTime(String keyword, PageRequest pageRequest) {
        return voteRepository.findNormalVoteDataWithTime(keyword,pageRequest);
    }

    private Slice<NormalVoteData> getVoteByPopularity(String keyword, PageRequest pageRequest) {
        return voteRepository.findNormalVoteDataWithPopularity(keyword, pageRequest);
    }

    public List<String> getRecommendVoteList(String keyword) {
        return voteRepository.findByTitleContains(keyword).stream()
                .map(Vote::getTitle)
                .collect(Collectors.toList());
    }

    public GetIsUserVoted isUserVoted(Long voteId, Long userId) {
        GetIsUserVoted getIsUserVoted = new GetIsUserVoted(false, null);
        voteResultRepository.getVoteResultByVoteIdAndUserId(voteId, userId).ifPresent(voteResult -> {
            getIsUserVoted.setVoted(true);
            getIsUserVoted.setUserChoice(voteResult.getChoice());
        });
        return getIsUserVoted;
    }


}