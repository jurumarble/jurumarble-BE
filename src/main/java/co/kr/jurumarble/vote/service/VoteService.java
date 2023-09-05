package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.drink.domain.DrinkFinder;
import co.kr.jurumarble.drink.domain.dto.DrinksUsedForVote;
import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.*;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.utils.PageableConverter;
import co.kr.jurumarble.vote.domain.*;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import co.kr.jurumarble.vote.service.request.CreateDrinkVoteServiceRequest;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final DrinkFinder drinkFinder;
    private final VoteValidator voteValidator;
    private final VoteFinder voteFinder;
    private final PageableConverter pageableConverter;
    private final VoteDrinkContentRepository voteDrinkContentRepository;


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
        DrinksUsedForVote drinksUsedForVote = drinkFinder.findDrinksUsedForVote(request.extractDrinkIds());
        Vote vote = request.toVote(userId);
        VoteDrinkContent voteDrinkContent = VoteDrinkContent.createFromDrinks(drinksUsedForVote);
        return voteGenerator.createDrinkVote(vote, voteDrinkContent);
    }

    public VoteData getVote(Long voteId) {
        VoteCommonData voteCommonData = voteRepository.findVoteCommonDataByVoteId(voteId).orElseThrow(VoteNotFoundException::new);

        if (VoteType.NORMAL == voteCommonData.getVoteType()) {
            VoteContent voteContent = voteContentRepository.findByVoteId(voteId).orElseThrow(VoteContentNotFoundException::new);
            return VoteData.generateNormalVoteData(voteCommonData, voteContent);
        }

        if (VoteType.DRINK == voteCommonData.getVoteType()) {
            VoteDrinkContent voteDrinkContent = voteDrinkContentRepository.findByVoteId(voteId).orElseThrow(VoteDrinkContentNotFoundException::new);
            return VoteData.generateDrinkVoteData(voteCommonData, voteDrinkContent);
        }

        throw new VoteTypeNotMatchException();
    }

    @Transactional
    public void updateNormalVote(UpdateNormalVoteServiceRequest request) {
        Vote vote = voteRepository.findById(request.getVoteId()).orElseThrow(VoteNotFoundException::new);
        isVoteOfUser(request.getUserId(), vote);
        VoteContent voteContent = voteContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteNotFoundException::new);
        vote.updateNormalVote(request);
        voteContent.update(request);
    }

    @Transactional
    public void updateDrinkVote(UpdateDrinkVoteServiceRequest request) {
        Vote vote = voteRepository.findById(request.getVoteId()).orElseThrow(VoteNotFoundException::new);
        isVoteOfUser(request.getUserId(), vote);
        VoteDrinkContent voteDrinkContent = voteDrinkContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteNotFoundException::new);
        DrinksUsedForVote drinksUsedForVote = drinkFinder.findDrinksUsedForVote(request.extractDrinkIds());
        vote.updateDrinkVote(request);
        voteDrinkContent.updateFromDrinks(drinksUsedForVote);
    }

    public void isVoteOfUser(Long userId, Vote vote) {
        if (!vote.isVoteOfUser(userId)) throw new UserNotAccessRightException();
    }

    @Transactional
    public void deleteVote(Long voteId, Long userId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        isVoteOfUser(userId, vote);
        deleteVoteContent(vote);
        voteRepository.delete(vote);
    }

    private void deleteVoteContent(Vote vote) {
        if (VoteType.DRINK == vote.getVoteType()) {
            VoteDrinkContent voteDrinkContent = voteDrinkContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteDrinkContentNotFoundException::new);
            voteDrinkContentRepository.delete(voteDrinkContent);
        }

        if (VoteType.NORMAL == vote.getVoteType()) {
            VoteContent voteContent = voteContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteContentNotFoundException::new);
            voteContentRepository.delete(voteContent);
        }
    }

    @Transactional
    public void doVote(DoVoteInfo info) {
        Vote vote = voteRepository.findById(info.getVoteId()).orElseThrow(VoteNotFoundException::new);
        User user = userRepository.findById(info.getUserId()).orElseThrow(UserNotFoundException::new);
        voteValidator.validateParcitipateVote(vote, user);
        VoteResult voteResult = new VoteResult(vote.getId(), user.getId(), info.getChoice());
        voteResultRepository.save(voteResult);
    }

    public Slice<VoteData> sortFindVotes(String keyword, SortByType sortBy, Integer page, Integer size) {

        if (SortByType.ByTime == sortBy) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            return findVotesByTime(keyword, pageRequest);
        }

        if (SortByType.ByPopularity == sortBy) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            return findVotesByPopularity(keyword, pageRequest);
        }

        throw new VoteSortByNotFountException();
    }

    public Slice<VoteData> findVotesByTime(String keyword, PageRequest pageable) {
        List<VoteCommonData> voteCommonDataByTime = voteRepository.findVoteCommonDataByTime(keyword, pageable);
        return voteFinder.getVoteData(pageable, voteCommonDataByTime);
    }

    public Slice<VoteData> findVotesByPopularity(String keyword, PageRequest pageable) {
        List<VoteCommonData> voteCommonDataByPopularity = voteRepository.findVoteCommonDataByPopularity(keyword, pageable);
        return voteFinder.getVoteData(pageable, voteCommonDataByPopularity);
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

    public HotDrinkVoteData getHotDrinkVote() {
        return voteRepository.getHotDrinkVote(LocalDateTime.now())
                .orElseGet(voteRepository::findOneDrinkVoteByPopular);
    }

    public Slice<VoteData> findDrinkVotes(String keyword, String region, SortByType sortBy, Integer pageNum, Integer pageSize) {

        if (SortByType.ByTime == sortBy) {
            List<VoteData> drinkVotesByTime = voteRepository.findDrinkVotesByTime(keyword, region, pageNum, pageSize);
            return pageableConverter.convertListToSlice(drinkVotesByTime, pageNum, pageSize);
        }

        if (SortByType.ByPopularity == sortBy) {
            List<VoteData> drinkVotesByPopularity = voteRepository.findDrinkVotesByPopularity(keyword, region, pageNum, pageSize);
            return pageableConverter.convertListToSlice(drinkVotesByPopularity, pageNum, pageSize);
        }

        throw new VoteSortByNotFountException();
    }

    public Slice<VoteData> getParticipatedVotes(Long userId, int page, int size) {
        List<VoteCommonData> voteCommonDataByParticipate = voteRepository.findVoteCommonDataByParticipate(userId, page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return voteFinder.getVoteData(pageRequest, voteCommonDataByParticipate);
    }

    public Slice<VoteData> getMyVote(Long userId, int page, int size) {
        List<VoteCommonData> voteCommonDataByParticipate = voteRepository.findVoteCommonDataByPostedUserId(userId, page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return voteFinder.getVoteData(pageRequest, voteCommonDataByParticipate);
    }
}