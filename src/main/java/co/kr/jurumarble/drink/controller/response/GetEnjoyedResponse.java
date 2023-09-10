package co.kr.jurumarble.drink.controller.response;

import lombok.Getter;

@Getter
public class GetEnjoyedResponse {

    private boolean enjoyed;

    public GetEnjoyedResponse(boolean enjoyed) {
        this.enjoyed = enjoyed;
    }
}
