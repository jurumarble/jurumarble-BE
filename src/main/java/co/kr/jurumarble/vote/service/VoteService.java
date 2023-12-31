package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.drink.domain.DrinkFinder;
import co.kr.jurumarble.drink.domain.dto.DrinksUsedForVote;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.SortByNotFountException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.notification.event.DoVoteEvent;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.utils.PageableConverter;
import co.kr.jurumarble.utils.SpringContext;
import co.kr.jurumarble.vote.domain.*;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import co.kr.jurumarble.vote.dto.GetIsUserVoted;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.dto.request.VoteWithPostedUserData;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import co.kr.jurumarble.vote.repository.dto.MyVotesCntData;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import co.kr.jurumarble.vote.service.request.CreateDrinkVoteServiceRequest;
import co.kr.jurumarble.vote.service.request.CreateNormalVoteServiceRequest;
import co.kr.jurumarble.vote.service.request.UpdateDrinkVoteServiceRequest;
import co.kr.jurumarble.vote.service.request.UpdateNormalVoteServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
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
    private final ApplicationEventPublisher eventPublisher;
    private final VoteDeleter voteDeleter;


    @Transactional
    public Long createNormalVote(CreateNormalVoteServiceRequest request, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteContent voteContent = request.toVoteContent();
        Vote vote = request.toVote(userId, request.getDetail());
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

    public VoteWithPostedUserData getVoteWithPostedUserData(Long voteId) {
        VoteWithPostedUserCommonData voteCommonData = voteRepository.findVoteCommonDataByVoteId(voteId).orElseThrow(VoteNotFoundException::new);
        VoteType voteType = voteCommonData.getVoteType();
        return voteType.execute(voteId, voteCommonData, SpringContext.getContext());
    }

    @Transactional
    public void updateNormalVote(UpdateNormalVoteServiceRequest request) {
        Vote vote = voteRepository.findById(request.getVoteId()).orElseThrow(VoteNotFoundException::new);
        voteValidator.isVoteOfUser(request.getUserId(), vote);
        VoteContent voteContent = voteContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteNotFoundException::new);
        vote.updateNormalVote(request);
        voteContent.update(request);
    }

    @Transactional
    public void updateDrinkVote(UpdateDrinkVoteServiceRequest request) {
        Vote vote = voteRepository.findById(request.getVoteId()).orElseThrow(VoteNotFoundException::new);
        voteValidator.isVoteOfUser(request.getUserId(), vote);
        VoteDrinkContent voteDrinkContent = voteDrinkContentRepository.findByVoteId(vote.getId()).orElseThrow(VoteNotFoundException::new);
        DrinksUsedForVote drinksUsedForVote = drinkFinder.findDrinksUsedForVote(request.extractDrinkIds());
        vote.updateDrinkVote(request);
        voteDrinkContent.updateFromDrinks(drinksUsedForVote);
    }

    @Transactional
    public void deleteVote(Long voteId, Long userId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        voteValidator.isVoteOfUser(userId, vote);
        voteDeleter.deleteVoteRelatedData(vote);
        voteRepository.delete(vote);
    }

    @Transactional
    public void doVote(DoVoteInfo info) {
        Vote vote = voteRepository.findById(info.getVoteId()).orElseThrow(VoteNotFoundException::new);
        User user = userRepository.findById(info.getUserId()).orElseThrow(UserNotFoundException::new);
        voteValidator.validateParticipateVote(vote, user);
        VoteResult voteResult = new VoteResult(vote.getId(), user.getId(), info.getChoice());
        voteResultRepository.save(voteResult);
        eventPublisher.publishEvent(new DoVoteEvent(this, vote.getId()));
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

        throw new SortByNotFountException();
    }

    public Slice<VoteData> findVotesByTime(String keyword, PageRequest pageable) {
        List<VoteCommonData> voteCommonDataByTime = voteRepository.findVoteCommonDataByTime(keyword, pageable);
        return voteFinder.getVoteData(pageable, voteCommonDataByTime);
    }

    public Slice<VoteData> findVotesByPopularity(String keyword, PageRequest pageable) {
        List<VoteCommonData> voteCommonDataByPopularity = voteRepository.findVoteCommonDataByPopularity(keyword, pageable);
        return voteFinder.getVoteData(pageable, voteCommonDataByPopularity);
    }

    public List<String> getRecommendVoteList(String keyword, int recommendCount) {
        return voteRepository.findByTitleContains(keyword, recommendCount).stream()
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

        throw new SortByNotFountException();
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

    public Slice<VoteData> getBookmarkedVotes(Long userId, int page, int size) {
        List<VoteCommonData> commonVoteDataByBookmark = voteRepository.findCommonVoteDataByBookmark(userId, page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return voteFinder.getVoteData(pageRequest, commonVoteDataByBookmark);
    }

    public MyVotesCntData getMyVotes(Long userId) {
        Long myWrittenVoteCnt = voteRepository.findMyWrittenVoteCnt(userId);
        Long myParticipatedVoteCnt = voteRepository.findMyParticipatedVoteCnt(userId);
        Long myBookmarkedVoteCnt = voteRepository.findMyBookmarkedVoteCnt(userId);
        return new MyVotesCntData(myWrittenVoteCnt, myParticipatedVoteCnt, myBookmarkedVoteCnt);
    }

    public Slice<VoteData> sortFindVotesV2(String keyword, SortByType sortBy, Integer page, Integer size, Long userId) {

        if (SortByType.ByTime == sortBy) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            return findVotesByTimeV2(userId, keyword, pageRequest);
        }

        if (SortByType.ByPopularity == sortBy) {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));
            return findVotesByPopularity(keyword, pageRequest);
        }

        throw new SortByNotFountException();
    }

    public Slice<VoteData> findVotesByTimeV2(Long userId, String keyword, PageRequest pageable) {
        List<VoteCommonData> voteCommonDataByTime;
        if(userId == null) {
            voteCommonDataByTime = voteRepository.findVoteCommonDataByTime(keyword, pageable);
        } else {
            voteCommonDataByTime = voteRepository.findVoteCommonDataByTimeAndUserId(userId, keyword, pageable);
        }
        return voteFinder.getVoteData(pageable, voteCommonDataByTime);
    }

}