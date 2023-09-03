package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VoteFinder {
    private final VoteRepository voteRepository;

    public Slice<VoteData> getVoteByPopularity(String keyword, PageRequest pageRequest) {
        List<VoteCommonData> voteCommonDataList = voteRepository.findVoteCommonDataByPopularity(keyword, pageRequest);

        List<Long> normalVoteIds = getNormalVoteIdsFromFindVoteCommonDataList(voteCommonDataList);
        List<VoteContent> voteContents = voteRepository.findVoteContentsByNormalVoteIds(normalVoteIds);

        List<Long> drinkVoteIds = getDrinkVoteIdsFromFindVoteCommonDataList(voteCommonDataList);
        List<VoteDrinkContent> voteDrinkContents = voteRepository.findVoteContentsByDrinkVoteIds(drinkVoteIds);
    }

    private List<Long> getNormalVoteIdsFromFindVoteCommonDataList(List<VoteCommonData> voteCommonDataList) {
        return voteCommonDataList.stream()
                .filter(voteCommonData -> voteCommonData.getVoteType().equals(VoteType.NORMAL))
                .map(VoteCommonData::getVoteId)
                .collect(Collectors.toList());
    }


    private List<Long> getDrinkVoteIdsFromFindVoteCommonDataList(List<VoteCommonData> voteCommonDataList) {
        return voteCommonDataList.stream()
                .filter(voteCommonData -> voteCommonData.getVoteType().equals(VoteType.DRINK))
                .map(VoteCommonData::getVoteId)
                .collect(Collectors.toList());
    }

}
