package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.user.dto.request.GetUserNickNameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NicknameGenerator {

    /**
     * 외부 api 호출해서 랜덤닉네임을 생성
     * @return GetUserNickNameRequest
     */
    public GetUserNickNameRequest generateRandomNickName() {
        URI uri = ofUri();
        ResponseEntity<GetUserNickNameRequest> result = requestNickName(uri);
        return result.getBody();
    }

    private static ResponseEntity<GetUserNickNameRequest> requestNickName(URI uri) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GetUserNickNameRequest> result = restTemplate.getForEntity(uri, GetUserNickNameRequest.class);
        return result;
    }

    private static URI ofUri() {
        return UriComponentsBuilder
                .fromUriString("https://nickname.hwanmoo.kr/")
                .queryParam("format", "json")
                .queryParam("count", 1)
                .queryParam("max_length", 8)
                .encode()
                .build()
                .toUri();
    }
}
