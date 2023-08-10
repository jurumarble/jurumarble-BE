package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.VoteData;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public Page<VoteData> findVoteDataWithPopularity(PageRequest pageRequest) {
        int pageNo = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        List<Tuple> findVotesOrderByPopularTuples = getVotesTupleOrderByPopular(pageNo, pageSize);

        List<Long> voteIds = getVoteIdsFromFindVotes(findVotesOrderByPopularTuples);

        List<VoteContent> voteContents = findVoteContentsByVoteIds(voteIds); //하나의 vote에서 두개의 voteContent 찾아야 함

        Map<Long, VoteContent> voteContentsMap = voteContents.stream()  // List를 순회하면 성능이 안나오므로 <voteId, VoteConent> 로 이루어진 Map을 만듬
                .collect(Collectors.toMap(VoteContent::getVoteId, voteContent -> voteContent));// ex) <1, {voteContent}>

        List<VoteData> voteData = getFindVoteListDatas(findVotesOrderByPopularTuples, voteContentsMap);

        long totalCount = getVoteTotalCount();

        return new PageImpl<>(voteData, pageRequest, totalCount);
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

    private List<VoteData> getFindVoteListDatas(List<Tuple> findVotesOrderByPopularTuples, Map<Long, VoteContent> voteContentsMap) {
        return findVotesOrderByPopularTuples.stream()
                .map(findVoteTuple -> {
                    Vote vote = findVoteTuple.get(0, Vote.class);
                    VoteContent voteContent = voteContentsMap.get(vote.getId());
                    return VoteData.builder()
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
    public Optional<VoteData> findVoteDataByVoteId(Long voteId) {
        VoteData voteData = jpaQueryFactory.select(
                Projections.bean(VoteData.class,
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
        return Optional.ofNullable(voteData);
    }

    @Override
    public Page<VoteData> findVoteDataWithTime(PageRequest pageRequest) {
        int pageNo = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        List<VoteData> voteData = jpaQueryFactory.select(
                        Projections.bean(VoteData.class,
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
                .orderBy(vote.createdDate.desc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();

        long totalCount = getVoteTotalCount();
        return new PageImpl<>(voteData, pageRequest, totalCount);
    }
}
