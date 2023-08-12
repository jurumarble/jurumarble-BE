package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // Deserialization 과정 중에 알 수 없는 속성이 포함된 경우 에러를 발생시키지 않도록 설정
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT); //JSON 문자열이 빈 문자열("")일 경우 해당 객체를 null 값으로 처리하도록 설정
    }
}
