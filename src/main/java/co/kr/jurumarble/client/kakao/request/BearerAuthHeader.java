package co.kr.jurumarble.client.kakao.request;

import lombok.Data;

@Data
public class BearerAuthHeader {
    private String authorization;

    public BearerAuthHeader(String token) {
        this.authorization = "Bearer " + token;
    }
}
