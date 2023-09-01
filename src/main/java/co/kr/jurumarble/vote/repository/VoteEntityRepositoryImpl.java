package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.kr.jurumarble.user.domain.QUser.user;
import static co.kr.jurumarble.vote.domain.QVote.vote;
import static co.kr.jurumarble.vote.domain.QVoteContent.voteContent;
import static co.kr.jurumarble.vote.domain.QVoteDrinkContent.voteDrinkContent;
import static co.kr.jurumarble.vote.domain.QVoteResult.voteResult;


@Repository
public class VoteEntityRepositoryImpl implements VoteEntityRepository {

    private static final int COUNT_OF_HOT_DRINK_VOTE = 1;
    private final JPAQueryFactory jpaQueryFactory;

    public VoteEntityRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Slice<VoteData> findVoteDataWithPopularity(String keyword, Pageable pageable) {

        List<Tuple> findVotesOrderByPopularTuples = getVotesTupleOrderByPopular(keyword, pageable);
        List<Long> voteIds = getVoteIdsFromFindVotes(findVotesOrderByPopularTuples);
        List<VoteContent> voteContents = findVoteContentsByVoteIds(voteIds); //각 vote에서 voteContent 찾아야 함
        Map<Long, VoteContent> voteContentsMap = voteContents.stream()  // List를 순회하면 성능이 안나오므로 <voteId, VoteConent> 로 이루어진 Map을 만듬
                .collect(Collectors.toMap(VoteContent::getVoteId, voteContent -> voteContent));// ex) <1, {voteContent}>
        List<VoteData> voteData = getFindVoteListDatas(findVotesOrderByPopularTuples, voteContentsMap);

        return getSlice(voteData, pageable.getPageSize(), pageable.getPageSize(), pageable);
    }

    private SliceImpl<VoteData> getSlice(List<VoteData> voteData, int pageable, int pageable1, Pageable pageable2) {
        boolean hasNext = false;

        if (voteData.size() > pageable) {
            hasNext = true;
            voteData = voteData.subList(0, pageable1); // 조회된 결과에서 실제 페이지의 데이터만 가져옴
        }

        return new SliceImpl<>(voteData, pageable2, hasNext);
    }

    private List<Tuple> getVotesTupleOrderByPopular(String keyword, Pageable pageable) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        BooleanExpression keywordExpression = getKeywordExpression(keyword);

        return jpaQueryFactory
                .select(vote, voteResult.id.count())
                .from(vote)
                .leftJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .where(vote.voteType.eq(VoteType.NORMAL).and(keywordExpression))
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
                            .votedCount(findVoteTuple.get(1, Long.class))
                            .build();
                }).collect(Collectors.toList());
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
    public Slice<VoteData> findVoteDataWithTime(String keyword, Pageable pageable) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        BooleanExpression keywordExpression = getKeywordExpression(keyword);

        List<VoteData> voteData = jpaQueryFactory.select(
                        Projections.bean(VoteData.class,
                                vote.id,
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti
                        ))
                .from(vote)
                .where(keywordExpression)
                .orderBy(vote.createdDate.desc())
                .offset(pageNo * pageSize)
                .limit(pageSize)
                .fetch();

        return getSlice(voteData, pageSize, pageSize, pageable);
    }

    private BooleanExpression getKeywordExpression(String keyword) {
        return keyword != null
                ? vote.title.like(keyword + "%")
                : null;
    }


    @Override
    public List<Vote> findByTitleContains(String keyword) {
        return jpaQueryFactory
                .selectFrom(vote)
                .innerJoin(voteContent)
                .on(vote.id.eq(voteContent.voteId))
                .innerJoin(voteResult)
                .on(vote.id.eq(voteResult.voteId))
                .where(vote.title.like(keyword + "%"))
                .groupBy(vote.id)
                .orderBy(voteResult.id.count().desc())
                .limit(5)
                .fetch();
    }

    @Override
    public Long countByVoteAndChoiceAndGenderAndAgeAndMBTI(Long voteId, ChoiceType choiceType, GenderType gender, Integer age, MbtiType mbti) {

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(voteResult.choice.eq(choiceType)); // 항상 포함되는 조건
        whereClause.and(vote.id.eq(voteId)); // 항상 포함되는 조건

        if (gender != null) {
            whereClause.and(user.gender.eq(gender));
        }
        if (age != null) {
            whereClause.and(user.age.eq(age));
        }
        if (mbti != null) {
            whereClause.and(user.mbti.eq(mbti));
        }

        return jpaQueryFactory
                .selectFrom(vote)
                .innerJoin(voteResult)
                .on(vote.id.eq(voteResult.voteId))
                .innerJoin(user)
                .on(vote.postedUserId.eq(user.id))
                .where(whereClause)
                .fetchCount();
    }

    @Override
    public Optional<HotDrinkVoteData> getHotDrinkVote(LocalDateTime nowTime) {

        LocalDateTime descendingHourTime = nowTime.withMinute(0);
        LocalDateTime aWeekAgoTime = descendingHourTime.minus(7, ChronoUnit.DAYS);

        HotDrinkVoteData hotDrinkVoteData = jpaQueryFactory.select(
                        Projections.bean(HotDrinkVoteData.class,
                                vote.id.as("voteId"),
                                vote.title.as("voteTitle"),
                                voteDrinkContent.drinkAImage,
                                voteDrinkContent.drinkBImage
                        ))
                .from(vote)
                .innerJoin(voteDrinkContent)
                .on(vote.id.eq(voteDrinkContent.voteId))
                .innerJoin(voteResult)
                .on(vote.id.eq(voteResult.voteId))
                .where(
                        vote.voteType.eq(VoteType.DRINK)
                                .and(vote.createdDate.goe(aWeekAgoTime))
                                .and(vote.createdDate.loe(descendingHourTime))
                )
                .groupBy(vote.id)
                .orderBy(vote.id.count().desc(), vote.title.asc())
                .limit(COUNT_OF_HOT_DRINK_VOTE)
                .fetchOne();

        return Optional.ofNullable(hotDrinkVoteData);
    }

    @Override
    public HotDrinkVoteData findOneDrinkVoteByPopular() {
        return jpaQueryFactory.select(Projections.bean(HotDrinkVoteData.class,
                        vote.id.as("voteId"),
                        vote.title.as("voteTitle"),
                        voteDrinkContent.drinkAImage,
                        voteDrinkContent.drinkBImage
                ))
                .from(vote)
                .innerJoin(voteDrinkContent)
                .on(vote.id.eq(voteDrinkContent.voteId))
                .groupBy(vote.id)
                .orderBy(vote.id.count().desc(), vote.title.asc())
                .limit(COUNT_OF_HOT_DRINK_VOTE)
                .fetchOne();
    }

}
