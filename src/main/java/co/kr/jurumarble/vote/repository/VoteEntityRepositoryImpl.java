package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static co.kr.jurumarble.bookmark.domain.QBookmark.bookmark;
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
    public List<VoteCommonData> findVoteCommonDataByPopularity(String keyword, Pageable pageable) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        BooleanExpression keywordExpression = getKeywordExpression(keyword);

        return jpaQueryFactory
                .select(
                        Projections.bean(
                                VoteCommonData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                voteResult.id.count().as("votedCount"),
                                vote.voteType,
                                vote.createdDate.as("createdAt")
                        )
                )
                .from(vote)
                .leftJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .where(keywordExpression)
                .groupBy(vote.id)
                .orderBy(voteResult.id.count().desc())
                .offset(pageNo * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }


    @Override
    public List<VoteContent> findVoteContentsByNormalVoteIds(List<Long> normalVoteIds) {
        return jpaQueryFactory
                .selectFrom(voteContent)
                .where(voteContent.voteId.in(normalVoteIds))
                .fetch();
    }

    @Override
    public List<VoteDrinkContent> findVoteContentsByDrinkVoteIds(List<Long> drinkVoteIds) {
        return jpaQueryFactory
                .selectFrom(voteDrinkContent)
                .where(voteDrinkContent.voteId.in(drinkVoteIds))
                .fetch();
    }


    @Override
    public List<VoteCommonData> findVoteCommonDataByTime(String keyword, Pageable pageable) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        BooleanExpression keywordExpression = getKeywordExpression(keyword);

        return jpaQueryFactory.select(
                        Projections.bean(VoteCommonData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                voteResult.count().as("voteCount"),
                                vote.voteType,
                                vote.createdDate.as("createdAt")
                        )
                )
                .from(vote)
                .leftJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .where(keywordExpression)
                .groupBy(vote.id)
                .orderBy(vote.createdDate.desc())
                .offset(pageNo * pageSize)
                .limit(pageSize + 1)
                .fetch();

    }

    @Override
    public List<VoteData> findDrinkVotesByTime(String keyword, String region, int pageNum, int pageSize) {

        BooleanBuilder searchConditions = new BooleanBuilder();
        setSearchConditions(keyword, region, searchConditions);

        return jpaQueryFactory.select(
                        Projections.bean(VoteData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                vote.voteType,
                                voteDrinkContent.drinkAImage.as("imageA"),
                                voteDrinkContent.drinkBImage.as("imageB"),
                                voteDrinkContent.drinkAName.as("titleA"),
                                voteDrinkContent.drinkBName.as("titleB"),
                                voteDrinkContent.region
                        ))
                .from(vote)
                .innerJoin(voteDrinkContent)
                .on(voteDrinkContent.voteId.eq(vote.id))
                .where(
                        vote.voteType.eq(VoteType.DRINK)
                                .and(searchConditions)
                )
                .groupBy(vote.id)
                .orderBy(vote.createdDate.desc())
                .offset(pageNum * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    @Override
    public List<VoteData> findDrinkVotesByPopularity(String keyword, String region, int pageNum, int pageSize) {
        BooleanBuilder searchConditions = new BooleanBuilder();
        setSearchConditions(keyword, region, searchConditions);

        return jpaQueryFactory.select(
                        Projections.bean(VoteData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                vote.id.count().as("votedCount"),
                                vote.voteType,
                                voteDrinkContent.drinkAImage.as("imageA"),
                                voteDrinkContent.drinkBImage.as("imageB"),
                                voteDrinkContent.drinkAName.as("titleA"),
                                voteDrinkContent.drinkBName.as("titleB"),
                                voteDrinkContent.region
                        ))
                .from(vote)
                .innerJoin(voteDrinkContent)
                .on(voteDrinkContent.voteId.eq(vote.id))
                .leftJoin(voteResult)
                .on(voteResult.voteId.eq(vote.id))
                .where(
                        vote.voteType.eq(VoteType.DRINK)
                                .and(searchConditions)
                )
                .groupBy(vote.id)
                .orderBy(vote.id.count().desc())
                .offset(pageNum * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    private void setSearchConditions(String keyword, String region, BooleanBuilder builder) {
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(vote.title.like(keyword + "%"));
        }

        if (region != null && !region.isEmpty()) {
            builder.and(voteDrinkContent.region.eq(region));
        }
    }

    private BooleanExpression getKeywordExpression(String keyword) {
        return keyword != null
                ? vote.title.like(keyword + "%")
                : null;
    }


    @Override
    public List<Vote> findByTitleContains(String keyword, int recommendCount) {
        return jpaQueryFactory
                .selectFrom(vote)
                .innerJoin(voteContent)
                .on(vote.id.eq(voteContent.voteId))
                .innerJoin(voteResult)
                .on(vote.id.eq(voteResult.voteId))
                .where(vote.title.like(keyword + "%"))
                .groupBy(vote.id)
                .orderBy(voteResult.id.count().desc())
                .limit(recommendCount)
                .fetch();
    }

    @Override
    public Long countByVoteAndChoiceAndGenderAndAgeAndMBTI(Long voteId, ChoiceType choiceType, GenderType gender, Integer age, MbtiType mbti, AlcoholLimitType alcoholLimit) {

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(voteResult.choice.eq(choiceType));
        whereClause.and(vote.id.eq(voteId));

        if (gender != null) {
            whereClause.and(user.gender.eq(gender));
        }
        if (age != null) {
            LocalDate localDate = LocalDate.now();
            Integer startYear = localDate.getYear() - (age + 8);
            whereClause.and(user.yearOfBirth.between(startYear, startYear + 9));
        }
        if (mbti != null) {
            whereClause.and(user.mbti.eq(mbti));
        }
        if (alcoholLimit != null) {
            whereClause.and(user.alcoholLimit.eq(alcoholLimit));
        }

        return jpaQueryFactory
                .selectFrom(vote)
                .innerJoin(voteResult)
                .on(vote.id.eq(voteResult.voteId))
                .innerJoin(user)
                .on(voteResult.votedUserId.eq(user.id))
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

    @Override
    public List<VoteCommonData> findVoteCommonDataByParticipate(Long userId, int pageNum, int pageSize) {
        return jpaQueryFactory.select(
                        Projections.bean(VoteCommonData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                vote.voteType
                        ))
                .from(vote)
                .innerJoin(voteResult)
                .on(voteResult.voteId.eq(vote.id))
                .where(voteResult.votedUserId.eq(userId))
                .orderBy(vote.createdDate.desc())
                .offset(pageNum * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    @Override
    public List<VoteCommonData> findVoteCommonDataByPostedUserId(Long userId, int pageNum, int pageSize) {
        return jpaQueryFactory.select(
                        Projections.bean(VoteCommonData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                voteResult.id.count().as("votedCount"),
                                vote.voteType,
                                vote.createdDate.as("createdAt")
                        ))
                .from(vote)
                .leftJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .groupBy(vote.id)
                .where(vote.postedUserId.eq(userId))
                .orderBy(vote.createdDate.desc())
                .offset(pageNum * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    @Override
    public List<VoteCommonData> findCommonVoteDataByBookmark(Long userId, int pageNum, int pageSize) {
        return jpaQueryFactory.select(
                        Projections.bean(VoteCommonData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                vote.voteType
                        ))
                .from(vote)
                .innerJoin(bookmark)
                .on(bookmark.voteId.eq(vote.id))
                .where(bookmark.userId.eq(userId))
                .orderBy(vote.createdDate.desc())
                .offset(pageNum * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    @Override
    public Long findMyParticipatedVoteCnt(Long userId) {
        return jpaQueryFactory.select(vote.count())
                .from(vote)
                .innerJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .where(voteResult.votedUserId.eq(userId))
                .fetchOne();
    }

    @Override
    public Long findMyWrittenVoteCnt(Long userId) {
        return jpaQueryFactory.select(vote.count())
                .from(vote)
                .where(vote.postedUserId.eq(userId))
                .fetchOne();
    }

    @Override
    public Long findMyBookmarkedVoteCnt(Long userId) {
        return jpaQueryFactory.select(vote.count())
                .from(vote)
                .innerJoin(bookmark)
                .on(bookmark.voteId.eq(vote.id))
                .where(bookmark.userId.eq(userId))
                .fetchOne();
    }

    @Override
    public Optional<VoteWithPostedUserCommonData> findVoteCommonDataByVoteId(Long voteId) {
        VoteWithPostedUserCommonData voteCommonData = jpaQueryFactory
                .select(
                        Projections.bean(
                                VoteWithPostedUserCommonData.class,
                                vote.id.as("voteId"),
                                vote.postedUserId,
                                vote.title,
                                vote.detail,
                                vote.filteredGender,
                                vote.filteredAge,
                                vote.filteredMbti,
                                voteResult.id.count().as("votedCount"),
                                vote.voteType,
                                vote.createdDate.as("createdAt"),
                                user.gender.as("postedUserGender"),
                                user.yearOfBirth.as("postedUserYearOfBirth"),
                                user.mbti.as("postedUserMbti"),
                                user.alcoholLimit.as("postedUserAlcoholLimit"),
                                user.nickname.as("postedUserNickname"),
                                user.imageUrl.as("postedUserImageUrl")
                        )
                )
                .from(vote)
                .leftJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .leftJoin(user).on(vote.postedUserId.eq(user.id))
                .where(vote.id.eq(voteId))
                .groupBy(vote.id)
                .fetchOne();
        return Optional.ofNullable(voteCommonData);
    }
}
