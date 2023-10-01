package co.kr.jurumarble.vote.enums;

import co.kr.jurumarble.common.enums.EnumModel;
import co.kr.jurumarble.utils.SpringContext;
import co.kr.jurumarble.vote.domain.DrinkVoteAction;
import co.kr.jurumarble.vote.domain.NormalVoteAction;
import co.kr.jurumarble.vote.domain.VoteAction;
import co.kr.jurumarble.vote.dto.request.VoteWithPostedUserData;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;

public enum VoteType implements EnumModel {
    NORMAL("일반투표", new NormalVoteAction(SpringContext.getBean(VoteContentRepository.class))),
    DRINK("전통주투표", new DrinkVoteAction(SpringContext.getBean(VoteDrinkContentRepository.class)));

    private final String value;
    private final VoteAction action;

    VoteType(String value, VoteAction action) {
        this.value = value;
        this.action = action;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    public VoteWithPostedUserData execute(Long voteId, VoteWithPostedUserCommonData voteCommonData) {
        return action.getVoteWithPostedUserData(voteId, voteCommonData);
    }
}
