package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.vote.IllegalVoteTypeException;
import co.kr.jurumarble.utils.PageableConverter;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VoteFinder {
    private final PageableConverter pageableConverter;
    private final VoteRepository voteRepository;


    // 1. 원하는 조건을 걸어서 투표 컨텐츠 없이 투표를 찾는다.
    // 2. 찾은 투표의 아이디와 타입으로 투표 컨텐츠를 찾는다.
    // 3. 찾은 투표 컨텐츠와 투표 데이터를 이어서 완전하게 만든다.
    public SliceImpl<VoteData> getVoteData(PageRequest pageable, List<VoteCommonData> voteCommonDataList) {
        List<VoteContent> voteContents = findNormalVoteContentByVoteIds(voteCommonDataList);
        List<VoteDrinkContent> voteDrinkContents = findDrinkVoteContentByVoteIds(voteCommonDataList);

        Map<Long, VoteContent> voteContentsMap = makeVoteContentsMap(voteContents);
        Map<Long, VoteDrinkContent> voteDrinkContentMap = makeVoteDrinkContentsMap(voteDrinkContents);

        List<VoteData> voteData = generateVoteDataFromContents(voteCommonDataList, voteContentsMap, voteDrinkContentMap);
        return pageableConverter.convertListToSlice(voteData, pageable.getPageSize(), pageable.getPageSize(), pageable);
    }


    private List<VoteContent> findNormalVoteContentByVoteIds(List<VoteCommonData> voteCommonDataList) {
        List<Long> normalVoteIds = getNormalVoteIdsFromFindVoteCommonDataList(voteCommonDataList);
        return voteRepository.findVoteContentsByNormalVoteIds(normalVoteIds);
    }

    private List<VoteDrinkContent> findDrinkVoteContentByVoteIds(List<VoteCommonData> voteCommonDataList) {
        List<Long> drinkVoteIds = getDrinkVoteIdsFromFindVoteCommonDataList(voteCommonDataList);
        return voteRepository.findVoteContentsByDrinkVoteIds(drinkVoteIds);
    }

    private List<Long> getNormalVoteIdsFromFindVoteCommonDataList(List<VoteCommonData> voteCommonDataList) {
        return voteCommonDataList.stream()
                .filter(voteCommonData -> VoteType.NORMAL == voteCommonData.getVoteType())
                .map(VoteCommonData::getVoteId)
                .collect(Collectors.toList());
    }

    private List<Long> getDrinkVoteIdsFromFindVoteCommonDataList(List<VoteCommonData> voteCommonDataList) {
        return voteCommonDataList.stream()
                .filter(voteCommonData -> VoteType.DRINK == voteCommonData.getVoteType())
                .map(VoteCommonData::getVoteId)
                .collect(Collectors.toList());
    }

    private Map<Long, VoteContent> makeVoteContentsMap(List<VoteContent> voteContents) {
        return voteContents.stream()
                .collect(Collectors.toMap(VoteContent::getVoteId, voteContent -> voteContent));
    }

    private Map<Long, VoteDrinkContent> makeVoteDrinkContentsMap(List<VoteDrinkContent> voteDrinkContents) {
        return voteDrinkContents.stream()
                .collect(Collectors.toMap(VoteDrinkContent::getVoteId, voteDrinkContent -> voteDrinkContent));
    }

    private List<VoteData> generateVoteDataFromContents(List<VoteCommonData> voteCommonDataList, Map<Long, VoteContent> voteContentsMap, Map<Long, VoteDrinkContent> voteDrinkContentMap) {
        return voteCommonDataList.stream()
                .map(voteCommonData -> convertContentsToVoteData(voteContentsMap, voteDrinkContentMap, voteCommonData)).collect(Collectors.toList());
    }

    private VoteData convertContentsToVoteData(Map<Long, VoteContent> voteContentsMap, Map<Long, VoteDrinkContent> voteDrinkContentMap, VoteCommonData voteCommonData) {
        if (VoteType.NORMAL == voteCommonData.getVoteType()) {
            VoteContent voteContent = voteContentsMap.get(voteCommonData.getVoteId());
            return VoteData.generateNormalVoteData(voteCommonData, voteContent);
        }
        if (VoteType.DRINK == voteCommonData.getVoteType()) {
            VoteDrinkContent voteDrinkContent = voteDrinkContentMap.get(voteCommonData.getVoteId());
            return VoteData.generateDrinkVoteData(voteCommonData, voteDrinkContent);
        }
        throw new IllegalVoteTypeException();
    }


}
