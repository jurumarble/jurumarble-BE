package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.user.dto.request.GetUserNickNameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
public class NicknameGenerator {

    /**
     * 외부 api 호출해서 랜덤닉네임을 생성
     * @return GetUserNickNameRequest
     */
    public String generateRandomNickName() {
        URI uri = ofUri();
        RestTemplate restTemplate = new RestTemplate();
        GetUserNickNameRequest getUserNickNameRequest = restTemplate.getForObject(uri, GetUserNickNameRequest.class);
        return Objects.requireNonNull(getUserNickNameRequest).getWords()[0];

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
