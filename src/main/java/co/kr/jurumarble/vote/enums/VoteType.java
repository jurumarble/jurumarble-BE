package co.kr.jurumarble.vote.enums;

import co.kr.jurumarble.common.enums.EnumModel;
import co.kr.jurumarble.vote.domain.DrinkVoteAction;
import co.kr.jurumarble.vote.domain.NormalVoteAction;
import co.kr.jurumarble.vote.domain.VoteAction;

public enum VoteType implements EnumModel {
    NORMAL("일반투표", new NormalVoteAction()),
    DRINK("전통주투표", new DrinkVoteAction());

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

    public void execute() {
        action.execute();
    }
}
