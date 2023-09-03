package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.vote.IllegalVoteTypeException;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VoteFinder {
    private final VoteRepository voteRepository;


    // 1. 원하는 조건을 걸어서 투표 컨텐츠 없이 투표를 찾는다.
    // 2. 찾은 투표의 아이디와 타입으로 투표 컨텐츠를 찾는다.
    // 3. 찾은 투표 컨텐츠와 투표 데이터를 이어서 완전하게 만든다.
    public Slice<VoteData> getVoteByPopularity(String keyword, PageRequest pageable) {
        List<VoteCommonData> voteCommonDataList = voteRepository.findVoteCommonDataByPopularity(keyword, pageable);

        List<VoteContent> voteContents = findNormalVoteContentByVoteIds(voteCommonDataList);
        List<VoteDrinkContent> voteDrinkContents = findDrinkVoteContentByVoteIds(voteCommonDataList);

        Map<Long, VoteContent> voteContentsMap = voteContents.stream()  // List를 순회하면 성능이 안나오므로 <voteId, VoteConent> 로 이루어진 Map을 만듬
                .collect(Collectors.toMap(VoteContent::getVoteId, voteContent -> voteContent));// ex) <1, {voteContent}>

        Map<Long, VoteDrinkContent> voteDrinkContentMap = voteDrinkContents.stream()
                .collect(Collectors.toMap(VoteDrinkContent::getVoteId, voteDrinkContent -> voteDrinkContent));

        List<VoteData> voteData = getFindVoteDataList(voteCommonDataList, voteContentsMap, voteDrinkContentMap);

        return getSlice(voteData, pageable.getPageSize(), pageable.getPageSize(), pageable);
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
    private List<VoteContent> findNormalVoteContentByVoteIds(List<VoteCommonData> voteCommonDataList) {
        List<Long> normalVoteIds = getNormalVoteIdsFromFindVoteCommonDataList(voteCommonDataList);
        return voteRepository.findVoteContentsByNormalVoteIds(normalVoteIds);
    }
    private List<VoteDrinkContent> findDrinkVoteContentByVoteIds(List<VoteCommonData> voteCommonDataList) {
        List<Long> drinkVoteIds = getDrinkVoteIdsFromFindVoteCommonDataList(voteCommonDataList);
        return voteRepository.findVoteContentsByDrinkVoteIds(drinkVoteIds);
    }

    private List<VoteData> getFindVoteDataList(List<VoteCommonData> voteCommonDataList, Map<Long, VoteContent> voteContentsMap, Map<Long, VoteDrinkContent> voteDrinkContentMap) {
        return voteCommonDataList.stream()
                .map(voteCommonData -> {
                    if (voteCommonData.getVoteType().equals(VoteType.NORMAL)) {
                        VoteContent voteContent = voteContentsMap.get(voteCommonData.getVoteId());
                        return VoteData.generateNormalVoteData(voteCommonData, voteContent);
                    }
                    if (voteCommonData.getVoteType().equals(VoteType.DRINK)) {
                        VoteDrinkContent voteDrinkContent = voteDrinkContentMap.get(voteCommonData.getVoteId());
                        return VoteData.generateDrinkVoteData(voteCommonData, voteDrinkContent);
                    }
                    throw new IllegalVoteTypeException();
                }).collect(Collectors.toList());
    }

    private SliceImpl<VoteData> getSlice(List<VoteData> voteData, int pageable, int pageable1, Pageable pageable2) {
        boolean hasNext = false;

        if (voteData.size() > pageable) {
            hasNext = true;
            voteData = voteData.subList(0, pageable1); // 조회된 결과에서 실제 페이지의 데이터만 가져옴
        }

        return new SliceImpl<>(voteData, pageable2, hasNext);
    }

}
