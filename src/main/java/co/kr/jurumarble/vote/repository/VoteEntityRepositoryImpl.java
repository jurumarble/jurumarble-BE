package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.NormalVoteData;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Slice<NormalVoteData> findNormalVoteDataWithPopularity(String keyword, Pageable pageable) {

        List<Tuple> findVotesOrderByPopularTuples = getVotesTupleOrderByPopular(keyword, pageable);

        List<Long> voteIds = getVoteIdsFromFindVotes(findVotesOrderByPopularTuples);

        List<VoteContent> voteContents = findVoteContentsByVoteIds(voteIds); //각 vote에서 voteContent 찾아야 함

        Map<Long, VoteContent> voteContentsMap = voteContents.stream()  // List를 순회하면 성능이 안나오므로 <voteId, VoteConent> 로 이루어진 Map을 만듬
                .collect(Collectors.toMap(VoteContent::getVoteId, voteContent -> voteContent));// ex) <1, {voteContent}>

        List<NormalVoteData> normalVoteData = getFindVoteListDatas(findVotesOrderByPopularTuples, voteContentsMap);

        boolean hasNext = false;
        if (normalVoteData.size() > pageable.getPageSize()) {
            hasNext = true;
            normalVoteData = normalVoteData.subList(0, pageable.getPageSize()); // 조회된 결과에서 실제 페이지의 데이터만 가져옴
        }

        return new SliceImpl<>(normalVoteData, pageable, hasNext);
    }

    private List<Tuple> getVotesTupleOrderByPopular(String keyword, Pageable pageable) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        BooleanExpression keywordExpression = getKeywordExpression(keyword);

        return jpaQueryFactory
                .select(vote, voteResult.id.count())
                .from(vote)
                .leftJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .where(keywordExpression)
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

    private List<NormalVoteData> getFindVoteListDatas(List<Tuple> findVotesOrderByPopularTuples, Map<Long, VoteContent> voteContentsMap) {
        return findVotesOrderByPopularTuples.stream()
                .map(findVoteTuple -> {
                    Vote vote = findVoteTuple.get(0, Vote.class);
                    VoteContent voteContent = voteContentsMap.get(vote.getId());
                    return NormalVoteData.builder()
                            .voteId(vote.getId())
                            .postedUserId(vote.getPostedUserId())
                            .title(vote.getTitle())
                            .detail(vote.getDetail())
                            .filteredGender(vote.getFilteredGender())
                            .filteredAge(vote.getFilteredAge())
                            .imageA(voteContent.getImageA())
                            .imageB(voteContent.getImageB())
                            .titleA(voteContent.getTitleA())
                            .titleB(voteContent.getTitleB())
                            .votedCount(findVoteTuple.get(1,Long.class))
                            .build();
                }).collect(Collectors.toList());
    }

    private long getVoteTotalCount() {
        return jpaQueryFactory
                .from(vote)
                .innerJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .groupBy(vote.id)
                .fetchCount();
    }


    @Override
    public Optional<NormalVoteData> findNormalVoteDataByVoteId(Long voteId) {
        NormalVoteData normalVoteData = jpaQueryFactory.select(
                Projections.bean(NormalVoteData.class,
                        vote.id,
                        vote.postedUserId,
                        vote.title,
                        vote.detail,
                        vote.filteredGender,
                        vote.filteredAge,
                        vote.filteredMbti,
                        voteContent.imageA,
                        voteContent.imageB,
                        voteContent.titleA,
                        voteContent.titleB
                ))
                .from(vote)
                .innerJoin(voteContent)
                .on(vote.id.eq(voteContent.voteId))
                .where(vote.id.eq(voteId))
                .fetchOne();
        return Optional.ofNullable(normalVoteData);
    }

    @Override
    public Slice<NormalVoteData> findNormalVoteDataWithTime(String keyword, Pageable pageable) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        BooleanExpression keywordExpression = getKeywordExpression(keyword);

        List<NormalVoteData> normalVoteData = jpaQueryFactory.select(
                        Projections.bean(NormalVoteData.class,
                                vote.id,
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                voteContent.imageA,
                                voteContent.imageB,
                                voteContent.titleA,
                                voteContent.titleB
                        ))
                .from(vote)
                .innerJoin(voteContent)
                .on(vote.id.eq(voteContent.voteId))
                .where(keywordExpression)
                .orderBy(vote.createdDate.desc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();

        boolean hasNext = false;
        if (normalVoteData.size() > pageSize) {
            hasNext = true;
            normalVoteData = normalVoteData.subList(0, pageSize); // 조회된 결과에서 실제 페이지의 데이터만 가져옴
        }

        return new SliceImpl<>(normalVoteData, pageable, hasNext);
    }

    private BooleanExpression getKeywordExpression(String keyword) {
        return keyword != null
                ? vote.title.like(keyword + "%")
                : null;
    }


    @Override
    public List<Vote> findByTitleContains(String keyword) {
        return jpaQueryFactory
                .select(vote)
                .from(vote)
                .innerJoin(voteContent)
                .on(vote.id.eq(voteContent.voteId))
                .innerJoin(voteResult)
                .on(vote.id.eq(voteResult.voteId))
                .where(vote.title.like(keyword + "%"))
                .groupBy(vote.id)
                .orderBy(voteResult.id.count().desc())
                .fetch();
    }

}
