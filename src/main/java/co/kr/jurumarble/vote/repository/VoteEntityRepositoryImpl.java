package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.dto.FindVoteListData;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    private static final int FIRST_INDEX_OF_VOTE_CONTENT = 0;
    private static final int SECOND_INDEX_OF_VOTE_CONTENT = 1;

    private final JPAQueryFactory jpaQueryFactory;

    public VoteEntityRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public List<FindVoteListData> findWithVoteResult(PageRequest pageRequest) {
        List<Tuple> findVotesOrderByPopularTuples = getVotesOrderByPopularTuples();

        List<Long> voteIds = getVoteIdsFromFindVotes(findVotesOrderByPopularTuples);

        List<VoteContent> voteContents = findVoteContentsByVoteIds(voteIds); //하나의 vote에서 두개의 voteContent 찾아야 함

        Map<Long, List<VoteContent>> voteContentsMap = voteContents.stream() // 하나의 voteId당 두개의 voteContent를 가지는 Map이 생김
                .collect(Collectors.groupingBy(VoteContent::getVoteId)); // ex) <1, {voteContentA, voteContentB}>

        return findVotesOrderByPopularTuples.stream()
                .map(findVoteTuple -> {
                    Vote vote = findVoteTuple.get(0, Vote.class);
                    VoteContent voteContentA = voteContentsMap.get(vote.getId()).get(FIRST_INDEX_OF_VOTE_CONTENT);
                    VoteContent voteContentB = voteContentsMap.get(vote.getId()).get(SECOND_INDEX_OF_VOTE_CONTENT);
                    return FindVoteListData.builder()
                            .voteId(vote.getId())
                            .postedUserId(vote.getPostedUserId())
                            .title(vote.getTitle())
                            .detail(vote.getDetail())
                            .filteredGender(vote.getFilteredGender())
                            .filteredAge(vote.getFilteredAge())
                            .voteContentA(voteContentA)
                            .voteContentB(voteContentB)
                            .build();
                }).collect(Collectors.toList());
    }


    private List<Tuple> getVotesOrderByPopularTuples() {
        return jpaQueryFactory
                .select(vote, voteResult.id.count())
                .from(vote)
                .innerJoin(voteResult).on(vote.id.eq(voteResult.voteId))
                .groupBy(vote.id)
                .orderBy(voteResult.id.count().desc())
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
}
