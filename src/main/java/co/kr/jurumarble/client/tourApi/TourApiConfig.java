package co.kr.jurumarble.client.tourApi;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients // Spring Boot 애플리케이션에서 Feign 클라이언트를 사용하도록 활성화
public class TourApiConfig {
}
