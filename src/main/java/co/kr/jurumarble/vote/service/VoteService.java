package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.AlreadyUserDoVoteException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteResult;
import co.kr.jurumarble.vote.dto.DoVoteInfo;
import co.kr.jurumarble.vote.dto.FindVoteListData;
import co.kr.jurumarble.vote.dto.VoteListData;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import co.kr.jurumarble.vote.dto.request.UpdateVoteRequest;
import co.kr.jurumarble.vote.dto.response.GetVoteResponse;
import co.kr.jurumarble.vote.enums.SortByType;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteResultRepository voteResultRepository;

    public void createVote(CreateVoteRequest request, Long userId) {

        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteContent voteContent = new VoteContent(request);
        Vote vote = new Vote(request, findUser, voteContent);

        voteRepository.save(vote);
    }

    public GetVoteResponse getVote(Long voteId) {
        return voteRepository.findById(voteId)
                .map(Vote::toDto)
                .orElseThrow(VoteNotFoundException::new);
    }

    public void updateVote(UpdateVoteRequest request, Long userId, Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        isVoteOfUser(userId, vote);

        vote.update(request);

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

        VoteResult voteResult = new VoteResult(vote, user, info.getChoice());

        voteResultRepository.save(voteResult);

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

        Slice<FindVoteListData> sliceBy = voteRepository.findSliceBy(pageRequest);
        voteListData = sliceBy.map(findVoteListData -> new VoteListData(findVoteListData.getVote(), findVoteListData.getCnt()));

        return voteListData;
    }

    private Slice<VoteListData> getVoteByPopularity(PageRequest pageRequest) {

        Slice<Vote> voteSlice = voteRepository.findWithVoteResult(pageRequest);

        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
            Long countVoted = voteResultRepository.countByVote(vote);
            return new VoteListData(vote, countVoted);
        });
        return voteListData;
    }
}
