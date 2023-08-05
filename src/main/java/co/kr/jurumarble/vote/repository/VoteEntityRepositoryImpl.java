package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.FindVoteListData;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static co.kr.jurumarble.vote.domain.QVote.vote;
import static co.kr.jurumarble.vote.domain.QVoteContent.voteContent;
import static co.kr.jurumarble.vote.domain.QVoteResult.voteResult;

@Repository
public class VoteEntityRepositoryImpl implements VoteEntityRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public VoteEntityRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public Page<FindVoteListData> findWithVoteWithPopular(PageRequest pageRequest) {
        int pageNo = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        List<Tuple> findVotesOrderByPopularTuples = getVotesTupleOrderByPopular(pageNo, pageSize);

        List<Long> voteIds = getVoteIdsFromFindVotes(findVotesOrderByPopularTuples);

        List<VoteContent> voteContents = findVoteContentsByVoteIds(voteIds); //하나의 vote에서 두개의 voteContent 찾아야 함

        Map<Long, VoteContent> voteContentsMap = voteContents.stream()  // List를 순회하면 성능이 안나오므로 <voteId, VoteConent> 로 이루어진 Map을 만듬
                .collect(Collectors.toMap(VoteContent::getVoteId, voteContent -> voteContent));// ex) <1, {voteContent}>

        List<FindVoteListData> findVoteListDatas = getFindVoteListDatas(findVotesOrderByPopularTuples, voteContentsMap);

        long totalCount = getTotalCount();

        return new PageImpl<>(findVoteListDatas, pageRequest, totalCount);
    }

    private List<Tuple> getVotesTupleOrderByPopular(int pageNo, int pageSize) {
        return jpaQueryFactory
                .select(vote, voteResult.id.count())
                .from(vote)
                .innerJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .groupBy(vote.id)
                .orderBy(voteResult.id.count().desc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();
    }

    private List<Long> getVoteIdsFromFindVotes(List<Tuple> findVotesOrderByPopularTuples) {
        return findVotesOrderByPopularTuples.stream()
                .map(findVoteTuple ->
                        findVoteTuple.get(0, Vote.class).getId())
                .collect(Collectors.toList()
                );
    }

    private List<VoteContent> findVoteContentsByVoteIds(List<Long> voteIds) {
        return jpaQueryFactory
                .selectFrom(voteContent)
                .where(voteContent.voteId.in(voteIds))
                .fetch();
    }

    private List<FindVoteListData> getFindVoteListDatas(List<Tuple> findVotesOrderByPopularTuples, Map<Long, VoteContent> voteContentsMap) {
        return findVotesOrderByPopularTuples.stream()
                .map(findVoteTuple -> {
                    Vote vote = findVoteTuple.get(0, Vote.class);
                    VoteContent voteContent = voteContentsMap.get(vote.getId());
                    return FindVoteListData.builder()
                            .voteId(vote.getId())
                            .postedUserId(vote.getPostedUserId())
                            .title(vote.getTitle())
                            .detail(vote.getDetail())
                            .filteredGender(vote.getFilteredGender())
                            .filteredAge(vote.getFilteredAge())
                            .voteContent(voteContent)
                            .voteNum(findVoteTuple.get(1,Long.class))
                            .build();
                }).collect(Collectors.toList());
    }

    private long getTotalCount() {
        return jpaQueryFactory
                .from(vote)
                .innerJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .groupBy(vote.id)
                .fetchCount();
    }
}
