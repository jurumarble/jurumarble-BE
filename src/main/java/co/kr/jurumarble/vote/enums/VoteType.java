package co.kr.jurumarble.vote.enums;

import co.kr.jurumarble.common.enums.EnumModel;
import co.kr.jurumarble.vote.domain.DrinkVoteAction;
import co.kr.jurumarble.vote.domain.NormalVoteAction;
import co.kr.jurumarble.vote.domain.VoteAction;
import co.kr.jurumarble.vote.dto.request.VoteWithPostedUserData;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import org.springframework.context.ApplicationContext;

public enum VoteType implements EnumModel {
    NORMAL("일반투표", NormalVoteAction.class),
    DRINK("전통주투표", DrinkVoteAction.class);


    private final String value;
    private final Class<? extends VoteAction> actionClass;

    VoteType(String value, Class<? extends VoteAction> actionClass) {
        this.value = value;
        this.actionClass = actionClass;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    public VoteWithPostedUserData execute(Long voteId,
                                          VoteWithPostedUserCommonData voteCommonData,
                                          ApplicationContext context) {
        VoteAction action = context.getBean(actionClass);
        return action.getVoteWithPostedUserData(voteId, voteCommonData);
    }
}
