package co.kr.jurumarble;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbHealthCheck {

    @GetMapping("/")
    public String check() {
        return "<h1>idle-backend 스웨거 주소: http://jurumarble-eb-env.eba-psdd54jg.ap-northeast-2.elasticbeanstalk.com/swagger-ui/index.html </h1>";
    }
}